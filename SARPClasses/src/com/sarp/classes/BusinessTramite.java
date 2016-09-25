package com.sarp.classes;

public class BusinessTramite {
	
	//Constructores
	public BusinessTramite(){}
	public BusinessTramite(Integer codigo, String nombre) {
		this.codigo = codigo;
		this.nombre = nombre;
	}

	//Atributos
	private Integer codigo;
	private String nombre;	

	//Operaciones
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
