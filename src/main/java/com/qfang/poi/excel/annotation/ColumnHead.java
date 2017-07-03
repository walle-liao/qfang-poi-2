package com.qfang.poi.excel.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.qfang.poi.excel.model.SheetHeadColumn;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2017年6月29日
 * @since 1.0
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ColumnHead {

	/**
	 * 标识属性对应的 excel 表头列的名称
	 * 
	 * @return
	 */
	String title();
	
	/**
	 * 定义execl表头的列宽
	 * @return
	 */
	int width() default SheetHeadColumn.DEFAULT_COLUMN_WIDTH;
	
}
