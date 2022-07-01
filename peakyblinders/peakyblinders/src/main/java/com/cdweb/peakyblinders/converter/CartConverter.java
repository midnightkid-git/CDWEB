package com.cdweb.peakyblinders.converter;

import com.cdweb.peakyblinders.models.Cart;
import com.cdweb.peakyblinders.models.Product;
import com.cdweb.peakyblinders.request.CartRequest;
import com.cdweb.peakyblinders.response.CartResponse;
import com.cdweb.peakyblinders.response.ProductResponse;
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
