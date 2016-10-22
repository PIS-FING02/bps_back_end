package com.sarp.dao.model;

import java.io.Serializable;
import java.util.GregorianCalendar;

import javax.persistence.*;

/**
 * The primary key class for the metricas_estado_numero database table.
 * 
 */
@Embeddable
public class MetricasEstadoNumeroPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String estado;

	@Column(name="internal_id")
	private Integer internalId;
	
	@Column(name = "date_created")
	@Temporal(TemporalType.TIMESTAMP)
	private GregorianCalendar dateCreated;

	public MetricasEstadoNumeroPK() {
	}
	public String getEstado() {
		return this.estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Integer getInternalId() {
		return this.internalId;
	}
	public void setInternalId(Integer internalId) {
		this.internalId = internalId;
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
		if (!(other instanceof MetricasEstadoNumeroPK)) {
			return false;
		}
		MetricasEstadoNumeroPK castOther = (MetricasEstadoNumeroPK)other;
		return 
			this.estado.equals(castOther.estado)
			&& this.internalId.equals(castOther.internalId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.estado.hashCode();
		hash = hash * prime + this.internalId.hashCode();
		
		return hash;
	}
}