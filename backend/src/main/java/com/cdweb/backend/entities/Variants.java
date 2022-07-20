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
public class Variants extends BaseEntity {
    private String variantName;
    private boolean isActive;
    @ManyToOne
    @JoinColumn(name = "attribute_id")
    private Attributes attribute;

    @OneToMany(mappedBy = "variant")
    private Set<ProductAttributeVariants> productAttributeVariants = new HashSet<>();
}
