package com.cqmike.baseservices.auth.security;

import com.cqmike.base.auth.Auth;
import com.cqmike.base.mvc.JWTAuthenticationInterceptor;
import com.cqmike.base.util.JsonUtils;
import com.cqmike.baseservices.auth.dto.JwtUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @program: baseServices
 * @description: jwt过滤器
 * @author: chen qi
 * @create: 2020-11-25 23:07
 **/
@Slf4j
public class AuthInterceptor extends JWTAuthenticationInterceptor {

    public AuthInterceptor(StringRedisTemplate redisTemplate) {
        super(redisTemplate);
    }

    protected void afterValid(String redisJson) {
        JwtUser user = JsonUtils.parse(redisJson, JwtUser.class);
        Auth.put(Auth.USER, user);
    }

}
