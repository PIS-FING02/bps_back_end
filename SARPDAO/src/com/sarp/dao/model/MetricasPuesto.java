package com.sarp.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * The persistent class for the metricas_puesto database table.
 * 
 */
@Entity
@Table(name="metricas_puesto")
@NamedQuery(name="MetricasPuesto.findAll", query="SELECT mp FROM MetricasPuesto mp")
public class MetricasPuesto implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private MetricasPuestoPK id;

	@Column(name="last_updated")
	@Temporal(TemporalType.TIMESTAMP)
	private GregorianCalendar lastUpdated;

	@Column(name="time_spent")
	private String timeSpent;

	public MetricasPuesto() {
	}

	public MetricasPuestoPK getId() {
		return this.id;
	}

	public void setId(MetricasPuestoPK id) {
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