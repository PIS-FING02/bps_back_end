package com.sarp.json.modeler;

import java.util.List;

public class JSONTramite {

	Integer codigo;
	String nombre;

	List<JSONPuesto> puestos;
	List<JSONSector> sectores;
	
	public JSONTramite(Integer cod, String nom, String r){
		this.codigo = cod;
		this.nombre = nom;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
