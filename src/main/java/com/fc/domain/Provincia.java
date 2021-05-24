package com.fc.domain;

import java.util.Collection;
import javax.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "provincia")
public class Provincia {

//===================VARIABLES===================================

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String nombre;

	@JsonIgnore
	@OneToMany(mappedBy = "provincia", cascade = CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
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
