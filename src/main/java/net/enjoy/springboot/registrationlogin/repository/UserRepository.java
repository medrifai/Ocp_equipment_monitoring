package net.enjoy.springboot.registrationlogin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.enjoy.springboot.registrationlogin.entity.Role;
import net.enjoy.springboot.registrationlogin.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u JOIN FETCH u.roles WHERE u.email = :email")
    User findByEmailWithRoles(String email);

    @Query("SELECT u FROM User u JOIN FETCH u.roles")
    List<User> findAllWithRoles();
    
    User findByEmail(String email);
    List<User> findByRoles(Role role);
}
