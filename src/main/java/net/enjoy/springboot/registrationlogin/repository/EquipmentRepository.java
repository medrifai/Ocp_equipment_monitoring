package net.enjoy.springboot.registrationlogin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.enjoy.springboot.registrationlogin.entity.Equipment;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
}
