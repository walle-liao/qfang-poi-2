package com.qfang.poi.excel.export.support;

import java.util.List;

import com.google.common.collect.Lists;
import com.qfang.poi.FileType;
import com.qfang.poi.excel.export.ExcelExport;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2017年6月30日
 * @since 1.0
 */
public class ExcelExportFactory {
	
	private static final List<ExcelExport> excelExports = Lists.newArrayList(
			new Excel97Export(), 
			new Excel07Export()
	);
	
	public static ExcelExport getExcelExport(FileType fileType) {
		return excelExports.stream()
				.filter(ep -> ep.supportFileType() == fileType)
				.findFirst()
				.get();
	}

}
