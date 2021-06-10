package com.conor.service.user.mapper;

import com.conor.service.user.entity.UserEntity;
import com.conor.service.user.param.UserParam;

import java.util.List;

/**
 * <p>Description: </p>
 * <p>@Author wuqiong  2021/6/10 </p>
 */
public interface UserMapper {

    public List<UserEntity> getUsers(UserParam param);


}
