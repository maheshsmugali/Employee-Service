package com.pws.employees.repository;

import com.pws.employees.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query("select o from Role o where o.name=:roleName")
    Optional<Role> findByRoleName(String roleName);
}
