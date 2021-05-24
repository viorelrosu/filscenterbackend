package com.fc.domain;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "reserva")
public class Reserva {
	// ===================VARIABLES===================================

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Boolean recurrente;
	
	private Date fechaInicio;

	@ManyToOne
	private Usuario usuario;
	
	@ManyToOne
	private Slot slot;
	
	// ===============================================================

	// ===================GETTERS Y SETTERS===========================

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getRecurrente() {
		return recurrente;
	}

	public void setRecurrente(Boolean recurrente) {
		this.recurrente = recurrente;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Slot getSlot() {
		return slot;
	}

	public void setSlot(Slot slot) {
		this.slot = slot;
	}
	
	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	
	// ===============================================================

	@Override
	public String toString() {
		return "Reserva [id=" + id + ", recurrente=" + recurrente + ", fechaInicio=" + fechaInicio + ", usuario="
				+ usuario + ", slot=" + slot + "]";
	}

}
