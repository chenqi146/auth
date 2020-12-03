package com.cqmike.baseservices.auth.security;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.cqmike.base.exception.BusinessException;
import com.cqmike.base.exception.CommonEnum;
import com.cqmike.baseservices.auth.constant.Cons;
import com.cqmike.baseservices.auth.dto.JwtUser;
import com.cqmike.baseservices.auth.entity.Resource;
import com.cqmike.baseservices.auth.entity.Role;
import com.cqmike.baseservices.auth.service.DynamicSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: baseServices
 * @description:
 * @author: chen qi
 * @create: 2020-12-02 20:50
 **/
public class DynamicSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private DynamicSecurityService dynamicSecurityService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @PostConstruct
    public void loadDataSource() {
        dynamicSecurityService.loadDataSource();
    }


    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        this.loadDataSource();
        //获取当前访问的路径
        FilterInvocation o1 = (FilterInvocation) o;
        HttpServletRequest request = o1.getHttpRequest();
        String token = request.getHeader(Cons.TOKEN_HEADER);
        if (StrUtil.isEmpty(token) || !StrUtil.startWith(token, Cons.TOKEN_PREFIX)) {
            throw new BusinessException(CommonEnum.FORBIDDEN);
        }

        if (StrUtil.startWith(token, Cons.TOKEN_PREFIX)) {
            token = StrUtil.removePrefix(token, Cons.TOKEN_PREFIX);
        }
        JwtUser jwtUser = JwtUtil.getClaim(token, Cons.TOKEN_USER, JwtUser.class);
        Set<Role> roles = jwtUser.getRoles();
        // 当前用户所拥有角色对应的资源id List

        Set<String> allRoleResourceIdSet = roles.stream().map(r -> redisTemplate.opsForSet().members(Cons.AUTH
                + StrUtil.COLON + Cons.ROLE_RESOURCE_KEY + StrUtil.COLON + r.getId())).filter(Objects::nonNull)
                .flatMap(s -> s.stream().map(Object::toString)).collect(Collectors.toSet());
//        List<Set<String>> sets = redisTemplate.<String, Set<String>>opsForHash().multiGet(Cons.AUTH
//                + StrUtil.COLON + Cons.ROLE_RESOURCE_KEY, roles.stream().map(r -> r.getId().toString()).collect(Collectors.toList()));
//        // 此用户所有角色的资源idSet
//        Set<String> allRoleResourceIdSet = sets.stream().flatMap(s -> s.stream().map(Object::toString)).collect(Collectors.toSet());

        List<Resource> resources = redisTemplate.<String, Resource>opsForHash()
                .multiGet(Cons.AUTH + StrUtil.COLON + Cons.RESOURCE_KEY, allRoleResourceIdSet);

        List<ConfigAttribute> configAttributes = new ArrayList<>();

        String url = o1.getRequestUrl();
        String path = URLUtil.getPath(url);
        PathMatcher pathMatcher = new AntPathMatcher();

        for (Resource resource : resources) {
            String pattern = resource.getUrl();
            if (pathMatcher.match(pattern, path)) {
                configAttributes.add(new org.springframework.security.access.SecurityConfig(resource.getId() + ":" + resource.getName()));
            }
        }
        // 未设置操作请求权限，返回空集合
        return configAttributes;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

}