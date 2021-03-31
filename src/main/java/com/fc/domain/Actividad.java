package com.fc.domain;

import java.util.Collection;
import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "actividad")
public class Actividad {

	// ===================VARIABLES===================================
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	private String nombre;

	@NotEmpty
	private String descripcion;

	@NotEmpty
	private Integer dificultad;

	@ManyToOne
	private TipoActividad tipoActividad;

	@JsonIgnore
	@OneToMany(mappedBy = "actividad", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Collection<Slot> slots;
	
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getDificultad() {
		return dificultad;
	}

	public void setDificultad(Integer dificultad) {
		this.dificultad = dificultad;
	}

	public TipoActividad getTipoActividad() {
		return tipoActividad;
	}

	public void setTipoActividad(TipoActividad tipoActividad) {
		this.tipoActividad = tipoActividad;
	}

	public Collection<Slot> getSlots() {
		return slots;
	}

	public void setSlots(Collection<Slot> slots) {
		this.slots = slots;
	}

	// ===============================================================


	@Override
	public String toString() {
		return "Actividad [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", dificultad="
				+ dificultad + ", tipoActividad=" + tipoActividad + ", slots=" + slots + "]";
	}

}
