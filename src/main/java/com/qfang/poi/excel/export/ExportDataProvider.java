package com.qfang.poi.excel.export;

import java.util.Collection;

import com.qfang.poi.excel.DataProvider;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2017年6月29日
 * @since 1.0
 */
public interface ExportDataProvider<T> extends DataProvider<T> {

	DataCollector<T> loadDatas();
	
}
