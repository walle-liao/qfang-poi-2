package com.qfang.poi.excel.export;

import java.util.Collection;

/**
 * Created by walle on 2017/7/1.
 */
public interface PageDataLoader<T> {

    /**
     * 返回总记录数
     * @return
     */
    int selectTotalCount();

    /**
     * 分页加载
     * @param pageNum
     * @param pageSize
     * @return
     */
    Collection<T> loadPageDatas(int pageNum, int pageSize);

}
