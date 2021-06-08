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

    /**
     * <p> 获取数据库 </p>
     * @param database 数据编号 0(默认)、1、2、3
     * @return com.conor.web.service.AbsRedisClientService
     * @author conor 2021-06-08 16:21
     */
    public AbsRedisClientService getRedisByDatabase(int database) {
        for (AbsRedisClientService clientService : absRedisClientService) {
            if (clientService.getDatabase() == database) return clientService;
        }
        return getDefaultRedis();
    }

    /**
     * <p> 连接默认数据 </p>
     * @return com.conor.web.service.AbsRedisClientService
     * @author conor 2021-06-08 16:20
     */
    public AbsRedisClientService getDefaultRedis() {
        return getRedisByDatabase(0);
    }

    /**
     * <p> 连接数据 1  </p>
     * @return com.conor.web.service.AbsRedisClientService
     * @author conor 2021-06-08 16:20
     */
    public AbsRedisClientService getRedis1() {
        return getRedisByDatabase(1);
    }

    /**
     * <p> 连接数据 2 </p>
     * @return com.conor.web.service.AbsRedisClientService
     * @author conor 2021-06-08 16:20
     */
    public AbsRedisClientService getRedis2() {
        return getRedisByDatabase(2);
    }

    /**
     * <p> 连接数据 3  </p>
     * @return com.conor.web.service.AbsRedisClientService
     * @author conor 2021-06-08 16:20
     */
    public AbsRedisClientService getRedis3() {
        return getRedisByDatabase(3);
    }


}
