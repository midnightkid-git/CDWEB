package com.cdweb.peakyblinders.payloads.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductInCartResponse {
    private int productInCartId;
    private String productName;
    private int price;
    private int totalItem;
    private String size;
}
