package com.cdweb.backend.converters;

import com.cdweb.backend.entities.OrderDetail;
import com.cdweb.backend.entities.Orders;
import com.cdweb.backend.entities.Users;
import com.cdweb.backend.payloads.requests.OrderRequest;
import com.cdweb.backend.payloads.responses.OrderDetailResponse;
import com.cdweb.backend.payloads.responses.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderConverter {

    private final OrderDetailConverter orderDetailConverter;

    public Orders toEntity(Users user, OrderRequest orderRequest, Double price){
        Orders order = Orders.builder()
                .address(orderRequest.getAddress())
                .phoneNumber(orderRequest.getPhoneNumber())
                .status(orderRequest.getStatus())
                .user(user)
                .totalPrice(price)
                .build();
        return order;
    }

    public OrderResponse toResponse(Orders orders){
        List<OrderDetailResponse> listOrderResponse = new ArrayList<>();
        orders.getListOrderItem().forEach((_x) -> {
            listOrderResponse.add(orderDetailConverter.toResponse(_x));
        });
        return OrderResponse.builder()
                .userId(orders.getUser().getId())
                .address(orders.getAddress())
                .orderId(orders.getId())
                .phoneNumber(orders.getPhoneNumber())
                .status(orders.getStatus())
                .totalPrice(orders.getTotalPrice())
                .listItem(listOrderResponse)
                .build();
    }
}
