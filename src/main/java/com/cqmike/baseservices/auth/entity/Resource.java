package com.cqmike.baseservices.auth.entity;

import com.cqmike.base.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @program: baseServices
 * @description: 资源表
 * @author: chen qi
 * @create: 2020-11-24 08:30
 **/
@Data
@Entity
@Table(name = "resource")
public class Resource extends BaseEntity {


    @Column(length = 32, nullable = false, columnDefinition = "bigint COMMENT '资源分类ID'")
    private Long categoryId;

    @Column(length = 256, columnDefinition = "DEFAULT '' COMMENT '资源名称'")
    private String name;

    @Column(length = 512, columnDefinition = "DEFAULT '' COMMENT '资源url'")
    private String url;

    @Column(length = 256, columnDefinition = "DEFAULT '' COMMENT '资源描述'")
    private String description;
}
