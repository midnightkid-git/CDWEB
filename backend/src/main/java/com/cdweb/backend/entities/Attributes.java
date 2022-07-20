package com.cdweb.backend.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Attributes extends BaseEntity{
    private String attributeName;
    private boolean isActive;
    @OneToMany(mappedBy = "attribute")
    private Set<Variants> variants= new HashSet<>();
}
