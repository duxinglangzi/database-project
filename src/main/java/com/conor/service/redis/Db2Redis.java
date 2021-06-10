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
public class Db2Redis extends AbsRedisClientService {
    @Resource(name = "redisDB2")
    private RedisTemplate<String, Object> template2;

    @Override
    protected int getDatabase() {
        return 2;
    }


    @Override
    public boolean expire(String key, long time) {
        if (time > 0) {
            template2.expire(key, time, TimeUnit.SECONDS);
            return true;
        } else {
            throw new RuntimeException("超时时间小于0");
        }
    }

    @Override
    public long getExpire(String key) {
        return template2.getExpire(key, TimeUnit.SECONDS);
    }

    @Override
    public boolean hasKey(String key) {
        return template2.hasKey(key);
    }

    @Override
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                template2.delete(key[0]);
            } else {
                template2.delete(Arrays.asList(key));
            }
        }
    }

    // ============================String=============================
    @Override
    public Object get(String key) {
        return key == null ? null : template2.opsForValue().get(key);
    }

    @Override
    public boolean set(String key, Object value) {
        template2.opsForValue().set(key, value);
        return true;
    }

    @Override
    public boolean set(String key, Object value, long time) {
        if (time > 0) {
            template2.opsForValue().set(key, value, time, TimeUnit.SECONDS);
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
        return template2.opsForValue().increment(key, delta);
    }

    @Override
    public long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return template2.opsForValue().increment(key, -delta);
    }
    // ================================Map=================================

    @Override
    public Object hget(String key, String item) {
        return template2.opsForHash().get(key, item);
    }

    @Override
    public Map<Object, Object> hmget(String key) {
        return template2.opsForHash().entries(key);
    }

    @Override
    public boolean hmset(String key, Map<String, Object> map) {
        template2.opsForHash().putAll(key, map);
        return true;
    }

    @Override
    public boolean hmset(String key, Map<String, Object> map, long time) {
        template2.opsForHash().putAll(key, map);
        if (time > 0) {
            expire(key, time);
        }
        return true;
    }

    @Override
    public boolean hset(String key, String item, Object value) {
        template2.opsForHash().put(key, item, value);
        return true;
    }

    @Override
    public boolean hset(String key, String item, Object value, long time) {
        template2.opsForHash().put(key, item, value);
        if (time > 0) {
            expire(key, time);
        }
        return true;
    }

    @Override
    public void hdel(String key, Object... item) {
        template2.opsForHash().delete(key, item);
    }

    @Override
    public boolean hHasKey(String key, String item) {
        return template2.opsForHash().hasKey(key, item);
    }

    @Override
    public double hincr(String key, String item, double by) {
        return template2.opsForHash().increment(key, item, by);
    }

    @Override
    public double hdecr(String key, String item, double by) {
        return template2.opsForHash().increment(key, item, -by);
    }
    // ============================set=============================

    @Override
    public Set<Object> sGet(String key) {
        return template2.opsForSet().members(key);
    }

    @Override
    public boolean sHasKey(String key, Object value) {
        return template2.opsForSet().isMember(key, value);
    }

    @Override
    public long sSet(String key, Object... values) {
        return template2.opsForSet().add(key, values);
    }

    @Override
    public long sSetAndTime(String key, long time, Object... values) {
        final Long count = template2.opsForSet().add(key, values);
        if (time > 0)
            expire(key, time);
        return count;
    }

    @Override
    public long sGetSetSize(String key) {
        return template2.opsForSet().size(key);
    }

    @Override
    public long setRemove(String key, Object... values) {
        final Long count = template2.opsForSet().remove(key, values);
        return count;
    }
    // ===============================list=================================

    @Override
    public List<Object> lGet(String key, long start, long end) {
        return template2.opsForList().range(key, start, end);
    }

    @Override
    public long lGetListSize(String key) {
        return template2.opsForList().size(key);
    }

    @Override
    public Object lGetIndex(String key, long index) {
        return template2.opsForList().index(key, index);
    }

    @Override
    public boolean lSet(String key, Object value) {
        template2.opsForList().rightPush(key, value);
        return true;
    }

    @Override
    public boolean lSet(String key, Object value, long time) {
        template2.opsForList().rightPush(key, value);
        if (time > 0) {
            expire(key, time);
        }
        return true;
    }

    @Override
    public boolean lSet(String key, List<Object> value) {
        template2.opsForList().rightPushAll(key, value);
        return true;
    }

    @Override
    public boolean lSet(String key, List<Object> value, long time) {
        template2.opsForList().rightPushAll(key, value);
        if (time > 0) {
            expire(key, time);
        }
        return true;
    }

    @Override
    public boolean lUpdateIndex(String key, long index, Object value) {
        template2.opsForList().set(key, index, value);
        return true;
    }
}
