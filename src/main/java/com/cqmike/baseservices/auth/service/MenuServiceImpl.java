package com.cqmike.baseservices.auth.service;

import com.cqmike.base.repository.BaseRepository;
import com.cqmike.base.service.AbstractCurdService;
import com.cqmike.baseservices.auth.entity.Menu;
import com.cqmike.baseservices.auth.entity.User;
import com.cqmike.baseservices.auth.repository.MenuRepository;
import com.cqmike.baseservices.auth.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import java.util.Optional;

/**
 * @program: baseServices
 * @description: 用户接口实现类
 * @author: chen qi
 * @create: 2020-11-26 08:15
 **/
@Service
public class MenuServiceImpl extends AbstractCurdService<Menu> implements MenuService {

    protected MenuServiceImpl(BaseRepository<Menu, Long> repository) {
        super(repository);
    }


}
