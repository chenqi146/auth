package com.cqmike.baseservices.auth.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.cqmike.base.util.JsonUtils;
import com.cqmike.base.util.RedisClient;
import com.cqmike.baseservices.auth.constant.Cons;
import com.cqmike.baseservices.auth.entity.Resource;
import com.cqmike.baseservices.auth.entity.RoleResourceRelation;
import com.cqmike.baseservices.auth.repository.ResourceRepository;
import com.cqmike.baseservices.auth.repository.RoleResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @program: baseServices
 * @description:
 * @author: chen qi
 * @create: 2020-12-02 20:54
 **/
@Service
@RequiredArgsConstructor
public class DynamicSecurityService {

    @Autowired
    private RedisClient redisClient;

    private final ResourceService resourceService;

    public void loadDataSource() {
        boolean hasKey = redisClient.hasKey(Cons.AUTH + StrUtil.COLON + Cons.RESOURCE_KEY);
        if (hasKey == Boolean.TRUE) {
            return;
        }
        resourceService.findAllFormCache();
        resourceService.initRoleResourceCache();

    }
}
