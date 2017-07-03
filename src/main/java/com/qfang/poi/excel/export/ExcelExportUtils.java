package com.qfang.poi.excel.export;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Objects;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.qfang.poi.FileType;
import com.qfang.poi.excel.model.ExcelSheet;
import com.qfang.poi.utils.Assert;

/**
 * excel 导出工具，支持导出多个 sheet 页
 * 
 * @author liaozhicheng.cn@163.com
 * @since 1.0
 */
@Deprecated
class ExcelExportUtils {
	
	public static boolean doExport(OutputStream os, ExcelExportDocument excelDocument) {
		Objects.requireNonNull(excelDocument);
		
		FileType fileType = excelDocument.getFileType();
		switch (fileType) {
			case EXCEL97:
				return doExport97(os, excelDocument);
				
			case EXCEL07:
				return doExport07(os, excelDocument);
				
			default:
				throw new IllegalArgumentException("error fileType : " + fileType);
		}
	}

//	public static boolean doLargeExport(OutputStream os, LargeExcelExportDocument excelDocument, boolean concurrent) {
//		Objects.requireNonNull(excelDocument);
//		
//		SXSSFWorkbook sxssfWorkbook = null;
//		try {
//			sxssfWorkbook = new SXSSFWorkbook(excelDocument.getWindowSize());
//			
//			List<ExcelSheet> sheets = excelDocument.getSheets();
//			Assert.isTrue(sheets.size() > 0);
//			
//			for(int i = 0; i < sheets.size(); i++) {
//				ExcelSheet excelSheet = sheets.get(i);
//				SheetListener listener = excelSheet.getSheetListener();
//				if(listener != null) listener.beforeBuild(excelSheet);
//
//				Sheet sheet = sxssfWorkbook.createSheet(excelSheet.getName());
//				buildSheetHead(sxssfWorkbook, excelSheet, sheet);
//				if(concurrent) {
//					// 使用多线程并发加载数据
//					buildConcurrentLargeSheetBody(excelSheet, sheet);
//				} else {
//					buildLargeSheetBody(excelSheet, sheet);
//				}
//
//				if(listener != null) listener.afterBuild(excelSheet);
//			}
//			sxssfWorkbook.write(os);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		} finally {
//			if (sxssfWorkbook != null) {
//				sxssfWorkbook.dispose();
//				try {
//					sxssfWorkbook.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		return true;
//	}
	
	private static boolean doExport97(OutputStream os, ExcelExportDocument excelDocument) {
		HSSFWorkbook hssfWorkbook = null;
		
		try {
			hssfWorkbook = new HSSFWorkbook();
			writeSheets(hssfWorkbook, excelDocument);
			hssfWorkbook.write(os);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (hssfWorkbook != null)
					hssfWorkbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	private static boolean doExport07(OutputStream os, ExcelExportDocument excelDocument) {
		XSSFWorkbook xssfWorkbook = null;
		try {
			xssfWorkbook = new XSSFWorkbook();
			writeSheets(xssfWorkbook, excelDocument);
			xssfWorkbook.write(os);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (xssfWorkbook != null) {
				try {
					xssfWorkbook.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	
	private static void writeSheets(Workbook workbook, ExcelExportDocument excelDocument) {
		List<ExcelSheet> sheets = excelDocument.getSheets();
		Assert.isTrue(sheets.size() > 0);
		
		for(int i = 0; i < sheets.size(); i++) {
			ExcelSheet excelSheet = sheets.get(i);
			Sheet sheet = workbook.createSheet(excelSheet.getName());
			writeSheet(workbook, excelSheet, sheet);
		}
	}
	
	private static void writeSheet(Workbook workbook, ExcelSheet excelSheet, Sheet sheet) {
		try {
//			SheetListener listener = excelSheet.getSheetListener();
//			if(listener != null) listener.beforeBuild(excelSheet);

			buildSheetHead(workbook, excelSheet, sheet);
			buildSheetBody(excelSheet, sheet);

//			if(listener != null) listener.afterBuild(excelSheet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 生成表头
	private static void buildSheetHead(Workbook workbook, ExcelSheet excelSheet, Sheet sheet) {
//		SheetHead head = excelSheet.getHead();
//		List<String> titles = head.getTitles();
//		final Row row = sheet.createRow(0);
//		for (int i = 0; i < titles.size(); i++) {
//			final Cell cell = row.createCell(i);
//			cell.setCellType(Cell.CELL_TYPE_STRING);
//			cell.setCellValue(titles.get(i));
//			
//			CellStyle style = workbook.createCellStyle();
//			style.setAlignment(CellStyle.ALIGN_LEFT);
//			cell.setCellStyle(style);
//		}
	}
	
	private static void buildSheetBody(ExcelSheet excelSheet, Sheet sheet) throws IllegalArgumentException, IllegalAccessException {
//		Collection<?> datas = excelSheet.getDatas();
//		Collection<?> datas = new ArrayList<>();
//		if(CollectionUtils.isEmpty(datas))
//			return ;
//		
//		Iterator<?> iterator = datas.iterator();
//		int rowNum = 1;  // 从第二行开始插入数据
//		while (iterator.hasNext()) {
//			Object data = iterator.next();
//			buildRow(excelSheet, sheet, data, rowNum);
//			rowNum++;
//		}
	}
	
//	private static void buildLargeSheetBody(ExcelSheet excelSheet, Sheet sheet) 
//			throws IllegalArgumentException, IllegalAccessException {
//		final LargeExportDataProvider<?> datasProvider = (LargeExportDataProvider<?>) excelSheet.getDatasProvider();
//		int rowNum = 1;  // 从第二行开始插入数据
//		do {
//			Collection<?> datas = datasProvider.loadPageDatas();
//			Iterator<?> iterator = datas.iterator();
//			while (iterator.hasNext()) {
//				Object data = iterator.next();
//				buildRow(excelSheet, sheet, data, rowNum);
//				rowNum++;
//			}
//		} while(!datasProvider.isLast());
//	}

//	private static void buildConcurrentLargeSheetBody(ExcelSheet excelSheet, Sheet sheet) 
//			throws IllegalArgumentException, IllegalAccessException {
//		final ConcurrentExportDataProvider<?> dataProvider = (ConcurrentExportDataProvider<?>) excelSheet.getDatasProvider();
//		dataProvider.loadDatas();
//		
//		int rowNum = 1;  // 从第二行开始插入数据
//		while(!dataProvider.isLast()) {
//			Object data = dataProvider.next();
//			
//			if(data == null)
//				continue ;
//			
//			buildRow(excelSheet, sheet, data, rowNum);
//			rowNum++;
//		}
//	}

//	private static void buildRow(ExcelSheet excelSheet, Sheet sheet, Object data, int rowNum) {
//		final SheetHead head = excelSheet.getHead();
//		final DataProvider<?> datasProvider = excelSheet.getDatasProvider();
//		final Map<String, SheetHeadColumn> headColumnMap = head.getHeadColumnMap();
//		final Field[] fields = head.getFields();
//		final List<String> fieldNames = head.getFieldNames();
//		
//		Row row = sheet.createRow(rowNum);
//		boolean dataHoldMap = Map.class.isAssignableFrom(data.getClass()) && fields == null;  // 查询返回的数据封装在 Map 中
//		
//		for(int j = 0, l = fieldNames.size(); j < l; j++) {
//			String cellValue = "", fieldName = fieldNames.get(j);
//			if(dataHoldMap) {
//				Map<?, ?> dataMap = (Map<?, ?>) data;
//				cellValue = getMapValue(fieldName, dataMap, datasProvider);
//			} else {
//				Field field = fields[j];
//				cellValue = getFieldValue(field, data, datasProvider);
//			}
//			
//			// 创建单元格
//			Cell cell = row.createCell(j);
//			cell.setCellType(Cell.CELL_TYPE_STRING);
//			cell.setCellValue(cellValue);
//			
//			// 设置列宽，只要设置一次就可以了
//			SheetHeadColumn headColumn = headColumnMap.get(fieldName);
//			sheet.setColumnWidth(j, headColumn.getWidth());
//		}
//	}
	
//	private static String getMapValue(String key, Map<?, ?> dataMap, DataProvider<?> datasProvider) {
//		String cellValue = "";
//		if(datasProvider.existsValueHandler(key)) {
//			ColumnValueHandler valueHandler = datasProvider.getValueHandler(key);
//			cellValue = valueHandler.processExportValue(dataMap);
//		} else {
//			Object value = dataMap.get(key);
//			if(value != null)
//				cellValue = value.toString();
//		}
//		return cellValue;
//	}
//	
//	private static String getFieldValue(Field field, Object data, DataProvider<?> datasProvider)
//			throws IllegalArgumentException, IllegalAccessException {
//		// 处理单元格的值
//		String cellValue = "";
//		final String fieldName = field.getName();
//		if(datasProvider.existsValueHandler(fieldName)) {
//			ColumnValueHandler valueHandler = datasProvider.getValueHandler(fieldName);
//			cellValue = valueHandler.processExportValue(data);
//		} else {
//			ReflectUtils.makeAccessible(field);
//			Object fieldValue = field.get(data);
//			if(fieldValue != null)
//				cellValue = fieldValue.toString();
//		}
//		return cellValue;
//	}
	
}
