package com.cqmike.baseservices.auth.repository;

import com.cqmike.base.repository.BaseRepository;
import com.cqmike.baseservices.auth.entity.Role;
import com.cqmike.baseservices.auth.entity.RoleResourceRelation;
import org.springframework.stereotype.Repository;

/**
 * @program: baseServices
 * @description: 用户dao
 * @author: chen qi
 * @create: 2020-11-25 22:50
 **/
@Repository
public interface RoleResourceRepository extends BaseRepository<RoleResourceRelation, Long> {

}
