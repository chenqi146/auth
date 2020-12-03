package com.cqmike.baseservices.auth.controller;

import cn.hutool.crypto.SecureUtil;
import com.cqmike.base.controller.BaseController;
import com.cqmike.base.exception.BusinessException;
import com.cqmike.base.form.ReturnForm;
import com.cqmike.baseservices.auth.constant.Cons;
import com.cqmike.baseservices.auth.convert.UserConvert;
import com.cqmike.baseservices.auth.dto.UserDto;
import com.cqmike.baseservices.auth.entity.User;
import com.cqmike.baseservices.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * @program: baseServices
 * @description: 用户
 * @author: chen qi
 * @create: 2020-11-28 12:15
 **/
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController extends BaseController {

    private final UserService userService;
    private final UserConvert userConvert;

    @DeleteMapping("/delete")
    public ReturnForm<String> delete(String username) {

        userService.findUserByUsername(username).ifPresent(u -> {
            Long id = u.getId();
            userService.removeById(id);
        });

        return ReturnForm.ok();
    }

    @PutMapping
    public ReturnForm<User> update(@RequestBody UserDto userDto) {

        User user = userService.findUserByUsername(userDto.getUsername()).orElseThrow(() -> new BusinessException("没有找到此用户"));
        // 记得注册的时候把密码加密一下
        String md5 = SecureUtil.md5(userDto.getUsername() + userDto.getPassword() + Cons.CQMIKE);
        userDto.setPassword(md5);

        User from = userConvert.from(userDto);
        from.setId(user.getId());
        from.setStatus(user.getStatus());
        from.setLastLoginTime(LocalDateTime.now());
        User result = userService.update(from);
        result.setPassword(null);
        return ReturnForm.ok(result);
    }

    @GetMapping("/{id}")
    public ReturnForm<User> get(@PathVariable("id") Long id) {

        User user = userService.findById(id);
        user.setPassword(null);
        return ReturnForm.ok(user);
    }

}
