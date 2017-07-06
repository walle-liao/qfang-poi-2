package com.qfang.poi.excel.export.data;

import org.apache.log4j.Logger;

import com.qfang.poi.excel.export.PageHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by walle on 2017/7/1.
 */
public class PagingDataProvider<T> extends AbstractPageableDataProvider<T> {

    private static final Logger LOG = Logger.getLogger(PagingDataProvider.class);

    public PagingDataProvider(PageDataLoader<T> pageDataLoader) {
        this(pageDataLoader, PageHelper.DEFAULT_PAGE_SIZE);
    }

    public PagingDataProvider(PageDataLoader<T> pageDataLoader, int pageSize) {
        super(pageDataLoader, pageSize);
    }

	@Override
	protected DataCollector<T> pagingLoadDatas(PageHelper pageHelper) {
		List<T> datas = new ArrayList<>();
        do {
            long start = System.currentTimeMillis();

            int currentPageNum = pageHelper.getNextPageNum();
            int pageSize = pageHelper.getPageSize();
            Collection<T> pageDatas = this.pageDataLoader.loadPageDatas(currentPageNum, pageSize);
            datas.addAll(pageDatas);

            long end = System.currentTimeMillis();
            if(LOG.isDebugEnabled()) {
                LOG.debug(
                        new StringBuilder().append("Load date in page number: ")
                                .append(currentPageNum).append(", page data size: ").append(datas.size())
                                .append(", use total time: ").append(end - start).append("ms")
                                .toString()
                );
            }
        } while(!pageHelper.isLastPage());
        return new DefaultDataCollector<T>(datas);
	}

}
