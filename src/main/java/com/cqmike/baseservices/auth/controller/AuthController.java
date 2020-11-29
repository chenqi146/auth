package com.cqmike.baseservices.auth.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.AsymmetricAlgorithm;
import cn.hutool.crypto.asymmetric.AsymmetricCrypto;
import com.cqmike.base.controller.BaseController;
import com.cqmike.base.exception.BusinessException;
import com.cqmike.base.form.ReturnForm;
import com.cqmike.base.util.JsonUtils;
import com.cqmike.baseservices.auth.constant.Cons;
import com.cqmike.baseservices.auth.convert.UserConvert;
import com.cqmike.baseservices.auth.dto.JwtUser;
import com.cqmike.baseservices.auth.dto.UserDto;
import com.cqmike.baseservices.auth.entity.Menu;
import com.cqmike.baseservices.auth.entity.Role;
import com.cqmike.baseservices.auth.entity.User;
import com.cqmike.baseservices.auth.security.JwtUtil;
import com.cqmike.baseservices.auth.service.MenuService;
import com.cqmike.baseservices.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


/**
 * @program: baseServices
 * @description: 登录注册
 * @author: chen qi
 * @create: 2020-11-26 08:04
 **/
@Validated
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController extends BaseController {

    private final UserService userService;
    private final UserConvert userConvert;
    private final StringRedisTemplate redisTemplate;

    private final static String TOKEN_HEADER = "Authorization";

    @PostMapping("/register")
    public ReturnForm<User> registerUser(@RequestBody UserDto userDto){
        // 记得注册的时候把密码加密一下
        String md5 = SecureUtil.md5(userDto.getUsername() + userDto.getPassword() + Cons.CQMIKE);
        userDto.setPassword(md5);
        return ReturnForm.ok(userService.create(userConvert.from(userDto)));
    }

    /**
     *  登录接口
     * @param username 登录名
     * @param password 密码 前端须md5加密
     * @return
     */
    @PostMapping("/login")
    public ReturnForm<String> login(@NotEmpty(message = "登录名不能为空") String username,
                                    @NotEmpty(message = "密码不能为空") String password) {

        User user = userService.findUserByUsername(username).orElseThrow(() -> new BusinessException("用户名或密码不存在"));

        String md5 = SecureUtil.md5(username + password + Cons.CQMIKE);

        if (!Objects.equals(md5, user.getPassword())) {
            throw new BusinessException("用户名或密码不存在");
        }

        Set<Menu> menus = user.getRoles().stream().flatMap(r -> r.getMenus().stream()).collect(Collectors.toSet());

        JwtUser jwtUser = new JwtUser(user, menus);
        String token = JwtUtil.createToken(jwtUser);
        redisTemplate.opsForValue().set(TOKEN_HEADER + StrUtil.COLON + token, JsonUtils.toJson(jwtUser), 30, TimeUnit.MINUTES);
        return ReturnForm.ok(token);
    }

}
