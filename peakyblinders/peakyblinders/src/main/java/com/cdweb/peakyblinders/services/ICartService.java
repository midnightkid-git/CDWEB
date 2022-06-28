package com.cdweb.peakyblinders.services;

import com.cdweb.peakyblinders.models.Cart;
import com.cdweb.peakyblinders.request.CartRequest;
import com.cdweb.peakyblinders.response.CartResponse;

import java.util.List;

public interface ICartService {
    public Cart saveCart(CartRequest cartRequest);
    public List<CartResponse> getAllCartItems();
}
