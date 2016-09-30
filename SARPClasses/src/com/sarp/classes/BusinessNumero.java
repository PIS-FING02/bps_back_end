package com.sarp.classes;

import java.util.Date;
import java.util.GregorianCalendar;

public class BusinessNumero {
	
	//Constructores
	public BusinessNumero() {}
	public BusinessNumero(Integer internalId, String externalId, GregorianCalendar hora, String estado, 
			Integer prioridad){
		this.internalId = internalId;
		this.externalId = externalId;
		this.hora = hora;
		this.estado = estado;
		this.prioridad = prioridad;

	}
	
	//Atributos
	private Integer internalId;
	private String externalId;
	private GregorianCalendar hora;
	private String estado;
	private Integer prioridad;


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

}

