package com.sarp.classes;

public class BusinessSector  {

	//Constructores
	public BusinessSector() {}

	public BusinessSector(Integer sectorid, String nombre, Integer codigoDisplay, String ruta) {
		this.sectorId = sectorid;
		this.nombre = nombre;
		this.setCodigoDisplay(codigoDisplay);
		this.setRuta(ruta);
	}

	//Atributos
	private Integer sectorId;
	private Integer codigoDisplay;
	private String nombre;
	private String ruta;
		
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

	public Integer getCodigoDisplay() {
		return codigoDisplay;
	}
	public void setCodigoDisplay(Integer codigoDisplay) {
		this.codigoDisplay = codigoDisplay;
	}
	public String getRuta() {
		return ruta;
	}
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	
}
