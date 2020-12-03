package com.cqmike.baseservices.auth.security;

import com.cqmike.base.exception.CommonEnum;
import com.cqmike.base.form.ReturnForm;
import com.cqmike.base.util.JsonUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: baseServices
 * @description:
 * @author: chen qi
 * @create: 2020-12-03 23:51
 **/
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control","no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println(JsonUtils.toJson(ReturnForm.error(CommonEnum.SIGNATURE_NOT_MATCH)));
        response.getWriter().flush();
    }
}