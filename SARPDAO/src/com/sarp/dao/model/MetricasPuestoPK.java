package com.sarp.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.GregorianCalendar;

import javax.persistence.*;

/**
 * The primary key class for the metricas_puesto database table.
 * 
 */
@Embeddable
public class MetricasPuestoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="nombre_maquina")
	private String nombreMaquina;

	@Column(name="usuario_atencion")
	private String usuarioAtencion;

	private String estado;
	
	@Column(name = "date_created")
	@Temporal(TemporalType.TIMESTAMP)
	private GregorianCalendar dateCreated;

	public MetricasPuestoPK() {
	}
	public String getNombreMaquina() {
		return this.nombreMaquina;
	}
	public void setNombreMaquina(String nombreMaquina) {
		this.nombreMaquina = nombreMaquina;
	}
	public String getUsuarioAtencion() {
		return this.usuarioAtencion;
	}
	public void setUsuarioAtencion(String usuarioAtencion) {
		this.usuarioAtencion = usuarioAtencion;
	}

	public String getEstado() {
		return this.estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public GregorianCalendar getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(GregorianCalendar dateCreated) {
		this.dateCreated = dateCreated;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MetricasPuestoPK)) {
			return false;
		}
		MetricasPuestoPK castOther = (MetricasPuestoPK)other;
		return 
			this.nombreMaquina.equals(castOther.nombreMaquina)
			&& this.usuarioAtencion.equals(castOther.usuarioAtencion)
			&& this.estado.equals(castOther.estado);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.nombreMaquina.hashCode();
		hash = hash * prime + this.usuarioAtencion.hashCode();
		hash = hash * prime + this.estado.hashCode();
		
		return hash;
	}
}