package net.enjoy.springboot.registrationlogin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.enjoy.springboot.registrationlogin.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
