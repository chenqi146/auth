package com.cqmike.baseservices.auth.entity;

import com.cqmike.base.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @program: baseServices
 * @description: 角色菜单关联表
 * @author: chen qi
 * @create: 2020-11-24 08:34
 **/
@Data
@Entity
@Table(name = "role_menu_relation")
public class RoleMenuRelation extends BaseEntity {


    @Column(length = 32, nullable = false, columnDefinition = "bigint COMMENT '菜单id'")
    private Long menuId;

    @Column(length = 32, nullable = false, columnDefinition = "bigint COMMENT '角色id'")
    private Long roleId;


}
