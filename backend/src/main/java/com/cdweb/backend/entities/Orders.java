package com.cdweb.backend.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Set;


@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Orders extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @OneToMany(mappedBy = "order")
    private Set<OrderDetail> listOrderItem;

    private Double totalPrice;
    private String status;
    private String address;
    private String phoneNumber;



}
