package com.cqmike.baseservices.auth.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.cqmike.base.util.JsonUtils;
import com.cqmike.baseservices.auth.constant.Cons;
import com.cqmike.baseservices.auth.entity.Resource;
import com.cqmike.baseservices.auth.entity.RoleResourceRelation;
import com.cqmike.baseservices.auth.repository.ResourceRepository;
import com.cqmike.baseservices.auth.repository.RoleResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
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
    private RedisTemplate<String, Object> redisTemplate;

    private final RoleResourceRepository roleResourceRepository;
    private final ResourceRepository resourceRepository;

    public void loadDataSource() {
//        Boolean hasKey = redisTemplate.hasKey(Cons.AUTH + StrUtil.COLON + Cons.RESOURCE_KEY);
//        if (hasKey == Boolean.TRUE) {
//            return;
//        }

        List<Resource> resourceList = resourceRepository.findAll();
        if (CollUtil.isEmpty(resourceList)) {
            return;
        }
        resourceList.forEach(r -> redisTemplate.opsForHash().put(Cons.AUTH + StrUtil.COLON + Cons.RESOURCE_KEY, r.getId().toString(), r));

        List<RoleResourceRelation> resourceRelations = roleResourceRepository.findAll();
        if (CollUtil.isEmpty(resourceRelations)) {
            return;
        }
        resourceRelations.forEach(r -> redisTemplate.opsForSet().add(Cons.AUTH + StrUtil.COLON + Cons.ROLE_RESOURCE_KEY + StrUtil.COLON + r.getRoleId(), r.getResourceId()));
//        Map<Long, Set<String>> map = resourceRelations.stream().collect(Collectors.groupingBy(RoleResourceRelation::getRoleId,
//                Collectors.mapping(r -> r.getResourceId().toString(), Collectors.toSet())));
//
//        map.forEach((k, v) -> redisTemplate.opsForSet().add(Cons.AUTH + StrUtil.COLON + Cons.ROLE_RESOURCE_KEY + StrUtil.COLON + k, v));

    }
}
