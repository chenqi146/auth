package com.cqmike.baseservices.auth.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @program: baseServices
 * @description: 启用禁用
 * @author: chen qi
 * @create: 2020-11-23 22:09
 **/
@Getter
@RequiredArgsConstructor
public enum  ActiveEnum {
    N(0, "禁用"), Y(1, "启用");
    private final Integer code;
    private final String msg;
}
