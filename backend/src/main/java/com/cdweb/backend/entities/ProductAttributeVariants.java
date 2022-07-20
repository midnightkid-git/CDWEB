package com.cdweb.backend.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductAttributeVariants extends BaseEntity {
    private boolean isActive;
    @ManyToOne
    @JoinColumn(name="product_attribute_id")
    private ProductAttributes productAttribute;
    @ManyToOne
    @JoinColumn(name="variant_id")
    private Variants variant;
}
