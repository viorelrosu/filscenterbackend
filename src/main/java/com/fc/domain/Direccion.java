package com.fc.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "direccion")
public class Direccion {

//===================VARIABLES===================================

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	private String calle;

	@NotEmpty
	private Integer numero;

	@Column(nullable = true)
	private String bloque;

	@Column(nullable = true)
	private Integer escalera;

	@Column(nullable = true)
	private Integer piso;

	@Column(nullable = true)
	private String puerta;

	@NotEmpty
	private Integer codigoPostal;

	@NotNull
	@ManyToOne
	private Localidad localidad;

	@OneToOne(cascade = CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

//===============================================================

//===================GETTERS Y SETTERS===========================

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getBloque() {
		return bloque;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setBloque(String bloque) {
		this.bloque = bloque;
	}

	public Integer getEscalera() {
		return escalera;
	}

	public void setEscalera(Integer escalera) {
		this.escalera = escalera;
	}

	public Integer getPiso() {
		return piso;
	}

	public void setPiso(Integer piso) {
		this.piso = piso;
	}

	public String getPuerta() {
		return puerta;
	}

	public void setPuerta(String puerta) {
		this.puerta = puerta;
	}

	public Integer getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(Integer codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public Localidad getLocalidad() {
		return localidad;
	}

	public void setLocalidad(Localidad localidad) {
		this.localidad = localidad;
	}

// ===============================================================

	@Override
	public String toString() {
		return "Direccion [id=" + id + ", calle=" + calle + ", numero=" + numero + ", bloque=" + bloque + ", escalera="
				+ escalera + ", piso=" + piso + ", puerta=" + puerta + ", codigoPostal=" + codigoPostal + ", localidad="
				+ localidad + ", usuario=" + usuario + "]";
	}

}
