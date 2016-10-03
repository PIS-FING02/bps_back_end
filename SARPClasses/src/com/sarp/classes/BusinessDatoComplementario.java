package com.sarp.classes;

import java.sql.Timestamp;

public class BusinessDatoComplementario {
	
	//Cnstructores
	public BusinessDatoComplementario(){}
	public BusinessDatoComplementario(String docIdentidad, String nombreCompleto, String tipoDoc) {
		super();
		this.docIdentidad = docIdentidad;
		this.nombreCompleto = nombreCompleto;
		this.tipoDoc = tipoDoc;
	}

	//Atributos
	private String docIdentidad;
	private String nombreCompleto;
	private String tipoDoc;
	private Timestamp timestamp;
	
	//Operaciones
	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public void setTipoDoc(String tipoDoc) {
		this.tipoDoc = tipoDoc;
	}
	public String getDocIdentidad() {
		return docIdentidad;
	}
	public void setDocIdentidad(String docIdentidad) {
		this.docIdentidad = docIdentidad;
	}
	public String getNombreCompleto() {
		return nombreCompleto;
	}
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	public String getTipoDoc() {
		return tipoDoc;
	}
	public void setTipo_doc(String tipoDoc) {
		this.tipoDoc = tipoDoc;
	}

}
