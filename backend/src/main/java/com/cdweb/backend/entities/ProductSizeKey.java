package com.cdweb.backend.entities;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
public class ProductSizeKey implements Serializable {

    @Column(name = "products_id")
    private Long productId;
    @Column(name = "size_id")
    private Long sizeId;

}
