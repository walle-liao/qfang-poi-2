package com.qfang.poi.excel.export.data;

import com.qfang.poi.excel.export.PageHelper;

/**
 * 提供数据缓存功能
 * 
 * @author liaozhicheng
 * @date 2017年7月6日
 * @since 1.0
 */
public abstract class AbstractCacheableDataProvider<T> extends AbstractConcurrentableDataProvider<T> {
	
	// 数据默认超时时间(s)，默认保存 30min
	protected static final int DEFAULT_CACHE_TIME_OUT = 1800;
	
	protected final String key;

	protected AbstractCacheableDataProvider(String key, PageDataLoader<T> pageDataLoader) {
		this(key, pageDataLoader, PageHelper.DEFAULT_PAGE_SIZE);
	}
	
	protected AbstractCacheableDataProvider(String key, PageDataLoader<T> pageDataLoader, int pageSize) {
		super(pageDataLoader, pageSize);
		this.key = key;
	}


}
