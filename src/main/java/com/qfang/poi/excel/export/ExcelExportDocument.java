package com.qfang.poi.excel.export;

import java.io.OutputStream;

import com.qfang.poi.excel.export.support.ExcelExportFactory;
import com.qfang.poi.excel.model.ExcelDocument;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2017年6月29日
 * @since 1.0
 */
public class ExcelExportDocument extends ExcelDocument {

	public ExcelExportDocument(String fileName) {
		super(fileName);
	}

	public boolean doExport(OutputStream os) {
		return ExcelExportFactory.getExcelExport(getFileType()).doExport(os, this);
	}
	
}
