package com.cdweb.backend.entities;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Roles extends BaseEntity {

        @Column(name = "role_name")
        private String roleName;
        @Column(name = "role_code")
        private String roleCode;

        @OneToMany(mappedBy = "roles")
        private Set<Users> users = new HashSet<>();
}
