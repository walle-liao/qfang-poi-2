package com.qfang.poi.excel.export.data;

import com.qfang.poi.excel.ColumnValueHandler;
import com.qfang.poi.excel.ColumnValueHandlerRegistry;
import com.qfang.poi.excel.support.DefaultColumnValueHandlerRegistry;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2017年6月29日
 * @since 1.0
 */
public abstract class AbstractExportDataProvider<T> implements ExportDataProvider<T> {

	private ColumnValueHandlerRegistry valueHandlerRegistry = new DefaultColumnValueHandlerRegistry();

	@Override
	public void registerValueHandler(String key, ColumnValueHandler valueHandler) {
		this.valueHandlerRegistry.registerValueHandler(key, valueHandler);
	}

	@Override
	public void removeValueHandler(String key) {
		this.valueHandlerRegistry.removeValueHandler(key);
	}

	@Override
	public ColumnValueHandler getValueHandler(String key) {
		return this.valueHandlerRegistry.getValueHandler(key);
	}

	@Override
	public boolean containsValueHandler(String key) {
		return this.valueHandlerRegistry.containsValueHandler(key);
	}

}
