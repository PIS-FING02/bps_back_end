package com.sarp.classes;

import java.sql.Timestamp;

public class BusinessTramite {

	// Atributos
	private String codigo;
	private String nombre;
	private Timestamp lastUpdated;

	// Constructores
	public BusinessTramite() {
	}

	public BusinessTramite(String codigo, String nombre) {
		this.codigo = codigo;
		this.nombre = nombre;
	}

	// Operaciones
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Timestamp getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Timestamp lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

}
