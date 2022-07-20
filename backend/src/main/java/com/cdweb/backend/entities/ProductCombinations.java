package com.cdweb.backend.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductCombinations extends BaseEntity {
    private String productVariantName;
    private String uniqueStringId;
    private Double price;
    private int quantity;
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Products product;
}
