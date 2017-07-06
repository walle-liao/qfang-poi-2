package com.qfang.poi.excel.export.data;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

/**
 * Created by walle on 2017/7/5.
 */
public class DefaultDataCollector<T> implements DataCollector<T> {

    private final Collection<T> datas;
    private final Iterator<T> iterator;

    public DefaultDataCollector(Collection<T> datas) {
        Objects.requireNonNull(datas);
        this.datas = datas;
        this.iterator = this.datas.iterator();
    }

    @Override
    public boolean hasNext() {
        return this.iterator.hasNext();
    }

    @Override
    public T next() {
        return this.iterator.next();
    }
}
