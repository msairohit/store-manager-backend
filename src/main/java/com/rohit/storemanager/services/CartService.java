package com.rohit.storemanager.services;

import com.rohit.storemanager.models.Cart;

import java.util.List;

public interface CartService {
    List<Cart> getAll();

    List<Cart> getByUserName(String userName);

    Cart add(Cart cart);

    boolean deleteItemFromCartForUser(String itemName);

    Cart update(Cart cart);
}
