package com.conor.service.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * <p>Description: </p>
 * <p>@Author conor  2021/6/4 </p>
 */
@Service
public class Db1Redis extends AbsRedisClientService {
    @Resource(name = "redisDB1")
    private RedisTemplate<String, Object> redisTemplate1;

    @Override
    protected int getDatabase() {
        return 1;
    }

    @Override
    public boolean expire(String key, long time) {
        if (time > 0) {
            redisTemplate1.expire(key, time, TimeUnit.SECONDS);
            return true;
        } else {
            throw new RuntimeException("超时时间小于0");
        }
    }

    @Override
    public long getExpire(String key) {
        return redisTemplate1.getExpire(key, TimeUnit.SECONDS);
    }

    @Override
    public boolean hasKey(String key) {
        return redisTemplate1.hasKey(key);
    }

    @Override
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate1.delete(key[0]);
            } else {
                redisTemplate1.delete(Arrays.asList(key));
            }
        }
    }

    // ============================String=============================
    @Override
    public Object get(String key) {
        return key == null ? null : redisTemplate1.opsForValue().get(key);
    }

    @Override
    public boolean set(String key, Object value) {
        redisTemplate1.opsForValue().set(key, value);
        return true;
    }

    @Override
    public boolean set(String key, Object value, long time) {
        if (time > 0) {
            redisTemplate1.opsForValue().set(key, value, time, TimeUnit.SECONDS);
        } else {
            this.set(key, value);
        }
        return true;
    }

    @Override
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate1.opsForValue().increment(key, delta);
    }

    @Override
    public long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate1.opsForValue().increment(key, -delta);
    }
    // ================================Map=================================

    @Override
    public Object hget(String key, String item) {
        return redisTemplate1.opsForHash().get(key, item);
    }

    @Override
    public Map<Object, Object> hmget(String key) {
        return redisTemplate1.opsForHash().entries(key);
    }

    @Override
    public boolean hmset(String key, Map<String, Object> map) {
        redisTemplate1.opsForHash().putAll(key, map);
        return true;
    }

    @Override
    public boolean hmset(String key, Map<String, Object> map, long time) {
        redisTemplate1.opsForHash().putAll(key, map);
        if (time > 0) {
            expire(key, time);
        }
        return true;
    }

    @Override
    public boolean hset(String key, String item, Object value) {
        redisTemplate1.opsForHash().put(key, item, value);
        return true;
    }

    @Override
    public boolean hset(String key, String item, Object value, long time) {
        redisTemplate1.opsForHash().put(key, item, value);
        if (time > 0) {
            expire(key, time);
        }
        return true;
    }

    @Override
    public void hdel(String key, Object... item) {
        redisTemplate1.opsForHash().delete(key, item);
    }

    @Override
    public boolean hHasKey(String key, String item) {
        return redisTemplate1.opsForHash().hasKey(key, item);
    }

    @Override
    public double hincr(String key, String item, double by) {
        return redisTemplate1.opsForHash().increment(key, item, by);
    }

    @Override
    public double hdecr(String key, String item, double by) {
        return redisTemplate1.opsForHash().increment(key, item, -by);
    }
    // ============================set=============================

    @Override
    public Set<Object> sGet(String key) {
        return redisTemplate1.opsForSet().members(key);
    }

    @Override
    public boolean sHasKey(String key, Object value) {
        return redisTemplate1.opsForSet().isMember(key, value);
    }

    @Override
    public long sSet(String key, Object... values) {
        return redisTemplate1.opsForSet().add(key, values);
    }

    @Override
    public long sSetAndTime(String key, long time, Object... values) {
        final Long count = redisTemplate1.opsForSet().add(key, values);
        if (time > 0)
            expire(key, time);
        return count;
    }

    @Override
    public long sGetSetSize(String key) {
        return redisTemplate1.opsForSet().size(key);
    }

    @Override
    public long setRemove(String key, Object... values) {
        final Long count = redisTemplate1.opsForSet().remove(key, values);
        return count;
    }
    // ===============================list=================================

    @Override
    public List<Object> lGet(String key, long start, long end) {
        return redisTemplate1.opsForList().range(key, start, end);
    }

    @Override
    public long lGetListSize(String key) {
        return redisTemplate1.opsForList().size(key);
    }

    @Override
    public Object lGetIndex(String key, long index) {
        return redisTemplate1.opsForList().index(key, index);
    }

    @Override
    public boolean lSet(String key, Object value) {
        redisTemplate1.opsForList().rightPush(key, value);
        return true;
    }

    @Override
    public boolean lSet(String key, Object value, long time) {
        redisTemplate1.opsForList().rightPush(key, value);
        if (time > 0) {
            expire(key, time);
        }
        return true;
    }

    @Override
    public boolean lSet(String key, List<Object> value) {
        redisTemplate1.opsForList().rightPushAll(key, value);
        return true;
    }

    @Override
    public boolean lSet(String key, List<Object> value, long time) {
        redisTemplate1.opsForList().rightPushAll(key, value);
        if (time > 0) {
            expire(key, time);
        }
        return true;
    }

    @Override
    public boolean lUpdateIndex(String key, long index, Object value) {
        redisTemplate1.opsForList().set(key, index, value);
        return true;
    }
}
