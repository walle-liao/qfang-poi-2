package com.qfang.poi.excel.export;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * thread-safe page helper
 * 注：如果采用分页方式加载导出数据的话，sql一定要带上排序条件，否则分页导出的所有数据中可能存在重复数据
 *
 * @author: liaozhicheng
 * @Timestamp: 2017/02/13 14:24
 */
public final class PageHelper {

    // 分页加载数据时，默认每页加载1000条记录
    public static final int DEFAULT_PAGE_SIZE = 1000;

    private final int totalRecord;  // 总记录数
    private final int pageSize;  // 分页查询时，每页查看的记录数
    private final AtomicInteger currentPage;  // 当前页，默认值为：1
    private final int totalPages;  // 总页数

    public PageHelper(int totalRecord, int pageSize) {
        this.totalRecord = totalRecord;
        this.pageSize = pageSize;

        this.currentPage = new AtomicInteger(1);
        this.totalPages = totalRecord / pageSize + ((totalRecord % pageSize == 0) ? 0 : 1);
    }

    public PageHelper(int totalRecord) {
        this(totalRecord, DEFAULT_PAGE_SIZE);
    }

    public int getNextPageNum() {
        return currentPage.getAndIncrement();
    }

    public int getTotalPages() {
        return this.totalPages;
    }

    public boolean isLastPage() {
        // 因为 page 是从第1页开始索引，所以必须大于 totalPages 才算加载完成
        return currentPage.get() > totalPages;
    }

	public int getTotalRecord() {
		return totalRecord;
	}

	public int getPageSize() {
		return pageSize;
	}

	public AtomicInteger getCurrentPage() {
		return currentPage;
	}

}
