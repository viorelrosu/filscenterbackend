package com.fc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fc.domain.TipoSuscripcion;

@Repository
public interface TipoSuscripcionRepository extends JpaRepository<TipoSuscripcion, Long>{
    
}
