package com.cqmike.baseservices.auth.convert;

import com.cqmike.base.convert.BaseConvert;
import com.cqmike.baseservices.auth.dto.UserDto;
import com.cqmike.baseservices.auth.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @program: baseServices
 * @description: user convert
 * @author: chen qi
 * @create: 2020-11-28 12:02
 **/
@Mapper(componentModel = "spring")
public interface UserConvert extends BaseConvert<User, UserDto> {


    @Mapping(target = "status", constant = "Y")
    @Override
    User from(UserDto dto);

}
