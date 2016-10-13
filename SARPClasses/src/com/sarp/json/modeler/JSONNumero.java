package com.sarp.json.modeler;

import java.util.GregorianCalendar;

public class JSONNumero {

	Integer id;
	String externalId;
	GregorianCalendar hora;
	String estado;
	Integer prioridad;
	JSONDatosComp datosComplementarios;
	Integer idTramite;
	String idSector;
	
	public JSONDatosComp getDatosComplementarios() {
		return datosComplementarios;
	}
	public void setDatosComplementarios(JSONDatosComp datosComplementarios) {
		this.datosComplementarios = datosComplementarios;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public Integer getIdTramite() {
		return idTramite;
	}
	public void setIdTramite(Integer idTramite) {
		this.idTramite = idTramite;
	}
	public String getIdSector() {
		return idSector;
	}
	public void setIdSector(String idSector) {
		this.idSector = idSector;
	}

}
