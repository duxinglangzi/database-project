package com.conor.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.conor.web.service.RedisFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <p>Description: </p>
 * <p>@Author conor  2021/6/3 </p>
 */
@RestController
@RequestMapping("/app")
public class AppController {

    @Autowired(required = false)
    private RedisFactory redisFactory;


    @ResponseBody
    @RequestMapping(value = "/redis0", method = RequestMethod.GET)
    public String defaultRedis() {
        redisFactory.getDefaultRedis().set(LocalDateTime.now().toString(), LocalDateTime.now().toString());
        return "{\"status\":\"ok\"}";
    }

    @ResponseBody
    @RequestMapping(value = "/redis1", method = RequestMethod.GET)
    public String redis1() {
        redisFactory.getRedis1().set(LocalDateTime.now().toString(), LocalDateTime.now().toString());
        return "{\"status\":\"ok\"}";
    }

    @ResponseBody
    @RequestMapping(value = "/redis2", method = RequestMethod.GET)
    public String redis2() {
        redisFactory.getRedis2().set(LocalDateTime.now().toString(), LocalDateTime.now().toString());
        return "{\"status\":\"ok\"}";
    }

    @ResponseBody
    @RequestMapping(value = "/redis3", method = RequestMethod.GET)
    public String redis3() {
        redisFactory.getRedis3().set(LocalDateTime.now().toString(), LocalDateTime.now().toString());
        return "{\"status\":\"ok\"}";
    }
}
