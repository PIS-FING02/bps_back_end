package com.sarp.classes;

import com.sarp.enumerados.EstadoPuesto;

public class BusinessPuesto {
	
	//Constructores
	public BusinessPuesto(){}
	public BusinessPuesto(String nombreMaquina, String usuarioId, String estado, Integer numeroPuesto) {
		this.nombreMaquina = nombreMaquina;
		this.usuarioId = usuarioId;
		this.setNumeroPuesto(numeroPuesto);
		this.estado = (estado != null) ? EstadoPuesto.valueOf(estado) : null;
	}
	
	//Atributos
	private String nombreMaquina;
	private String usuarioId;
	private EstadoPuesto estado;
	private Integer numeroPuesto;
	private BusinessNumero numeroAsignado; 
	
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
	public BusinessNumero getNumeroAsignado() {
		return numeroAsignado;
	}
	public void setNumeroAsignado(BusinessNumero numero) {
		this.numeroAsignado = numero;
	}
	public Integer getNumeroPuesto() {
		return this.numeroPuesto;
	}
	public void setNumeroPuesto(Integer numeroPuesto) {
		this.numeroPuesto = numeroPuesto;
	}

}

