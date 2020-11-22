package com.cqmike.baseservices.auth.security;

import com.cqmike.base.exception.CommonEnum;
import com.cqmike.base.form.ReturnForm;
import com.cqmike.base.util.JsonUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 当用户尝试访问安全的REST资源而不提供任何凭据时
 *
 * @author chen qi
 * @date 2020-11-13 17:58
 **/
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        // 当用户尝试访问安全的REST资源而不提供任何凭据时，将调用此方法发送401 响应
        response.getWriter().write(JsonUtils.toJson(ReturnForm.error(CommonEnum.SIGNATURE_NOT_MATCH)));
    }
}
