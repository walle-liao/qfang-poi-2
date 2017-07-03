package com.qfang.poi;

import com.qfang.poi.utils.FileUtils;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2017年4月25日
 * @since 1.0
 */
public abstract class AbstractOfficeDocument implements OfficeDocument {

	// 文件名
	protected final String fileName;
	
	// 文件类型
	protected final FileType fileType;
	
	protected AbstractOfficeDocument(String fileName) {
		this.fileName = fileName;
		this.fileType = FileUtils.getFileType(fileName);
	}

	@Override
	public String getFileName() {
		return this.fileName;
	}

	@Override
	public FileType getFileType() {
		return this.fileType;
	}

}
