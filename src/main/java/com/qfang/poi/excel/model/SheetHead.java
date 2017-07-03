package com.qfang.poi.excel.model;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Sheet页对应的表头（导入导出都需要有对应的表头）
 * 
 * @author liaozhicheng.cn@163.com
 * @since 1.0
 */
public class SheetHead {
	
	private final List<SheetHeadColumn> columns = new ArrayList<>();
	
	// 表头所占行高（默认表头占1行）
	private int headRows = 1;
	
	public SheetHead appendColumn(String title, String key) {
		this.columns.add(new SheetHeadColumn(title, key));
		return this;
	}

	public SheetHead appendColumn(String title, String key, int width) {
		this.columns.add(new SheetHeadColumn(title, key, width));
		return this;
	}
	
	public SheetHead appendColumn(SheetHeadColumn headColumn) {
		this.columns.add(headColumn);
		return this;
	}

	public SheetHead appendColumn(Collection<SheetHeadColumn> headColumns) {
		this.columns.addAll(headColumns);
		return this;
	}

	public List<String> getKeys() {
		return this.columns.stream().map(SheetHeadColumn::getKey).collect(toList());
	}
	
	public int getHeadRows() {
		return headRows;
	}

	public void setHeadRows(int headRows) {
		this.headRows = headRows;
	}

	public List<SheetHeadColumn> getColumns() {
		return columns;
	}
	
}
