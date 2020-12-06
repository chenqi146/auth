package com.cqmike.baseservices.auth.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.AsymmetricAlgorithm;
import cn.hutool.crypto.asymmetric.AsymmetricCrypto;
import com.cqmike.base.controller.BaseController;
import com.cqmike.base.exception.BusinessException;
import com.cqmike.base.form.ReturnForm;
import com.cqmike.base.util.JsonUtils;
import com.cqmike.base.util.RedisClient;
import com.cqmike.baseservices.auth.constant.Cons;
import com.cqmike.baseservices.auth.convert.UserConvert;
import com.cqmike.baseservices.auth.dto.JwtUser;
import com.cqmike.baseservices.auth.dto.UserDto;
import com.cqmike.baseservices.auth.entity.Menu;
import com.cqmike.baseservices.auth.entity.Role;
import com.cqmike.baseservices.auth.entity.User;
import com.cqmike.baseservices.auth.enums.ActiveEnum;
import com.cqmike.baseservices.auth.security.JwtUtil;
import com.cqmike.baseservices.auth.service.UserService;
import io.jsonwebtoken.lang.Maps;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Map;
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
    private final RedisClient redisClient;

    private final PasswordEncoder passwordEncoder;

    private final static String TOKEN_HEADER = "Authorization";

    @PostMapping("/register")
    public ReturnForm<User> registerUser(@RequestBody UserDto userDto) {
        // 记得注册的时候把密码加密一下
        String username = userDto.getUsername();
        User user = userService.findUserByUsername(username).orElse(null);
        if (user != null) {
            throw new BusinessException("用户名已存在");
        }
        String encode = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encode);
        User from = userConvert.from(userDto);

        return ReturnForm.ok(userService.create(from));
    }

    /**
     * 登录接口
     *
     * @param username 登录名
     * @param password 密码 前端须md5加密
     * @return
     */
    @PostMapping("/login")
    public ReturnForm<Map<String, String>> login(@NotEmpty(message = "登录名不能为空") String username,
                                                 @NotEmpty(message = "密码不能为空") String password) {

        UserDetails userDetails = userService.loadUserByUsername(username);

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BusinessException("用户名或密码不存在");
        }

        if (!userDetails.isEnabled()) {
            throw new BusinessException("该用户已被禁用");
        }
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
                null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = JwtUtil.createToken(userDetails.getUsername());

        redisClient.setex(TOKEN_HEADER + StrUtil.COLON + token, userDetails, 30 * 60);
        return ReturnForm.ok(Maps.of("token", token).build());
    }

}
