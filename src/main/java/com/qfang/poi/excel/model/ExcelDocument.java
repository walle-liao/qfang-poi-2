package com.qfang.poi.excel.model;

import com.qfang.poi.AbstractOfficeDocument;
import com.qfang.poi.excel.DataProvider;
import com.qfang.poi.excel.SheetHeadBuilder;
import com.qfang.poi.excel.SheetNameGenerator;
import com.qfang.poi.excel.support.AnnotationSheetHeadBuilder;
import com.qfang.poi.excel.support.DefaultSheetNameGenerator;
import org.apache.commons.lang.StringUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2017年4月25日
 * @since 1.0
 */
public class ExcelDocument extends AbstractOfficeDocument {
	
	// excel 里面默认的 sheet 页
	private final List<ExcelSheet> sheets = new LinkedList<>();
	
	private SheetNameGenerator sheetNameGenerator;
	
	private Object sheetCreateMonitor = new Object();

	public ExcelDocument(String fileName) {
		super(fileName);
		this.sheetNameGenerator = new DefaultSheetNameGenerator();
	}

	/// ---------- create sheet -----------
	public ExcelDocument createSheet(SheetHeadBuilder headBuilder, DataProvider<?> datasProvider) {
		this.createSheet(headBuilder, datasProvider, "");
		return this;
	}
	
	public ExcelDocument createSheet(SheetHeadBuilder headBuilder, DataProvider<?> datasProvider, String sheetName) {
		synchronized (sheetCreateMonitor) {
			ExcelSheet sheet = new ExcelSheet(headBuilder, datasProvider);
			if(StringUtils.isBlank(sheetName)) {
				sheetName = this.sheetNameGenerator.generateSheetName(sheet, this);
			}
			sheet.setName(sheetName);
			sheet.setIndex(this.getNextIndex());
			this.addSheet(sheet);
		}
		return this;
	}

	public <T> ExcelDocument createSheetWithAnnotation(Class<T> annotationClass, DataProvider<T> dataProvider) {
		return this.createSheet(new AnnotationSheetHeadBuilder(annotationClass), dataProvider);
	}
	
	
	public void addSheet(ExcelSheet excelSheet) {
		this.sheets.add(excelSheet);
	}
	
	public List<ExcelSheet> getSheets() {
		return sheets;
	}

	public SheetNameGenerator getSheetNameGenerator() {
		return sheetNameGenerator;
	}

	public void setSheetNameGenerator(SheetNameGenerator sheetNameGenerator) {
		this.sheetNameGenerator = sheetNameGenerator;
	}

	private int getNextIndex() {
		return this.sheets.size();
	}

}
