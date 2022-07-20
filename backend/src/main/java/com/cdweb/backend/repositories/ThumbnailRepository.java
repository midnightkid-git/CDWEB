package com.cdweb.backend.repositories;

import com.cdweb.backend.entities.Thumbnails;
import com.cdweb.backend.entities.Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThumbnailRepository extends JpaRepository<Thumbnails, Long> {
    List<Thumbnails> findByProduct(Products product);
    List<Thumbnails> findByProductAndIsActiveTrue(Products productId);
}
