package com.sarp.json.modeler;

import java.util.GregorianCalendar;

public class JSONMetricasNumero {

	@Override
	public String toString() {
		return "JSONMetricasPuesto [internalId=" + internalId.toString() + ", externalId=" + externalId + ", estado="
				+ estado + ", codigoTramite=" + codigoTramite + ", rutaSector=" + rutaSector + ", usuarioAtencion=" + usuarioAtencion
				+ ", lastUpdated=" + lastUpdated + ", dateCreated=" + dateCreated + "]";
	}
	
	private Integer internalId;
	private String externalId;
	private String estado;
	private String codigoTramite;
	private String rutaSector;
	private String usuarioAtencion;
	private String lastUpdated;
	private String dateCreated;
	public Integer getInternalId() {
		return internalId;
	}
	public void setInternalId(Integer internalId) {
		this.internalId = internalId;
	}
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getCodigoTramite() {
		return codigoTramite;
	}
	public void setCodigoTramite(String codigoTramite) {
		this.codigoTramite = codigoTramite;
	}
	public String getRutaSector() {
		return rutaSector;
	}
	public void setRutaSector(String rutaSector) {
		this.rutaSector = rutaSector;
	}
	public String getUsuarioAtencion() {
		return usuarioAtencion;
	}
	public void setUsuarioAtencion(String usuarioAtencion) {
		this.usuarioAtencion = usuarioAtencion;
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

