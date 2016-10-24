package com.sarp.json.modeler;

import java.util.List;


public class JSONTramite {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JSONTramite other = (JSONTramite) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	Integer codigo;
	String nombre;

	List<JSONPuesto> puestos;
	List<JSONSector> sectores;
	
	public JSONTramite(){}
	
	public JSONTramite(Integer cod, String nom, String r){
		this.codigo = cod;
		this.nombre = nom;
	}

	public List<JSONPuesto> getPuestos() {
		return puestos;
	}

	public void setPuestos(List<JSONPuesto> puestos) {
		this.puestos = puestos;
	}

	public List<JSONSector> getSectores() {
		return sectores;
	}

	public void setSectores(List<JSONSector> sectores) {
		this.sectores = sectores;
	}
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

}
