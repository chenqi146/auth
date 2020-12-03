package com.cqmike.baseservices.auth.entity;

import com.cqmike.base.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @program: baseServices
 * @description: 用户角色关系表
 * @author: chen qi
 * @create: 2020-11-24 08:10
 **/

@Getter
@Setter
@Entity
@Table(name = "user_role_relation")
public class UserRoleRelation extends BaseEntity {

    @Column(name = "user_id", length = 32, nullable = false, columnDefinition = "bigint COMMENT '用户id'")
    private Long userId;

    @Column(name = "role_id", length = 32, nullable = false, columnDefinition = "bigint COMMENT '角色id'")
    private Long roleId;

}
