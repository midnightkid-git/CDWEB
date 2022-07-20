package com.cdweb.backend.converters;

import com.cdweb.backend.entities.ProductCombinations;
import com.cdweb.backend.payloads.requests.ProductCombinationRequest;
import com.cdweb.backend.payloads.responses.ProductCombinationResponse;
import org.springframework.stereotype.Component;

@Component
public class ProductCombinationConverter {
    public ProductCombinations toEntity(ProductCombinationRequest request){
        return ProductCombinations.builder()
                .price(request.getPrice())
                .productVariantName(request.getProductVariantName())
                .quantity(request.getQuantity())
                .build();
    }

    public ProductCombinationResponse toResponse(ProductCombinations entity){
        return ProductCombinationResponse.builder()
                .id(entity.getId())
                .price(entity.getPrice())
                .productVariantName(entity.getProductVariantName())
                .quantity(entity.getQuantity())
                .uniqueStringId(entity.getUniqueStringId())
                .productId(entity.getProduct().getId())
                .build();
    }
}
