package com.sarp.dao.model;

import java.io.Serializable;

import javax.persistence.*;
import java.util.GregorianCalendar;


/**
 * The persistent class for the metricas_estado_numero database table.
 * 
 */
@Entity
@Table(name="metricas_estado_numero")
@NamedQuery(name="MetricasEstadoNumero.findAll", query="SELECT m FROM MetricasEstadoNumero m")
public class MetricasEstadoNumero implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private MetricasEstadoNumeroPK id;

	@Column(name="last_updated")
	@Temporal(TemporalType.TIMESTAMP)
	private GregorianCalendar lastUpdated;

	@Column(name="time_spent")
	private String timeSpent;

	public MetricasEstadoNumero() {
	}

	public MetricasEstadoNumeroPK getId() {
		return this.id;
	}

	public void setId(MetricasEstadoNumeroPK id) {
		this.id = id;
	}

	public GregorianCalendar getLastUpdated() {
		return this.lastUpdated;
	}

	public void setLastUpdated(GregorianCalendar lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getTimeSpent() {
		return this.timeSpent;
	}

	public void setTimeSpent(String timeSpent) {
		this.timeSpent = timeSpent;
	}

}