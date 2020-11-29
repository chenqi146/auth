package com.cqmike.baseservices.auth.entity;

import com.cqmike.base.convert.LocalDateTimeAttributeConverter;
import com.cqmike.base.entity.BaseEntity;
import com.cqmike.baseservices.auth.enums.ActiveEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User extends BaseEntity {

    @Column(nullable = false, columnDefinition = "varchar(32) COMMENT '登录名'")
    private String username = "";

    @Column(nullable = false, columnDefinition = "varchar(32) COMMENT '用户名'")
    private String nickname = "";

    @Column(nullable = false, columnDefinition = "varchar(64) COMMENT '密码'")
    private String password = "";

    @Column(columnDefinition = "varchar(32) COMMENT '手机号码'")
    private String phone = "";

    @Column(columnDefinition = "varchar(512) COMMENT '头像'")
    private String icon = "";

    @Column(nullable = false, columnDefinition = "varchar(512) COMMENT '邮箱'")
    private String email = "";

    @Convert(converter = LocalDateTimeAttributeConverter.class)
    @Column(columnDefinition = "datetime DEFAULT NULL COMMENT '登录时间'")
    private LocalDateTime lastLoginTime;

    @Column(length = 1, columnDefinition = "int DEFAULT 1 COMMENT '帐号启用状态 1-启用, 0-禁用'")
    private ActiveEnum status;

    /**
     * 配置多对多
     */
    @ManyToMany
    @JoinTable
    private Set<Role> roles = new HashSet<>();

}
