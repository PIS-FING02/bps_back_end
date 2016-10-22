package com.sarp.classes;

public class BusinessSectorRol {

	@Override
	public String toString() {
		return "BusinessSectorRol [sectorId=" + sectorId + ", rol=" + rol + "]";
	}
	public String sectorId;
	public String rol;
	
	 
	public BusinessSectorRol(String sectorId, String rol) {

		this.sectorId = sectorId;
		this.rol = rol;
	}
	
	public String getSectorId() {
		return sectorId;
	}
	public void setSectorId(String sectorId) {
		this.sectorId = sectorId;
	}
	public String getRol() {
		return rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}
	
	
	
}
