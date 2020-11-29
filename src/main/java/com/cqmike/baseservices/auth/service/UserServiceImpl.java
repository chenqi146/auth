package com.cqmike.baseservices.auth.service;

import com.cqmike.base.repository.BaseRepository;
import com.cqmike.base.service.AbstractCurdService;
import com.cqmike.baseservices.auth.entity.User;
import com.cqmike.baseservices.auth.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @program: baseServices
 * @description: 用户接口实现类
 * @author: chen qi
 * @create: 2020-11-26 08:15
 **/
@Service
public class UserServiceImpl extends AbstractCurdService<User> implements UserService {

    @Resource
    private UserRepository userRepository;

    protected UserServiceImpl(BaseRepository<User, Long> repository) {
        super(repository);
    }

    @Override
    public Optional<User> findUserByUsername(@NotEmpty String username) {
        return userRepository.findUserByUsername(username);
    }

}
