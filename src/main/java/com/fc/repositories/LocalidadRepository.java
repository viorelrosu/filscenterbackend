package com.fc.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fc.domain.Localidad;


@Repository
public interface LocalidadRepository extends JpaRepository<Localidad, Long>{
	public Localidad findOneByNombreAndProvinciaId(String nombre, Long provinciaId);
	
}