package com.cqmike.baseservices.auth.entity;

import com.cqmike.base.convert.LocalDateTimeAttributeConverter;
import com.cqmike.base.entity.BaseEntity;
import com.cqmike.baseservices.auth.enums.ActiveEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User extends BaseEntity {

    @Column(length = 32, nullable = false, columnDefinition = "DEFAULT '' COMMENT '登录名'")
    private String userName;

    @Column(length = 32, nullable = false, columnDefinition = "DEFAULT '' COMMENT '用户名'")
    private String nickName;

    @Column(length = 64, nullable = false, columnDefinition = "DEFAULT '' COMMENT '密码'")
    private String password;

    @Column(length = 32, columnDefinition = "DEFAULT '' COMMENT '手机号码'")
    private String phone;

    @Column(length = 512, columnDefinition = "DEFAULT '' COMMENT '头像'")
    private String icon;

    @Column(length = 32, nullable = false, columnDefinition = "DEFAULT '' COMMENT '邮箱'")
    private String email;

    @Convert(converter = LocalDateTimeAttributeConverter.class)
    @Column(columnDefinition = "COMMENT '登录时间'")
    private LocalDateTime lastLoginTime;

    @Column(length = 1, columnDefinition = "int DEFAULT 1 COMMENT '帐号启用状态 1-启用, 0-禁用'")
    private ActiveEnum status;

}
