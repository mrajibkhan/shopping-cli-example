package com.mrk.shop.views;

import com.mrk.shop.models.Bundle;
import com.mrk.shop.models.Order;
import com.mrk.shop.models.OrderItem;
import com.mrk.shop.models.Product;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * Created by Rajib Khan on 27/09/2017
 */
@AllArgsConstructor
@Slf4j
public class OrderView {
    private Order order;

    /**
     * Prints {@link Order} details in Console
     */
    public void print() {
        StringBuilder orderStrBld = new StringBuilder("\n\n");
        orderStrBld.append("Order:").append("\n").append("=======\n");
        orderStrBld.append("Total: ").append(String.format("$%.2f%n", order.getOrderTotal().doubleValue()));
        orderStrBld.append("Items:").append("\n").append("=======\n");
        Product product;
        Map<Bundle, Integer> itemSplit;
        for(OrderItem item : order.getOrderItems()) {
            product = item.getProduct();
            itemSplit = item.getItemBundleSplit();
            orderStrBld.append(String.format("%d %s $%s %n", item.getTotalQuantity(), product.getCode(), item.getTotalPrice()));
            for(Bundle bundle : itemSplit.keySet()) {
                orderStrBld.append(String.format("      %d x %s%n", itemSplit.get(bundle), bundle));
            }
        }

        log.info(orderStrBld.toString());
    }
}
