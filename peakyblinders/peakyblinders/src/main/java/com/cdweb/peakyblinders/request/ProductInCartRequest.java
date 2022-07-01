package com.cdweb.peakyblinders.request;

import com.cdweb.peakyblinders.models.Cart;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Getter
@Setter
public class ProductInCartRequest {
    private String productName;
    private int price;
    private int totalItem;
    private String size;
}
