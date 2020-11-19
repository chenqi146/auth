package com.cqmike.baseservices.auth.security;

import com.cqmike.base.generator.SnowflakeIdWorker;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author chen qi
 * @date 2020-11-13 18:06
 **/
@Component
@ConfigurationProperties(prefix = "auth.jwt")
public class JwtProvider implements InitializingBean {

    private String base64Secret;
    private Long expireTime;
    public static final String AUTHORITIES_KEY = "auth";
    private JwtParser jwtParser;
    private JwtBuilder jwtBuilder;
    private final StringRedisTemplate stringRedisTemplate;
    private final SnowflakeIdWorker snowflakeIdWorker;

    public JwtProvider(StringRedisTemplate stringRedisTemplate, SnowflakeIdWorker snowflakeIdWorker) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.snowflakeIdWorker = snowflakeIdWorker;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(base64Secret);
        Key key = Keys.hmacShaKeyFor(keyBytes);
        jwtParser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();
        jwtBuilder = Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS512);
    }


    public Claims getClaims(String token) {
        return jwtParser
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 创建Token 设置永不过期，
     * Token 的时间有效性转到Redis 维护
     *
     * @param authentication /
     * @return /
     */
    public String createToken(Authentication authentication, Map<String, Object> claims) {
        if (claims == null || claims.isEmpty()) {
            claims = Maps.newHashMap();
        }
        /*
         * 获取权限列表
         */
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        claims.put(AUTHORITIES_KEY, authorities);
        return jwtBuilder
                // 加入ID确保生成的 Token 都不一致
                .setId(String.valueOf(snowflakeIdWorker.getId()))
                .setClaims(claims)
                .setSubject(authentication.getName())
                .compact();
    }
    /**
     * 创建Token 设置永不过期，
     * Token 的时间有效性转到Redis 维护
     *
     * @param authentication /
     * @return /
     */
    public String createToken(Authentication authentication) {
        /*
         * 获取权限列表
         */
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        return jwtBuilder
                // 加入ID确保生成的 Token 都不一致
                .setId(String.valueOf(snowflakeIdWorker.getId()))
                .claim(AUTHORITIES_KEY, authorities)
                .setSubject(authentication.getName())
                .compact();
    }

    /**
     * 依据Token 获取鉴权信息
     *
     * @param token /
     * @return /
     */
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        String authoritiesStr = claims.get(AUTHORITIES_KEY, String.class);
        List<? extends GrantedAuthority> authorities = Lists.newArrayList();
        if (authoritiesStr != null && !authoritiesStr.equals("")) {
            authorities = Arrays.stream(authoritiesStr.split(","))
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }

        User principal = new User(claims.getSubject(), "******", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }
}
