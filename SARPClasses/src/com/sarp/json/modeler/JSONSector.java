package com.sarp.json.modeler;

import java.util.List;

public class JSONSector {
	
	private String codigo;
	private String nombre;
	private String rutaSector;
	private List<JSONPuesto> puestos;
	private List<JSONDisplay> displays;
	@Override
	public String toString() {
		return "JSONSector [codigo=" + codigo + ", nombre=" + nombre + ", rutaSector=" + rutaSector + ", puestos="
				+ puestos + ", displays=" + displays + ", tramites=" + tramites + "]";
	}
	private List<JSONTramite> tramites;
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getRutaSector() {
		return rutaSector;
	}
	public void setRutaSector(String rutaSector) {
		this.rutaSector = rutaSector;
	}
	public List<JSONPuesto> getPuestos() {
		return puestos;
	}
	public void setPuestos(List<JSONPuesto> puestos) {
		this.puestos = puestos;
	}
	public List<JSONDisplay> getDisplays() {
		return displays;
	}
	public void setDisplays(List<JSONDisplay> displays) {
		this.displays = displays;
	}
	public List<JSONTramite> getTramites() {
		return tramites;
	}
	public void setTramites(List<JSONTramite> tramites) {
		this.tramites = tramites;
	}
	
}
