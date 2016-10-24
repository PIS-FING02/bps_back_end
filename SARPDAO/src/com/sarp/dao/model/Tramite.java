package com.sarp.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the tramite database table.
 * 
 */
@Entity
@NamedQuery(name="Tramite.findAll", query="SELECT t FROM Tramite t")
public class Tramite implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String codigo;

	@Column(name = "date_created", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp dateCreated;

	@Version
	@Column(name="last_updated")
	private Timestamp lastUpdated;

	private String nombre;

	//bi-directional many-to-one association to Numero
	@OneToMany(mappedBy="tramite", fetch=FetchType.EAGER)
	private List<Numero> numeros;

	//bi-directional many-to-many association to Puesto
	//@ManyToMany(mappedBy="tramites")
	//private List<Puesto> puestos;
	@ManyToMany
	@JoinTable(
		name="PUESTO_TRAMITE"
		, joinColumns={
			@JoinColumn(name="codigo_tramite")
			}
		, inverseJoinColumns={
			@JoinColumn(name="nombre_maquina")
			}
		)
	private List<Puesto> puestos;
	
	//bi-directional many-to-many association to NumeroTramite
	@OneToMany(mappedBy = "tramite")
    private List<NumeroTramite> numeroTramites;
		
	//bi-directional many-to-many association to Sector
	@ManyToMany
	@JoinTable(
		name="tramite_sector"
		, joinColumns={
			@JoinColumn(name="codigo_tramite")
			}
		, inverseJoinColumns={
			@JoinColumn(name="codigo_sector")
			}
		)
	private List<Sector> sectors;

	public Tramite() {
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
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

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Numero> getNumeros() {
		return this.numeros;
	}

	public void setNumeros(List<Numero> numeros) {
		this.numeros = numeros;
	}

	public Numero addNumero(Numero numero) {
		getNumeros().add(numero);
		numero.setTramite(this);

		return numero;
	}

	public Numero removeNumero(Numero numero) {
		getNumeros().remove(numero);
		numero.setTramite(null);

		return numero;
	}

	public List<Puesto> getPuestos() {
		return this.puestos;
	}

	public void setPuestos(List<Puesto> puestos) {
		this.puestos = puestos;
	}

	public List<Sector> getSectors() {
		return this.sectors;
	}

	public void setSectors(List<Sector> sectors) {
		this.sectors = sectors;
	}
	
	public List<NumeroTramite> getNumeroTramites() {
		return this.numeroTramites;
	}

	public void addNumeroTramite(NumeroTramite numeroTramite) {
		this.numeroTramites.add(numeroTramite);
	}
	
	public void removeNumeroTramite(NumeroTramite numeroTramite) {
		this.numeroTramites.remove(numeroTramite);
	}

}