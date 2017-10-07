package com.mrk.shop.models;

import com.mrk.shop.exceptions.InvalidItemException;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by Rajib Khan on 26/09/2017
 */

@Data @AllArgsConstructor
public class ShoppingCartItem {
    private Integer quantity;
    private String productCode;

    public static ShoppingCartItem getInstance(String itemStr) throws InvalidItemException{
        String[] arr = itemStr.split(" ");
        ShoppingCartItem item = null;
        if (arr.length == 2) {
            try {
                item = new ShoppingCartItem(Integer.parseInt(arr[0]), arr[1]);
            } catch (NumberFormatException nfEx) {
                throw new InvalidItemException("not a valid number: " + itemStr);
            }
        } else {
            throw new InvalidItemException("Invalid entry: " + itemStr);
        }

        return item;
    }
}
