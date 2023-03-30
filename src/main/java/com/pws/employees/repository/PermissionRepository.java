package com.pws.employees.repository;

import com.pws.employees.entity.Permission;
import com.pws.employees.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission,Integer> {

    @Query("select p from Permission p where p.role.id= :id")
    List<Permission>getAllUserPermisonsByRoleId(Integer id);
}
