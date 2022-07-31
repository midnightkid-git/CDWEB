package com.cdweb.backend.repositories;

import com.cdweb.backend.entities.ProductSizeKey;
import com.cdweb.backend.entities.ProductSizes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductSizeRepository extends JpaRepository<ProductSizes, Long> {
}
