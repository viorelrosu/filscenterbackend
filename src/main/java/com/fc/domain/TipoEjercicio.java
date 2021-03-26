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
@Table(name = "tipo_ejercicio")
public class TipoEjercicio {

	// ===================VARIABLES===================================

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	private String nombre;

	@JsonIgnore
	@OneToMany(mappedBy = "tipoEjercicio", fetch = FetchType.EAGER)
	private Collection<Ejercicio> ejercicios;

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

	public Collection<Ejercicio> getEjercicios() {
		return ejercicios;
	}

	public void setEjercicios(Collection<Ejercicio> ejercicios) {
		this.ejercicios = ejercicios;
	}

	@Override
	public String toString() {
		return "TipoEjercicio [id=" + id + ", nombre=" + nombre + ", ejercicios=" + ejercicios + "]";
	}

	// ===============================================================

}
