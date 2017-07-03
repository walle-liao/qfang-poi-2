package com.qfang.poi;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2017年4月25日
 * @since 1.0
 */
public interface OfficeDocument {
	
	/**
	 * 文件名
	 * @return
	 */
	String getFileName();
	
	/**
	 * 文件类型
	 * @return
	 */
	FileType getFileType();

}
