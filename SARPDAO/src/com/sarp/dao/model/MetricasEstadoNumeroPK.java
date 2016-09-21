package com.sarp.dao.model;

import java.io.Serializable;
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

	@Column(name="numero_internal_id")
	private Integer numeroInternalId;

	public MetricasEstadoNumeroPK() {
	}
	public String getEstado() {
		return this.estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Integer getNumeroInternalId() {
		return this.numeroInternalId;
	}
	public void setNumeroInternalId(Integer numeroInternalId) {
		this.numeroInternalId = numeroInternalId;
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
			&& this.numeroInternalId.equals(castOther.numeroInternalId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.estado.hashCode();
		hash = hash * prime + this.numeroInternalId.hashCode();
		
		return hash;
	}
}