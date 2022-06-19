package com.cdweb.peakyblinders.repositories;

import com.cdweb.peakyblinders.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Users findByUsername(String username);

    Users findByUsernameAndIsActive(String username, boolean isActive);

    List<Users> findByIsActiveTrue();
}
