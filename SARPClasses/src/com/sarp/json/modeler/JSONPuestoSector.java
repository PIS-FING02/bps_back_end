package com.sarp.json.modeler;

public class JSONPuestoSector {
	@Override
	public String toString() {
		return "JSONPuestoSector [sector=" + sector + ", puesto=" + puesto + "]";
	}
	JSONSector sector;
	JSONPuesto puesto;
	
	public JSONSector getSector() {
		return sector;
	}
	public void setSector(JSONSector sector) {
		this.sector = sector;
	}
	public JSONPuesto getPuesto() {
		return puesto;
	}
	public void setPuesto(JSONPuesto puesto) {
		this.puesto = puesto;
	}
	
	
}
