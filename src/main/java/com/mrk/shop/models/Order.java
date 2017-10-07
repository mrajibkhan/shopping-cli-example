package com.mrk.shop.models;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rajib Khan on 26/09/2017
 */

@Data
public class Order {
    private List<OrderItem> orderItems = new ArrayList<OrderItem>();

    /**
     * calculated price for the Order
     * @return
     */
    public BigDecimal getOrderTotal() {
        return orderItems.stream().map(orderItem -> orderItem.getTotalPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public String toString() {
        return "Order(orderTotal=" + getOrderTotal() + ", orderItems=" + orderItems + ")";
    }
}
