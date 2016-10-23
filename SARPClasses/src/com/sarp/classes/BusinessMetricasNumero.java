package com.sarp.classes;

import java.util.GregorianCalendar;

public class BusinessMetricasNumero{
	
	//Constructores
	public BusinessMetricasNumero(){}
	public BusinessMetricasNumero(Integer internalId, String externalId, String estado, Integer codigoTramite, String rutaSector, String usuarioAtencion, GregorianCalendar lastUpdated, GregorianCalendar dateCreated) {
		this.internalId = internalId;
		this.externalId = externalId;
		this.estado = estado;
		this.codigoTramite = codigoTramite;
		this.rutaSector = rutaSector;
		this.usuarioAtencion = usuarioAtencion;
		this.lastUpdated = lastUpdated;
		this.dateCreated = dateCreated;		
	}

	//Atributos
	private Integer internalId;
	private String externalId;
	private String estado;
	private Integer codigoTramite;
	private String rutaSector;
	private String usuarioAtencion;
	private GregorianCalendar lastUpdated;
	private GregorianCalendar dateCreated;
	
	//Operaciones
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
	public Integer getCodigoTramite() {
		return codigoTramite;
	}
	public void setCodigoTramite(Integer codigoTramite) {
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

}

