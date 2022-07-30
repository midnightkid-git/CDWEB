package com.cdweb.backend.entities;

import lombok.*;
import org.apache.catalina.User;

import javax.persistence.*;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartItem extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Products product;

    private String size;

    private int quantity;

}
