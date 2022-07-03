package com.cdweb.peakyblinders.services;

import com.cdweb.peakyblinders.converter.CartConverter;
import com.cdweb.peakyblinders.converter.ProductInCartConverter;
import com.cdweb.peakyblinders.models.Cart;
import com.cdweb.peakyblinders.repositories.CartRepository;
import com.cdweb.peakyblinders.repositories.ProductInCartRepository;
import com.cdweb.peakyblinders.payloads.request.CartRequest;
import com.cdweb.peakyblinders.payloads.response.CartResponse;
import com.cdweb.peakyblinders.payloads.response.ProductInCartResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CartService implements ICartService{
    private final CartRepository cartRepository;
    private final CartConverter cartConverter;
    private final ProductInCartRepository productInCartRepository;
    private final ProductInCartConverter productInCartConverter;

    public Cart saveCart(CartRequest cartRequest){
        Cart cart = cartRepository.save(cartConverter.toEntity(cartRequest));
        return cart;
    }
    public List<CartResponse> getAllCartItems(){
        List<CartResponse> cartResponses = new ArrayList<>();
        cartRepository.findAll().forEach(
                c->{
                    List<ProductInCartResponse> productInCartResponses = new ArrayList<>();
                    productInCartRepository.findByCart(c).forEach(pic->{
                        productInCartResponses.add(productInCartConverter.toRes(pic));
                    });
                    CartResponse cartResponse = cartConverter.toRes(c);
                    cartResponse.setProductInCarts(productInCartResponses);
                    cartResponses.add(cartResponse);
                }
        );
        return cartResponses;
    }
}
