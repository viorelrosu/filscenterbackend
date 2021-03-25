package com.fc.domain;

import java.util.Collection;
import javax.persistence.*;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "provincia")
public class Provincia {

//===================VARIABLES===================================

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	private String nombre;

	@JsonIgnore
	@OneToMany(mappedBy = "provincia", fetch = FetchType.EAGER)
	private Collection<Localidad> localidades;
//===============================================================

	public Provincia() {

	}

	public Provincia(String nombre) {
		this.nombre = nombre;
	}

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

	public Collection<Localidad> getLocalidades() {
		return localidades;
	}

	public void setLocalidades(Collection<Localidad> localidades) {
		this.localidades = localidades;
	}

//===============================================================
	@Override
	public String toString() {
		return "Provincia [id=" + id + ", nombre=" + nombre + "]";
	}

}
