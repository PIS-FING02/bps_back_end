package com.sarp.classes;

import java.sql.Timestamp;

public class BusinessTramiteSector {

	public Integer codigoSector;
	public Integer codigoTramite;
	private Timestamp timestamp;
	
	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getCodigoSector() {
		return codigoSector;
	}
	public void setCodigoSector(Integer codigoSector) {
		this.codigoSector = codigoSector;
	}
	public Integer getCodigoTramite() {
		return codigoTramite;
	}
	public void setCodigoTramite(Integer codigoTramite) {
		this.codigoTramite = codigoTramite;
	}
	
	
}
