package com.cdweb.backend.repositories;

import com.cdweb.backend.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<Users, Long> {

    @Query(value = "select u.* from  users u join roles r on u.roles_id = r.id " +
            "where u.username = :username and r.role_code = :role_code and u.is_active = true", nativeQuery = true)
    Users findByUsernameAndRoleCodeAndIsActive(@Param("username") String username, @Param("role_code") String roleCode);
    @Query(value = "select u.* from  users u join roles r on u.roles_id = r.id " +
            "where u.gmail = :gmail and r.role_code = :role_code and u.is_active = true", nativeQuery = true)
    Users findByGmailAndRoleCodeAndIsActive(@Param("gmail") String gmail, @Param("role_code") String roleCode);

    Users findByUsername(String username);

    Users findByIdAndIsActiveFalse(Long userId);

    List<Users> findByIsActiveTrue();
    Boolean existsByUsername(String username);

    Boolean existsByGmail(String gmail);

}
