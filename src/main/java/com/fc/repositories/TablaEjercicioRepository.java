package com.fc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fc.domain.TablaEjercicio;

@Repository
public interface TablaEjercicioRepository extends JpaRepository<TablaEjercicio, Long>{
    
}
