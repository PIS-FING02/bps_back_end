package com.sarp.classes;

import java.util.Date;

public class BusinessDatoComplementario {
	private String doc_identidad;
	private String nombre_completo;
	private String tipo_doc;
	private Date date_create;
	private Date last_update;
	public String getDoc_identidad() {
		return doc_identidad;
	}
	public void setDoc_identidad(String doc_identidad) {
		this.doc_identidad = doc_identidad;
	}
	public String getNombre_completo() {
		return nombre_completo;
	}
	public void setNombre_completo(String nombre_completo) {
		this.nombre_completo = nombre_completo;
	}
	public String getTipo_doc() {
		return tipo_doc;
	}
	public void setTipo_doc(String tipo_doc) {
		this.tipo_doc = tipo_doc;
	}
	public Date getDate_create() {
		return date_create;
	}
	public void setDate_create(Date date_create) {
		this.date_create = date_create;
	}
	public Date getLast_update() {
		return last_update;
	}
	public void setLast_update(Date last_update) {
		this.last_update = last_update;
	}
	public BusinessDatoComplementario(String doc_identidad, String nombre_completo, String tipo_doc, Date date_create,
			Date last_update) {
		super();
		this.doc_identidad = doc_identidad;
		this.nombre_completo = nombre_completo;
		this.tipo_doc = tipo_doc;
		this.date_create = date_create;
		this.last_update = last_update;
	}
	
	
	
}
