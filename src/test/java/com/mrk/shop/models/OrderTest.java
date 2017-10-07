package com.mrk.shop.models;

import com.mrk.shop.TestDataUtils;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class OrderTest {

    Order order;

    @Before
    public void setup() {
        order = TestDataUtils.createOrder();
    }

    @Test
    public void getTotalQuantityShouldReturn50() {
        assertThat("should return total price as 130", order.getOrderTotal(), equalTo(new BigDecimal(130)));
    }

}