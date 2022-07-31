package com.cdweb.backend.payloads.responses;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
public class OrderResponse {
    private Long userId;
    private Long orderId;
    private String address;
    private String phoneNumber;
    private Double totalPrice;
    private String status;
    private List<OrderDetailResponse> listItem;
}
