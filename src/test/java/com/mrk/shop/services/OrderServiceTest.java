package com.mrk.shop.services;

import com.mrk.shop.models.*;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

    OrderService service = new OrderService();

    @Mock
    ProductCatalog catalog;

    @Mock
    ProductCatalog.CatalogItem catalogItem;

    @Test
    public void calculateItemQuantitiesTest1() {
        List<Integer> expected = Arrays.asList(5,5,3);
        List<Integer> quantities = service.calculateItemQuantities(new ArrayList<>(), 13, new Integer[] {9,5,3}, 0);
        assertThat("should return 5,5,3", quantities, equalTo(expected));
    }

    @Test
    public void calculateItemQuantitiesTest2() {
        List<Integer> expected = Arrays.asList(10);
        List<Integer> quantities = service.calculateItemQuantities(new ArrayList<>(), 10, new Integer[] {10,5,3}, 0);
        assertThat("should return 10", quantities, equalTo(expected));
    }

    @Test
    public void calculateItemQuantitiesTest3() {
        List<Integer> expected = Arrays.asList(10,5,3,3);
        List<Integer> quantities = service.calculateItemQuantities(new ArrayList<>(), 21, new Integer[] {10,5,3}, 0);
        assertThat("should return 10,5,3,3", quantities, equalTo(expected));
    }

    @Test
    public void calculateItemQuantitiesShouldReturnEmpty() {
        List<Integer> quantities = service.calculateItemQuantities(new ArrayList<>(), 7, new Integer[] {10,5,3}, 0);
        assertThat("should return empty list", quantities.size(), equalTo(0));
    }

    @Test
    public void createOrderItemTest() {
        Product product = new Product("name1", "testCode1");
        Bundle bundle1 = new Bundle(9, new BigDecimal(12.00));
        Bundle bundle2 = new Bundle(5, new BigDecimal(5.00));
        Bundle bundle3 = new Bundle(4, new BigDecimal(4.00));
        Bundle bundle4 = new Bundle(3, new BigDecimal(3.00));

        OrderItem item = service.createOrderItem(product, Arrays.asList(bundle1, bundle2, bundle3, bundle4), Arrays.asList(9,9,5,3,3));

        Map<Bundle,Integer> itemSplit = item.getItemBundleSplit();
        assertThat(itemSplit.size(), is(3));
        assertThat(itemSplit.get(bundle1), is(2));
    }

    @Test
    public void generateOrderTest() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(new ShoppingCartItem(19, "prodCode"));

        Bundle bundle1 = new Bundle(9, new BigDecimal(12.00));
        Bundle bundle2 = new Bundle(5, new BigDecimal(5.00));
        List<Bundle> bundles = Arrays.asList(bundle1,bundle2);

        Mockito.doReturn(Optional.of(catalogItem)).when(catalog).getCatalogItemByProductCode(Mockito.anyString());
        Mockito.doReturn(bundles).when(catalogItem).getBundles();
        Mockito.doReturn(new Product("prodName", "prodCode")).when(catalogItem).getProduct();

        Order order = service.generateOrder(cart, catalog);
        assertThat("should create an order", order, notNullValue());

        List<OrderItem> orderItems = order.getOrderItems();
        assertThat("should contain one item", orderItems.size(), is(1));

        OrderItem orderItem = orderItems.get(0);
        Map<Bundle, Integer> itemSplit = orderItem.getItemBundleSplit();
        assertThat(itemSplit.size(), is(2));

        // split quantities
        assertThat(itemSplit.get(bundle1), is(1));
        assertThat(itemSplit.get(bundle2), is(2));
    }
}