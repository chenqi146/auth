package com.cqmike.baseservices.auth.entity;

import com.cqmike.base.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @program: baseServices
 * @description: 资源分类
 * @author: chen qi
 * @create: 2020-11-24 08:32
 **/
@Data
@Entity
@Table(name = "resource_category")
public class ResourceCategory extends BaseEntity {

    @Column(length = 256, nullable = false, columnDefinition = "DEFAULT '' COMMENT '分类'")
    private String name;

    @Column(length = 5, columnDefinition = "int DEFAULT 0 COMMENT '排序 0最大'")
    private Integer sort;

}
