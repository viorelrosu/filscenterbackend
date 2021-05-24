package com.fc.domain;

import java.util.Collection;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tipo_suscripcion")
public class TipoSuscripcion {
	// ===================VARIABLES===================================

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nombre;

	private Integer duracion;

	private Double tarifa;

	@JsonIgnore
	@OneToMany(mappedBy = "tipoSuscripcion", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
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
