package com.test.demo.plugin;

import org.apache.ibatis.session.RowBounds;

/**
 * Project: ExerciseTimer
 * Package: com.exercise.plugin
 * Author: mason
 * Time: 17:20
 * Date: 2018-04-02
 * Created with IntelliJ IDEA
 */
public class PageRowBounds extends RowBounds {
    private long total;

    public PageRowBounds() {
        super();
    }

    public PageRowBounds(int offset, int limit) {
        super(offset, limit);
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
