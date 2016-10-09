package com.sarp.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the metricas_puesto database table.
 * 
 */
@Entity
@Table(name="metricas_puesto")
@NamedQuery(name="MetricasPuesto.findAll", query="SELECT m FROM MetricasPuesto m")
public class MetricasPuesto implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private MetricasPuestoPK id;

	@Column(name="date_created")
	private Timestamp dateCreated;

	@Version
	@Column(name="last_updated")
	private Timestamp lastUpdated;

	@Column(name="time_spent")
	private Integer timeSpent;

	public MetricasPuesto() {
	}

	public MetricasPuestoPK getId() {
		return this.id;
	}

	public void setId(MetricasPuestoPK id) {
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