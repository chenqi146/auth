package com.cqmike.baseservices.auth.dto;

import com.cqmike.baseservices.auth.entity.Menu;
import com.cqmike.baseservices.auth.entity.User;
import lombok.Data;
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
public class JwtUser {

    private final Long id;
    private final String username;
    private final String nickname;
    private final String phone;
    private final String email;
    private Set<Menu> menuList;

    public JwtUser(User user, Set<Menu> menuList) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.username = user.getUsername();
        this.phone = user.getPhone();
        this.email = user.getEmail();
        this.menuList = menuList;
    }

}
