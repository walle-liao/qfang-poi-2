package com.qfang.poi.excel.export.data;

import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2017年7月6日
 * @since 1.0
 */
public class RedisCacheDataProvider<T> extends AbstractCacheableDataProvider<T> {
	
	private RedisTemplate<String, T> redisTemplate;

	public RedisCacheDataProvider(String key, PageDataLoader<T> pageDataLoader) {
		super(key, pageDataLoader);
	}

	public RedisCacheDataProvider(String key, PageDataLoader<T> pageDataLoader, int pageSize) {
		super(key, pageDataLoader, pageSize);
	}
	
	
	@Override
	protected Consumer<Collection<T>> pageDataConsumer() {
		BoundListOperations<String, T> listOperations = redisTemplate.boundListOps(key);
		listOperations.expire(DEFAULT_CACHE_TIME_OUT, TimeUnit.SECONDS);
		return pageDatas -> pageDatas.forEach(listOperations::rightPush);
	}

	@Override
	protected Supplier<DataCollector<T>> dataCollectorSupplier() {
		return null;
	}

}
