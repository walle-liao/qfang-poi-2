package com.qfang.poi.excel.support;

import com.qfang.poi.excel.ColumnValueHandler;
import org.apache.commons.lang.time.FastDateFormat;

import java.util.Date;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2017年6月29日
 * @since 1.0
 */
public abstract class DefaultColumnValueHandler implements ColumnValueHandler {
	
	protected static FastDateFormat format = FastDateFormat.getInstance("yyyy-MM-dd");
	
	private static final ColumnValueHandler INSTANCE = new DefaultColumnValueHandler() {
		@Override
		public Object handle(Object value) {
			if(value == null)
				return "";

			if(value instanceof Date)
				return format.format((Date) value);

			return value.toString();
		}
	};
	
	public static ColumnValueHandler getInstance() {
		return INSTANCE;
	}

}
