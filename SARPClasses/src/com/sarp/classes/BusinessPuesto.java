package com.sarp.classes;

import com.sarp.enumerados.EstadoPuesto;

public class BusinessPuesto {
	
	//Constructores
	public BusinessPuesto(){}
	public BusinessPuesto(String nombreMaquina, String usuarioId, String estado, Integer numero) {
		this.nombreMaquina = nombreMaquina;
		this.usuarioId = usuarioId;
		this.setNumero(numero);
		this.estado = (estado != null) ? EstadoPuesto.valueOf(estado) : null;
	}
	


	//Atributos
	private String nombreMaquina;
	private String usuarioId;
	private EstadoPuesto estado;
	private Integer numero;

	
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
	public Integer getNumero() {
		return numero;
	}
	public void setNumero(Integer numero) {
		this.numero = numero;
	}


}

