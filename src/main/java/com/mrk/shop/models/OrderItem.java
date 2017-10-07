package com.mrk.shop.models;

import lombok.Data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rajib Khan on 26/09/2017
 * {@link OrderItem} contains {@link Product}s and corresponding order quantity.
 * Quantities are associated with Bundle
 */
@Data
public class OrderItem {
    Product product;

    // container for Bundle and corresponding quantity
    Map<Bundle, Integer> itemBundleSplit = new HashMap<>();

    /**
     * calculated total item quantity
     * @return
     */
    public Integer getTotalQuantity() {
        return itemBundleSplit.keySet().stream().map(bundle ->
                bundle.getQuantity() * itemBundleSplit.get(bundle)).mapToInt(Integer::intValue).sum();
    }

    /**
     * calculated total item price
     * @return
     */
    public BigDecimal getTotalPrice() {
        return itemBundleSplit.keySet().stream().map(bundle ->
            bundle.getPrice().multiply(new BigDecimal(itemBundleSplit.get(bundle)))
        ).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public String toString() {
        return product + ", totalQuantity=" + getTotalQuantity() + " totalPrice=" + getTotalPrice()
                + " itemSplit=" + getItemBundleSplit();
    }

}
