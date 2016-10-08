package com.sarp.json.modeler;

import java.util.List;

public class JSONPuesto {

	@Override
	public String toString() {
		return "JSONPuesto [nombreMaquina=" + nombreMaquina + ", usuarioId=" + usuarioId + ", numeroPuesto="
				+ numeroPuesto + ", estado=" + estado + ", sectores=" + sectores + ", tramites=" + tramites
				+ ", numeroAsignado=" + numeroAsignado + "]";
	}
	String nombreMaquina;
	String usuarioId;
	Integer numeroPuesto;
	String estado;
	List<JSONSector> sectores;
	List<JSONTramite> tramites;
	
	public List<JSONTramite> getTramites() {
		return tramites;
	}
	public void setTramites(List<JSONTramite> tramites) {
		this.tramites = tramites;
	}
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

