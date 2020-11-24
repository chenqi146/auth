package com.cqmike.baseservices.auth.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @program: baseServices
 * @description: 菜单类型
 * @author: chen qi
 * @create: 2020-11-23 22:09
 **/
@Getter
@RequiredArgsConstructor
public enum MenuTypeEnum {
    MENU(0, "菜单"), BUTTON(1, "按钮");
    private final Integer code;
    private final String msg;
}
