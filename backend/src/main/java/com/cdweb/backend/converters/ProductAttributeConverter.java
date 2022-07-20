package com.cdweb.backend.converters;

import com.cdweb.backend.entities.ProductAttributes;
import com.cdweb.backend.payloads.responses.ProductAttributeResponse;
import org.springframework.stereotype.Component;

@Component
public class ProductAttributeConverter {
    public ProductAttributeResponse toResponse (ProductAttributes entity) {
        return ProductAttributeResponse.builder()
                .id(entity.getId())
                .productId(entity.getProduct().getId())
                .attributeId(entity.getAttribute().getId())
                .build();
    }
}