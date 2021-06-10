package com.conor.controller;

import com.alibaba.fastjson.JSONObject;
import com.conor.service.user.entity.UserEntity;
import com.conor.service.user.mapper.UserMapper;
import com.conor.service.user.param.UserParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>Description: </p>
 * <p>@Author wuqiong  2021/6/9 </p>
 */
@RestController
@RequestMapping("/database")
public class DruidController {

    @Autowired
    private UserMapper userMapper;

    @ResponseBody
    @RequestMapping(value = "/getUsers", method = RequestMethod.GET)
    public String getUserInfo(){
        UserParam param = new UserParam();
        List<UserEntity> users = userMapper.getUsers(param);
        return JSONObject.toJSONString(users);
    }



}
