package com.sarp.dao.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the puesto database table.
 * 
 */
@Entity
@NamedQuery(name="Puesto.findAll", query="SELECT p FROM Puesto p")
public class Puesto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="nombre_maquina")
	private String nombreMaquina;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_created")
	private Date dateCreated;

	private String estado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_updated")
	private Date lastUpdated;

	@Column(name="usuario_id")
	private String usuarioId;

	//bi-directional many-to-many association to Numero
	@ManyToMany
	@JoinTable(
		name="atencion"
		, joinColumns={
			@JoinColumn(name="nombre_maquina")
			}
		, inverseJoinColumns={
			@JoinColumn(name="internal_id")
			}
		)
	private List<Numero> numeros;

	//bi-directional many-to-many association to Sector
	@ManyToMany
	@JoinTable(
		name="puesto_sector"
		, joinColumns={
			@JoinColumn(name="nombre_maquina")
			}
		, inverseJoinColumns={
			@JoinColumn(name="codigo_sector")
			}
		)
	private List<Sector> sectors;

	//bi-directional many-to-many association to Tramite
	@ManyToMany
	@JoinTable(
		name="puesto_tramite"
		, joinColumns={
			@JoinColumn(name="nombre_maquina")
			}
		, inverseJoinColumns={
			@JoinColumn(name="codigo_tramite")
			}
		)
	private List<Tramite> tramites;

	public Puesto() {
	}

	public String getNombreMaquina() {
		return this.nombreMaquina;
	}

	public void setNombreMaquina(String nombreMaquina) {
		this.nombreMaquina = nombreMaquina;
	}

	public Date getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getLastUpdated() {
		return this.lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(String usuarioId) {
		this.usuarioId = usuarioId;
	}

	public List<Numero> getNumeros() {
		return this.numeros;
	}

	public void setNumeros(List<Numero> numeros) {
		this.numeros = numeros;
	}

	public List<Sector> getSectors() {
		return this.sectors;
	}

	public void setSectors(List<Sector> sectors) {
		this.sectors = sectors;
	}

	public List<Tramite> getTramites() {
		return this.tramites;
	}

	public void setTramites(List<Tramite> tramites) {
		this.tramites = tramites;
	}

}