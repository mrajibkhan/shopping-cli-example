package com.mrk.shop.models;

import com.mrk.shop.exceptions.InvalidItemException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;

public class ShoppingCartItemTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getInstanceShouldReturnCartItemForValidString() throws Exception {
        ShoppingCartItem cartItem = ShoppingCartItem.getInstance("10 TESTPRD");
        assertThat(cartItem, isA(ShoppingCartItem.class));
    }

    @Test
    public void getInstanceShouldItemWithQuantity() throws Exception {
        ShoppingCartItem cartItem = ShoppingCartItem.getInstance("10 TESTPRD");
        assertThat(cartItem.getQuantity(), is(10));
    }

    @Test
    public void getInstanceShouldItemWithProductCode() throws Exception {
        ShoppingCartItem cartItem = ShoppingCartItem.getInstance("10 TESTPRD");
        assertThat(cartItem.getProductCode(), is("TESTPRD"));
    }

    @Test
    public void getInstanceShouldThrowExceptionForInvalidString() throws Exception {
        thrown.expect(InvalidItemException.class);
        thrown.expectMessage("not a valid number: AA TESTPRD");
        ShoppingCartItem.getInstance("AA TESTPRD");
    }
}