package com.fc.domain;

import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.*;

@Entity
@Table(name = "usuario")
public class Usuario {
	// ===================VARIABLES===================================
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	@Column(unique=true)
	private String nombreUsuario;

	@NotEmpty
	private String password;

	@NotEmpty
	private String nombre;

	@NotEmpty
	private String apellidos;

	@NotEmpty
	@Column(unique=true)
	private String dni;

	@NotEmpty
	private String email;

	@NotEmpty
	private String telefono;

	@NotEmpty
	private Date fechaNacimiento;

	@Column(nullable = true)
	private String cuentaBancaria;

	@Column(nullable = true)
	private Integer numeroTarjeta;

	@Column(nullable = true)
	private String biografia;

	@ManyToOne
	private Rol rol;

	@ManyToOne
	private Suscripcion suscripcion;

	@ManyToOne
	private Taquilla taquilla;

	@OneToOne
	@JoinColumn(name = "direccion_id")
	private Direccion direccion;

	@JsonIgnore
	@OneToMany(mappedBy = "usuario")
	private Collection<Factura> facturas;

	@JsonIgnore
	@OneToMany(mappedBy = "monitor")
	private Collection<Actividad> actividades;

	@JsonIgnore
	@OneToMany(mappedBy = "usuario")
	private Collection<Reserva> reservas;

	@JsonIgnore
	@OneToMany(mappedBy = "suscriptor")
	private Collection<TablaEjercicio> tablasEjercicioSuscriptor;

	@JsonIgnore
	@OneToMany(mappedBy = "monitor")
	private Collection<TablaEjercicio> tablasEjercicioMonitor;

	// ===============================================================

	// ===================GETTERS Y SETTERS===========================

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getCuentaBancaria() {
		return cuentaBancaria;
	}

	public void setCuentaBancaria(String cuentaBancaria) {
		this.cuentaBancaria = cuentaBancaria;
	}

	public Integer getNumeroTarjeta() {
		return numeroTarjeta;
	}

	public void setNumeroTarjeta(Integer numeroTarjeta) {
		this.numeroTarjeta = numeroTarjeta;
	}

	public String getBiografia() {
		return biografia;
	}

	public void setBiografia(String biografia) {
		this.biografia = biografia;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public Suscripcion getSuscripcion() {
		return suscripcion;
	}

	public void setSuscripcion(Suscripcion suscripcion) {
		this.suscripcion = suscripcion;
	}

	public Taquilla getTaquilla() {
		return taquilla;
	}

	public void setTaquilla(Taquilla taquilla) {
		this.taquilla = taquilla;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	public Collection<Factura> getFacturas() {
		return facturas;
	}

	public void setFacturas(Collection<Factura> facturas) {
		this.facturas = facturas;
	}

	public Collection<Actividad> getActividades() {
		return actividades;
	}

	public void setActividades(Collection<Actividad> actividades) {
		this.actividades = actividades;
	}

	public Collection<Reserva> getReservas() {
		return reservas;
	}

	public void setReservas(Collection<Reserva> reservas) {
		this.reservas = reservas;
	}

	public Collection<TablaEjercicio> getTablasEjercicioSuscriptor() {
		return tablasEjercicioSuscriptor;
	}

	public void setTablasEjercicioSuscriptor(Collection<TablaEjercicio> tablasEjercicioSuscriptor) {
		this.tablasEjercicioSuscriptor = tablasEjercicioSuscriptor;
	}

	public Collection<TablaEjercicio> getTablasEjercicioMonitor() {
		return tablasEjercicioMonitor;
	}

	public void setTablasEjercicioMonitor(Collection<TablaEjercicio> tablasEjercicioMonitor) {
		this.tablasEjercicioMonitor = tablasEjercicioMonitor;
	}

	// ===============================================================

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombreUsuario=" + nombreUsuario + ", password=" + password + ", nombre="
				+ nombre + ", apellidos=" + apellidos + ", dni=" + dni + ", email=" + email + ", telefono=" + telefono
				+ ", fechaNacimiento=" + fechaNacimiento + ", cuentaBancaria=" + cuentaBancaria + ", numeroTarjeta="
				+ numeroTarjeta + ", biografia=" + biografia + ", rol=" + rol + ", suscripcion=" + suscripcion
				+ ", taquilla=" + taquilla + ", direccion=" + direccion + ", facturas=" + facturas + ", actividades="
				+ actividades + ", reservas=" + reservas + ", tablasEjercicioSuscriptor=" + tablasEjercicioSuscriptor
				+ ", tablasEjercicioMonitor=" + tablasEjercicioMonitor + "]";
	}

}
