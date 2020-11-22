package com.cqmike.baseservices.auth.entity;

import com.cqmike.base.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "users")
public class Users extends BaseEntity {

    @Column(length = 32, nullable = false, columnDefinition = "DEFAULT '' COMMENT '登录名'")
    private String userName;

    @Column(length = 32, nullable = false, columnDefinition = "DEFAULT '' COMMENT '用户名'")
    private String nickName;

    @Column(length = 64, nullable = false, columnDefinition = "DEFAULT '' COMMENT '密码'")
    private String password;

    @Column(length = 32, columnDefinition = "DEFAULT '' COMMENT '手机号码'")
    private String phone;

    @Column(length = 32, nullable = false, columnDefinition = "DEFAULT '' COMMENT '邮箱'")
    private String email;

}
