package com.sarp.classes;

public class BusinessSector  {

	//Constructores
	public BusinessSector() {}


	public BusinessSector(String sectorid, String nombre,String ruta) {
		this.sectorId = sectorid;
		this.nombre = nombre;
		this.ruta = ruta;
	}

	//Atributos
	private String sectorId;
	private String nombre;
	private String ruta;
	
	//Operaciones
	public String getSectorId() {
		return sectorId;
	}

	public void setSectorId(String sectorId) {
		this.sectorId = sectorId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRuta() {
		return ruta;
	}
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	
}
