package com.qfang.poi.excel.model;

/**
 * excel Sheet页表头中的每一列
 * 
 * @author liaozhicheng.cn@163.com
 * @since 1.0
 */
public class SheetHeadColumn {
	
	public static final int DEFAULT_COLUMN_WIDTH = 10;
	private static final int DEFAULT_MULTIPLE = 256;
	
	// 表头的名称
	private final String title;
	
	// 映射数据对应的 key
	private final String key;
	
	// 列宽
	private final int width;
	
	public SheetHeadColumn(String title, String key) {
		this(title, key, DEFAULT_COLUMN_WIDTH);
	}
	
	public SheetHeadColumn(String title, String key, int width) {
		this.title = title;
		this.key = key;
		this.width = width * DEFAULT_MULTIPLE;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getKey() {
		return key;
	}

	public int getWidth() {
		return width;
	}
	
}
