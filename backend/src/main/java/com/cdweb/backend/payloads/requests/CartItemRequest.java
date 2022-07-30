package com.cdweb.backend.payloads.requests;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class CartItemRequest {
    private Long productId;
    private String size;
    private int quantity;
}
