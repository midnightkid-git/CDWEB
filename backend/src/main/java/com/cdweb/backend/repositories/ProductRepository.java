package com.cdweb.backend.repositories;

import com.cdweb.backend.entities.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;




public interface ProductRepository extends JpaRepository<Products, Long> {
    Products findByProductNameAndIsActiveTrue(String productName);
    Products findByIdAndIsActiveTrue(Long productId);
    boolean existsByIdAndIsActiveTrue(Long id);
    Boolean existsByProductNameAndIsActiveTrue(String productName);
    Page findByIsActiveTrueOrderByModifiedDateDesc(Pageable pageable);

    long countByIsActiveTrue();
}
