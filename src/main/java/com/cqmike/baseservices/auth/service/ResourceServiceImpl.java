package com.cqmike.baseservices.auth.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.cqmike.base.repository.BaseRepository;
import com.cqmike.base.service.AbstractCurdService;
import com.cqmike.base.util.RedisClient;
import com.cqmike.baseservices.auth.constant.Cons;
import com.cqmike.baseservices.auth.entity.Resource;
import com.cqmike.baseservices.auth.entity.Role;
import com.cqmike.baseservices.auth.entity.RoleResourceRelation;
import com.cqmike.baseservices.auth.repository.ResourceRepository;
import com.cqmike.baseservices.auth.repository.RoleResourceRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: baseServices
 * @description: 用户接口实现类
 * @author: chen qi
 * @create: 2020-11-26 08:15
 **/
@Service
public class ResourceServiceImpl extends AbstractCurdService<Resource> implements ResourceService {


    private final ResourceRepository resourceRepository;
    private final RoleResourceRepository roleResourceRepository;
    private final RedisClient redisClient;

    protected ResourceServiceImpl(BaseRepository<Resource, Long> repository,
                                  ResourceRepository resourceRepository,
                                  RoleResourceRepository roleResourceRepository,
                                  RedisClient redisClient) {
        super(repository);
        this.resourceRepository = resourceRepository;
        this.roleResourceRepository = roleResourceRepository;
        this.redisClient = redisClient;
    }


    @Override
    public List<Resource> findListByRoleList(Collection<Role> roles) {
        if (CollUtil.isEmpty(roles)) {
            Map<String, Resource> map = redisClient.hgetAll(Cons.AUTH + StrUtil.COLON + Cons.RESOURCE_KEY, Resource.class);

            List<Resource> resourceList = new ArrayList<>(map.values());
            if (CollUtil.isNotEmpty(resourceList)) {
                return resourceList;
            }
            List<Resource> all = resourceRepository.findAll();
            all.forEach(r -> redisClient.hset(Cons.AUTH + StrUtil.COLON + Cons.RESOURCE_KEY, r.getId().toString(), r));
            return all;
        }
        Map<String, Resource> resourceMap = redisClient
                .hmget(Cons.AUTH + StrUtil.COLON + Cons.RESOURCE_KEY,
                        Resource.class, roles.stream().map(r -> redisClient.smembers(Cons.AUTH
                                + StrUtil.COLON + Cons.ROLE_RESOURCE_KEY + StrUtil.COLON + r.getId(), String.class)).filter(Objects::nonNull)
                                .flatMap(s -> s.stream().map(Object::toString)).distinct().toArray(String[]::new));
        if (CollUtil.isEmpty(resourceMap)) {
            return Collections.emptyList();
        }

        return CollUtil.newArrayList(resourceMap.values());
    }

    @Override
    public List<Resource> findAllFormCache() {
        return this.findListByRoleList(null);
    }

    @Override
    public void initRoleResourceCache() {

        List<RoleResourceRelation> resourceRelations = roleResourceRepository.findAll();
        if (CollUtil.isEmpty(resourceRelations)) {
            return;
        }
        resourceRelations.forEach(r -> redisClient.sadd(Cons.AUTH + StrUtil.COLON + Cons.ROLE_RESOURCE_KEY + StrUtil.COLON + r.getRoleId(), r.getResourceId()));
    }
}
