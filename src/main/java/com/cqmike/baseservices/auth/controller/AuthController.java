package com.cqmike.baseservices.auth.controller;

import com.cqmike.base.controller.BaseController;
import com.cqmike.baseservices.auth.entity.User;
import com.cqmike.baseservices.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @program: baseServices
 * @description: 登录注册
 * @author: chen qi
 * @create: 2020-11-26 08:04
 **/
@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController extends BaseController {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;

    @PostMapping("/register")
    public User registerUser(@RequestBody Map<String,String> registerUser){
        User user = new User();
        user.setUsername(registerUser.get("username"));
        // 记得注册的时候把密码加密一下
        user.setPassword(bCryptPasswordEncoder.encode(registerUser.get("password")));
        User save = userService.create(user);
        return save;
    }

}
