package com.cdweb.backend.services;

import com.cdweb.backend.entities.Users;
import com.cdweb.backend.payloads.requests.CartItemRequest;
import com.cdweb.backend.payloads.requests.OrderRequest;
import com.cdweb.backend.payloads.responses.CartResponse;

import java.util.List;

public interface ICartService {
    List<CartResponse> findByUserId(Users user);
    CartResponse save(Users user, CartItemRequest request);
    boolean removeItem(Long cartId);
    boolean order(Users user, OrderRequest orderRequest);
}
