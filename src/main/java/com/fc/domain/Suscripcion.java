package com.fc.domain;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "suscripcion")
public class Suscripcion {
	// ===================VARIABLES===================================

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Date fechaAlta;

	@Column(nullable=true)
	private Date fechaBaja;
	
	private boolean recurrente;
	
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
	
	public boolean isRecurrente() {
		return recurrente;
	}
	
	public Boolean getRecurrente() {
		return recurrente;
	}

	public void setRecurrente(boolean recurrente) {
		this.recurrente = recurrente;
	}
	

	// ===============================================================
	

	@Override
	public String toString() {
		return "Suscripcion [id=" + id + ", fechaAlta=" + fechaAlta + ", fechaBaja=" + fechaBaja + ", recurrente="
				+ recurrente + ", tipoSuscripcion=" + tipoSuscripcion + ", usuario=" + usuario + "]";
	}
	
}
