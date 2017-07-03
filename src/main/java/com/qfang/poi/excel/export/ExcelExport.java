package com.qfang.poi.excel.export;

import java.io.OutputStream;

import com.qfang.poi.FileType;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2017年6月30日
 * @since 1.0
 */
public interface ExcelExport {

	FileType supportFileType();
	
	boolean doExport(OutputStream os, ExcelExportDocument exportDocument);
	
}
