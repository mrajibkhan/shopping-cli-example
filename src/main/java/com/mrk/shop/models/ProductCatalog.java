package com.mrk.shop.models;

import lombok.Data;
import java.util.List;
import java.util.Optional;

/**
 * Created by Rajib Khan on 26/09/2017
 * {@link ProductCatalog} contains {@link Product} and {@link Bundle} information
 */

@Data
public class ProductCatalog {
    List<CatalogItem> catalogItems;

    /**
     * finds and returns {@link CatalogItem} by {@link Product} code
     * @param productCode
     * @return
     */
    public Optional<CatalogItem> getCatalogItemByProductCode(String productCode) {
        return catalogItems.stream().filter(item -> item.getProduct().getCode().equals(productCode)).findAny();
    }

    @Data
    public static class CatalogItem {
        private Product product;
        private List<Bundle> bundles;
    }
}
