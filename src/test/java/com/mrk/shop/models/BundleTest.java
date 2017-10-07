package com.mrk.shop.models;

import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;

public class BundleTest {

    @Test
    public void toStringTest() {
        Bundle bundle = new Bundle(9, new BigDecimal(12.00));
        assertThat(bundle.toString(), is(equalTo("9 $12.00")));
    }

}