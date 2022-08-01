package com.cdweb.backend.services.impl;

import com.cdweb.backend.converters.CartConverter;
import com.cdweb.backend.converters.OrderConverter;
import com.cdweb.backend.converters.OrderDetailConverter;
import com.cdweb.backend.entities.*;
import com.cdweb.backend.payloads.requests.CartItemRequest;
import com.cdweb.backend.payloads.requests.OrderRequest;
import com.cdweb.backend.payloads.responses.CartResponse;
import com.cdweb.backend.repositories.*;
import com.cdweb.backend.services.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements ICartService {

    private final CartItemRepository cartItemRepository;

    private final OrderRepository orderRepository;

    private final CartConverter cartConverter;

    private final OrderConverter orderConverter;

    private final OrderDetailConverter orderDetailConverter;

    private final UserRepository userRepository;

    private final OrderDetailRepository orderDetailRepository;



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
                            .imageLink(_x.getProduct().getThumbnails().get(0).getImageLink())
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
            CartItem cartItem = this.updateCartItem(user,request);
            if(cartItem == null){
                CartItem newEntity = cartConverter.toEntity(request);
                newEntity.setUser(user);
                newEntity.setProduct(products);
                newEntity.setQuantity(request.getQuantity());
                newEntity.setSize(request.getSize());
                cartItemRepository.save(newEntity);
                return cartConverter.toResponse(newEntity);
            }
            return cartConverter.toResponse(cartItem);
        }
        return null;
    }

    @Override
    public boolean removeItem(Long cartId) {
        CartItem entity = cartItemRepository.findById(cartId).orElseThrow(()-> new IllegalArgumentException("not found cart by ID"));
        if(entity != null){
            cartItemRepository.delete(entity);
            return true;
        }else
            return false;
    }

    @Override
    public boolean order(Users user, OrderRequest orderRequest) {
        List<CartItem> listCartItem = cartItemRepository.findByUser(user);
        final Double[] totalPrice = {0.0};
        listCartItem.forEach((_x) -> {
            totalPrice[0] = totalPrice[0] + _x.getProduct().getOriginalPrice() * _x.getQuantity() - _x.getProduct().getDiscount();
        });
        Orders order = orderConverter.toEntity(user,orderRequest, totalPrice[0]);
        Orders savedOrder = orderRepository.save(order);
        if(listCartItem.size() > 0) {
            listCartItem.forEach((_x) -> {
                OrderDetail orderDetail = orderDetailConverter.toEntity(_x, savedOrder);
                orderDetailRepository.save(orderDetail);
                cartItemRepository.delete(_x);
            });
            return true;
        }else
            return false;

    }



    public CartItem updateCartItem(Users user, CartItemRequest request){
        CartItem cartItem = cartItemRepository.findByUser_IdAndProductIdAndSize(user.getId(), request.getProductId(), request.getSize());
       if(cartItem != null){
           if((cartItem.getQuantity() + request.getQuantity()) == 0){
               cartItemRepository.delete(cartItem);
               return null;
           }else{
               cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
               cartItem = cartItemRepository.save(cartItem);
           }
       }
        return cartItem;
    }



}
