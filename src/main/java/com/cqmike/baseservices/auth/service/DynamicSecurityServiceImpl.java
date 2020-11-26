package com.cqmike.baseservices.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @program: baseServices
 * @description: 用户权限
 * @author: chen qi
 * @create: 2020-11-26 22:51
 **/
@Service
@RequiredArgsConstructor
public class DynamicSecurityServiceImpl implements DynamicSecurityService {

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public void loadAuthDataSource() {
        // TODO: 2020-11-26 存储刷新redis缓存
    }
}
