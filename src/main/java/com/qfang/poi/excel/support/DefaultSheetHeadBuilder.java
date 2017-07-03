package com.qfang.poi.excel.support;

import com.qfang.poi.excel.SheetHeadBuilder;
import com.qfang.poi.excel.model.SheetHead;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2017年6月29日
 * @since 1.0
 */
public class DefaultSheetHeadBuilder implements SheetHeadBuilder {

	private final SheetHead head;

	public DefaultSheetHeadBuilder() {
		this.head = new SheetHead();
	}

	public DefaultSheetHeadBuilder append(String title, String key, int width) {
		this.head.appendColumn(title, key, width);
		return this;
	}

	public DefaultSheetHeadBuilder append(String title, String key) {
		this.head.appendColumn(title, key);
		return this;
	}

	@Override
	public SheetHead build() {
		return this.head;
	}

}
