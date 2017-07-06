package com.qfang.poi.excel.export.data;

import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by walle on 2017/7/5.
 */
public class ConcurrentDataCollector<T> implements DataCollector<T> {

    private final Queue<T> queue;
    private final AtomicInteger liveTaskSize;

    public ConcurrentDataCollector(Queue<T> queue, AtomicInteger liveTaskSize) {
        this.queue = queue;
        this.liveTaskSize = liveTaskSize;
    }

    @Override
    public boolean hasNext() {
        return this.liveTaskSize.get() > 0 || !queue.isEmpty();
    }

    @Override
    public T next() {
        return queue.poll();
    }
}
