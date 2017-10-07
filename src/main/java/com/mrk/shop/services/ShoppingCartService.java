package com.mrk.shop.services;

import com.mrk.shop.exceptions.InvalidFileException;
import com.mrk.shop.exceptions.InvalidItemException;
import com.mrk.shop.models.ShoppingCart;
import com.mrk.shop.models.ShoppingCartItem;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 * Created by Rajib Khan on 26/09/2017
 * ShoppingCartService populates cart items based on the file provided.
 * Each line of the file should contain the quantity and product code
 */

@Service
@Slf4j
public class ShoppingCartService {

    @Value("${order.file}") @Setter
    private String orderInputFile;

    /**
     * Reads content of the file and adds ShoppingCartItems to the ShoppingCart
     * @return ShoppingCart
     * @throws InvalidFileException
     */
    public ShoppingCart readInputFile() throws InvalidFileException {
        ShoppingCart cart = new ShoppingCart();
        List<String> lines;

        log.info("reading customer order from file:" + orderInputFile);

        try {
            Stream<String> stream = Files.lines(Paths.get(orderInputFile));
            lines = stream.collect(Collectors.toList());
        } catch (IOException ioEx) {
            String errMsg = "Couldn't read order from file. Error: " + ioEx;
            log.error(errMsg);
            throw new InvalidFileException(errMsg);
        }

        // add items to the cart
        for(String line : lines) {
            try {
                cart.addItem(ShoppingCartItem.getInstance(line));
            } catch (InvalidItemException e) {
                log.error("Couldn't convert input order line : " + line + ". Skipping the line.");
            }
        }

        return cart;
    }
}
