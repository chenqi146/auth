package com.cqmike.baseservices.auth.service;

import com.cqmike.base.service.CurdService;
import com.cqmike.baseservices.auth.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.validation.constraints.NotEmpty;
import java.util.Optional;

/**
 * @program: baseServices
 * @description: 用户接口
 * @author: chen qi
 * @create: 2020-11-26 08:14
 **/
public interface UserService extends CurdService<User>, UserDetailsService {

    Optional<User> findUserByUsername(@NotEmpty String username);

    /**
     *  从缓存中获取user
     *  没有从db中获取  都没有抛异常
     *  user基础属性
     * @param username
     * @return
     */
    User fetchUserByCache(String username);
}
