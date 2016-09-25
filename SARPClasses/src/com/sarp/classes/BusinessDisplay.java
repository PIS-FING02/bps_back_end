package com.sarp.classes;

public class BusinessDisplay {
	
	//Constructores
	public BusinessDisplay() {}	
	public BusinessDisplay(Integer codigo, String rutaArchivo) {		
		this.codigo = codigo;
		this.rutaArchivo = rutaArchivo;		
	}
	
	//Atributos
	private Integer codigo;
	private String rutaArchivo;
	
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
	
}
