package net.enjoy.springboot.registrationlogin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.enjoy.springboot.registrationlogin.entity.Alert;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {

    // Méthode pour obtenir les alertes résolues
    List<Alert> findByResolved(boolean resolved);

}
