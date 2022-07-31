package com.cdweb.backend.entities;

import lombok.*;

import javax.persistence.*;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDetail extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders order;

    private Long productId;

    private String category;

    private String brand;

    private String productName;

    private Double originalPrice;

    private int discount;

    private String size;

    private int quantity;

    private String imageLink;
}
