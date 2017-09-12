package com.xingfeng;

/**
 * 时间段，以一小时为间隔
 * Created by Xingfeng on 2017-09-11.
 */
public class Period {

    private int start;//起始时间
    private Order order;//预订者
    private int period;//预定时长，只有起始时间有

    public Period(int start, Order order, int period) {
        this.start = start;
        this.order = order;
        this.period = period;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }
}
