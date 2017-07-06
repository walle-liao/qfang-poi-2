package com.qfang.poi.excel.export.data;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2017年7月6日
 * @since 1.0
 */
public class RedisCacheDataCollector<T> implements DataCollector<T> {

	private final String key;
	private final AtomicInteger liveTaskSize;
	private final BoundListOperations<String, T> listOperations;
	
	public RedisCacheDataCollector(String key, AtomicInteger liveTaskSize, RedisTemplate<String, T> redisTemplate) {
		this.key = key;
		this.liveTaskSize = liveTaskSize;
		this.listOperations = redisTemplate.boundListOps(key);
	}
	
	@Override
	public boolean hasNext() {
//		return this.liveTaskSize.get() > 0 || listOperations.
		return true;
	}

	@Override
	public T next() {
		return null;
	}

}
