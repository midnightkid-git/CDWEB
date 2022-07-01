package com.cdweb.peakyblinders.models;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Getter
@Setter
@Entity
public class Size {
    @Column(columnDefinition = "serial")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sizeId;
    private String typeSize;
    private int totalSize;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
