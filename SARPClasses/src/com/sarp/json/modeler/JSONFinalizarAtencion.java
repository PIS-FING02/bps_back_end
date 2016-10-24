package com.sarp.json.modeler;

import java.util.List;

public class JSONFinalizarAtencion {

	String nombreMaquina;
	Integer id;
	List<JSONTramiteResultado> tramiteResultado;
	
	public String getNombreMaquina() {
		return nombreMaquina;
	}
	public void setNombreMaquina(String nombreMaquina) {
		this.nombreMaquina = nombreMaquina;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public List<JSONTramiteResultado> getTramiteResultado() {
		return tramiteResultado;
	}
	public void setTramiteResultado(List<JSONTramiteResultado> tramiteResultado) {
		this.tramiteResultado = tramiteResultado;
	}
	
	
}
