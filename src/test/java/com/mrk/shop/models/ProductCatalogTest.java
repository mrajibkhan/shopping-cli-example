package com.mrk.shop.models;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ProductCatalogTest {

    ProductCatalog catalog;
    Bundle bundle1;
    Bundle bundle2;
    Product product1;

    @Before
    public void setup() {
        ProductCatalog.CatalogItem item = new ProductCatalog.CatalogItem();
        product1 = new Product("name", "testCode");
        item.setProduct(product1);
        bundle1 = new Bundle(9, new BigDecimal(12.00));
        bundle1 = new Bundle(4, new BigDecimal(5.00));
        item.setBundles(Arrays.asList(bundle1, bundle2));
        catalog = new ProductCatalog();
        catalog.setCatalogItems(Arrays.asList(item));
    }

    @Test
    public void getCatalogItemByProductCodeReturnsItem() {
        Optional<ProductCatalog.CatalogItem> actualItem = catalog.getCatalogItemByProductCode("testCode");
        assertThat("Catalog item with product code should not be empty", actualItem.isPresent(), is(true));
    }

    @Test
    public void getCatalogItemByProductCodeReturnsEmptyOptional() {
        Optional<ProductCatalog.CatalogItem> actualItem = catalog.getCatalogItemByProductCode("noProduct");
        assertThat("Catalog item with product code should be empty", actualItem.isPresent(), is(false));
    }

}