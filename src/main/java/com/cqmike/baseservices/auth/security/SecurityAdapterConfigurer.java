package com.cqmike.baseservices.auth.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;

/**
 * Security过滤器配置
 *
 * @author chen qi
 * @date 2020-11-13 18:44
 **/
@RequiredArgsConstructor
public class SecurityAdapterConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        super.configure(builder);
    }
}
