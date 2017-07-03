package com.qfang.poi.excel;

/**
 * excel 导入/导出时列值处理器
 * 
 * @author liaozhicheng
 * @since 1.0
 */
public interface ColumnValueHandler {
	
	Object handle(Object value);
	
}
