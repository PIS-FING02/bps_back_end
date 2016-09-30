package com.sarp.json.modeler;

public class JSONDisplay {
	Integer displayId;
	String rutaArchivo;
	Integer sectorId;
	
	public String getRutaArchivo() {
		return rutaArchivo;
	}
	public void setRutaArchivo(String rutaArchivo) {
		this.rutaArchivo = rutaArchivo;
	}
	public Integer getDisplayId() {
		return displayId;
	}
	public Integer getSectorId() {
		return sectorId;
	}
	public void setDisplayId(Integer displayId) {
		this.displayId = displayId;
	}
	public void setSectorId(Integer sectorId) {
		this.sectorId = sectorId;
	}
}
