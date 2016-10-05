package com.sarp.classes;

import java.sql.Timestamp;
import java.util.GregorianCalendar;

public class BusinessNumero {

	// Atributos
	private Integer internalId;
	private String externalId;
	private GregorianCalendar hora;
	private String estado;
	private Integer prioridad;
	private Timestamp lastUpdated;
	private Integer codTramite;
	private String codSector;

	// Constructores

	public BusinessNumero(Integer internalId, String externalId, GregorianCalendar hora, String estado,
			Integer prioridad, Integer codT, String codS) {
		this.internalId = internalId;
		this.externalId = externalId;
		this.hora = hora;
		this.estado = estado;
		this.prioridad = prioridad;
		this.codTramite = codT;
		this.codSector = codS;
	}

	public BusinessNumero(Integer internalId, String externalId, GregorianCalendar hora, String estado, Integer prioridad) {
		/* (1) duda pancho-guzman */
		this.internalId = internalId;
		this.externalId = externalId;
		this.hora = hora;
		this.estado = estado;
		this.prioridad = prioridad;
			
	}
	// Operaciones
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

	public GregorianCalendar getHora() {
		return hora;
	}

	public void setHora(GregorianCalendar hora) {
		this.hora = hora;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Integer getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(Integer prioridad) {
		this.prioridad = prioridad;
	}

	public Timestamp getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Timestamp lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public Integer getCodTramite() {
		return codTramite;
	}

	public void setCodTramite(Integer codTramite) {
		this.codTramite = codTramite;
	}

	public String getCodSector() {
		return codSector;
	}

	public void setCodSector(String codSector) {
		this.codSector = codSector;
	}
	
	
	
	

}
