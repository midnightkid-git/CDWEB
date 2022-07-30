package com.cdweb.backend.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductSizes {
    private int quantity;

    @EmbeddedId
    ProductSizeKey id;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "products_id")
    private Products product;

    @ManyToOne
    @MapsId("sizeId")
    @JoinColumn(name = "size")
    private Sizes sizes;

}
