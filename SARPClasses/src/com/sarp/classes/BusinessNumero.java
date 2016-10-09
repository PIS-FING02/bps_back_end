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
	public BusinessNumero(){}
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

	public BusinessNumero(Integer internalId, String externalId, GregorianCalendar hora, String estado,
			Integer prioridad) {
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

	/*********** Metodos auxiliares para testing ****************/

	public String obtenerImpresion() {
		return "InternalID " + internalId.toString() + "\n" + "ExternalID " + externalId + "\n" + "hora "
				+ hora.getTime().toString() + "\n" + "estado " + estado + "\n" + "Prioridad " + prioridad.toString() + "\n"
				+ "Timestamp " + lastUpdated.toString() + "\n" + "Codigo Tramite " + codTramite.toString() + "\n"
				+ "Codigo Sector " + codSector;
	}

	public void printNumero() {
		System.out.println("ExternalID: " + externalId + "\n" + "hora: "
				+ hora.getTime().toString() + "\n" 
				+ "Prioridad " + prioridad.toString() + "\n"
				+ "Estado: "+estado);
	}
	
}
