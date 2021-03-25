package com.fc.domain;

import java.util.Collection;

import javax.persistence.*;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "sala")
public class Sala {

	// ===================VARIABLES===================================
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	private Integer numero;

	@NotEmpty
	private Integer aforoMax;
	
	@JsonIgnore
	@OneToMany(mappedBy = "sala", fetch = FetchType.EAGER)
	private Collection<ClaseProgramada> clasesProgramadas;
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

	public Collection<ClaseProgramada> getClasesProgramadas() {
		return clasesProgramadas;
	}

	public void setClasesProgramadas(Collection<ClaseProgramada> clasesProgramadas) {
		this.clasesProgramadas = clasesProgramadas;
	}
	
	// ===============================================================

	@Override
	public String toString() {
		return "Sala [id=" + id + ", numero=" + numero + ", aforoMax=" + aforoMax + ", clasesProgramadas="
				+ clasesProgramadas + "]";
	}
	

}
