package com.cqmike.baseservices.auth.repository;

import com.cqmike.base.repository.BaseRepository;
import com.cqmike.baseservices.auth.entity.User;

import java.util.Optional;

/**
 * @program: baseServices
 * @description: 用户dao
 * @author: chen qi
 * @create: 2020-11-25 22:50
 **/
public interface UserRepository extends BaseRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
