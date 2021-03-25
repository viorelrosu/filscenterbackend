package com.fc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fc.domain.ClaseProgramada;

@Repository
public interface ClaseProgramadaRepository extends JpaRepository<ClaseProgramada, Long>{
    
}