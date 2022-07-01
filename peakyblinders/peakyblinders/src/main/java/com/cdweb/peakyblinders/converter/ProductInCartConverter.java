package com.cdweb.peakyblinders.converter;

import com.cdweb.peakyblinders.models.ProductInCart;
import com.cdweb.peakyblinders.request.ProductInCartRequest;
import com.cdweb.peakyblinders.response.ProductInCartResponse;
import org.springframework.stereotype.Component;

@Component
public class ProductInCartConverter {
    public ProductInCart toEntity(ProductInCartRequest productInCartRequest){
        return ProductInCart.builder()
                .productName(productInCartRequest.getProductName())
                .price(productInCartRequest.getPrice())
                .size(productInCartRequest.getSize())
                .totalItem(productInCartRequest.getTotalItem())
                .build();
    }
    public ProductInCartResponse toRes(ProductInCart productInCart){
        return ProductInCartResponse.builder()
                .productInCartId(productInCart.getProductInCartId())
                .productName(productInCart.getProductName())
                .totalItem(productInCart.getTotalItem())
                .price(productInCart.getPrice())
                .size(productInCart.getSize())
                .build();
    }
}
