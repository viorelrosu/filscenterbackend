package com.fc.domain;

import java.util.Collection;
import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "slot")
public class Slot {
	
	// ===================VARIABLES===================================

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	private String diaSemana;

	@NotEmpty
	private Integer horaInicio;

	@NotEmpty
	private Integer aforoActual;

	@ManyToOne(cascade = CascadeType.ALL)
	private Sala sala;

	@ManyToOne(cascade = CascadeType.ALL)
	private Actividad actividad;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Usuario monitor;

	@JsonIgnore
	@OneToMany(mappedBy = "slot", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Collection<Reserva> reservas;

	// ===============================================================

	// ===================GETTERS Y SETTERS===========================

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(Integer horaInicio) {
		this.horaInicio = horaInicio;
	}

	public Integer getAforoActual() {
		return aforoActual;
	}

	public void setAforoActual(Integer aforoActual) {
		this.aforoActual = aforoActual;
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}

	public Actividad getActividad() {
		return actividad;
	}

	public void setActividad(Actividad actividad) {
		this.actividad = actividad;
	}

	public Collection<Reserva> getReservas() {
		return reservas;
	}

	public void setReservas(Collection<Reserva> reservas) {
		this.reservas = reservas;
	}
	
	public String getDiaSemana() {
		return diaSemana;
	}

	public void setDiaSemana(String diaSemana) {
		this.diaSemana = diaSemana;
	}

	public Usuario getMonitor() {
		return monitor;
	}

	public void setMonitor(Usuario monitor) {
		this.monitor = monitor;
	}

	// ===============================================================

	@Override
	public String toString() {
		return "ClaseProgramada [id=" + id + ", diaSemana=" + diaSemana + ", horaInicio=" + horaInicio + ", aforoActual="
				+ aforoActual + ", sala=" + sala + ", actividad=" + actividad + ", reservas=" + reservas + "]";
	}

}
