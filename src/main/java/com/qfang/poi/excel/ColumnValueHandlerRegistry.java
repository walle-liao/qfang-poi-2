package com.qfang.poi.excel;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2017年6月29日
 * @since 1.0
 */
public interface ColumnValueHandlerRegistry {
	
    /**
     * 注册一个值处理器
     * @param key
     * @param valueHandler
     */
	void registerValueHandler(String key, ColumnValueHandler valueHandler);

    /**
     * 删除一个值处理器
     * @param key
     */
    void removeValueHandler(String key);

    /**
     * 获取值处理器
     * @param key
     * @return
     */
    ColumnValueHandler getValueHandler(String key);

    /**
     * 对应的 key 是否存在值处理器
     * @param key
     * @return
     */
    boolean containsValueHandler(String key);

}
