package com.cdweb.backend.converters;

import com.cdweb.backend.entities.ProductAttributeVariants;
import com.cdweb.backend.payloads.responses.ProductAttributeVariantResponse;
import org.springframework.stereotype.Component;

@Component
public class ProductAttributeVariantConverter {
    public ProductAttributeVariantResponse toResponse (ProductAttributeVariants entity){
        return ProductAttributeVariantResponse.builder()
                .id(entity.getId())
                .productAttributeId(entity.getProductAttribute().getId())
                .variantId(entity.getVariant().getId())
                .build();
    }
}
