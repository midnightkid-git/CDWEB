package com.cdweb.backend.repositories;

import com.cdweb.backend.entities.Attributes;
import com.cdweb.backend.entities.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttributeRepository extends JpaRepository<Attributes, Long> {

    Optional<Attributes> findById(Long id);

    Page<Attributes> findByIsActiveTrueOrderByModifiedDateDesc(Pageable pageable);


    Attributes findByIdAndIsActiveTrue(Long id);

    List<Attributes> findByIsActiveTrue();

    Attributes findByAttributeNameAndIsActiveTrue(String attributeName);

    @Query(value = "select attr.* from  attributes attr join product_attributes pa on attr.id = pa.attribute_id " +
            "where pa.product_id = :product_id and pa.is_active = true and attr.is_active = true ", nativeQuery = true)
    List<Attributes> findByProductIdAndIsActive(@Param("product_id") Long productId);

    boolean existsByIdAndIsActiveTrue(Long id);

    long countByIsActiveTrue();

    Boolean existsByAttributeNameAndIsActiveTrue(String attributeName);
}
