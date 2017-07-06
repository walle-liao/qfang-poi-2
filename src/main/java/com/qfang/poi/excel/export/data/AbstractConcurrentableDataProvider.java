package com.qfang.poi.excel.export.data;

import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.apache.log4j.Logger;

import com.qfang.poi.excel.export.PageHelper;

/**
 * 提供并发的数据加载功能
 * 
 * @author liaozhicheng
 * @date 2017年7月6日
 * @since 1.0
 */
public abstract class AbstractConcurrentableDataProvider<T> extends AbstractPageableDataProvider<T> {
	
	private static final Logger LOG = Logger.getLogger(AbstractConcurrentableDataProvider.class);

	private static final int MAX_MUM_POOL_SIZE = Runtime.getRuntime().availableProcessors() * 2;
    private final static ExecutorService executorService = Executors.newFixedThreadPool(MAX_MUM_POOL_SIZE);

    // 存活的任务数量，只有当所有的数据加载任务都退出时，整个数据加载才算完成
    protected AtomicInteger liveTaskSize;

   	protected AbstractConcurrentableDataProvider(PageDataLoader<T> pageDataLoader) {
   		super(pageDataLoader);
   	}
   	
   	protected AbstractConcurrentableDataProvider(PageDataLoader<T> pageDataLoader, int pageSize) {
   		super(pageDataLoader, pageSize);
   	}
    
	@Override
	protected DataCollector<T> pagingLoadDatas(PageHelper pageHelper) {
		int totalPages = pageHelper.getTotalPages();
        this.liveTaskSize = new AtomicInteger(totalPages);

        for(int i = 0; i < totalPages; i++) {
            executorService.execute(() -> {
                try {
                    long start = System.currentTimeMillis();

                    int pageNum = pageHelper.getNextPageNum();
                    Collection<T> datas = this.pageDataLoader.loadPageDatas(pageNum, pageSize);
                    pageDataConsumer().accept(datas);

                    long end = System.currentTimeMillis();
                    LOG.info(new StringBuilder().append("load date in page number: ")
                            .append(pageNum).append(", page data size: ").append(datas.size())
                            .append(", use total time: ").append(end - start).append("ms")
                            .append(", current thread: ").append(Thread.currentThread().getName()).toString());
                } finally {
                    int lt = liveTaskSize.decrementAndGet();
                    LOG.info("current live task size : " + lt);
                }
            });
        }
        return dataCollectorSupplier().get();
	}
	
	protected abstract Consumer<Collection<T>> pageDataConsumer();
	
	protected abstract Supplier<DataCollector<T>> dataCollectorSupplier();
	
}
