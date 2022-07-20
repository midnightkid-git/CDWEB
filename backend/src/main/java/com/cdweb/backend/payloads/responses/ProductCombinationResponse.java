package com.cdweb.backend.payloads.responses;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder
@Getter
@Setter
public class ProductCombinationResponse {
    private Long id;
    private String productVariantName;
    private String uniqueStringId;
    private Double price;
    private int quantity;
    private Long productId;
}
