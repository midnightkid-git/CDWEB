package com.cdweb.backend.payloads.responses;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class ProductAttributeVariantResponse {
    private Long id;
    private Long productAttributeId;
    private Long variantId;

}
