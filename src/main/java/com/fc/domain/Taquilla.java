package com.fc.domain;

import java.util.Collection;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "taquilla")
public class Taquilla {

	// ===================VARIABLES===================================

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Integer numero;

	@JsonIgnore
	@OneToMany(mappedBy = "taquilla", fetch = FetchType.EAGER)
	private Collection<Usuario> usuarios;
	
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

	public Collection<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Collection<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	@PreRemove
	public void nullificarTaquillas() {
		usuarios.forEach(usuario -> usuario.setTaquilla(null));
	}
	
	// ===============================================================

	@Override
	public String toString() {
		return "Taquilla [id=" + id + ", numero=" + numero + ", usuarios=" + usuarios + "]";
	}
	

}
