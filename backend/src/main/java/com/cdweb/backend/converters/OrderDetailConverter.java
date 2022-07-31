package com.cdweb.backend.converters;

import com.cdweb.backend.entities.CartItem;
import com.cdweb.backend.entities.OrderDetail;
import com.cdweb.backend.entities.Orders;
import com.cdweb.backend.payloads.responses.OrderDetailResponse;
import com.cdweb.backend.repositories.OrderDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderDetailConverter {
    private final OrderDetailRepository orderDetailRepository;

    public OrderDetail toEntity(CartItem cartItem, Orders order){
      return OrderDetail.builder()
              .discount(cartItem.getProduct().getDiscount())
              .productName(cartItem.getProduct().getProductName())
              .originalPrice(cartItem.getProduct().getOriginalPrice())
              .quantity(cartItem.getQuantity())
              .category(cartItem.getProduct().getCategories().getName())
              .brand(cartItem.getProduct().getBrands().getName())
              .productId(cartItem.getProduct().getId())
              .size(cartItem.getSize())
              .order(order)
              .imageLink(cartItem.getProduct().getThumbnails().get(0).getImageLink())
              .build();
    }

    public OrderDetailResponse toResponse(OrderDetail orderDetail){
        return OrderDetailResponse.builder()
                .productId(orderDetail.getProductId())
                .productName(orderDetail.getProductName())
                .price(orderDetail.getOriginalPrice())
                .discount(orderDetail.getDiscount())
                .imageLink(orderDetail.getImageLink())
                .size(orderDetail.getSize())
                .quantity(orderDetail.getQuantity())
                .build();
    }
}
