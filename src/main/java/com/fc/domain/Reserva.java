package com.fc.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "reserva")
public class Reserva {
	// ===================VARIABLES===================================

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private Boolean recurrente;

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
	
	// ===============================================================

	@Override
	public String toString() {
		return "Reserva [id=" + id + ", recurrente=" + recurrente + ", usuario=" + usuario + ", slot=" + slot + "]";
	}

}
