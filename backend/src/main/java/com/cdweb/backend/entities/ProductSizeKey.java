package com.cdweb.backend.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ProductSizeKey implements Serializable {

    @Column(name = "products_id")
    private Long productId;
    @Column(name = "size")
    private Long sizeId;

}
