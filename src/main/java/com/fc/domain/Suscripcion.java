package com.fc.domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	
	@ManyToOne(cascade = CascadeType.ALL)
	private TipoSuscripcion tipoSuscripcion;
	
	@JsonIgnore
	@OneToMany(mappedBy = "suscripcion", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
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
	
	public Collection<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Collection<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	// ===============================================================
	
	@Override
	public String toString() {
		return "Suscripcion [id=" + id + ", fechaAlta=" + fechaAlta + ", fechaBaja=" + fechaBaja + ", tipoSuscripcion="
				+ tipoSuscripcion + ", usuarios=" + usuarios + "]";
	}
	
}
