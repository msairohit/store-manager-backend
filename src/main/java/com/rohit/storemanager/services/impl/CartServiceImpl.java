package com.rohit.storemanager.services.impl;

import com.rohit.storemanager.models.Cart;
import com.rohit.storemanager.repositories.CartRepository;
import com.rohit.storemanager.services.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Override
    public List<Cart> getAll() {
        return cartRepository.findAll();
    }

    @Override
    public List<Cart> getByUserName(String userName) {
        return cartRepository.findAllByUserName(userName);
    }

    @Override
    public Cart add(Cart cart) {
        cart.setTotalCost(cart.getPrice() * cart.getQuantity());
        AtomicReference<Cart> save = new AtomicReference<>();
        try {
            save.set(cartRepository.save(cart));
        } catch (DataIntegrityViolationException e) {
                log.error(e.getLocalizedMessage());
            Optional<Cart> optionalCart = cartRepository.findByUserNameAndProductName(cart.getUserName(), cart.getProductName());
            optionalCart.ifPresent(cartFromDb -> {
                log.debug("item already present in the cart for user.");
                cartFromDb.setQuantity(cartFromDb.getQuantity() + cart.getQuantity());
                cartFromDb.setTotalCost(cartFromDb.getPrice() * cartFromDb.getQuantity());
                save.set(cartRepository.save(cartFromDb));//FIXME: return the updated value.
            });
            if(!optionalCart.isPresent()) {
                log.debug("item not present in the cart for user.");
            }
        }
        return save.get();
    }

    @Override
    public boolean deleteItemFromCartForUser(String itemName) {
        //FIXME : assume the user to be SYSTEM for now, later get from UserObject once user is logged in.
        Optional<Cart> record = cartRepository.findByUserNameAndProductName("SYSTEM", itemName);
        record.ifPresent((cart) -> cartRepository.delete(cart));
        return record.isPresent();

    }

    @Override
    public Cart update(Cart cart) {
        Optional<Cart> byUserNameAndProductName = cartRepository.findByUserNameAndProductName(cart.getUserName(), cart.getProductName());
        AtomicReference<Cart> save = new AtomicReference<>();
        byUserNameAndProductName.ifPresent(cart1 -> {
            cart.setId(cart1.getId());
            cart.setTotalCost(cart.getPrice() * cart.getQuantity());
            save.set(cartRepository.save(cart));
        });
        return save.get();
    }
}
