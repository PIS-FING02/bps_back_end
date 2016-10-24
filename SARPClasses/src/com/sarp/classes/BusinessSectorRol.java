package com.sarp.classes;

public class BusinessSectorRol {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rol == null) ? 0 : rol.hashCode());
		result = prime * result + ((sectorId == null) ? 0 : sectorId.hashCode());
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
		BusinessSectorRol other = (BusinessSectorRol) obj;
		if (rol == null) {
			if (other.rol != null)
				return false;
		} else if (!rol.equals(other.rol))
			return false;
		if (sectorId == null) {
			if (other.sectorId != null)
				return false;
		} else if (!sectorId.equals(other.sectorId))
			return false;
		return true;
	}

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
