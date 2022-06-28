package com.cdweb.peakyblinders.models;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Getter
@Setter
@Entity
public class ProductInCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private int productInCartId;
    private String productName;
    private int price;
    private int totalItem;
    private String size;
    @ManyToOne
    @JoinColumn(name = "cart_item_id")
    private Cart cart;
}
