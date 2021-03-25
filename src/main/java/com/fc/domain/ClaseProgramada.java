package com.fc.domain;

import java.util.Collection;
import javax.persistence.*;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "clase_programada")
public class ClaseProgramada {
	
	// ===================VARIABLES===================================

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	private String dia;

	@NotEmpty
	private Integer horaInicio;

	@NotEmpty
	private Integer aforoActual;

	@ManyToOne
	private Sala sala;

	@ManyToOne
	private Actividad actividad;

	@JsonIgnore
	@OneToMany(mappedBy = "claseProgramada", fetch = FetchType.EAGER)
	private Collection<Reserva> reservas;

	// ===============================================================

	// ===================GETTERS Y SETTERS===========================

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
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

	// ===============================================================

	@Override
	public String toString() {
		return "ClaseProgramada [id=" + id + ", dia=" + dia + ", horaInicio=" + horaInicio + ", aforoActual="
				+ aforoActual + ", sala=" + sala + ", actividad=" + actividad + ", reservas=" + reservas + "]";
	}

}
