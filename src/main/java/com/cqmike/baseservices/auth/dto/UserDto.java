package com.cqmike.baseservices.auth.dto;

import com.cqmike.base.form.BaseForm;
import lombok.Data;

/**
 * @program: baseServices
 * @description: user
 * @author: chen qi
 * @create: 2020-11-28 12:00
 **/
@Data
public class UserDto extends BaseForm {

    private String username;
    private String nickname;
    private String password;
    private String phone;
    private String icon;
    private String email;
}
