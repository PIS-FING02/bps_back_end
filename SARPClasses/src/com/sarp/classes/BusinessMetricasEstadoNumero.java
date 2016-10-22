package com.sarp.classes;

import java.util.GregorianCalendar;

public class BusinessMetricasEstadoNumero{
	
	//Constructores
	public BusinessMetricasEstadoNumero(){}
	public BusinessMetricasEstadoNumero(Integer internalId, String estado, String timeSpent, GregorianCalendar lastUpdated, GregorianCalendar dateCreated) {
		this.setInternalId(internalId);
		this.estado = estado;
		this.timeSpent = timeSpent;
		this.lastUpdated = lastUpdated;
		this.dateCreated = dateCreated;
	}

	//Atributos
	private Integer internalId;
	private String estado;
	private String timeSpent;
	private GregorianCalendar lastUpdated;
	private GregorianCalendar dateCreated;
	
	//Operaciones	
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getTimeSpent() {
		return timeSpent;
	}
	public void setTimeSpent(String timeSpent) {
		this.timeSpent = timeSpent;
	}
	public GregorianCalendar getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(GregorianCalendar lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	public GregorianCalendar getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(GregorianCalendar dateCreated) {
		this.dateCreated = dateCreated;
	}
	public Integer getInternalId() {
		return internalId;
	}
	public void setInternalId(Integer internalId) {
		this.internalId = internalId;
	}

}

