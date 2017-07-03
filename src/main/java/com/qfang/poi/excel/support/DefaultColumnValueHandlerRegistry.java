package com.qfang.poi.excel.support;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import com.qfang.poi.excel.ColumnValueHandler;
import com.qfang.poi.excel.ColumnValueHandlerRegistry;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2017年6月29日
 * @since 1.0
 */
public class DefaultColumnValueHandlerRegistry implements ColumnValueHandlerRegistry {

    // 对应的数据转换器 { key: fieldName, value: valueHandler }
    private final Map<String, ColumnValueHandler> valueHandlerMap = new ConcurrentHashMap<>();

    protected Map<String, ColumnValueHandler> getValueHandlerMap() {
        return this.valueHandlerMap;
    }

    @Override
    public void registerValueHandler(String key, ColumnValueHandler valueHandler) {
    	Objects.requireNonNull(key);
    	Objects.requireNonNull(valueHandler);
        this.valueHandlerMap.put(key, valueHandler);
    }

    @Override
    public void removeValueHandler(String key) {
        this.valueHandlerMap.remove(key);
    }

    @Override
    public ColumnValueHandler getValueHandler(String key) {
        if (this.containsValueHandler(key)) {
            return this.valueHandlerMap.get(key);
        }
        return DefaultColumnValueHandler.getInstance();
    }

    @Override
    public boolean containsValueHandler(String key) {
        return this.valueHandlerMap.containsKey(key);
    }
}
