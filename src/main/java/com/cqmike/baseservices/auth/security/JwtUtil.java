package com.cqmike.baseservices.auth.security;

import com.cqmike.base.generator.SnowflakeIdWorker;
import com.cqmike.baseservices.auth.dto.JwtUser;
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

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author chen qi
 * @date 2020-11-13 18:06
 **/
public class JwtUtil {

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    private static final String SECRET = "cuAihCz53DZRjZwbsGcZJ2Ai6At=T142uphtJMsk7iQ+";
    private static final String ISS = "cqmike";

    // 创建token
    public static String createToken(JwtUser jwtUser) {
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
        return Jwts.builder()
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .setIssuer(ISS)
                .claim("user", jwtUser)
                .setSubject(jwtUser.getUsername())
                .setIssuedAt(new Date())
                .compact();
    }

    // 从token中获取用户名
    public static String getUsername(String token){
        return getTokenBody(token).getSubject();
    }

    private static Claims getTokenBody(String token){
        return Jwts.parserBuilder()
                .setSigningKey(SECRET).build()
                .parseClaimsJws(token)
                .getBody();
    }
}
