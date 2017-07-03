package com.qfang.poi.excel.export;

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * TODO
 * 
 * @author liaozhicheng
 * @date 2017年7月3日
 * @since 1.0
 */
public class ConcurrentLargeExportDataProvider<T> extends AbstractExportDataProvider<T> {

    private static final Logger LOG = Logger.getLogger(ConcurrentLargeExportDataProvider.class);

    // 最大30个线程同时加载数据，线程数量越多加载速度理论上更快，但是内存压力也越大
    private static final int MAX_MUM_POOL_SIZE = 30;
    private final static ExecutorService executorService = Executors.newFixedThreadPool(MAX_MUM_POOL_SIZE);

    private final PageDataLoader<T> pageDataLoader;

    // 分页查询时，每页查询的记录数
    private final int pageSize;

    // 数据缓冲队列
    private final Queue<T> dataQueue;

    // 存活的任务数量，只有当所有的数据加载任务都退出时，整个数据加载才算完成
    private AtomicInteger liveTaskSize;

    public ConcurrentLargeExportDataProvider(PageDataLoader<T> pageDataLoader) {
        this(pageDataLoader, PageHelper.DEFAULT_PAGE_SIZE);
    }

    public ConcurrentLargeExportDataProvider(PageDataLoader<T> pageDataLoader, int pageSize) {
    	this.pageDataLoader = pageDataLoader;
        this.pageSize = pageSize;
        this.dataQueue = new ConcurrentLinkedQueue<T>();
    }

    @Override
    public final Collection<T> loadDatas() {
        int totalRecord = this.pageDataLoader.selectTotalCount();
        PageHelper page = new PageHelper(totalRecord, this.pageSize);
        int totalPages = page.getTotalPages();
        this.liveTaskSize = new AtomicInteger(totalPages);

        for(int i = 0; i < totalPages; i++) {
            executorService.execute(() -> {
                try {
                    long start = System.currentTimeMillis();

                    int pageNum = page.getNextPageNum();
                    Collection<T> datas = this.pageDataLoader.loadPageDatas(pageNum, pageSize);
                    dataQueue.addAll(datas);

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
        return dataQueue;
    }

    public T next() {
        return dataQueue.poll();
    }

    public boolean isLast() {
        return liveTaskSize.get() == 0 && dataQueue.isEmpty();
    }

}
