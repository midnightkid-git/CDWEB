package com.cdweb.backend.repositories;

import com.cdweb.backend.entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Roles, Long> {
//    List<Roles> findByIsActiveTrue();
    Roles findByRoleCode(String roleName);
}
