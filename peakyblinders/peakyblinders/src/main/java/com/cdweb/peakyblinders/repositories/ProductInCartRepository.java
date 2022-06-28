package com.cdweb.peakyblinders.repositories;

import com.cdweb.peakyblinders.models.Cart;
import com.cdweb.peakyblinders.models.Product;
import com.cdweb.peakyblinders.models.ProductInCart;
import com.cdweb.peakyblinders.models.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInCartRepository extends JpaRepository<ProductInCart,Integer> {
    List<ProductInCart> findByCart(Cart cart);
}
