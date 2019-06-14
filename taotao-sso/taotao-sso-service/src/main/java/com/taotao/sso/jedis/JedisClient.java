package com.taotao.sso.jedis;

public interface JedisClient {

	String set(String key, String value);
	String get(String key);
	Boolean exists(String key);
	Long expire(String key, int seconds);
	Long ttl(String key);
	Long incr(String key);
	Long hset(String key, String field, String value);
	String hget(String key, String field);
	Long hdel(String key, String... field);

	/**
	 *
	 * @param key
	 * @return long 返回删除执行的条数
	 */
	Long del(String key);
}
