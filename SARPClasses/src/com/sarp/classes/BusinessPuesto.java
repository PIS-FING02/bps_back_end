package com.sarp.classes;

import com.sarp.enumerados.EstadoPuesto;

public class BusinessPuesto {
	
	//Constructores
	public BusinessPuesto(){}
	public BusinessPuesto(String nombreMaquina, String usuarioId, String estado) {
		this.nombreMaquina = nombreMaquina;
		this.usuarioId = usuarioId;
		if(estado != null){
			this.estado = EstadoPuesto.valueOf(estado);
		}
	}
	
	//Atributos
	private String nombreMaquina;
	private String usuarioId;
	private EstadoPuesto estado;
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

}

