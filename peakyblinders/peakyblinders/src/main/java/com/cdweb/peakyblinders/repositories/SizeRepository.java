package com.cdweb.peakyblinders.repositories;

import com.cdweb.peakyblinders.models.Product;
import com.cdweb.peakyblinders.models.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SizeRepository extends JpaRepository<Size,Integer> {
    List<Size> findByProduct(Product product);
}
