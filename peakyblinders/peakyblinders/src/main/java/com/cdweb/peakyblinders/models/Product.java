package com.cdweb.peakyblinders.models;

import lombok.*;

import javax.persistence.*;

import java.util.Set;
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Getter
@Setter
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private int productId;
    private String productName;
    private int price;
    private int categoryId;
    private String categoryName;
//    private List<String> thumbnails;
    @OneToMany(mappedBy = "product")
    private Set<Size> sizes;

}
