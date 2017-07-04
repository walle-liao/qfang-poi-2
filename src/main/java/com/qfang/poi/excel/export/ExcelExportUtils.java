package com.qfang.poi.excel.export;

import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.qfang.poi.FileType;
import com.qfang.poi.excel.ColumnValueHandler;
import com.qfang.poi.excel.ColumnValueHandlerRegistry;
import com.qfang.poi.excel.DataProvider;
import com.qfang.poi.excel.model.ExcelSheet;
import com.qfang.poi.excel.model.SheetHead;
import com.qfang.poi.excel.model.SheetHeadColumn;
import com.qfang.poi.utils.Assert;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2017年6月30日
 * @since 1.0
 */
final class ExcelExportUtils {
	
	private static final Logger LOG = Logger.getLogger(ExcelExportUtils.class);

	public static boolean doExport(OutputStream os, ExcelExportDocument exportDocument) {
		try(Workbook workbook = newWorkbookInstance(exportDocument)) {
			writeSheets(workbook, exportDocument);
			workbook.write(os);
			return true;
		} catch (Exception e) {
			LOG.error("write excel error", e);
		}
		return false;
	}
	
	protected static Workbook newWorkbookInstance(ExcelExportDocument document) {
		FileType fileType = document.getFileType();
		if(FileType.EXCEL97 == fileType)
			return new HSSFWorkbook();
		
		if(FileType.EXCEL07 == fileType && document instanceof LargeExcelExportDocument) {
			LargeExcelExportDocument doc = (LargeExcelExportDocument) document;
			return new SXSSFWorkbook(doc.getWindowSize());
		}
		
		return new XSSFWorkbook();		
	}
	
	
	protected static void writeSheets(Workbook workbook, ExcelExportDocument excelDocument) {
		List<ExcelSheet> excelSheets = excelDocument.getSheets();
		Assert.isTrue(CollectionUtils.isNotEmpty(excelSheets), "No sheet page find in excel document: " + excelDocument.getFileName());
		
		for(ExcelSheet excelSheet : excelSheets) {
			Sheet sheet = workbook.createSheet(excelSheet.getName());
			writeSheet(workbook, excelSheet, sheet);
		}
	}
	
	protected static void writeSheet(Workbook workbook, ExcelSheet excelSheet, Sheet sheet) {
		buildSheetHead(workbook, excelSheet, sheet);
		buildSheetBody(excelSheet, sheet, excelSheet.getHead().getHeadRows());
	}
	
	protected static void buildSheetHead(Workbook workbook, ExcelSheet excelSheet, Sheet sheet) {
		List<SheetHeadColumn> headColumns = excelSheet.getHead().getColumns();
		
		final Row row = sheet.createRow(0);
		for(int i = 0, len = headColumns.size(); i < len; i++) {
			SheetHeadColumn column = headColumns.get(i);
			final Cell cell = row.createCell(i);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(column.getTitle());
			
			CellStyle style = workbook.createCellStyle();
			style.setAlignment(CellStyle.ALIGN_LEFT);
			cell.setCellStyle(style);
			
			sheet.setColumnWidth(i, column.getWidth());
		}
	}
	
	protected static void buildSheetBody(ExcelSheet excelSheet, Sheet sheet, int startRows) {
		DataProvider<?> datasProvider = excelSheet.getDataProvider();
		if(!ExportDataProvider.class.isAssignableFrom(datasProvider.getClass())) {
			throw new IllegalArgumentException("");
		}
		
		Collection<?> datas = ((ExportDataProvider<?>) datasProvider).loadDatas();
		if(CollectionUtils.isEmpty(datas))
			return ;
		
		Iterator<?> iterator = datas.iterator();
		int rowNum = startRows;
		while (iterator.hasNext()) {
			Object data = iterator.next();
			buildRow(excelSheet, sheet, data, rowNum);
			rowNum++;
		}
	}
	
	protected static void buildRow(ExcelSheet excelSheet, Sheet sheet, Object data, int rowNum) {
		final SheetHead head = excelSheet.getHead();
		final ColumnValueHandlerRegistry valueHandlerRegistry = excelSheet.getColumnValueHandlerRegistry();
		final List<String> keys = head.getKeys();

		Row row = sheet.createRow(rowNum);
		for(int j = 0, l = keys.size(); j < l; j++) {
			String cellValue = "", key = keys.get(j);
			if(valueHandlerRegistry.containsValueHandler(key)) {
				ColumnValueHandler valueHandler = valueHandlerRegistry.getValueHandler(key);
				Object value = valueHandler.handle(data);
				cellValue = value != null ? value.toString() : cellValue;
			} else {
				cellValue = getValue(key, data);
			}

			// 创建单元格
			Cell cell = row.createCell(j);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(cellValue);
		}
	}
	
	private static String getValue(String key, Object data) {
		Object value = null;
		if(Map.class.isAssignableFrom(data.getClass())) {
			value = ((Map<?, ?>) data).get(key);
		} else {
			try {
				value = BeanUtils.getProperty(data, key);
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				LOG.error("get property error, property name : " + key, e);
			}
		}
		return value != null ? value.toString() : "";
	}

}
