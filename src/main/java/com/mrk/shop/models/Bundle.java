package com.mrk.shop.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by Rajib Khan on 26/09/2017
 *
 * {@link Bundle} is used to define price rules for any catalog product
 */
@Data @AllArgsConstructor
public class Bundle {
    private Integer quantity;
    private BigDecimal price;

    @Override
    public String toString() {
        return String.format("%d $%.2f", quantity, price);
    }
}
