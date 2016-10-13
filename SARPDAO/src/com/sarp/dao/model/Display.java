package com.sarp.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;
import java.util.Date;
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


	//bi-directional many-to-one association to Sector
	@OneToMany(mappedBy="display", fetch=FetchType.EAGER)
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

	public void setIdDisplay(String idDisplay) {
		this.id_display = id_display;
	}

	public List<Sector> getSectors() {
		return this.sectors;
	}

	public void setSectors(List<Sector> sectors) {
		this.sectors = sectors;
	}

	public Sector addSector(Sector sector) {
		getSectors().add(sector);
		sector.setDisplay(this);

		return sector;
	}

	public Sector removeSector(Sector sector) {
		getSectors().remove(sector);
		sector.setDisplay(null);

		return sector;
	}

}