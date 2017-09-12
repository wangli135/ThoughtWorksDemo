package com.xingfeng;

/**
 * 预订者
 * Created by Xingfeng on 2017-09-11.
 */
public class Order {

    private String name;

    public Order(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;

        Order order = (Order) o;

        return name != null ? name.equals(order.name) : order.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
