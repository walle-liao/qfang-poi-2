package com.qfang.poi.excel.export.support;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.qfang.poi.FileType;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2017年6月30日
 * @since 1.0
 */
public class Excel07Export extends AbstractExcelExport {

	@Override
	public FileType supportFileType() {
		return FileType.EXCEL07;
	}

	@Override
	protected Workbook newWorkbookInstance() {
		return new XSSFWorkbook();
	}

}
