package com.fc.domain;

import java.util.Collection;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "rol")
public class Rol {
	// ===================VARIABLES===================================

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nombre;

	@JsonIgnore
	@OneToMany(mappedBy = "rol", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Collection<Usuario> usuarios;

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

	public Collection<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Collection<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	// ===============================================================

	@Override
	public String toString() {
		return "Rol [id=" + id + ", nombre=" + nombre + ", usuarios=" + usuarios + "]";
	}

}
