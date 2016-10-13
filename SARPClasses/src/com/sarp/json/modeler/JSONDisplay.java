package com.sarp.json.modeler;

import java.util.List;

public class JSONDisplay {
	
	String idDisplay;
	List<JSONSector> sectores;
	
	public String getIdDisplay() {
		return idDisplay;
	}
	public void setIdDisplay(String idDisplay) {
		this.idDisplay = idDisplay;
	}

	public List<JSONSector> getSectores() {
		return sectores;
	}
	public void setSectores(List<JSONSector> sectores) {
		this.sectores = sectores;
	}

}
