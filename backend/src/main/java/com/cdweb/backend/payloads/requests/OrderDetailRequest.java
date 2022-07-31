package com.cdweb.backend.payloads.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderDetailRequest {
    private String productName;
    private String size;
    private String category;
    private String brand;
    private Long productId;
    private int price;
    private int discount;
    private int quantity;

}
