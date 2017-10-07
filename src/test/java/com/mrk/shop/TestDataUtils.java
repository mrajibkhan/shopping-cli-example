package com.mrk.shop;

import com.mrk.shop.models.Bundle;
import com.mrk.shop.models.Order;
import com.mrk.shop.models.OrderItem;
import com.mrk.shop.models.Product;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TestDataUtils {

    public static Order createOrder() {
        Order order = new Order();
        OrderItem item1 = TestDataUtils.createOrderItem(new Product("name1", "testCode1"));
        OrderItem item2 = TestDataUtils.createOrderItem(new Product("name2", "testCode2"));
        order.setOrderItems(Arrays.asList(item1, item2));
        return order;
    }

    public static OrderItem createOrderItem (Product product) {
        Bundle bundle1 = new Bundle(9, new BigDecimal(12.00));
        Bundle bundle2 = new Bundle(5, new BigDecimal(5.00));

        OrderItem item = new OrderItem();
        item.setProduct(product);

        Map<Bundle, Integer> itemSplit = new HashMap<>();
        itemSplit.put(bundle1, 5);
        itemSplit.put(bundle2, 1);
        item.setItemBundleSplit(itemSplit);

        return item;
    }
}
