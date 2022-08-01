package com.cdweb.backend.payloads.responses;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CartResponse {
    private Long cartId;
    private Long productId;
    private String productName;
    private Double productPrice;
    private String imageLink;
    private int discount;
    private int quantity;
    private String size;
}
