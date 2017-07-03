package com.qfang.poi.excel.support;

import com.qfang.poi.excel.SheetNameGenerator;
import com.qfang.poi.excel.model.ExcelDocument;
import com.qfang.poi.excel.model.ExcelSheet;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2017年6月30日
 * @since 1.0
 */
public class DefaultSheetNameGenerator implements SheetNameGenerator {

	private static final String DEFAULT_SHEET_NAME = "Sheet";
	
	@Override
	public String generateSheetName(ExcelSheet excelSheet, ExcelDocument excelDocument) {
		return new StringBuilder(DEFAULT_SHEET_NAME).append(excelDocument.getSheets().size() + 1).toString();
	}

}
