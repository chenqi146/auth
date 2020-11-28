package com.cqmike.baseservices.auth.controller;

import com.cqmike.base.controller.BaseController;
import com.cqmike.base.form.ReturnForm;
import com.cqmike.baseservices.auth.convert.UserConvert;
import com.cqmike.baseservices.auth.dto.UserDto;
import com.cqmike.baseservices.auth.entity.User;
import com.cqmike.baseservices.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;


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

    @PostMapping("/register")
    public User registerUser(@RequestBody UserDto userDto){
        // 记得注册的时候把密码加密一下
        userDto.setPassword(userDto.getPassword());
        User from = userConvert.from(userDto);
        return userService.create(from);
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


        return ReturnForm.ok("null");
    }

}
