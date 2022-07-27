package com.cdweb.backend.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.HashSet;
import java.util.Set;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Sizes{
    @Id
    private Long id;
    private String sizeName;

    @OneToMany(mappedBy = "sizes")
    private Set<ProductSizes> sizes;

}
