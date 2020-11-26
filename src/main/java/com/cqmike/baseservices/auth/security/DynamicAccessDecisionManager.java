package com.cqmike.baseservices.auth.security;

import cn.hutool.core.collection.CollUtil;
import com.cqmike.base.exception.BusinessException;
import com.cqmike.base.exception.CommonEnum;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

/**
 * @program: baseServices
 * @description: 动态权限决策管理器，用于判断用户是否有访问权限
 * @author: chen qi
 * @create: 2020-11-26 23:54
 **/
public class DynamicAccessDecisionManager implements AccessDecisionManager {

    @Override
    public void decide(Authentication authentication, Object o,
                       Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        // 当接口未被配置资源时直接放行
        if (CollUtil.isEmpty(collection)) {
            return;
        }
        for (ConfigAttribute configAttribute : collection) {
            //将访问所需资源或用户拥有资源进行比对
            String needAuthority = configAttribute.getAttribute();
            for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
                if (Objects.equals(needAuthority.trim(), grantedAuthority.getAuthority())) {
                    return;
                }
            }
        }
        throw new BusinessException(CommonEnum.FORBIDDEN);
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
