package com.fc.domain;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.*;


@Entity
@Table(name = "factura")
public class Factura {

	// ===================VARIABLES===================================

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	private Date fecha;

	@NotEmpty
	private Integer numero;
	
	@NotEmpty
	private Double importe;

	@NotNull
	private Boolean pagado;

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

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Boolean getPagado() {
		return pagado;
	}

	public void setPagado(Boolean pagado) {
		this.pagado = pagado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Double getImporte() {
		return importe;
	}

	public void setImporte(Double importe) {
		this.importe = importe;
	}
	
	// ===============================================================

	@Override
	public String toString() {
		return "Factura [id=" + id + ", fecha=" + fecha + ", numero=" + numero + ", importe=" + importe + ", pagado="
				+ pagado + ", usuario=" + usuario + "]";
	}

}
