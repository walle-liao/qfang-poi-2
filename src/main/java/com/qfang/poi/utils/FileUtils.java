package com.qfang.poi.utils;

import java.io.File;

import org.apache.commons.lang.Validate;

import com.qfang.poi.FileType;

/**
 * 文件操作工具类
 * 
 * @author liaozhicheng.cn@163.com
 * @since 1.0
 */
public final class FileUtils {
	
	/**
	 * 给定一个文件名，获取该文件名的后缀
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileSuffix(String fileName) {
		Validate.notEmpty(fileName);
		
		String suffix = "";
		int indexDot = fileName.lastIndexOf(".");
		if (indexDot > 0) {
			suffix = fileName.substring(indexDot + 1, fileName.length());
		}
		return suffix;
	}
	
	
	public static String getFileSuffix(File file) {
		Validate.notNull(file);
		return getFileSuffix(file.getName());
	}
	
	
	public static FileType getFileType(String fileName) {
		String fileSuffix = getFileSuffix(fileName);
		for(FileType ft : FileType.values()) {
			if(ft.getSuffix().equalsIgnoreCase(fileSuffix))
				return ft;
		}
		return null;
	}
	
	
	public static FileType getFileType(File file) {
		Validate.notNull(file);
		return getFileType(file.getName());
	}

	private FileUtils() {}
	
}
