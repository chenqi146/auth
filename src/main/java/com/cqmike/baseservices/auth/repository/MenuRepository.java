package com.cqmike.baseservices.auth.repository;

import com.cqmike.base.repository.BaseRepository;
import com.cqmike.baseservices.auth.entity.Menu;
import com.cqmike.baseservices.auth.entity.Resource;
import org.springframework.stereotype.Repository;

/**
 * @program: baseServices
 * @description: 用户dao
 * @author: chen qi
 * @create: 2020-11-25 22:50
 **/
@Repository
public interface MenuRepository extends BaseRepository<Menu, Long> {

}
