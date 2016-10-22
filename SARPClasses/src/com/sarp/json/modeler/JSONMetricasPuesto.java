package com.sarp.json.modeler;
public class JSONMetricasPuesto {

	@Override
	public String toString() {
		return "JSONMetricasPuesto [nombreMaquina=" + nombreMaquina + ", usuarioAtencion=" + usuarioAtencion + ", estado="
				+ estado + ", timeSpent=" + timeSpent + ", lastUpdated=" + lastUpdated + ", dateCreated=" + dateCreated + "]";
	}
	private String nombreMaquina;
	private String usuarioAtencion;
	private String estado;
	private String timeSpent;
	private String lastUpdated;
	private String dateCreated;
	
	public String getNombreMaquina() {
		return nombreMaquina;
	}
	public void setNombreMaquina(String nombreMaquina) {
		this.nombreMaquina = nombreMaquina;
	}
	public String getUsuarioAtencion() {
		return usuarioAtencion;
	}
	public void setUsuarioAtencion(String usuarioAtencion) {
		this.usuarioAtencion = usuarioAtencion;
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

