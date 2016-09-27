package com.sarp.dao.model;

import java.io.Serializable;
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
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer codigo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_created")
	private Date dateCreated;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_updated")
	private Date lastUpdated;

	@Column(name="ruta_archivo")
	private String rutaArchivo;

	//bi-directional many-to-one association to Sector
	@OneToMany(mappedBy="display", fetch=FetchType.EAGER)
	private List<Sector> sectors;

	public Display() {
		//sectors = new LinkedList<Sector>();
	}

	public Integer getCodigo() {
		return this.codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
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

	public String getRutaArchivo() {
		return this.rutaArchivo;
	}

	public void setRutaArchivo(String rutaArchivo) {
		this.rutaArchivo = rutaArchivo;
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