package com.fc.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "ejercicio_serie")
public class EjercicioSerie {
	// ===================VARIABLES===================================

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	private Integer series;

	@NotEmpty
	private Integer repeticiones;

	@NotEmpty
	private Integer porSemana;

	@ManyToOne(cascade = CascadeType.ALL)
	private Ejercicio ejercicio;

	@ManyToOne(cascade = CascadeType.ALL)
	private TablaEjercicio tablaEjercicio;
	// ===============================================================

	// ===================GETTERS Y SETTERS===========================

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getSeries() {
		return series;
	}

	public void setSeries(Integer series) {
		this.series = series;
	}

	public Integer getRepeticiones() {
		return repeticiones;
	}

	public void setRepeticiones(Integer repeticiones) {
		this.repeticiones = repeticiones;
	}

	public Integer getPorSemana() {
		return porSemana;
	}

	public void setPorSemana(Integer porSemana) {
		this.porSemana = porSemana;
	}

	public Ejercicio getEjercicio() {
		return ejercicio;
	}

	public void setEjercicio(Ejercicio ejercicio) {
		this.ejercicio = ejercicio;
	}

	public TablaEjercicio getTablaEjercicio() {
		return tablaEjercicio;
	}

	public void setTablaEjercicio(TablaEjercicio tablaEjercicio) {
		this.tablaEjercicio = tablaEjercicio;
	}

	// ===============================================================

	@Override
	public String toString() {
		return "EjercicioSerie [id=" + id + ", series=" + series + ", repeticiones=" + repeticiones + ", porSemana="
				+ porSemana + ", ejercicio=" + ejercicio + ", tablaEjercicio=" + tablaEjercicio + "]";
	}

}
