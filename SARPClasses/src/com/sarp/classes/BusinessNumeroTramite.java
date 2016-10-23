package com.sarp.classes;

import java.sql.Timestamp;

import com.sarp.enumerados.EstadoPuesto;

public class BusinessNumeroTramite {
	
	//Constructores
	public BusinessNumeroTramite(){}
	public BusinessNumeroTramite(Integer codigoTramite, String resultadoFinal) {
		this.setCodigoTramite(codigoTramite);
		this.resultadoFinal = resultadoFinal;
	}

	//Atributos
	private Integer codigoTramite;
	private String resultadoFinal;

	//Operaciones
	public String getResultadoFinal() {
		return resultadoFinal;
	}
	public void setResultadoFinal(String resultadoFinal) {
		this.resultadoFinal = resultadoFinal;
	}
	public Integer getCodigoTramite() {
		return codigoTramite;
	}
	public void setCodigoTramite(Integer codigoTramite) {
		this.codigoTramite = codigoTramite;
	}


}

