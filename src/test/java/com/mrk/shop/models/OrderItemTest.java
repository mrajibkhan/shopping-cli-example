package com.mrk.shop.models;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class OrderItemTest {

    OrderItem item;

    @Before
    public void setup() {
        Product product = new Product("name", "testCode");
        Bundle bundle1 = new Bundle(9, new BigDecimal(12.00));
        Bundle bundle2 = new Bundle(5, new BigDecimal(5.00));

        item = new OrderItem();
        item.setProduct(product);

        Map<Bundle, Integer> itemSplit = new HashMap<>();
        itemSplit.put(bundle1, 5);
        itemSplit.put(bundle2, 1);
        item.setItemBundleSplit(itemSplit);
    }

    @Test
    public void getTotalQuantityShouldReturn50() {
        assertThat("should return total item quantity as 50", item.getTotalQuantity(), is(50));
    }

    @Test
    public void getTotalShouldReturn65() {
        assertThat("should return total item price as 65", item.getTotalPrice(), equalTo(new BigDecimal(65)));
    }

}