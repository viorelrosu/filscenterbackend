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
@Table(name = "tipo_suscripcion")
public class TipoSuscripcion {
	// ===================VARIABLES===================================

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	private String nombre;

	@NotEmpty
	private Integer duracion;

	@NotEmpty
	private Double tarifa;

	@JsonIgnore
	@OneToMany(mappedBy = "tipoSuscripcion", fetch = FetchType.EAGER)
	private Collection<Suscripcion> suscripciones;

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

	public Integer getDuracion() {
		return duracion;
	}

	public void setDuracion(Integer duracion) {
		this.duracion = duracion;
	}

	public Double getTarifa() {
		return tarifa;
	}

	public void setTarifa(Double tarifa) {
		this.tarifa = tarifa;
	}

	public Collection<Suscripcion> getSuscripciones() {
		return suscripciones;
	}

	public void setSuscripciones(Collection<Suscripcion> suscripciones) {
		this.suscripciones = suscripciones;
	}

	// ===============================================================

	@Override
	public String toString() {
		return "TipoSuscripcion [id=" + id + ", nombre=" + nombre + ", duracion=" + duracion + ", tarifa=" + tarifa
				+ ", suscripciones=" + suscripciones + "]";
	}
}
