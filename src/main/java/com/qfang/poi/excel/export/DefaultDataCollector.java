package com.qfang.poi.excel.export;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

/**
 * Created by walle on 2017/7/5.
 */
public class DefaultDataCollector<T> implements DataCollector<T> {

    private final Collection<T> datas;
    private final Iterator<T> iterator;

    private DefaultDataCollector(Collection<T> datas) {
        Objects.requireNonNull(datas);
        this.datas = datas;
        this.iterator = this.datas.iterator();
    }

    public static DefaultDataCollector newInstance(Collection<?> datas) {
        return new DefaultDataCollector(datas);
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
