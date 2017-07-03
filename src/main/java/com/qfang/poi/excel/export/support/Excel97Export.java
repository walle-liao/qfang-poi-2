package com.qfang.poi.excel.export.support;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import com.qfang.poi.FileType;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2017年6月30日
 * @since 1.0
 */
public class Excel97Export extends AbstractExcelExport {

	@Override
	public FileType supportFileType() {
		return FileType.EXCEL97;
	}

	@Override
	protected Workbook newWorkbookInstance() {
		return new HSSFWorkbook();
	}

}
