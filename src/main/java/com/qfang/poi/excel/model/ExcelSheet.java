package com.qfang.poi.excel.model;

import com.qfang.poi.excel.ColumnValueHandlerRegistry;
import com.qfang.poi.excel.DataProvider;
import com.qfang.poi.excel.SheetHeadBuilder;
import com.qfang.poi.excel.support.AnnotationSheetHeadBuilder;

/**
 * Excel文档里面的Sheet页
 * 
 * @author liaozhicheng.cn@163.com
 * @since 1.0
 */
public class ExcelSheet {
	
	// 表头
	private final SheetHead head;

	private final DataProvider<?> dataProvider;

	// sheet 页名称
	private String name;

	// 第几个 sheet 页 （0-based）
	private int index;
	
	public ExcelSheet(SheetHeadBuilder headBuilder, DataProvider<?> dataProvider) {
		this.head = headBuilder.build();
		this.dataProvider = dataProvider;
	}

	public static ExcelSheet createWithAnnotation(Class<?> clazz, DataProvider<?> dataProvider) {
		return new ExcelSheet(new AnnotationSheetHeadBuilder(clazz), dataProvider);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public SheetHead getHead() {
		return head;
	}
	
	public DataProvider<?> getDataProvider() {
		return dataProvider;
	}
	
	public ColumnValueHandlerRegistry getColumnValueHandlerRegistry() {
		return this.dataProvider;
	}
	
}
