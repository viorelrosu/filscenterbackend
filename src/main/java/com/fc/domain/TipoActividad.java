package com.fc.domain;

import java.util.Collection;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tipo_actividad")
public class TipoActividad {

	// ===================VARIABLES===================================
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	private String nombre;
	
	@JsonIgnore
	@OneToMany(mappedBy = "tipoActividad", fetch = FetchType.EAGER)
	private Collection<Actividad> actividades;
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

	public Collection<Actividad> getActividades() {
		return actividades;
	}

	public void setActividades(Collection<Actividad> actividades) {
		this.actividades = actividades;
	}
	
	// ===============================================================

	@Override
	public String toString() {
		return "TipoActividad [id=" + id + ", nombre=" + nombre + ", actividades=" + actividades + "]";
	}
	
	
	

}
