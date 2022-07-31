package com.cdweb.backend.services.impl;

import com.cdweb.backend.converters.OrderConverter;
import com.cdweb.backend.entities.CartItem;
import com.cdweb.backend.entities.Orders;
import com.cdweb.backend.entities.Products;
import com.cdweb.backend.entities.Users;
import com.cdweb.backend.payloads.requests.UpdateOrderStatusRequest;
import com.cdweb.backend.payloads.responses.OrderResponse;
import com.cdweb.backend.repositories.OrderRepository;
import com.cdweb.backend.services.IOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements IOrderService {
   private final OrderRepository orderRepository;

  private final OrderConverter orderConverter;

    @Override
    public List<OrderResponse> getListOrderOfUserOrderResponse(Users user) {
        List<Orders> orders = orderRepository.findByUser_Id(user.getId());
        List<OrderResponse> listOrderResponse = new ArrayList<>();
        orders.forEach((_x) -> {
            listOrderResponse.add(orderConverter.toResponse(_x));
        });
        return listOrderResponse;
    }

    @Override
    public List<OrderResponse> getListOrder() {
        List<Orders> orders = orderRepository.findAll();
        List<OrderResponse> listOrderResponse = new ArrayList<>();
        orders.forEach((_x) -> {
            listOrderResponse.add(orderConverter.toResponse(_x));
        });
        return listOrderResponse;
    }

    @Override
    public OrderResponse update(UpdateOrderStatusRequest request, Long orderId) {
        Orders entity = orderRepository.findById(orderId).get();
        entity.setStatus(request.getStatus());
        orderRepository.save(entity);
        return null;
    }

    @Override
    public OrderResponse getOrderById(Long id) {
        Orders entity = orderRepository.findById(id).get();
        return orderConverter.toResponse(entity);
    }


}
