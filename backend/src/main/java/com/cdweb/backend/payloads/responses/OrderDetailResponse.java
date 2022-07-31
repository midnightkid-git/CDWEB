package com.cdweb.backend.payloads.responses;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
public class OrderDetailResponse {
    private Long productId;
    private String productName;
    private String size;
    private Double price;
    private int discount;
    private String imageLink;
    private int quantity;
}
