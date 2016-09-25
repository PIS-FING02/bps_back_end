package com.sarp.classes;

public class BusinessSector  {

	//Constructores
	public BusinessSector() {}
	public BusinessSector(Integer sectorid, String nombre) {
		this.sectorId = sectorid;
		this.nombre = nombre;
	}

	//Atributos
	private Integer sectorId;
	private String nombre;
	
	//Operaciones
	public Integer getSectorId() {
		return sectorId;
	}

	public void setSectorId(Integer sectorId) {
		this.sectorId = sectorId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}
