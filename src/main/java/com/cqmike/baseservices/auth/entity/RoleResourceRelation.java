package com.cqmike.baseservices.auth.entity;

import com.cqmike.base.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @program: baseServices
 * @description: 角色资源关联表
 * @author: chen qi
 * @create: 2020-11-24 08:35
 **/
@Data
@Entity
@Table(name = "role_resource_relation")
public class RoleResourceRelation extends BaseEntity {

    @Column(length = 32, nullable = false, columnDefinition = "bigint COMMENT '资源id'")
    private Long resourceId;

    @Column(length = 32, nullable = false, columnDefinition = "bigint COMMENT '角色id'")
    private Long roleId;


}
