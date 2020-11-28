package com.cqmike.baseservices.auth.dto;

import com.cqmike.baseservices.auth.entity.Menu;
import com.cqmike.baseservices.auth.entity.User;
import lombok.Data;
import lombok.ToString;

import java.util.List;

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
    private String password;
    private List<Menu> menuList;

    public JwtUser(User user, List<Menu> menuList) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.menuList = menuList;
    }

}
