package com.cdweb.backend.repositories;

import com.cdweb.backend.entities.ProductCombinations;
import com.cdweb.backend.entities.Products;
import com.cdweb.backend.payloads.responses.ProductCombinationResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductCombinationRepository extends JpaRepository<ProductCombinations, Long> {
    List<ProductCombinations> findByProductAndIsActiveTrue(Products product);

    @Query(value = "SELECT min(price) FROM product_combinations " +
            "WHERE product_combinations.product_id = :product_id and product_combinations.is_active = true", nativeQuery = true)
    Double min(@Param("product_id") Long productId);

    @Query(value = "SELECT max(price) FROM product_combinations " +
            "WHERE product_combinations.product_id = :product_id and product_combinations.is_active = true", nativeQuery = true)
    Double max(@Param("product_id") Long productId);

}
