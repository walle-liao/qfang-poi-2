package com.qfang.poi.excel.export.support;

import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.qfang.poi.excel.ColumnValueHandler;
import com.qfang.poi.excel.DataProvider;
import com.qfang.poi.excel.export.ExcelExport;
import com.qfang.poi.excel.export.ExcelExportDocument;
import com.qfang.poi.excel.export.ExportDataProvider;
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
public abstract class AbstractExcelExport implements ExcelExport {
	
	private static final Logger LOG = Logger.getLogger(AbstractExcelExport.class);

	@Override
	public boolean doExport(OutputStream os, ExcelExportDocument exportDocument) {
		try(Workbook workbook = newWorkbookInstance()) {
			writeSheets(workbook, exportDocument);
			workbook.write(os);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	protected abstract Workbook newWorkbookInstance();
	
	
	protected void writeSheets(Workbook workbook, ExcelExportDocument excelDocument) {
		List<ExcelSheet> excelSheets = excelDocument.getSheets();
		Assert.isTrue(CollectionUtils.isNotEmpty(excelSheets), "No sheet page find in excel document: " + excelDocument.getFileName());
		
		for(ExcelSheet excelSheet : excelSheets) {
			Sheet sheet = workbook.createSheet(excelSheet.getName());
			writeSheet(workbook, excelSheet, sheet);
		}
	}
	
	protected void writeSheet(Workbook workbook, ExcelSheet excelSheet, Sheet sheet) {
		buildSheetHead(workbook, excelSheet, sheet);
		buildSheetBody(excelSheet, sheet, excelSheet.getHead().getHeadRows());
	}
	
	protected void buildSheetHead(Workbook workbook, ExcelSheet excelSheet, Sheet sheet) {
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
	
	protected void buildSheetBody(ExcelSheet excelSheet, Sheet sheet, int startRows) {
		ExportDataProvider<?> datasProvider = (ExportDataProvider<?>) excelSheet.getDataProvider();
		Collection<?> datas = datasProvider.loadDatas();
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
	
	protected void buildRow(ExcelSheet excelSheet, Sheet sheet, Object data, int rowNum) {
		final SheetHead head = excelSheet.getHead();
		final DataProvider<?> datasProvider = excelSheet.getDataProvider();
		final List<String> keys = head.getKeys();

		Row row = sheet.createRow(rowNum);
		for(int j = 0, l = keys.size(); j < l; j++) {
			String cellValue = "", key = keys.get(j);
			if(datasProvider.containsValueHandler(key)) {
				ColumnValueHandler valueHandler = datasProvider.getValueHandler(key);
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
				value = BeanUtils.getSimpleProperty(data, key);
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				LOG.error("get property error, property name : " + key, e);
			}
		}
		return value != null ? value.toString() : "";
	}
	
}
