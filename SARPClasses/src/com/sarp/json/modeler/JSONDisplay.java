package com.sarp.json.modeler;

import java.util.List;

public class JSONDisplay {
	Integer displayId;
	String rutaArchivo;
	List<JSONSector> sectores;
	
	public String getRutaArchivo() {
		return rutaArchivo;
	}
	public void setRutaArchivo(String rutaArchivo) {
		this.rutaArchivo = rutaArchivo;
	}
	public Integer getDisplayId() {
		return displayId;
	}
	public List<JSONSector> getSectores() {
		return sectores;
	}
	public void setSectores(List<JSONSector> sectores) {
		this.sectores = sectores;
	}
	public void setDisplayId(Integer displayId) {
		this.displayId = displayId;
	}
	
}
