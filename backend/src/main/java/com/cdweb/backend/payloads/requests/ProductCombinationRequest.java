package com.cdweb.backend.payloads.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCombinationRequest {
    private Long id;
    private Long productId;
    private String productVariantName;
    private Double price;
    private int quantity;

}
