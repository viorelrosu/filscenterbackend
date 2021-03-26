package com.fc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fc.domain.TipoEjercicio;

@Repository
public interface TipoEjercicioRepository extends JpaRepository<TipoEjercicio, Long>{
    
}
