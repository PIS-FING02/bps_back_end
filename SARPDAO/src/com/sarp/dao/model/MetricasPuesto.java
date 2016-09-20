package com.sarp.dao.model;

import java.io.Serializable;
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_created")
	private Date dateCreated;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_updated")
	private Date lastUpdated;

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

	public Date getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getLastUpdated() {
		return this.lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public Integer getTimeSpent() {
		return this.timeSpent;
	}

	public void setTimeSpent(Integer timeSpent) {
		this.timeSpent = timeSpent;
	}

}