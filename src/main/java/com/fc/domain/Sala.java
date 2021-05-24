package com.fc.domain;

import java.util.Collection;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "sala")
public class Sala {

	// ===================VARIABLES===================================
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique=true)
	private Integer numero;

	private Integer aforoMax;
	
	@JsonIgnore
	@OneToMany(mappedBy = "sala", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
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

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Integer getAforoMax() {
		return aforoMax;
	}

	public void setAforoMax(Integer aforoMax) {
		this.aforoMax = aforoMax;
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
		return "Sala [id=" + id + ", numero=" + numero + ", aforoMax=" + aforoMax + ", slots="
				+ slots + "]";
	}
	

}
