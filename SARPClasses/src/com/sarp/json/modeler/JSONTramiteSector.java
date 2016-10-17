package com.sarp.json.modeler;

public class JSONTramiteSector {
	
	Integer tramiteId;
	String tramiteNombre;
	String sectorId;
	String sectorNombre;
	
	public String getTramiteNombre() {
		return tramiteNombre;
	}

	public void setTramiteNombre(String tramiteNombre) {
		this.tramiteNombre = tramiteNombre;
	}

	public String getSectorNombre() {
		return sectorNombre;
	}

	public void setSectorNombre(String sectorNombre) {
		this.sectorNombre = sectorNombre;
	}
	
	public JSONTramiteSector() {}
	
	public Integer getTramiteId() {
		return tramiteId;
	}
	public void setTramiteId(Integer tramiteId) {
		this.tramiteId = tramiteId;
	}
	public String getSectorId() {
		return sectorId;
	}
	public void setSectorId(String sectorId) {
		this.sectorId = sectorId;
	}
	

}
