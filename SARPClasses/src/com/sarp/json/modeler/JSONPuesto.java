package com.sarp.json.modeler;

import java.util.List;

public class JSONPuesto {

	String nombreMaquina;
	String usuarioId;
	Integer numeroPuesto;
	String estado;
	List<JSONSector> sectores;
	JSONNumero numeroAsignado;
	
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getNombreMaquina() {
		return nombreMaquina;
	}
	public void setNombreMaquina(String nombreMaquina) {
		this.nombreMaquina = nombreMaquina;
	}  
	
	public Integer getNumeroPuesto() {
		return numeroPuesto;
	}
	public void setNumeroPuesto(Integer numeroPuesto) {
		this.numeroPuesto = numeroPuesto;
	}
	public List<JSONSector> getSectores() {
		return sectores;
	}
	public void setSectores(List<JSONSector> sectores) {
		this.sectores = sectores;
	}
	public JSONNumero getNumeroAsignado() {
		return numeroAsignado;
	}
	public void setNumeroAsignado(JSONNumero numeroAsignado) {
		this.numeroAsignado = numeroAsignado;
	}
	public String getUsuarioId() {
		return usuarioId;
	}
	public void setUsuarioId(String usuarioId) {
		this.usuarioId = usuarioId;
	}
	
}
