package com.cdweb.backend.entities;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Products extends BaseEntity {
    private String productName;
    private String description;
    private Double originalPrice;
    private int originalQuantity;
    private int discount;
    @Column(name = "is_active")
    private boolean isActive;
    @ManyToOne
    @JoinColumn(name = "categories_id")
    private Categories categories;

    @ManyToOne
    @JoinColumn(name = "brands_id")
    private Brands brands;

    @OneToMany(mappedBy = "product")
    private Set<Thumbnails> thumbnails = new HashSet<>();

    @OneToMany(mappedBy = "product")
    private Set<ProductAttributes> productAttributes = new HashSet<>();

    @OneToMany(mappedBy = "product")
    private Set<ProductCombinations> productCombinations = new HashSet<>();
}
