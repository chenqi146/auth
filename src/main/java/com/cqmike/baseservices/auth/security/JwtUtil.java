package com.cqmike.baseservices.auth.security;

import cn.hutool.core.collection.CollUtil;
import com.cqmike.baseservices.auth.dto.JwtUser;
import com.cqmike.baseservices.auth.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Date;

/**
 * TODO
 *
 * @author chen qi
 * @date 2020-11-13 18:06
 **/
public class JwtUtil {

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    private static final String SECRET = "cuAihCz53DZRjZwbsGcZJ2Ai6At=f32r3r3r23rT142uphtJMsk7iQ+";
    private static final String ISS = "cqmike";

    // 创建token
    public static String createToken(JwtUser jwtUser) {
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
        return Jwts.builder()
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .setIssuer(ISS)
                .setExpiration(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                .claim("user", jwtUser)
                .setSubject(jwtUser.getUsername())
                .setIssuedAt(new Date())
                .compact();
    }

    // 从token中获取用户名
    public static String getUsername(String token) {
        return getTokenBody(token).getSubject();
    }

    private static Claims getTokenBody(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET).build()
                .parseClaimsJws(token)
                .getBody();
    }
}
