package com.cdweb.peakyblinders.converter;

import com.cdweb.peakyblinders.models.Cart;
import com.cdweb.peakyblinders.payloads.request.CartRequest;
import com.cdweb.peakyblinders.payloads.response.CartResponse;
import org.springframework.stereotype.Component;

@Component
public class CartConverter {
    public Cart toEntity(CartRequest cartRequest) {
        return Cart.builder()
                .totalPrice(cartRequest.getTotalPrice())
                .build();
    }

    public CartResponse toRes(Cart cart) {
        return CartResponse.builder()
                .cartItemId(cart.getCartItemId())
                .totalPrice(cart.getTotalPrice())
                .build();
    }
}
