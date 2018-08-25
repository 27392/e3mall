package cn.haohaoli.common.jedis;

import java.util.List;
import java.util.Set;

/**
 * @author Liwenhao
 * @date 2018/8/13 15:13
 */
public interface JedisClient {

	String set(String key, String value);

	String get(String key);

	Long del(String key);

	Boolean exists(String key);

	Long expire(String key, int seconds);

	Long ttl(String key);

	Long incr(String key);

	Long hset(String key, String field, String value);

	String hget(String key, String field);

	Set<String> hkeys(String key);

	List<String> hvals(String key);

	Boolean hexists(String key, String field);

	Long hdel(String key, String... field);

	/**
	 * 批量删除key
	 * @param key
	 * @return
	 */
	Long hdels(String key);

}
