package com.cdweb.peakyblinders.repositories;

import com.cdweb.peakyblinders.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    List<Product> findAll();
//    Product findByProductName(String productName);
//    Product findByProductNameAndIsActiveTrue(String productName);
}
