package com.cdweb.backend.payloads.requests;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductRequest {
    private Long id;
    private String productName;
    private String description;
    private Double originalPrice;
    private int originalQuantity;
    private int discount;
    private List<String> imageLinks;
    private List<AttributeAndVariantsRequest> attributes;
    private List<ProductCombinationRequest> combinations;
    private String brandName;
    private String categoryName;
}
