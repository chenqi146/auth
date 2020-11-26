//package com.cqmike.baseservices.auth.security;
//
//import com.cqmike.base.exception.BusinessException;
//import com.cqmike.base.exception.CommonEnum;
//import com.cqmike.base.form.ReturnForm;
//import com.cqmike.base.util.JsonUtils;
//import com.cqmike.baseservices.auth.dto.JwtUser;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Optional;
//
///**
// * @program: baseServices
// * @description: jwt过滤器
// * @author: chen qi
// * @create: 2020-11-25 23:07
// **/
//@Slf4j
//@NoArgsConstructor
//public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//
//    private AuthenticationManager authenticationManager;
//
//    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
//        this.authenticationManager = authenticationManager;
//        super.setFilterProcessesUrl("/auth/login");
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//
//        // 从输入流中获取到登录的信息
//        try {
//            JwtUser loginUser = new ObjectMapper().readValue(request.getInputStream(), JwtUser.class);
//            Optional.ofNullable(loginUser).orElseThrow(() -> new BusinessException("参数中没有携带登录信息"));
//            return authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword(), new ArrayList<>())
//            );
//        } catch (IOException e) {
//            log.error("build UsernamePasswordAuthenticationToken throw Exception; ", e);
//            return null;
//        }
//    }
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
//                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
//        // 查看源代码会发现调用getPrincipal()方法会返回一个实现了`UserDetails`接口的对象
//        // 所以就是JwtUser啦
//        JwtUser jwtUser = (JwtUser) authResult.getPrincipal();
//        jwtUser.setPassword(null);
//        String token = JwtUtil.createToken(jwtUser);
//        // 返回创建成功的token
//        // 但是这里创建的token只是单纯的token
//        // 按照jwt的规定，最后请求的格式应该是 `Bearer token`
//        response.setHeader("token", JwtUtil.TOKEN_PREFIX + token);
//    }
//
//    @Override
//    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
//                                              AuthenticationException failed) throws IOException, ServletException {
//        //当用户在没有授权的情况下访问受保护的REST资源时，将调用此方法发送403 Forbidden响应
//        response.getWriter().write(JsonUtils.toJson(ReturnForm.error(CommonEnum.FORBIDDEN)));
//    }
//}
