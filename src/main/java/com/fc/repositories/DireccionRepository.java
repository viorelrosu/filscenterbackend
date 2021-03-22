package com.fc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.fc.domain.Direccion;

@Repository
public interface DireccionRepository extends JpaRepository<Direccion, Long>{
    
}
