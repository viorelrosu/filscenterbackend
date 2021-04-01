package com.fc.domain;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "suscripcion")
public class Suscripcion {
	// ===================VARIABLES===================================

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	private Date fechaAlta;

	@Column(nullable=true)
	private Date fechaBaja;
	
	@ManyToOne
	private TipoSuscripcion tipoSuscripcion;
	
	@ManyToOne
	private Usuario usuario;

	// ===============================================================
	
	// ===================GETTERS Y SETTERS===========================
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public TipoSuscripcion getTipoSuscripcion() {
		return tipoSuscripcion;
	}

	public void setTipoSuscripcion(TipoSuscripcion tipoSuscripcion) {
		this.tipoSuscripcion = tipoSuscripcion;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	// ===============================================================
	
	@Override
	public String toString() {
		return "Suscripcion [id=" + id + ", fechaAlta=" + fechaAlta + ", fechaBaja=" + fechaBaja + ", tipoSuscripcion="
				+ tipoSuscripcion + ", usuario=" + usuario + "]";
	}
	
}
