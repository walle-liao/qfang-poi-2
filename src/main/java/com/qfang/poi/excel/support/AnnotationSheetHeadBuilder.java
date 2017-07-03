package com.qfang.poi.excel.support;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.qfang.poi.excel.SheetHeadBuilder;
import com.qfang.poi.excel.annotation.ColumnHead;
import com.qfang.poi.excel.model.SheetHead;
import com.qfang.poi.excel.model.SheetHeadColumn;
import com.qfang.poi.utils.ReflectUtils;

/**
 * 
 * TODO
 * 
 * @author liaozhicheng
 * @date 2017年7月3日
 * @since 1.0
 */
public class AnnotationSheetHeadBuilder implements SheetHeadBuilder {

	private final Class<?> type;

	public AnnotationSheetHeadBuilder(Class<?> type) {
		this.type = type;
	}

	@Override
	public SheetHead build() {
		return ParseSheetHeadUtils.parseHead(this.type);
	}

	
	private static final class ParseSheetHeadUtils {

		private static final Map<Class<?>, SheetHead> sheetHeadCache = new ConcurrentHashMap<>();

		private static SheetHead parseHead(Class<?> targetClazz) {
			SheetHead sheetHead = sheetHeadCache.get(targetClazz);
			sheetHead = sheetHead == null ? doParseHead(targetClazz) : sheetHead;
			return sheetHead;
		}

		/**
		 * 根据实体类中的注解，解析出 excel 模板的表头
		 *
		 * @param targetClazz
		 * @return
		 */
		private static SheetHead doParseHead(Class<?> targetClazz) {
			synchronized (targetClazz) {
				// retry
				SheetHead cacheHead = sheetHeadCache.get(targetClazz);
				if (cacheHead != null)
					return cacheHead;

				SheetHead sheetHead = new SheetHead();
				Field[] fields = ReflectUtils.getSpecified(targetClazz, ColumnHead.class);
				List<SheetHeadColumn> headColumnList = Stream.of(fields).map(field -> {
					ColumnHead an = field.getAnnotation(ColumnHead.class);
					return new SheetHeadColumn(an.title(), field.getName(), an.width());
				}).collect(Collectors.toList());
				sheetHead.appendColumn(headColumnList);

				sheetHeadCache.put(targetClazz, sheetHead);
				return sheetHead;
			}
		}
	}

}
