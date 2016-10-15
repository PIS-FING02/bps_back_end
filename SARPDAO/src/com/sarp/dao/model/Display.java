package com.sarp.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the display database table.
 * 
 */
@Entity
@NamedQuery(name="Display.findAll", query="SELECT d FROM Display d")
public class Display implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id_display;

	@Column(name="date_created")
	private Timestamp dateCreated;

	@Version
	@Column(name="last_updated")
	private Timestamp lastUpdated;

	//bi-directional many-to-many association to Sector
		@ManyToMany
		@JoinTable(
			name="display_sector"
			, joinColumns={
				@JoinColumn(name="codigo_display")
				}
			, inverseJoinColumns={
				@JoinColumn(name="codigo_sector")
				}
			)
		private List<Sector> sectors;
	
	public Display() {
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

	public String getIdDisplay() {
		return this.id_display;
	}

	public void setIdDisplay(String id_display) {
		this.id_display = id_display;
	}

	public List<Sector> getSectors() {
		return this.sectors;
	}

	public void setSectors(List<Sector> sectors) {
		this.sectors = sectors;
	}

}