package com.fc.domain;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ejercicio")
public class Ejercicio {
	// ===================VARIABLES===================================

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	private String nombre;
	
	@JsonIgnore
	@OneToMany(mappedBy = "ejercicio", fetch = FetchType.EAGER)
	private Collection<EjercicioSerie> ejercicioSeries;
	
	// ===============================================================
	
	// ===================GETTERS Y SETTERS===========================

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Collection<EjercicioSerie> getEjercicioSeries() {
		return ejercicioSeries;
	}

	public void setEjercicioSeries(Collection<EjercicioSerie> ejercicioSeries) {
		this.ejercicioSeries = ejercicioSeries;
	}
	
	// ===============================================================

	@Override
	public String toString() {
		return "Ejercicio [id=" + id + ", nombre=" + nombre + ", ejercicioSeries=" + ejercicioSeries + "]";
	}
	

}
