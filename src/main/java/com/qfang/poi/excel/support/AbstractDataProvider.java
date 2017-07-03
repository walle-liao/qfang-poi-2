package com.qfang.poi.excel.support;

import com.qfang.poi.excel.ColumnValueHandler;
import com.qfang.poi.excel.DataProvider;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2017年6月29日
 * @since 1.0
 */
public abstract class AbstractDataProvider<T> implements DataProvider<T> {

	@Override
	public void registerValueHandler(String key, ColumnValueHandler valueHandler) {
		
	}

	@Override
	public void removeValueHandler(String key) {
		
	}

	@Override
	public ColumnValueHandler getValueHandler(String key) {
		return null;
	}

	@Override
	public boolean containsValueHandler(String key) {
		return false;
	}

}
