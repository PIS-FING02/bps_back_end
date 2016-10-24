package com.sarp.classes;

import java.sql.Timestamp;

public class BusinessDisplay {
	
	//Constructores
	public BusinessDisplay() {}	
	public BusinessDisplay(String idDisplay) {		
		this.idDisplay = idDisplay;	
	}

	//Atributos
	private String idDisplay;
	private Timestamp lastUpdated;
	
	//Operaciones

	public String getIdDisplay() {
		return idDisplay;
	}
	public void setIdDisplay(String idDisplay) {
		this.idDisplay = idDisplay;
	}
	public Timestamp getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(Timestamp lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	
}
