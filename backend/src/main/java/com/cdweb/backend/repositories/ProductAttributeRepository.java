package com.cdweb.backend.repositories;

import com.cdweb.backend.entities.ProductAttributes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductAttributeRepository extends JpaRepository<ProductAttributes, Long> {
}
