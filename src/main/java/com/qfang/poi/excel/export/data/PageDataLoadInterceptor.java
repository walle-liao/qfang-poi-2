package com.qfang.poi.excel.export.data;

import java.util.Collection;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2017年7月7日
 * @since 1.0
 */
public interface PageDataLoadInterceptor extends DataLoadInterceptor {
	
	void beforePageDataLoad(int pageNum, int pageSize);
	
	void afterPageDataLoad(int pageNum, int pageSize, Collection<?> pageDatas);

}
