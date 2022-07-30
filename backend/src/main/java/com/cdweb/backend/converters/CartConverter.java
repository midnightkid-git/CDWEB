package com.cdweb.backend.converters;

import com.cdweb.backend.entities.Brands;
import com.cdweb.backend.entities.CartItem;
import com.cdweb.backend.payloads.requests.BrandRequest;
import com.cdweb.backend.payloads.requests.CartItemRequest;
import com.cdweb.backend.payloads.responses.CartResponse;
import com.cdweb.backend.repositories.CartItemRepository;
import com.cdweb.backend.repositories.CategoryRepository;
import com.cdweb.backend.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartConverter {

    private final CartItemRepository cartItemRepository;

    private final ProductRepository productRepository;

    public CartItem toEntity(CartItemRequest request){
        return CartItem.builder()
                .size(request.getSize())
                .quantity(request.getQuantity())
                .product(productRepository.getById(request.getProductId()))
                .build();
    }

    public CartResponse toResponse(CartItem cartItem){
        return CartResponse.builder()
                .productId(cartItem.getProduct().getId())
                .productName(cartItem.getProduct().getProductName())
                .discount(cartItem.getProduct().getDiscount())
                .quantity(cartItem.getQuantity())
                .size(cartItem.getSize())
                .cartId(cartItem.getId())
                .build();
    }

}
