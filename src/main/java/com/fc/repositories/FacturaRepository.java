package com.fc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fc.domain.Factura;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long>{
    
}
