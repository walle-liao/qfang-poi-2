package com.qfang.poi.excel.export;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by walle on 2017/7/1.
 */
public class LargeExportDataProvider<T> extends AbstractExportDataProvider<T> {

    private static final Logger LOG = Logger.getLogger(LargeExportDataProvider.class);

    // 分页数据加载器
    private final PageDataLoader<T> pageDataLoader;

    // 分页查询时，每页查看的记录数
    private final int pageSize;

    public LargeExportDataProvider(PageDataLoader<T> pageDataLoader) {
        this(pageDataLoader, PageHelper.DEFAULT_PAGE_SIZE);
    }

    public LargeExportDataProvider(PageDataLoader<T> pageDataLoader, int pageSize) {
        this.pageDataLoader = pageDataLoader;
        this.pageSize = pageSize;
    }

    @Override
    public DataCollector<T> loadDatas() {
        int totalRecord = this.pageDataLoader.selectTotalCount();
        PageHelper pageHelper = new PageHelper(totalRecord, this.pageSize);

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
                );
            }
        } while(!pageHelper.isLastPage());

        return DefaultDataCollector.newInstance(datas);
    }

}
