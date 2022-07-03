package com.cdweb.peakyblinders.payloads.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Builder
public class CartResponse {
    private int cartItemId;
    private List<ProductInCartResponse> productInCarts;
    private int totalPrice;
}
