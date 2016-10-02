package com.sarp.classes;

import java.sql.Timestamp;

public class BusinessDisplay {
	
	//Constructores
	public BusinessDisplay() {}	
	public BusinessDisplay(Integer codigo, String rutaArchivo) {		
		this.codigo = codigo;
		this.rutaArchivo = rutaArchivo;	
	}
	public BusinessDisplay(Integer codigo, String rutaArchivo, Timestamp timestamp) {		
		this.codigo = codigo;
		this.rutaArchivo = rutaArchivo;	
		this.timestamp = timestamp;
	}
	
	//Atributos
	private Integer codigo;
	private String rutaArchivo;
	private Timestamp timestamp;
	
	//Operaciones
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
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	
}
