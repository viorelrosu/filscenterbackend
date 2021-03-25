package com.fc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fc.domain.Sala;

@Repository
public interface SalaRepository extends JpaRepository<Sala, Long>{
    
}
