package com.cdweb.backend.services.impl;

import com.cdweb.backend.converters.CartConverter;
import com.cdweb.backend.entities.*;
import com.cdweb.backend.payloads.requests.CartItemRequest;
import com.cdweb.backend.payloads.responses.CartResponse;
import com.cdweb.backend.repositories.CartItemRepository;
import com.cdweb.backend.repositories.ProductRepository;
import com.cdweb.backend.services.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements ICartService {

    private final CartItemRepository cartItemRepository;

    private final CartConverter cartConverter;


    private final ProductRepository productRepository;

    @Override
    public List<CartResponse> findByUserId(Users user) {
        List<CartResponse> response = new ArrayList<>();
        List<CartItem> listCartItem = cartItemRepository.findByUser(user);
        listCartItem.forEach((_x) -> {
            response.add(CartResponse.builder()
                            .cartId(_x.getId())
                            .discount(_x.getProduct().getDiscount())
                            .quantity(_x.getQuantity())
                            .size(_x.getSize())
                            .productPrice(_x.getProduct().getOriginalPrice())
                            .productName(_x.getProduct().getProductName())
                            .productId(_x.getProduct().getId())
                    .build());
        });
        return response;
    }

    @Override
    public CartResponse save(Users user, CartItemRequest request) {
        Products products = productRepository.findByIdAndIsActiveTrue(request.getProductId());
        if(products != null){
            Long cartItemContainId = this.containSize(user,request);
            if(cartItemContainId != null){
                CartItem newEntity = cartItemRepository.findById(cartItemContainId);
                newEntity.setQuantity(request.getQuantity());
            }else {
                CartItem newEntity = cartConverter.toEntity(request);
                newEntity.setProduct(products);
                newEntity.setQuantity(request.getQuantity());
                newEntity.setSize(request.getSize());
                cartItemRepository.save(newEntity);
                return cartConverter.toResponse(newEntity);
            }
        }
        return null;
    }

    @Override
    public boolean removeItem(Long cartId) {
        CartItem entity = cartItemRepository.findById(cartId);
        if(entity != null){
            cartItemRepository.delete(entity);
            return true;
        }else
            return false;
    }

    @Override
    public boolean removeCart(Users user) {
        user.getCartItems().forEach((_x) -> {
            cartItemRepository.delete(_x);
        });
        return true;
    }


    public Long containSize(Users user, CartItemRequest request){
        AtomicReference<Long> containCartItemId = null;
        user.getCartItems().forEach((_x) -> {
            if((_x.getProduct().getId() == request.getProductId()) && (_x.getSize() == request.getSize())){
                containCartItemId.set(_x.getId());
            }
        });
        return containCartItemId.get();
    }

}
