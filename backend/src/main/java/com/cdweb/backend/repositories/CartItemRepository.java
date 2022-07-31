package com.cdweb.backend.repositories;

import com.cdweb.backend.entities.CartItem;
import com.cdweb.backend.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUser(Users user);

    CartItem findByUser_IdAndProductIdAndSize(Long id, Long productId, String size);
}
