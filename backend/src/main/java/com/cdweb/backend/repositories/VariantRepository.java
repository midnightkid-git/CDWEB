package com.cdweb.backend.repositories;

import com.cdweb.backend.entities.Variants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface VariantRepository extends JpaRepository<Variants, Long> {

    List<Variants> findByAttributeIdAndIsActiveTrue(Long attributeId);

    Variants findByVariantNameAndIsActiveTrue(String variantName);

    Variants findByIdAndIsActiveTrue(Long id);

    Variants findByVariantNameAndAttributeIdAndIsActiveTrue(String variantName, Long attributeId);

    @Query(value = "select v.* from product_attributes pa join product_attribute_variants pav on pa.id = pav.product_attribute_id" +
            " join variants v on v.id = pav.variant_id " +
            "where pa.product_id = :product_id and pa.attribute_id = :attribute_id and v.is_active = true and pa.is_active = true and pav.is_active = true", nativeQuery = true)
    List<Variants> findByProductIdAndIsActive(@Param("product_id") Long productId, @Param("attribute_id") Long attributeId);
}
