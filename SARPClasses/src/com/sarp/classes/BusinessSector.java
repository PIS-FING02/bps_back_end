package com.sarp.classes;


import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BusinessSector  {

	private Integer sectorId;
	private String nombre;

	public BusinessSector(Integer sectorid, String nombre) {
		this.sectorId = sectorid;
		this.nombre = nombre;
	}
	
	public BusinessSector() {}
	
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
