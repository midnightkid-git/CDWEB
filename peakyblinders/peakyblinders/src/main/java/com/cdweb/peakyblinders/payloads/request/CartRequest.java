package com.cdweb.peakyblinders.payloads.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartRequest {
    private List<ProductInCartRequest> productInCarts;
    private int totalPrice;
}
