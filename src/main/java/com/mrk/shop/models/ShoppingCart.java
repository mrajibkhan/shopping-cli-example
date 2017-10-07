package com.mrk.shop.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rajib Khan on 26/09/2017
 */

@Data
public class ShoppingCart {
    private List<ShoppingCartItem> items = new ArrayList<ShoppingCartItem>();

    public void addItem(ShoppingCartItem item) {
        items.add(item);
    }
}
