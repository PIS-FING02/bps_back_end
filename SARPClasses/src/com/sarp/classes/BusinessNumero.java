package com.sarp.classes;

import java.util.Date;

public class BusinessNumero {
	
	//Constructores
	public BusinessNumero() {}
	public BusinessNumero(Integer internalId, Integer codigoTramite, String externalId, Date hora, String estado, 
			Integer prioridad, BusinessDatoComplementario dato){
		this.internalId = internalId;
		this.codigoTramite = codigoTramite;
		this.externalId = externalId;
		this.hora = hora;
		this.estado = estado;
		this.prioridad = prioridad;
		this.datoComplementario = dato;
	}
	
	//Atributos
	private Integer internalId;
	private Integer codigoTramite;
	private String externalId;
	private Date hora;
	private String estado;
	private Integer prioridad;
	private BusinessDatoComplementario datoComplementario;

	//Operaciones
	public Integer getInternalId() {
		return internalId;
	}
	public void setInternalId(Integer internalId) {
		this.internalId = internalId;
	}

	public Integer getCodigoTramite() {
		return codigoTramite;
	}
	public void setCodigoTramite(Integer codigoTramite) {
		this.codigoTramite = codigoTramite;
	}

	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public Date getHora() {
		return hora;
	}
	public void setHora(Date hora) {
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

	public BusinessDatoComplementario getDatoComplementario() {
		return datoComplementario;
	}
	public void setDatoComplementario(BusinessDatoComplementario datoComplementario) {
		this.datoComplementario = datoComplementario;
	}
}

