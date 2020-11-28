package com.cqmike.baseservices.auth.config;

import com.cqmike.base.mvc.BaseAppConfigurer;
import com.cqmike.baseservices.auth.security.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;


/**
 * @program: baseServices
 * @description: mvc
 * @author: chen qi
 * @create: 2020-11-28 11:32
 **/
@Component
@Configuration
public class WebMvcConfig extends BaseAppConfigurer {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(new AuthInterceptor(redisTemplate));
        interceptorRegistration.addPathPatterns("/**");
        interceptorRegistration.excludePathPatterns("/auth/login");
        interceptorRegistration.excludePathPatterns("/error/**");
    }
}
