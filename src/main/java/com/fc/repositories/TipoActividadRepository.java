package com.fc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fc.domain.TipoActividad;

@Repository
public interface TipoActividadRepository extends JpaRepository<TipoActividad, Long>{
    
}
