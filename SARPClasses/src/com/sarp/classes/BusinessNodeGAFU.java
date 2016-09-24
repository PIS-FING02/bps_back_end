package com.sarp.classes;


import java.util.ArrayList;
import java.util.Date;

public class BusinessNodeGAFU {
 private String codigo;
 private String descripcion;
 private Date fechaDesde;
 private Date fechaHasta;
 private final ArrayList<BusinessNodeGAFU> hijos = new ArrayList<BusinessNodeGAFU>();
 private String nombre;
 private BusinessNodeGAFU padre;
 private String restriccion;	

 

public BusinessNodeGAFU(String codigo, String descripcion, Date fechaDesde, Date fechaHasta, String nombre, BusinessNodeGAFU padre,
		String restriccion) {
	super();
	this.codigo = codigo;
	this.descripcion = descripcion;
	this.fechaDesde = fechaDesde;
	this.fechaHasta = fechaHasta;
	this.nombre = nombre;

	this.restriccion = restriccion;
}

public String getCodigo() {
	return codigo;
}
 
 public void setCodigo(String codigo) {
	this.codigo = codigo;
}
 
 
public String getDescripcion() {
	return descripcion;
}

public void setDescripcion(String descripcion) {
	this.descripcion = descripcion;
}

public Date getFechaDesde() {
	return fechaDesde;
}

public void setFechaDesde(Date fechaDesde) {
	this.fechaDesde = fechaDesde;
}

public void setPadre(BusinessNodeGAFU padre) {
	this.padre = padre;
}

public Date getFechaHasta() {
	return fechaHasta;
}

public void setFechaHasta(Date fechaHasta) {
	this.fechaHasta = fechaHasta;
}

public ArrayList<BusinessNodeGAFU> getHijos() {
	return hijos;
}

public String getNombre() {
	return nombre;
}

public BusinessNodeGAFU getPadre() {
	return padre;
}

public String getRestriccion() {
	return restriccion;
}

public void addHijo(BusinessNodeGAFU hijo) { 
		this.hijos.add(hijo);	
	}


public String obtenerCamino(){
	BusinessNodeGAFU padre = this.getPadre();
	String dir = "";
	while(padre !=null){
		dir = padre.getCodigo() +"/" + dir;
		padre = padre.getPadre();
	}
	return dir;
}

}