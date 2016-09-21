package com.sarp.dao.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the sector database table.
 * 
 */
@Entity
@NamedQuery(name="Sector.findAll", query="SELECT s FROM Sector s")
public class Sector implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer codigo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_created")
	private Date dateCreated;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_updated")
	private Date lastUpdated;

	private String nombre;

	@Column(name="ruta_sector")
	private String rutaSector;

	//bi-directional many-to-many association to Puesto
	@ManyToMany(mappedBy="sectors")
	private List<Puesto> puestos;

	//bi-directional many-to-one association to Display
	@ManyToOne
	@JoinColumn(name="codigo_display")
	private Display display;

	//bi-directional many-to-many association to Tramite
	@ManyToMany(mappedBy="sectors")
	private List<Tramite> tramites;

	public Sector() {
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

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRutaSector() {
		return this.rutaSector;
	}

	public void setRutaSector(String rutaSector) {
		this.rutaSector = rutaSector;
	}

	public List<Puesto> getPuestos() {
		return this.puestos;
	}

	public void setPuestos(List<Puesto> puestos) {
		this.puestos = puestos;
	}

	public Display getDisplay() {
		return this.display;
	}

	public void setDisplay(Display display) {
		this.display = display;
	}

	public List<Tramite> getTramites() {
		return this.tramites;
	}

	public void setTramites(List<Tramite> tramites) {
		this.tramites = tramites;
	}

}