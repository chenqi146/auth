package com.cqmike.baseservices.auth.dto;

import com.cqmike.baseservices.auth.entity.Menu;
import com.cqmike.baseservices.auth.entity.Role;
import com.cqmike.baseservices.auth.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Set;

/**
 * @program: baseServices
 * @description: jwt
 * @author: chen qi
 * @create: 2020-11-25 23:01
 **/
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class JwtUser {

    private Long id;
    private String username;
    private String nickname;
    private String phone;
    private String email;
    private Set<Role> roles;

    public JwtUser(User user, Set<Role> roles) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.username = user.getUsername();
        this.phone = user.getPhone();
        this.email = user.getEmail();
        this.roles = roles;
    }

}
