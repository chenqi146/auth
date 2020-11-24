package com.cqmike.baseservices.auth.entity;

import com.cqmike.base.entity.BaseEntity;
import com.cqmike.baseservices.auth.enums.ActiveEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @program: baseServices
 * @description: 角色表
 * @author: chen qi
 * @create: 2020-11-23 22:51
 **/

@Getter
@Setter
@Entity
@Table(name = "role")
public class Role extends BaseEntity {

    @Column(length = 64, nullable = false, columnDefinition = "DEFAULT '' COMMENT '角色名称'")
    private String name;

    @Column(length = 512, nullable = false, columnDefinition = "DEFAULT '' COMMENT '描述'")
    private String description;

    @Column(length = 1, columnDefinition = "int DEFAULT 1 COMMENT '角色启用状态 1-启用, 0-禁用'")
    private ActiveEnum status;

    @Column(length = 5, columnDefinition = "int DEFAULT 0 COMMENT '排序 0最大'")
    private Integer sort;

}
