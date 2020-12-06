package com.cqmike.baseservices.auth.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.cqmike.base.exception.BusinessException;
import com.cqmike.base.repository.BaseRepository;
import com.cqmike.base.service.AbstractCurdService;
import com.cqmike.base.util.JsonUtils;
import com.cqmike.base.util.RedisClient;
import com.cqmike.baseservices.auth.constant.Cons;
import com.cqmike.baseservices.auth.dto.JwtUser;
import com.cqmike.baseservices.auth.entity.Role;
import com.cqmike.baseservices.auth.entity.User;
import com.cqmike.baseservices.auth.entity.UserRoleRelation;
import com.cqmike.baseservices.auth.repository.UserRepository;
import com.cqmike.baseservices.auth.repository.UserRoleRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import java.util.*;

/**
 * @program: baseServices
 * @description: 用户接口实现类
 * @author: chen qi
 * @create: 2020-11-26 08:15
 **/
@Service
public class UserServiceImpl extends AbstractCurdService<User> implements UserService {

    @Resource
    private RedisClient redisClient;
    @Resource
    private UserRepository userRepository;
    @Resource
    private UserRoleRepository userRoleRepository;
    @Resource
    private ResourceService resourceService;

    protected UserServiceImpl(BaseRepository<User, Long> repository) {
        super(repository);
    }

    @Override
    public Optional<User> findUserByUsername(@NotEmpty String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //获取用户信息
        return loadUser(username);
    }

    private JwtUser loadUser(String username) {
        User user = fetchUserByCache(username);

        Set<String> urSet = redisClient.smembers(Cons.AUTH + StrUtil.COLON + Cons.USER_ROLE_KEY, String.class);
        String[] urArray = urSet.stream().map(Object::toString).toArray(String[]::new);
        if (ArrayUtil.isEmpty(urArray)) {
            UserRoleRelation userRoleRelation = new UserRoleRelation();
            userRoleRelation.setUserId(user.getId());
            Example<UserRoleRelation> of = Example.of(userRoleRelation);
            List<UserRoleRelation> all = userRoleRepository.findAll(of);
            if (CollUtil.isNotEmpty(all)) {
                urArray = all.stream().map(Object::toString).toArray(String[]::new);
                redisClient.sadd(Cons.AUTH + StrUtil.COLON + Cons.USER_ROLE_KEY, urArray);
            }
        }

        Map<String, Role> roleMap = redisClient.hmget(Cons.AUTH + StrUtil.COLON + Cons.ROLE_KEY, Role.class, urArray);
        List<com.cqmike.baseservices.auth.entity.Resource> listByRoleList = resourceService.findListByRoleList(roleMap.values());
        return new JwtUser(user, CollUtil.newHashSet(listByRoleList));

    }

    /**
     *  不要带角色
     * @param username
     * @return
     */
    @Override
    public User fetchUserByCache(String username) {
        User user = redisClient.hget(Cons.AUTH + StrUtil.COLON + Cons.USER_KEY, username, User.class);
        if (user != null) {
            return user;
        }

        User dbUser = findUserByUsername(username).orElseThrow(() -> new BusinessException("没有此用户"));
        redisClient.hset(Cons.AUTH + StrUtil.COLON + Cons.USER_KEY, username, dbUser);
        return dbUser;
    }
}
