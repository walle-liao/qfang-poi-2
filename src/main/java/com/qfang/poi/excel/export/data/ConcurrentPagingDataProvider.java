package com.qfang.poi.excel.export.data;

import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.qfang.poi.excel.export.PageHelper;

/**
 * 
 * TODO
 * 
 * @author liaozhicheng
 * @date 2017年7月3日
 * @since 1.0
 */
public class ConcurrentPagingDataProvider<T> extends AbstractConcurrentableDataProvider<T> {

    // 数据缓冲队列
    private final Queue<T> dataQueue;

    public ConcurrentPagingDataProvider(PageDataLoader<T> pageDataLoader) {
        this(pageDataLoader, PageHelper.DEFAULT_PAGE_SIZE);
    }

    public ConcurrentPagingDataProvider(PageDataLoader<T> pageDataLoader, int pageSize) {
    	super(pageDataLoader, pageSize);
        this.dataQueue = new ConcurrentLinkedQueue<T>();
    }

	@Override
	protected Consumer<Collection<T>> pageDataConsumer() {
		return pageDatas -> dataQueue.addAll(pageDatas);
	}

	@Override
	protected Supplier<DataCollector<T>> dataCollectorSupplier() {
		return () -> new ConcurrentDataCollector<T>(dataQueue, liveTaskSize);
	}

}
