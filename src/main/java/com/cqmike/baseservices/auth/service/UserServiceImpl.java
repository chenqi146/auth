package com.cqmike.baseservices.auth.service;

import com.cqmike.base.service.AbstractCurdService;
import com.cqmike.baseservices.auth.entity.User;
import com.cqmike.baseservices.auth.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import java.util.Optional;

/**
 * @program: baseServices
 * @description: 用户接口实现类
 * @author: chen qi
 * @create: 2020-11-26 08:15
 **/
@Service
public class UserServiceImpl extends AbstractCurdService<User> implements UserService {

    private final UserRepository userRepository;

    protected UserServiceImpl(UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findUserByUsername(@NotEmpty String username) {
        return userRepository.findUserByUsername(username);
    }

}
