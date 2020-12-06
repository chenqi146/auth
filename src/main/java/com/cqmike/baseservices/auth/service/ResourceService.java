package com.cqmike.baseservices.auth.service;

import com.cqmike.base.service.CurdService;
import com.cqmike.baseservices.auth.entity.Resource;
import com.cqmike.baseservices.auth.entity.Role;
import com.cqmike.baseservices.auth.entity.RoleResourceRelation;

import java.util.Collection;
import java.util.List;

/**
 * @program: baseServices
 * @description: 用户接口
 * @author: chen qi
 * @create: 2020-11-26 08:14
 **/
public interface ResourceService extends CurdService<Resource> {

    /**
     *  根据角色获取权限 已经去重了
     * @param roles
     * @return
     */
    List<Resource> findListByRoleList(Collection<Role> roles);

    /**
     * 从缓存中获取所有资源列表， 没有会刷新缓存
     * @return
     */
    List<Resource> findAllFormCache();
    /**
     * 初始化缓存中所有资源角色关系列表
     * @return
     */
    void initRoleResourceCache();

}
