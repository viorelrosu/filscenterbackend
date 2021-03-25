package com.fc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fc.domain.Suscripcion;

@Repository
public interface SuscripcionRepository extends JpaRepository<Suscripcion, Long>{
    
}
