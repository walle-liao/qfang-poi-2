package com.qfang.poi.excel;

import com.qfang.poi.excel.model.ExcelDocument;
import com.qfang.poi.excel.model.ExcelSheet;
import com.qfang.poi.excel.support.DefaultSheetNameGenerator;

/**
 * sheet 页名称生成器
 * 默认生成规则按 sheet 页所在索引生成 Sheet1, Sheet2, Sheet3, etc, {@link DefaultSheetNameGenerator}
 * 支持扩展，例如在多数据源导出（导出多个城市数据时）可以按城市名称生成 sheet 页名称（深圳，广州，中山.. 这样的 sheet 页名称）
 * 
 * @author liaozhicheng
 * @date 2017年6月30日
 * @since 1.0
 */
public interface SheetNameGenerator {

	String generateSheetName(ExcelSheet excelSheet, ExcelDocument excelDocument);
	
}
