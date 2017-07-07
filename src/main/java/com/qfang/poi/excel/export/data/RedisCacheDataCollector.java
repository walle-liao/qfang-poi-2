package com.qfang.poi.excel.export.data;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.LinkedList;
import java.util.List;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2017年7月6日
 * @since 1.0
 */
public class RedisCacheDataCollector<T> implements DataCollector<T> {

	// 默认每次从缓存中抓取 500 条数据进行写excel，写完这个批次后再抓取后500条，避免频繁和缓存服务器交互
	// 根据单条数据类型的大小来设置该值，在频繁的网络交互和单次网络交互传输数据量大小之间进行平衡
	private final static int DEFAULT_FETCH_STEP = 500;

	private final String key;
	private final BoundListOperations<String, T> listOperations;
	private final int totalSize;
	private final int fetchStep;
	private final LinkedList<T> fetchCachedList;
	private int readIndex = 0;
	private int fetchIndex = 0;
	
	public RedisCacheDataCollector(String key, RedisTemplate<String, T> redisTemplate) {
		this(key, redisTemplate, DEFAULT_FETCH_STEP);
	}

	public RedisCacheDataCollector(String key, RedisTemplate<String, T> redisTemplate, int fetchStep) {
		this.key = key;
		this.listOperations = redisTemplate.boundListOps(this.key);
		this.totalSize = Math.toIntExact(this.listOperations.size());
		this.fetchStep = fetchStep;
		this.fetchCachedList = new LinkedList<>();
	}
	
	@Override
	public boolean hasNext() {
		return readIndex < totalSize;
	}

	@Override
	public T next() {
		if(!fetchCachedList.isEmpty()) {
			readIndex++;
			return fetchCachedList.removeFirst();
		}

		this.doFetch();
		readIndex++;
		return fetchCachedList.removeFirst();
	}

	private void doFetch() {
		List<T> tmpList = listOperations.range(fetchIndex, fetchIndex + this.fetchStep);
		if(CollectionUtils.isEmpty(tmpList)) {
			// 如果抓取不到数据，则让 excel 写线程立即跳出循环，可能会有如下原因导致读取不到数据：
			// 1、此时缓存中对应的 key 刚好失效
			// 2、缓存服务器变为不可用
			this.readIndex = this.totalSize;
		}
		fetchIndex += this.fetchStep;
		fetchCachedList.addAll(tmpList);
	}

}
