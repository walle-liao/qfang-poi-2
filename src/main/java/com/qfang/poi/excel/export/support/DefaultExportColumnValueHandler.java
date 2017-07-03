package com.qfang.poi.excel.export.support;

import java.util.Date;

import org.apache.commons.lang.time.FastDateFormat;

import com.qfang.poi.excel.ColumnValueHandler;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2017年6月29日
 * @since 1.0
 */
public class DefaultExportColumnValueHandler implements ColumnValueHandler {

	private static final FastDateFormat format = FastDateFormat.getInstance("yyyy-MM-dd");
	
	private static final DefaultExportColumnValueHandler INSTANCE = new DefaultExportColumnValueHandler();
	
	public static ColumnValueHandler getInstance() {
		return INSTANCE;
	}
	
	@Override
	public String handle(Object value) {
		if(value == null)
			return "";
		
		if(value instanceof Date)
			return format.format((Date) value);
		
		return value.toString();
	}

}
