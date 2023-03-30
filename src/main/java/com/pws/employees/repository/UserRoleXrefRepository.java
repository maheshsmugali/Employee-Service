package com.pws.employees.repository;
import com.pws.employees.entity.Role;
import com.pws.employees.entity.User;
import com.pws.employees.entity.UserRoleXRef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


    @Repository
    public interface UserRoleXrefRepository extends JpaRepository<UserRoleXRef, Integer> {

        @Query("select o.user from UserRoleXRef o where o.role.id= :roleId")
        List<User> fetchAllUsersByRoleId(Integer roleId);

        @Query("select o.role from UserRoleXRef o where o.user.id= :id and o.role.isActive=true")
        List<Role> findAllUserRoleByUserId(Integer id);

        @Query("select o.role from UserRoleXRef o where o.role.name= :role")
        Optional<Role> findByRole(String role);

    }
