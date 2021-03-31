package com.fc.domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tabla_ejercicio")
public class TablaEjercicio {
	// ===================VARIABLES===================================

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	private Date fechaInicio;

	@NotEmpty
	private Date fechaFin;

	@NotEmpty
	private Boolean activa;

	@JsonIgnore
	@OneToMany(mappedBy = "tablaEjercicio", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Collection<EjercicioSerie> ejercicioSeries;

	@ManyToOne
	private Usuario monitor;

	@ManyToOne
	private Usuario suscriptor;

	// ===============================================================

	// ===================GETTERS Y SETTERS===========================

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Boolean getActiva() {
		return activa;
	}

	public void setActiva(Boolean activa) {
		this.activa = activa;
	}

	public Collection<EjercicioSerie> getEjercicioSeries() {
		return ejercicioSeries;
	}

	public void setEjercicioSeries(Collection<EjercicioSerie> ejercicioSeries) {
		this.ejercicioSeries = ejercicioSeries;
	}

	public Usuario getMonitor() {
		return monitor;
	}

	public void setMonitor(Usuario monitor) {
		this.monitor = monitor;
	}

	public Usuario getSuscriptor() {
		return suscriptor;
	}

	public void setSuscriptor(Usuario suscriptor) {
		this.suscriptor = suscriptor;
	}

	// ===============================================================

	@Override
	public String toString() {
		return "TablaEjercicio [id=" + id + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", activa="
				+ activa + ", ejercicioSeries=" + ejercicioSeries + ", monitor=" + monitor + ", suscriptor="
				+ suscriptor + "]";
	}

}
