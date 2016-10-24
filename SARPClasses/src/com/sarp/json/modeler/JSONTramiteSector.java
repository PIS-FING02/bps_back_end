package com.sarp.json.modeler;

public class JSONTramiteSector {
	
	String tramiteId;
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
	
	public String getTramiteId() {
		return tramiteId;
	}
	public void setTramiteId(String tramiteId) {
		this.tramiteId = tramiteId;
	}
	public String getSectorId() {
		return sectorId;
	}
	public void setSectorId(String sectorId) {
		this.sectorId = sectorId;
	}
	

}
