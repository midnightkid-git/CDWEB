package com.cdweb.peakyblinders.repositories;

import com.cdweb.peakyblinders.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart,Integer> {
    List<Cart> findAll();

}
