package com.sarp.classes;

import java.sql.Timestamp;

import com.sarp.enumerados.EstadoPuesto;

public class BusinessPuesto {
	
	//Constructores
	public BusinessPuesto(){}
	public BusinessPuesto(String nombreMaquina, String usuarioId, String estado, Integer numeroPuesto) {
		this.nombreMaquina = nombreMaquina;
		this.usuarioId = usuarioId;
		this.estado = (estado != null) ? EstadoPuesto.valueOf(estado) : null;
		this.numeroPuesto = numeroPuesto;
	}

	//Atributos
	private String nombreMaquina;
	private String usuarioId;
	private EstadoPuesto estado;
	private Integer numeroPuesto;
	private Timestamp lastUpdated;

	//Operaciones
	public String getNombreMaquina() {
		return nombreMaquina;
	}
	public void setNombreMaquina(String nombreMaquina) {
		this.nombreMaquina = nombreMaquina;
	}
	public String getUsuarioId() {
		return usuarioId;
	}
	public void setUsuarioId(String usuarioId) {
		this.usuarioId = usuarioId;
	}
	public EstadoPuesto getEstado() {
		return estado;
	}
	public void setEstado(EstadoPuesto estado) {
		this.estado = estado;
	}
	public Integer getNumeroPuesto() {
		return numeroPuesto;
	}
	public void setNumeroPuesto(Integer numeroPuesto) {
		this.numeroPuesto = numeroPuesto;
	}
	public Timestamp getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(Timestamp lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

}

