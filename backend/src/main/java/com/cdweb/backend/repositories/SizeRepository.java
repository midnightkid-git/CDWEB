package com.cdweb.backend.repositories;

import com.cdweb.backend.entities.Sizes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SizeRepository extends JpaRepository<Sizes,String> {

   Sizes findBySizeId(String size_id);
}
