package com.cqmike.baseservices.auth.controller;

import com.cqmike.base.controller.BaseController;
import com.cqmike.base.form.ReturnForm;
import com.cqmike.baseservices.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/delete")
    public ReturnForm<String> delete(String loginName) {


        return ReturnForm.ok();
    }

}
