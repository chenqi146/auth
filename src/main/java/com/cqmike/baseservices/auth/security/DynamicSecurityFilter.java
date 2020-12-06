package com.cqmike.baseservices.auth.security;

import cn.hutool.core.util.StrUtil;
import com.cqmike.base.auth.Auth;
import com.cqmike.base.exception.BusinessException;
import com.cqmike.base.exception.CommonEnum;
import com.cqmike.base.util.JsonUtils;
import com.cqmike.base.util.RedisClient;
import com.cqmike.baseservices.auth.constant.Cons;
import com.cqmike.baseservices.auth.dto.JwtUser;
import com.cqmike.baseservices.auth.entity.Role;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Multimap;
import com.google.common.collect.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * @program: baseServices
 * @description:   动态权限过滤器，用于实现基于路径的动态权限过滤
 * @author: chen qi
 * @create: 2020-12-02 20:49
 **/
public class DynamicSecurityFilter extends AbstractSecurityInterceptor implements Filter {

    @Autowired
    private DynamicSecurityMetadataSource dynamicSecurityMetadataSource;

    @Autowired
    private IgnoreUrlsConfig ignoreUrlsConfig;

    @Autowired
    private RedisClient redisClient;

    @Autowired
    public void setMyAccessDecisionManager(DynamicAccessDecisionManager dynamicAccessDecisionManager) {
        super.setAccessDecisionManager(dynamicAccessDecisionManager);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        FilterInvocation fi = new FilterInvocation(servletRequest, servletResponse, filterChain);
        //OPTIONS请求直接放行
        if(request.getMethod().equals(HttpMethod.OPTIONS.toString())){
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
            return;
        }
        //白名单请求直接放行
        PathMatcher pathMatcher = new AntPathMatcher();
        for (String path : ignoreUrlsConfig.getUrls()) {
            if(pathMatcher.match(path,request.getRequestURI())){
                fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
                return;
            }
        }

        String token = request.getHeader(Cons.TOKEN_HEADER);
        if (StrUtil.isEmpty(token) || !StrUtil.startWith(token, Cons.TOKEN_PREFIX)) {
            throw new BusinessException(CommonEnum.FORBIDDEN);
        }

        if (StrUtil.startWith(token, Cons.TOKEN_PREFIX)) {
            token = StrUtil.removePrefix(token, Cons.TOKEN_PREFIX);
        }

        String key = Cons.TOKEN_HEADER + StrUtil.COLON + token;
        boolean hasKey = redisClient.hasKey(key);
        if (!hasKey) {
            throw new BusinessException(CommonEnum.SIGNATURE_NOT_MATCH);
        }

        JwtUser user = redisClient.get(key, JwtUser.class);
        // fixme auth空指针
        Auth.put(Auth.USER, user);
        redisClient.expire(key, 30 * 60);

        //此处会调用AccessDecisionManager中的decide方法进行鉴权操作
        InterceptorStatusToken interceptorStatusToken = super.beforeInvocation(fi);
        try {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            super.afterInvocation(interceptorStatusToken, null);
        }
    }


    @Override
    public void destroy() {
    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return dynamicSecurityMetadataSource;
    }

}
