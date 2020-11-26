package com.cqmike.baseservices.auth.security;

import cn.hutool.core.util.URLUtil;
import com.cqmike.baseservices.auth.service.DynamicSecurityService;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;

/**
 * @program: baseServices
 * @description: 动态权限获取
 * @author: chen qi
 * @create: 2020-11-26 22:48
 **/
public class DynamicSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private DynamicSecurityService dynamicSecurityService;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @PostConstruct
    public void init() {

    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        dynamicSecurityService.loadAuthDataSource();

        //获取当前访问的路径
        FilterInvocation filterInvocation = (FilterInvocation) o;
        String url = filterInvocation.getRequestUrl();
        String path = URLUtil.getPath(url);

        List<ConfigAttribute> configAttributes = Lists.newArrayList();

        PathMatcher pathMatcher = new AntPathMatcher();

        // TODO: 2020-11-26  获取redis中的权限返回
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
