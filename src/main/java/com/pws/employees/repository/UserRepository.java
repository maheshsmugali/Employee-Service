package com.pws.employees.repository;

import com.pws.employees.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    @Query("select o from User o where o.email=:email")
    Optional<User> findByEmail(String email);

    @Query("SELECT c FROM User c WHERE c.email = ?1")
    Optional<User> findUserByEmail(String email);

    public  User findByResetPasswordToken(String token);

//    User findByUsername(String username);
}
