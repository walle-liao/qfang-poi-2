package com.qfang.poi.excel.export;

/**
 * 
 * TODO
 * 
 * @author liaozhicheng
 * @date 2017年7月4日
 * @since 1.0
 */
public class LargeExcelExportDocument extends ExcelExportDocument {
	
	private static final int DEFAULT_WINDOW_SIZE = 100;
	
	private int windowSize;

	public LargeExcelExportDocument(String fileName) {
		this(fileName,  DEFAULT_WINDOW_SIZE);
	}
	
	public LargeExcelExportDocument(String fileName, int windowSize) {
		super(fileName);
		this.windowSize = DEFAULT_WINDOW_SIZE;
	}

	public int getWindowSize() {
		return windowSize;
	}

	public void setWindowSize(int windowSize) {
		this.windowSize = windowSize;
	}

}
