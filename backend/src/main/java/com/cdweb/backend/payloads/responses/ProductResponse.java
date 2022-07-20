package com.cdweb.backend.payloads.responses;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
public class ProductResponse {
    private Long id;
    private String productName;
    private String description;
    private String originalPrice;
    private int originalQuantity;
    private int discount;
    private List<String> imageLinks;
    private List<AttributeAndVariantsResponse> attributeAndVariants;
    private List<ProductCombinationResponse> combinations;
    private String brandName;
    private String categoryName;
    private Date createdDate;
    private Date modifiedDate;
}
