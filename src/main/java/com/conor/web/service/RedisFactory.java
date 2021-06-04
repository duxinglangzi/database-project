package com.conor.web.service;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>Description: </p>
 * <p>@Author conor  2021/6/4 </p>
 */
@Service
public class RedisFactory {

    @Resource
    private List<AbsRedisClientService> absRedisClientService;

    public AbsRedisClientService getRedisByDatabase(int database) {
        for (AbsRedisClientService clientService : absRedisClientService) {
            if (clientService.getDatabase() == database) return clientService;
        }
        return getDefaultRedis();
    }

    public AbsRedisClientService getDefaultRedis() {
        return getRedisByDatabase(0);
    }

    public AbsRedisClientService getRedis1() {
        return getRedisByDatabase(1);
    }

    public AbsRedisClientService getRedis2() {
        return getRedisByDatabase(2);
    }

    public AbsRedisClientService getRedis3() {
        return getRedisByDatabase(3);
    }


}
