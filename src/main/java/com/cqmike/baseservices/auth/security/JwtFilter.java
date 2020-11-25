package com.cqmike.baseservices.auth.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * jwt过滤器
 *
 * @author chen qi
 * @date 2020-11-13 18:48
 **/
@Slf4j
public class JwtFilter extends GenericFilterBean {

    private final JwtUtil provider;
    private final StringRedisTemplate stringRedisTemplate;

    public static final String TOKEN = "token";

    public JwtFilter(JwtUtil provider, StringRedisTemplate stringRedisTemplate) {
        this.provider = provider;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        final String token = request.getHeader(TOKEN);
        if (token == null || token.isEmpty()) {
            // token 为空
            log.info("非法token: {}", token);
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        // 校验token

        final Authentication authentication = provider.getAuthentication(token);


    }
}
