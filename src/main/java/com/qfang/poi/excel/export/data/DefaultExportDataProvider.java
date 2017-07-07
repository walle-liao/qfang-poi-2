package com.qfang.poi.excel.export.data;

import java.util.Collection;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableList;

/**
 * 
 * TODO
 * 
 * @author liaozhicheng
 * @date 2017年7月3日
 * @since 1.0
 */
public class DefaultExportDataProvider<T> extends AbstractExportDataProvider<T> {
	
	private final Collection<T> datas;

	public DefaultExportDataProvider(Collection<T> datas) {
		this.datas = ImmutableList.copyOf(datas);
	}

	public DefaultExportDataProvider(Supplier<Collection<T>> datasSupplier) {
		this(datasSupplier.get());
	}
	
    @Override
    public DataCollector<T> loadDatas() {
    	this.dataLoadInterceptors.stream().forEach(DataLoadInterceptor::beforeDataLoad);
    	DataCollector<T> dataCollector = new DefaultDataCollector<T>(this.datas);
    	this.dataLoadInterceptors.stream().forEach(interceptor -> interceptor.afterDataLoad(this.datas));
        return dataCollector;
    }

}
