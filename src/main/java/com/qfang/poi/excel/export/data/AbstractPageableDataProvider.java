package com.qfang.poi.excel.export.data;

import com.qfang.poi.excel.export.PageHelper;

/**
 * 提供分页数据加载功能
 * 
 * @author liaozhicheng
 * @date 2017年7月6日
 * @since 1.0
 */
public abstract class AbstractPageableDataProvider<T> extends AbstractExportDataProvider<T> {
	
	// 分页数据加载器
	protected final PageDataLoader<T> pageDataLoader;

    // 分页查询时，每页查看的记录数
    protected final int pageSize;
    
    protected AbstractPageableDataProvider(PageDataLoader<T> pageDataLoader) {
    	this(pageDataLoader, PageHelper.DEFAULT_PAGE_SIZE);
    }

    protected AbstractPageableDataProvider(PageDataLoader<T> pageDataLoader, int pageSize) {
    	this.pageDataLoader = pageDataLoader;
    	this.pageSize = pageSize;
    }

	@Override
	public DataCollector<T> loadDatas() {
		int totalRecord = this.pageDataLoader.selectTotalCount();
        PageHelper pageHelper = new PageHelper(totalRecord, this.pageSize);
		return pagingLoadDatas(pageHelper);
	}
    
	protected abstract DataCollector<T> pagingLoadDatas(PageHelper pageHelper);
	
}
