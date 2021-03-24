package com.fc.domain;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "actividad")
public class Actividad {

	// ===================VARIABLES===================================
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	private String nombre;

	@NotEmpty
	private String descripcion;

	@NotEmpty
	private Integer dificultad;

	@ManyToOne
	private TipoActividad tipoActividad;

	@ManyToOne
	private Usuario monitor;

	@JsonIgnore
	@OneToMany(mappedBy = "actividad", fetch = FetchType.EAGER)
	private Collection<ClaseProgramada> clasesProgramadas;
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getDificultad() {
		return dificultad;
	}

	public void setDificultad(Integer dificultad) {
		this.dificultad = dificultad;
	}

	public TipoActividad getTipoActividad() {
		return tipoActividad;
	}

	public void setTipoActividad(TipoActividad tipoActividad) {
		this.tipoActividad = tipoActividad;
	}

	public Usuario getMonitor() {
		return monitor;
	}

	public void setMonitor(Usuario monitor) {
		this.monitor = monitor;
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
		return "Actividad [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", dificultad="
				+ dificultad + ", tipoActividad=" + tipoActividad + ", monitor=" + monitor + ", clasesProgramadas="
				+ clasesProgramadas + "]";
	}

}
