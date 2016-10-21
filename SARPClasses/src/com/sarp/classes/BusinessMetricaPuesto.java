package com.sarp.classes;

import java.sql.Timestamp;
import java.util.GregorianCalendar;

import com.sarp.enumerados.EstadoPuesto;

public class BusinessMetricaPuesto {
	
	//Constructores
	public BusinessMetricaPuesto(){}
	public BusinessMetricaPuesto(String nombreMaquina, String usuarioAtencion, String estado, Integer timeSpent, GregorianCalendar lastUpdated, GregorianCalendar dateCreated) {
		this.setNombreMaquina(nombreMaquina);
		this.usuarioAtencion = usuarioAtencion;
		this.estado = (estado != null && !estado.equals("")) ? EstadoPuesto.valueOf(estado) : null;
		this.timeSpent = timeSpent;
		this.lastUpdated = lastUpdated;
		this.dateCreated = dateCreated;
	}

	//Atributos
	private String nombreMaquina;
	private String usuarioAtencion;
	private EstadoPuesto estado;
	private Integer timeSpent;
	private GregorianCalendar lastUpdated;
	private GregorianCalendar dateCreated;
	
	//Operaciones
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
	public EstadoPuesto getEstado() {
		return estado;
	}
	public void setEstado(EstadoPuesto estado) {
		this.estado = estado;
	}
	public Integer getTimeSpent() {
		return timeSpent;
	}
	public void setTimeSpent(Integer timeSpent) {
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

}

