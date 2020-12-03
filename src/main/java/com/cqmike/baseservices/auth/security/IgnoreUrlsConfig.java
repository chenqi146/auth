package com.cqmike.baseservices.auth.security;

/**
 * @program: baseServices
 * @description: 用于配置白名单资源路径
 * @author: chen qi
 * @create: 2020-12-02 23:28
 **/

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ConfigurationProperties(prefix = "secure.ignored")
public class IgnoreUrlsConfig {

    private List<String> urls = new ArrayList<>();
}
