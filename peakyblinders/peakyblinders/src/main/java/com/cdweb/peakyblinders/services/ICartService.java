package com.cdweb.peakyblinders.services;

import com.cdweb.peakyblinders.models.Cart;
import com.cdweb.peakyblinders.payloads.request.CartRequest;
import com.cdweb.peakyblinders.payloads.response.CartResponse;

import java.util.List;

public interface ICartService {
    public Cart saveCart(CartRequest cartRequest);
    public List<CartResponse> getAllCartItems();
}
