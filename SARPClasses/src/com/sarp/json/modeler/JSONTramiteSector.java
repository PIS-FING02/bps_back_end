package com.sarp.json.modeler;

public class JSONTramiteSector {
	
	JSONTramite tramite;
	JSONSector sector;
	
	public JSONTramiteSector(){}
	public JSONTramiteSector(JSONTramite tramite, JSONSector sector) {
		this.tramite = tramite;
		this.sector = sector;
	}
	public JSONTramite getTramite() {
		return tramite;
	}
	public void setTramite(JSONTramite tramite) {
		this.tramite = tramite;
	}
	public JSONSector getSector() {
		return sector;
	}
	public void setSector(JSONSector sector) {
		this.sector = sector;
	}
	
	
}
