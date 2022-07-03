package com.cdweb.peakyblinders.models;

import lombok.*;

import javax.persistence.*;

import java.util.Set;
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Getter
@Setter
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private int cartItemId;
    private int totalPrice;
    @OneToMany(mappedBy = "cart")
    private Set<ProductInCart> productInCarts;
}
