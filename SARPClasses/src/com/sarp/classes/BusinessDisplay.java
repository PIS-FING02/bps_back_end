package com.sarp.classes;

import java.util.Date;

public class BusinessDisplay {
	private Integer codigo;
	private String rutaArchivo;
	private Date dateCreated;;
	private Date lastUpdated;;
	
	public BusinessDisplay() {
	}
	public BusinessDisplay(Integer codigo, String rutaArchivo, Date dateCreated, Date lastUpdated) {		
		this.setCodigo(codigo);
		this.setRutaArchivo(rutaArchivo);
		this.setDateCreated(dateCreated);
		this.setLastUpdated(lastUpdated);
	}
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public String getRutaArchivo() {
		return rutaArchivo;
	}
	public void setRutaArchivo(String rutaArchivo) {
		this.rutaArchivo = rutaArchivo;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public Date getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	
}
