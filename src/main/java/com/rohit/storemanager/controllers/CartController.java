package com.rohit.storemanager.controllers;

import com.rohit.storemanager.models.ApiResponse;
import com.rohit.storemanager.models.Cart;
import com.rohit.storemanager.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping
    public ApiResponse addEntry(@RequestBody Cart cart) {
        return ApiResponse
                .builder()
                .result(cartService.add(cart))
                .build();
    }

    @PutMapping
    //FIXME: check if there is enough quantity to add to cart or not, of not show message saying out of stock and add only till available.
    public ApiResponse updateQuantity(@RequestBody Cart cart) {
        return ApiResponse
                .builder()
                .result(cartService.update(cart))
                .build();
    }

    @GetMapping
    public ApiResponse getAll() {
        return ApiResponse
                .builder()
                .result(cartService.getAll())
                .build();
    }

    @GetMapping("/{userName}")
    public ApiResponse getCartDetailsOfUser(@PathVariable("userName") String userName) {
        return ApiResponse
                .builder()
                .result(cartService.getByUserName(userName))
                .build();
    }

    @DeleteMapping("/{itemName}")
    public ApiResponse deleteItemFromCartForUser(@PathVariable("itemName") String itemName) {
        return ApiResponse
                .builder()
                .result(cartService.deleteItemFromCartForUser(itemName))
                .build();
    }
}
