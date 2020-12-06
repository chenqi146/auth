package com.cqmike.baseservices.auth.security;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.cqmike.base.exception.BusinessException;
import com.cqmike.base.exception.CommonEnum;
import com.cqmike.base.util.RedisClient;
import com.cqmike.baseservices.auth.constant.Cons;
import com.cqmike.baseservices.auth.entity.Resource;
import com.cqmike.baseservices.auth.entity.Role;
import com.cqmike.baseservices.auth.service.DynamicSecurityService;
import com.cqmike.baseservices.auth.service.ResourceService;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
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
    private RedisClient redisClient;

    @Autowired
    private ResourceService resourceService;

    @PostConstruct
    public void loadDataSource() {
        dynamicSecurityService.loadDataSource();
    }


    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        this.loadDataSource();
        //获取当前访问的路径
        FilterInvocation o1 = (FilterInvocation) o;
        // 当前用户所拥有角色对应的资源id List
        // 此用户所有角色的资源idSet

        List<Resource> resourceList = resourceService.findAllFormCache();

        if (CollUtil.isEmpty(resourceList)) {
            return Collections.emptyList();
        }

        List<ConfigAttribute> configAttributes = new ArrayList<>();

        String url = o1.getRequestUrl();
        String path = URLUtil.getPath(url);
        PathMatcher pathMatcher = new AntPathMatcher();

        for (Resource resource : resourceList) {
            String pattern = resource.getUrl();
            if (pathMatcher.match(pattern, path)) {
                configAttributes.add(new org.springframework.security.access.SecurityConfig(resource.getId() + StrUtil.COLON + resource.getName()));
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