package com.fc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fc.domain.Ejercicio;

@Repository
public interface EjercicioRepository extends JpaRepository<Ejercicio, Long>{
    
}
