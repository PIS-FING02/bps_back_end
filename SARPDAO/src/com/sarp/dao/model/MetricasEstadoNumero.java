package com.sarp.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;
import java.util.Date;


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

	@Column(name = "date_created", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp dateCreated;

	@Version
	@Column(name="last_updated")
	private Timestamp lastUpdated;

	@Column(name="time_spent")
	private Integer timeSpent;

	public MetricasEstadoNumero() {
	}

	public MetricasEstadoNumeroPK getId() {
		return this.id;
	}

	public void setId(MetricasEstadoNumeroPK id) {
		this.id = id;
	}

	public Timestamp getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Timestamp dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Timestamp getLastUpdated() {
		return this.lastUpdated;
	}

	public void setLastUpdated(Timestamp lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public Integer getTimeSpent() {
		return this.timeSpent;
	}

	public void setTimeSpent(Integer timeSpent) {
		this.timeSpent = timeSpent;
	}

}