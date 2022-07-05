package com.rohit.storemanager.repositories;

import com.rohit.storemanager.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findAllByUserName(String userName);

    Optional<Cart> findByUserNameAndProductName(String userName, String productName);
}
