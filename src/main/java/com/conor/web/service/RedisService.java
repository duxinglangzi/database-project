package com.conor.web.service;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * <p>Description: </p>
 * <p>@Author conor  2021/6/3 </p>
 */
@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class RedisService {

    @Value("${spring.redis.databases.defaultDB}")
    public Integer defaultDatabase;
    @Value("${spring.redis.databases.db1}")
    public Integer db1;
    @Value("${spring.redis.databases.db2}")
    public Integer db2;
    @Value("${spring.redis.databases.db3}")
    public Integer db3;


    @Bean(name = "defaultRedisDB")
    public RedisTemplate<String, Object> getDefault(RedisProperties properties) {
        RedisTemplate<String, Object> defaultTemplate = getRedisTemplate();
        defaultTemplate.setConnectionFactory(initConnectionFactory(properties, defaultDatabase));
        return defaultTemplate;
    }

    @Bean(name = "redisDB1")
    public RedisTemplate<String, Object> getDB1(RedisProperties properties) {
        RedisTemplate<String, Object> redisTemplate = getRedisTemplate();
        redisTemplate.setConnectionFactory(initConnectionFactory(properties, db1));
        return redisTemplate;
    }

    @Bean(name = "redisDB2")
    public RedisTemplate<String, Object> getDB2(RedisProperties properties) {
        RedisTemplate<String, Object> redisTemplate = getRedisTemplate();
        redisTemplate.setConnectionFactory(initConnectionFactory(properties, db2));
        return redisTemplate;
    }

    @Bean(name = "redisDB3")
    public RedisTemplate<String, Object> getDB3(RedisProperties properties) {
        RedisTemplate<String, Object> redisTemplate = getRedisTemplate();
        redisTemplate.setConnectionFactory(initConnectionFactory(properties, db3));
        return redisTemplate;
    }

    private RedisTemplate<String, Object> getRedisTemplate() {
        final RedisTemplate<String, Object> template = new RedisTemplate<>();
        // 使用 fastJson 序列化
        final FastJsonRedisSerializer<?> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
        // value值的序列化使用 fastJsonRedisSerializer
        template.setValueSerializer(fastJsonRedisSerializer);
        template.setHashValueSerializer(fastJsonRedisSerializer);
        // key的序列化使用 StringRedisSerializer
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        // 开启事务
        template.setEnableTransactionSupport(true);
        return template;
    }


    private LettuceConnectionFactory initConnectionFactory(RedisProperties properties, int databaseIndex) {
        // 1、创建一个配置
        RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration();
        standaloneConfiguration.setDatabase(databaseIndex);
        standaloneConfiguration.setHostName(properties.getHost());
        standaloneConfiguration.setPort(properties.getPort());
        standaloneConfiguration.setPassword(RedisPassword.of(properties.getPassword()));

        // 2、初始化 redis连接池信息
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxTotal(properties.getLettuce().getPool().getMaxActive());
        poolConfig.setMaxIdle(properties.getLettuce().getPool().getMaxIdle());
        poolConfig.setMinIdle(properties.getLettuce().getPool().getMinIdle());
        poolConfig.setMaxWaitMillis(properties.getLettuce().getPool().getMaxWait().toMillis());

        // 3、配置连接池方式
        LettucePoolingClientConfiguration lettucePoolingClientConfiguration = LettucePoolingClientConfiguration.builder()
                .commandTimeout(properties.getTimeout())
                .poolConfig(poolConfig)
                .clientName("Client_Connection_Database_" + databaseIndex)
                .build();
        LettuceConnectionFactory factory = new LettuceConnectionFactory(standaloneConfiguration, lettucePoolingClientConfiguration);
        // 设置使用的redis数据库
        factory.setDatabase(databaseIndex);
        // 重新初始化工厂
        factory.afterPropertiesSet();
        return factory;
    }

}
