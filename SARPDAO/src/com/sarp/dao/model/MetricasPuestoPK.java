package com.sarp.dao.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the metricas_puesto database table.
 * 
 */
@Embeddable
public class MetricasPuestoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="codigo_puesto")
	private Integer codigoPuesto;

	@Column(name="usuario_atencion")
	private Integer usuarioAtencion;

	@Column(name="dia_mes_anio")
	private String diaMesAnio;

	private String estado;

	public MetricasPuestoPK() {
	}
	public Integer getCodigoPuesto() {
		return this.codigoPuesto;
	}
	public void setCodigoPuesto(Integer codigoPuesto) {
		this.codigoPuesto = codigoPuesto;
	}
	public Integer getUsuarioAtencion() {
		return this.usuarioAtencion;
	}
	public void setUsuarioAtencion(Integer usuarioAtencion) {
		this.usuarioAtencion = usuarioAtencion;
	}
	public String getDiaMesAnio() {
		return this.diaMesAnio;
	}
	public void setDiaMesAnio(String diaMesAnio) {
		this.diaMesAnio = diaMesAnio;
	}
	public String getEstado() {
		return this.estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
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
			this.codigoPuesto.equals(castOther.codigoPuesto)
			&& this.usuarioAtencion.equals(castOther.usuarioAtencion)
			&& this.diaMesAnio.equals(castOther.diaMesAnio)
			&& this.estado.equals(castOther.estado);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.codigoPuesto.hashCode();
		hash = hash * prime + this.usuarioAtencion.hashCode();
		hash = hash * prime + this.diaMesAnio.hashCode();
		hash = hash * prime + this.estado.hashCode();
		
		return hash;
	}
}