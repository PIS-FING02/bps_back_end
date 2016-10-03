package com.sarp.classes;

import java.sql.Timestamp;

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
	private Timestamp timestamp;

	//Operaciones
	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
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
