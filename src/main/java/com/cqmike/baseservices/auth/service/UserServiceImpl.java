package com.cqmike.baseservices.auth.service;

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
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @Override
    public Optional<User> findUserByUsername(@NotEmpty String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public List<User> findAll(Specification<User> specification, Sort sort) {
        return null;
    }

    @Override
    public Page<User> findAll(Specification<User> specification, Pageable pageable) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public List<User> findAllByIds(Collection<Long> ids) {
        return null;
    }

    @Override
    public Optional<User> fetchById(Long id) {
        return Optional.empty();
    }

    @Override
    public User findById(Long id) {
        return null;
    }

    @Override
    public User findByIdOfNullable(Long id) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public User create(User form) {
        return null;
    }

    @Override
    public List<User> createInBatch(Collection<User> forms) {
        return null;
    }

    @Override
    public User update(User form) {
        return null;
    }

    @Override
    public List<User> updateInBatch(Collection<User> forms) {
        return null;
    }

    @Override
    public User removeById(Long id) {
        return null;
    }

    @Override
    public User removeByIdOfNullable(Long id) {
        return null;
    }

    @Override
    public void remove(User form) {

    }

    @Override
    public void removeInBatch(Collection<Long> ids) {

    }

    @Override
    public void removeAll(Collection<User> forms) {

    }

    @Override
    public void removeAll() {

    }
}
