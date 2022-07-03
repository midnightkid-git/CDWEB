package com.cdweb.peakyblinders.services;

import com.cdweb.peakyblinders.converter.ProductInCartConverter;
import com.cdweb.peakyblinders.models.Cart;
import com.cdweb.peakyblinders.models.ProductInCart;
import com.cdweb.peakyblinders.repositories.ProductInCartRepository;
import com.cdweb.peakyblinders.payloads.response.ProductInCartResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductInCartService {
    private final ProductInCartRepository productInCartRepository;
    private final ProductInCartConverter productInCartConverter;
    public ProductInCartResponse saveProductInCart(ProductInCart productInCart, Cart cart){
        ProductInCart productInCartEntity = productInCartRepository.save(ProductInCart.builder()
                        .productName(productInCart.getProductName())
                        .size(productInCart.getSize())
                        .totalItem(productInCart.getTotalItem())
                        .price(productInCart.getPrice())
                        .cart(cart)
                        .build());
        return productInCartConverter.toRes(productInCartEntity);
    }
}
