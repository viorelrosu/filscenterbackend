package com.fc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fc.domain.Slot;

@Repository
public interface SlotRepository extends JpaRepository<Slot, Long>{
    
}