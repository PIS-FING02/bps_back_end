package com.sarp.classes;

public class BusinessNumeroTramite {
	
	//Constructores
	public BusinessNumeroTramite(){}
	public BusinessNumeroTramite(String codigoTramite, String resultadoFinal) {
		this.setCodigoTramite(codigoTramite);
		this.resultadoFinal = resultadoFinal;
	}

	//Atributos
	private String codigoTramite;
	private String resultadoFinal;

	//Operaciones
	public String getResultadoFinal() {
		return resultadoFinal;
	}
	public void setResultadoFinal(String resultadoFinal) {
		this.resultadoFinal = resultadoFinal;
	}
	public String getCodigoTramite() {
		return codigoTramite;
	}
	public void setCodigoTramite(String codigoTramite) {
		this.codigoTramite = codigoTramite;
	}

}

