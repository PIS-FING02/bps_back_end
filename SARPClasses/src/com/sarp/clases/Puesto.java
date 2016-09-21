package com.sarp.clases;
import java.util.Date;

import com.sarp.enumerados.EstadoPuesto;

public class Puesto {
	
	//Constructor
	public Puesto(String nombreMaquina) {
		
		this.nombreMaquina = nombreMaquina;
		this.usuarioId = null;
		this.estado = EstadoPuesto.CERRADO;
		
		Date fechaCreado = new Date();
		this.dateCreated = fechaCreado;
		this.lastUpdate = fechaCreado;
	}
	
	//Atributos
	private String nombreMaquina;
	private String usuarioId;
	private EstadoPuesto estado;
	private Date dateCreated;
	private Date lastUpdate;
	
	//Operaciones
	public String getNombreMaquina() {
		return nombreMaquina;
	}

	public void setNombreMaquina(String nombreMaquina) {
		this.nombreMaquina = nombreMaquina;
	}

	public String getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(String usuarioId) {
		this.usuarioId = usuarioId;
	}

	public EstadoPuesto getEstado() {
		return estado;
	}

	public void setEstado(EstadoPuesto estado) {
		this.estado = estado;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	
	
	

}

