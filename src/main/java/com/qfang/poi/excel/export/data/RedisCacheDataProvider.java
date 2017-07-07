package com.qfang.poi.excel.export.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.springframework.data.redis.core.BoundHashOperations;
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
	
	@SuppressWarnings("unchecked")
	@Override
	protected Consumer<Collection<T>> pageDataConsumer() {
		BoundHashOperations<String, Integer, Object> hashOperations = redisTemplate.boundHashOps(key);
		hashOperations.getOperations().boundListOps("datas");
		
		hashOperations.get("ttt");
		hashOperations.put(Integer.valueOf(1), "zzz");
		
		BoundListOperations<String, T> listOperations = redisTemplate.boundListOps(key);
		listOperations.expire(DEFAULT_CACHE_TIME_OUT, TimeUnit.SECONDS);
		return pageDatas -> listOperations.leftPushAll((T[]) pageDatas.toArray());
	}

	@Override
	protected Supplier<DataCollector<T>> dataCollectorSupplier() {
		return () -> new RedisCacheDataCollector<T>(this.key, this.redisTemplate);
	}

	
	class CacheDataHolder {
		
		// 数据
		private List<?> datas;
		
		// 总记录数
		private int totalCount;
		
		// 数据是否加载完成
		private boolean loadCompiled = false;
		
		private boolean loadSuccess = true; 
		
		public Map<String, Object> toMap() {
			Map<String, Object> result = new HashMap<>();
			result.put("loadSuccess", Boolean.valueOf(loadSuccess));
			
			return result;
		}
		
	}
	
}
