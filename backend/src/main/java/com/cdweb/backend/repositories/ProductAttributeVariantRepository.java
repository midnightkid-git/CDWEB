package com.cdweb.backend.repositories;

import com.cdweb.backend.entities.ProductAttributeVariants;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductAttributeVariantRepository extends JpaRepository<ProductAttributeVariants, Long> {
}
