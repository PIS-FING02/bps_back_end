package com.sarp.json.modeler;

import java.util.List;

public class JSONSector {
	
	Integer codigo;
	String nombre;
	String rutaSector;
	List<JSONPuesto> puestos;
	List<JSONDisplay> displays;
	List<JSONTramite> tramites;
	
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
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
