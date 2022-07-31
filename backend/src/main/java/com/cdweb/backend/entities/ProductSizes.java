package com.cdweb.backend.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductSizes extends BaseEntity {
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "products_id")
    private Products product;

    @ManyToOne
    @JoinColumn(name = "size_id")
    private Sizes sizes;

}
