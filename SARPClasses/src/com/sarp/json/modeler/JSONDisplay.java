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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idDisplay == null) ? 0 : idDisplay.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JSONDisplay other = (JSONDisplay) obj;
		if (idDisplay == null) {
			if (other.idDisplay != null)
				return false;
		} else if (!idDisplay.equals(other.idDisplay))
			return false;
		return true;
	}

}
