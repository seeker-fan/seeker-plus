package com.wf.seeker.util;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * redis工具类
 * 
 * @author Fan.W
 * @since 1.8
 */
@Component
public class RedisUtils {

	private static final Logger logger = LoggerFactory.getLogger(RedisUtils.class);

	private static StringRedisTemplate redisTemplate;

	private RedisUtils(StringRedisTemplate template) throws IOException {
		RedisUtils.redisTemplate = template;
	}

	/**
	 * 指定缓存失效时间
	 * 
	 * @param key 键
	 * @param time 时间(秒)
	 * @return
	 */
	public static boolean expire(String key, long time) {
		try {
			if (time > 0) {
				redisTemplate.expire(key, time, TimeUnit.SECONDS);
			}
			return true;
		} catch (Exception e) {
			logger.error("redis操作异常", e);
			return false;
		}
	}

	/**
	 * 根据key 获取过期时间
	 * 
	 * @param key 键 不能为null
	 * @return 时间(秒) 返回0代表为永久有效
	 */
	public static long getExpire(String key) {
		return redisTemplate.getExpire(key, TimeUnit.SECONDS);
	}

	/**
	 * 判断key是否存在
	 * 
	 * @param key 键
	 * @return true 存在 false不存在
	 */
	public static boolean hasKey(String key) {
		try {
			return redisTemplate.hasKey(key);
		} catch (Exception e) {
			logger.error("redis操作异常", e);
			return false;
		}
	}

	/**
	 * 删除缓存
	 * 
	 * @param key 可以传一个值 或多个
	 */
	@SuppressWarnings("unchecked")
	public static void del(String... key) {
		if (key != null && key.length > 0) {
			if (key.length == 1) {
				redisTemplate.delete(key[0]);
			} else {
				redisTemplate.delete(CollectionUtils.arrayToList(key));
			}
		}
	}

	// ============================String begin=============================
	/**
	 * 普通缓存获取
	 * 
	 * @param key 键
	 * @return 值
	 */
	public static String get(String key) {
		return key == null ? null : redisTemplate.opsForValue().get(key);
	}

	/**
	 * 普通缓存放入
	 * 
	 * @param key 键
	 * @param value 值
	 * @return true成功 false失败
	 */
	public static boolean set(String key, String value) {
		try {
			redisTemplate.opsForValue().set(key, value);
			return true;
		} catch (Exception e) {
			logger.error("redis操作异常", e);
			return false;
		}
	}

	/**
	 * 普通缓存放入并设置时间
	 * 
	 * @param key 键
	 * @param value 值
	 * @param time 时间(秒) time要大于0 如果time小于等于0 将设置无限期
	 * @return true成功 false 失败
	 */
	public static boolean set(String key, String value, long time) {
		try {
			if (time > 0) {
				redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
			} else {
				set(key, value);
			}
			return true;
		} catch (Exception e) {
			logger.error("redis操作异常", e);
			return false;
		}
	}

	/**
	 * 递增
	 * 
	 * @param key 键
	 * @param by 要增加几(大于0)
	 * @return
	 */
	public static long incr(String key, long delta) {
		if (delta < 0) {
			throw new RuntimeException("递增因子必须大于0");
		}
		return redisTemplate.opsForValue().increment(key, delta);
	}

	/**
	 * 递减
	 * 
	 * @param key 键
	 * @param by 要减少几(小于0)
	 * @return
	 */
	public static long decr(String key, long delta) {
		if (delta < 0) {
			throw new RuntimeException("递减因子必须大于0");
		}
		return redisTemplate.opsForValue().increment(key, -delta);
	}
	// ============================String end=============================

	// ============================Map begin=============================
	/**
	 * HashGet
	 * 
	 * @param key 键 不能为null
	 * @param item 项 不能为null
	 * @return 值
	 */
	public static String hget(String key, String item) {
		return (String) redisTemplate.opsForHash().get(key, item);
	}

	/**
	 * 获取hashKey对应的所有键值
	 * 
	 * @param key 键
	 * @return 对应的多个键值
	 */
	public static Map<Object, Object> hmget(String key) {
		return redisTemplate.opsForHash().entries(key);
	}

	/**
	 * 向一张hash表中放入数据,如果不存在将创建
	 * 
	 * @param key 键
	 * @param item 项
	 * @param value 值
	 * @return true 成功 false失败
	 */
	public static boolean hset(String key, String item, Object value) {
		try {
			redisTemplate.opsForHash().put(key, item, value);
			return true;
		} catch (Exception e) {
			logger.error("redis操作异常", e);
			return false;
		}
	}

	/**
	 * 向一张hash表中放入数据,如果不存在将创建
	 * 
	 * @param key 键
	 * @param item 项
	 * @param value 值
	 * @param time 时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
	 * @return true 成功 false失败
	 */
	public static boolean hset(String key, String item, Object value, long time) {
		try {
			redisTemplate.opsForHash().put(key, item, value);
			if (time > 0) {
				expire(key, time);
			}
			return true;
		} catch (Exception e) {
			logger.error("redis操作异常", e);
			return false;
		}
	}

	/**
	 * HashSet
	 * 
	 * @param key 键
	 * @param map 对应多个键值
	 * @return true 成功 false 失败
	 */
	public static boolean hmset(String key, Map<String, String> map) {
		try {
			redisTemplate.opsForHash().putAll(key, map);
			return true;
		} catch (Exception e) {
			logger.error("redis操作异常", e);
			return false;
		}
	}

	/**
	 * HashSet 并设置时间,只能设置大key时间，不能设置小key时间
	 * 
	 * @param key 键
	 * @param map 对应多个键值
	 * @param time 时间(秒)
	 * @return true成功 false失败
	 */
	public static boolean hmset(String key, Map<String, String> map, long time) {
		try {
			redisTemplate.opsForHash().putAll(key, map);
			if (time > 0) {
				expire(key, time);
			}
			return true;
		} catch (Exception e) {
			logger.error("redis操作异常", e);
			return false;
		}
	}

	/**
	 * 删除hash表中的值
	 * 
	 * @param key 键 不能为null
	 * @param item 项 可以使多个 不能为null
	 */
	public static void hdel(String key, Object... item) {
		redisTemplate.opsForHash().delete(key, item);
	}

	/**
	 * 判断hash表中是否有该项的值
	 * 
	 * @param key 键 不能为null
	 * @param item 项 不能为null
	 * @return true 存在 false不存在
	 */
	public static boolean hHasKey(String key, String item) {
		return redisTemplate.opsForHash().hasKey(key, item);
	}

	/**
	 * hash递增 如果不存在,就会创建一个 并把新增后的值返回
	 * 
	 * @param key 键
	 * @param item 项
	 * @param by 要增加几(大于0)
	 * @return
	 */
	public static double hincr(String key, String item, double by) {
		return redisTemplate.opsForHash().increment(key, item, by);
	}

	/**
	 * hash递减
	 * 
	 * @param key 键
	 * @param item 项
	 * @param by 要减少记(小于0)
	 * @return
	 */
	public static double hdecr(String key, String item, double by) {
		return redisTemplate.opsForHash().increment(key, item, -by);
	}
	// ============================Map end=============================

	// =================================Set begin==============================================
	/**
	 * 返回redis中所有key
	 * 
	 * @return
	 */
	public static Set<String> keys() {

		return redisTemplate.keys("*");
	}

	/**
	 * 向集合中添加一个或者多个成员
	 * 
	 * @param key
	 * @param members
	 */
	public static void sadd(String key, List<String> members) {

		String[] strs = members.toArray(new String[members.size()]);
		redisTemplate.opsForSet().add(key, strs);

	}

	/**
	 * 返回集合中成员数量
	 * 
	 * @param key
	 * @return
	 */
	public static long scard(String key) {

		return redisTemplate.opsForSet().size(key);
	}

	/**
	 * 返回给定集合的差集，注意是key1 比 key2 少了什么
	 * 
	 * @param key1
	 * @param key2
	 * @return
	 */
	public static Set<String> sdiff(String key1, String key2) {

		return redisTemplate.opsForSet().difference(key1, key2);

	}

	/**
	 * 返回给定集合的差集，并存储在destination,(返回key1 内容比 key2 内容多的成员)
	 * 
	 * @param destination
	 * @param key1
	 * @param key2
	 * 
	 * 命令行：sdiff inner intersect 程序： difference inner intersect
	 * 
	 */
	public static void sdiffAndStore(String key1, String key2, String destination) {

		redisTemplate.opsForSet().differenceAndStore(key1, key2, destination);

	}

	/**
	 * 返回给定集合的交集
	 * 
	 * @param key1
	 * @param key2
	 * @return
	 */
	public static Set<String> sinter(String key1, String key2) {

		return redisTemplate.opsForSet().intersect(key1, key2);

	}

	/**
	 * 返回给定集合的交集，并存储在destination
	 * 
	 * @param destination
	 * @param key1
	 * @param key2
	 * 
	 */
	public static void sinterAndStore(String destination, String key1, String key2) {

		redisTemplate.opsForSet().intersectAndStore(key1, key2, destination);

	}

	/**
	 * 返回指定key中所有成员
	 * 
	 * @param key
	 * @return
	 */
	public static Set<String> smembers(String key) {

		return redisTemplate.opsForSet().members(key);

	}
	// =================================Set end==============================================

	// ==========================redis message delay begin===========================

	public static boolean zadd(String key, String value, long score) {
		return redisTemplate.opsForZSet().add(key, value, score);
	}

	public static long countList(String key) {
		return redisTemplate.opsForList().size(key);
	}

	public static Set<String> zrangeByScore(String key, long start, long end, boolean orderByDesc) {
		if (orderByDesc) {
			return redisTemplate.opsForZSet().range(key, start, end);
		} else {
			return redisTemplate.opsForZSet().reverseRange(key, start, end);
		}
	}

	/**
	 * 获取redis 指定位置之间的元素
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public static List<String> rangeList(String key, long start, long end) {
		return redisTemplate.opsForList().range(key, start, end);
	}

	/**
	 * 获取zset中成员的score值
	 * 
	 * @param key
	 * @param member
	 * @return
	 */
	public static long getScore(String key, String member) {
		return redisTemplate.opsForZSet().score(key, member).longValue();
	}

	/**
	 * 往list中放值
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean lpush(String key, String value) {
		redisTemplate.opsForList().rightPush(key, value);
		return true;
	}

	/**
	 * 删除 list 所有成员
	 * 
	 * @param key
	 * @param count
	 * @param value
	 * @return
	 */
	public static boolean removeListValue(String key, long count, String value) {
		redisTemplate.opsForList().remove(key, count, value);
		return true;
	}

	/**
	 * 删除list 所有成员
	 * 
	 * @param key
	 * @param count
	 * @param values
	 * @return
	 */
	public static int removeListValue(String key, long count, List<String> values) {
		int result = 0;
		if (values != null && values.size() > 0) {
			for (String value : values) {
				if (removeListValue(key, count, value)) {
					result++;
				}
			}
		}
		return result;
	}

	/**
	 * 删除list 所有成员
	 * 
	 * @param key
	 * @param values
	 * @return
	 */
	public static int removeListValue(String key, List<String> values) {
		return removeListValue(key, 1, values);
	}

	/**
	 * 删除list 所有成员
	 * 
	 * @param key
	 * @param val
	 */
	public static void zdel(String key, String... val) {
		redisTemplate.opsForZSet().remove(key, val);
	}

	// ==========================redis message delay end===========================
	public static void main(String[] args) {
		System.out.println(Long.parseLong("1528880353424"));

	}
}
