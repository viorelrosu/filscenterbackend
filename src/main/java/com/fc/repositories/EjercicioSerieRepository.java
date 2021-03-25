package com.fc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fc.domain.EjercicioSerie;

@Repository
public interface EjercicioSerieRepository extends JpaRepository<EjercicioSerie, Long>{
    
}
