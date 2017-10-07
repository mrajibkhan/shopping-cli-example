package com.mrk.shop.models;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ShoppingCartTest {

    @Test
    public void addItemTest() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(new ShoppingCartItem(10, "Test"));
        assertThat("should add an item into the cart", cart.getItems().size(), is(1));
    }

}