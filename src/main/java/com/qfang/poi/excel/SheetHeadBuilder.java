package com.qfang.poi.excel;

import com.qfang.poi.excel.model.SheetHead;
import com.qfang.poi.excel.support.AnnotationSheetHeadBuilder;
import com.qfang.poi.excel.support.DefaultSheetHeadBuilder;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2017年6月29日
 * @since 1.0
 * 
 * @see DefaultSheetHeadBuilder
 * @see AnnotationSheetHeadBuilder
 */
public interface SheetHeadBuilder {

	SheetHead build();

}
