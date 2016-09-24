package com.sarp.json.modeler;

public class JSONTramite {

	int codigo;
	String nombre;
	String rol;
	
	public JSONTramite(int cod, String nom, String r){
		this.codigo = cod;
		this.nombre = nom;
		this.rol = r;
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

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

}
