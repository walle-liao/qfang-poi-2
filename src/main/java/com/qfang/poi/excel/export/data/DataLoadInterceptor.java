package com.qfang.poi.excel.export.data;

import java.util.Collection;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2017年7月7日
 * @since 1.0
 */
public interface DataLoadInterceptor {
	
	default void beforeDataLoad() {
		// nothing to do ...
	}
	
	default void afterDataLoad(Collection<?> datas) {
		// nothing to do ...
	}

}
