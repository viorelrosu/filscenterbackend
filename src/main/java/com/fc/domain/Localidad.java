package com.fc.domain;

import java.util.Collection;
import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "localidad")
public class Localidad{

//===================VARIABLES===================================

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	private String nombre;

	@ManyToOne(cascade = CascadeType.ALL)
	private Provincia provincia;

	@JsonIgnore
	@OneToMany(mappedBy = "localidad", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Collection<Direccion> direcciones;
//===============================================================

	public Localidad() {

	}

	public Localidad(String nombre) {
		this.nombre = nombre;
	}

	public Localidad(String nombre, Provincia provincia) {
		this.nombre = nombre;
		this.provincia = provincia;
	}

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

	public Provincia getProvincia() {
		return provincia;
	}

	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public Collection<Direccion> getDirecciones() {
		return direcciones;
	}

	public void setDirecciones(Collection<Direccion> direcciones) {
		this.direcciones = direcciones;
	}
	
//===============================================================

	

	@Override
	public String toString() {
		return "Localidad [id=" + id + ", nombre=" + nombre + ", provincia=" + provincia + ", direcciones="
				+ direcciones + "]";
	}

}
