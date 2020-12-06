package com.cqmike.baseservices.auth.dto;

import cn.hutool.core.util.StrUtil;
import com.cqmike.baseservices.auth.entity.Menu;
import com.cqmike.baseservices.auth.entity.Resource;
import com.cqmike.baseservices.auth.entity.Role;
import com.cqmike.baseservices.auth.entity.User;
import com.cqmike.baseservices.auth.enums.ActiveEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
public class JwtUser implements UserDetails {

    private Long id;
    private String username;
    private ActiveEnum status;
    private String password;
    private String nickname;
    private String phone;
    private String email;
    private Set<Resource> resources;

    public JwtUser(User user, Set<Resource> resources) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.password = user.getPassword();
        this.username = user.getUsername();
        this.status = user.getStatus();
        this.phone = user.getPhone();
        this.email = user.getEmail();
        this.resources = resources;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return resources.stream()
                .map(role -> new SimpleGrantedAuthority(role.getId() + StrUtil.COLON + role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return status == ActiveEnum.Y;
    }
}
