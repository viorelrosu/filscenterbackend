package com.fc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fc.domain.Taquilla;

@Repository
public interface TaquillaRepository extends JpaRepository<Taquilla, Long>{
    
}