package com.sarp.classes;

import java.util.Date;

public class BusinessTramite {
	public BusinessTramite(Integer codigo, String nombre) {
		this.codigo = codigo;
		this.nombre = nombre;
		Date fechaCreado = new Date();
		this.dateCreated = fechaCreado;
		this.lastUpdate = fechaCreado;
	}

	private Integer codigo;

	private String nombre;
	
	private Date dateCreated;
	
	private Date lastUpdate;
	
	
	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}
