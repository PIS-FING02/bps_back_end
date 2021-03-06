package com.sarp.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.*;
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
	@Column(name="nombre_maquina")
	private String nombreMaquina;

	@Column(name="date_created", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp dateCreated;

	private String estado;
	
	private Integer numero;
	
	@Version
	@Column(name="last_updated")
	private Timestamp lastUpdated;

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

	//bi-directional one-to-one association to Numero
	@OneToOne
	@JoinColumn(name="numero_puesto")
	private Numero numero_puesto;
		
	public Puesto() {
	}

	public String getNombreMaquina() {
		return this.nombreMaquina;
	}

	public void setNombreMaquina(String nombreMaquina) {
		this.nombreMaquina = nombreMaquina;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
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

	public Numero getNumero_puesto() {
		return numero_puesto;
	}

	public void setNumero_puesto(Numero numero_puesto) {
		this.numero_puesto = numero_puesto;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	

}