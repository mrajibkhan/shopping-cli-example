package com.mrk.shop.services;

import com.mrk.shop.models.*;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Rajib Khan on 26/09/2017
 * {@link OrderService} converts {@link ShoppingCart} into an {@link Order}
 * by applying price rules ({@link Bundle})
 */

@Service
public class OrderService {

    /**
     * Generates {@link Order} from {@link ShoppingCart}
     * {@link OrderItem} will only be generated if the {@link ShoppingCartItem} matches
     * a {@link com.mrk.shop.models.ProductCatalog.CatalogItem} and the total quantity
     * can be delivered from existing {@link Bundle}
     * @param cart
     * @param catalog
     * @return
     */
    public Order generateOrder(ShoppingCart cart, ProductCatalog catalog) {
        Order order = new Order();
        Optional<ProductCatalog.CatalogItem> catalogItemOpt = Optional.empty();
        List<Bundle> bundles;
        for (ShoppingCartItem cartItem : cart.getItems()) {
            catalogItemOpt = catalog.getCatalogItemByProductCode(cartItem.getProductCode());
            Integer cartItemQty = cartItem.getQuantity();
            if(catalogItemOpt.isPresent()) {
                ProductCatalog.CatalogItem catalogItem = catalogItemOpt.get();
                bundles = catalogItem.getBundles();
                bundles.sort(Comparator.comparingInt(Bundle::getQuantity).reversed());
                Integer[] bundleQtyList = bundles.stream().map(Bundle::getQuantity).toArray(Integer[]::new);

                List<Integer> orderItemQty = calculateItemQuantities(new ArrayList<>(), cartItemQty, bundleQtyList, 0);
                order.getOrderItems().add(createOrderItem(catalogItem.getProduct(), bundles, orderItemQty));
            }
        }

        return order;
    }

    /**
     * Creates {@link OrderItem} based on the {@link Bundle}s and orderItemQty.
     * {@link OrderItem} will contain split based on the {@link Bundle}
     * @param product
     * @param bundles
     * @param orderItemQty
     * @return
     */
    OrderItem createOrderItem(Product product, List<Bundle> bundles, List<Integer> orderItemQty) {
        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(product);
        Map<Bundle, Integer> orderItemBundle = new HashMap<Bundle, Integer>();
        for(Bundle bundle : bundles) {
            for(Integer currQty : orderItemQty) {
                if(bundle.getQuantity().equals(currQty)) {
                    if (orderItemBundle.containsKey(bundle)) {
                        orderItemBundle.put(bundle, orderItemBundle.get(bundle) + 1);
                    } else {
                        orderItemBundle.put(bundle, 1);
                    }
                }
            }
        }
        orderItem.setItemBundleSplit(orderItemBundle);

        return orderItem;
    }


    /**
     * Calculates quantities based on the total number and available quantities.
     * Tries to use the biggest values as many times as possible. Numbers in the availableQuantities
     * can be used multiple times. If the sum of used numbers doesn't match the total then this
     * method returns and empty {@link List}. Examples:
     * 1. total = 10, availableQuantities = [10, 5, 2] will return [10]
     * 2. total = 13, availableQuantities = [9, 5, 3] will return [5,5,3]
     * 3. total = 7, availableQuantities = [10, 5, 3] will return []
     * @param tempList to support calculation (used by recursion)
     * @param total
     * @param availableQuantities quantities from {@link Bundle}s
     * @param pos
     * @return
     */
    List<Integer> calculateItemQuantities(List<Integer> tempList, int total,Integer[] availableQuantities,int pos){
        List<Integer> result = new ArrayList<Integer>();
        if(total == 0){
            return new ArrayList<Integer>(tempList);
        }

        for(int i=pos; i < availableQuantities.length; i++){
            if(total >= availableQuantities[i]){
                tempList.add(availableQuantities[i]);
                result = calculateItemQuantities(tempList,total-availableQuantities[i],availableQuantities,i);
                if(!result.isEmpty()) {
                    return result;
                }
                tempList.remove(tempList.size()-1);
            }
        }

        return result;
    }
}
