package com.cqmike.baseservices.auth.service;

import com.cqmike.base.service.CurdService;
import com.cqmike.baseservices.auth.entity.User;

import javax.validation.constraints.NotEmpty;
import java.util.Optional;

/**
 * @program: baseServices
 * @description: 用户接口
 * @author: chen qi
 * @create: 2020-11-26 08:14
 **/
public interface UserService extends CurdService<User> {

    Optional<User> findUserByUsername(@NotEmpty String username);
}
