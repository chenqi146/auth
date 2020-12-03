package com.cqmike.baseservices.auth.entity;

import com.cqmike.base.entity.BaseEntity;
import com.cqmike.baseservices.auth.enums.ActiveEnum;
import com.cqmike.baseservices.auth.enums.MenuTypeEnum;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @program: baseServices
 * @description: 菜单表
 * @author: chen qi
 * @create: 2020-11-24 08:13
 **/

@Data
@Entity
@Table(name = "menu")
@NoArgsConstructor
public class Menu extends BaseEntity {

    @Column(nullable = false, columnDefinition = "varchar(64) COMMENT 'code'")
    private String code;

    @Column(nullable = false, columnDefinition = "varchar(64) COMMENT '父级code'")
    private String parentCode;

    @Column(nullable = false, columnDefinition = "varchar(64) COMMENT '菜单名称'")
    private String name;

    @Column(length = 5, nullable = false, columnDefinition = "int COMMENT '菜单类型 0-菜单, 1-按钮'")
    private MenuTypeEnum type;

    @Column(length = 5, columnDefinition = "int DEFAULT 0 COMMENT '排序'")
    private Integer sort;

    @Column(columnDefinition = "varchar(512) COMMENT '菜单图标'")
    private String icon = "";

    @Column(columnDefinition = "varchar(512) COMMENT '备注'")
    private String remark = "";

    @Column(length = 1, columnDefinition = "int DEFAULT 1 COMMENT '菜单启用禁用 1-启用, 0-禁用'")
    private ActiveEnum status;

}
