package com.cdweb.backend.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductAttributes extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Products product;
    @ManyToOne
    @JoinColumn(name = "attribute_id")
    private Attributes attribute;

    private boolean isActive;

    @OneToMany(mappedBy = "productAttribute")
    private Set<ProductAttributeVariants> productAttributeVariants = new HashSet<>();
}
