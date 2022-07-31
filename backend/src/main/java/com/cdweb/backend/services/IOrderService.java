package com.cdweb.backend.services;

import com.cdweb.backend.entities.Users;
import com.cdweb.backend.payloads.requests.UpdateOrderStatusRequest;
import com.cdweb.backend.payloads.responses.OrderResponse;

import java.util.List;

public interface IOrderService {

    List<OrderResponse> getListOrderOfUserOrderResponse(Users user);

    List<OrderResponse> getListOrder();

    OrderResponse update(UpdateOrderStatusRequest request, Long orderId);

    OrderResponse getOrderById(Long id);
}
