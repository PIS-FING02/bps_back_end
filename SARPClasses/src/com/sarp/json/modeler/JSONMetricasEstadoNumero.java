package com.sarp.json.modeler;
public class JSONMetricasEstadoNumero {

	@Override
	public String toString() {
		return "JSONMetricasPuesto [internalId=" + internalId.toString() + ", estado=" + estado + ", timeSpent="
				+ timeSpent + ", lastUpdated=" + lastUpdated + ", dateCreated=" + dateCreated + "]";
	}
	private Integer internalId;
	private String estado;
	private String timeSpent;
	private String lastUpdated;
	private String dateCreated;
	
	public Integer getInternalId() {
		return internalId;
	}
	public void setInternalId(Integer internalId) {
		this.internalId = internalId;
	}
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
	public String getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	public String getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}
	
}

