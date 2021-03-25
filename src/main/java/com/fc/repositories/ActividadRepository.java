package com.fc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fc.domain.Actividad;

@Repository
public interface ActividadRepository extends JpaRepository<Actividad, Long>{
    
}
