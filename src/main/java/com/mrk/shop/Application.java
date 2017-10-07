package com.mrk.shop;

import com.mrk.shop.exceptions.InvalidFileException;
import com.mrk.shop.models.Order;
import com.mrk.shop.models.ProductCatalog;
import com.mrk.shop.models.ShoppingCart;
import com.mrk.shop.services.CatalogReaderService;
import com.mrk.shop.services.OrderService;
import com.mrk.shop.services.ShoppingCartService;
import com.mrk.shop.views.OrderView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileNotFoundException;

/*
 * Created by Rajib Khan on 26/09/2017
 */
@SpringBootApplication
@Slf4j
public class Application implements CommandLineRunner {

    @Autowired
    CatalogReaderService catalogReaderService;

    @Autowired
    ShoppingCartService shoppingCartService;

    @Autowired
    OrderService orderService;

    @Override
    public void run(String... args) throws Exception {
        // read product catalog from catalog file provided. Default file is "catalog.yaml" in the root
        // directory of the application
        ProductCatalog catalog = null;
        try {
            catalog = catalogReaderService.readProductCatalog();
            log.debug(catalog.toString());
        } catch (FileNotFoundException | InvalidFileException ex) {
            log.error("Error: Missing or incorrect catalog file. (" + ex.getMessage() + ")");
        }

        // read shopping cart items from customer order file provided. Default file is "order-input.txt" in the root
        // directory of the application
        ShoppingCart cart = null;
        try {
            cart = shoppingCartService.readInputFile();
            log.debug(cart.toString());
        } catch (InvalidFileException ex) {
            log.error("Error: Missing or incorrect order file. (" + ex.getMessage() + ")");
        }

        // Generate order by applying bundle price rules to the shopping cart items
        if(cart != null && catalog != null) {
            Order order = orderService.generateOrder(cart, catalog);
            log.debug(order.toString());

            // Display result
            OrderView orderView = new OrderView(order);
            orderView.print();
        }

    }



    public static void main(String[] args) {
        log.info("My Flower Shop");
        SpringApplication.run(Application.class, args);
    }
}
