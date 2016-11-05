package com.sarp.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;

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
	private String codigo;

	@Column(name = "date_created", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp dateCreated;

	@Version
	@Column(name="last_updated")
	private Timestamp lastUpdated;

	private String nombre;
	
	//bi-directional many-to-one association to Numero
	@OneToMany(mappedBy="sector", fetch=FetchType.EAGER)
	private List<Numero> numeros;

	@Column(name="ruta_sector")
	private String rutaSector;
	
	@Column(name="habilitado")
	private boolean habilitado;
	
	@Column(name="es_hoja")
	private boolean esHoja;

	//bi-directional many-to-many association to Puesto
	@ManyToMany(mappedBy="sectors")
	private List<Puesto> puestos;

	//bi-directional many-to-many association to Puesto
	@ManyToMany(mappedBy="sectors")
	private List<Display> displays;

	//bi-directional many-to-many association to Tramite
	@ManyToMany(mappedBy="sectors")
	private List<Tramite> tramites;

	public Sector() {
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
	
	public List<Display> getDisplays() {
		return this.displays;
	}

	public void setDisplays(List<Display> displays) {
		this.displays = displays;
	}

	public List<Tramite> getTramites() {
		return this.tramites;
	}

	public void setTramites(List<Tramite> tramites) {
		this.tramites = tramites;
	}
	
	public List<Numero> getNumeros() {
		return this.numeros;
	}

	public void setNumeros(List<Numero> numeros) {
		this.numeros = numeros;
	}

	public Numero addNumero(Numero numero) {
		getNumeros().add(numero);
		numero.setSector(this);

		return numero;
	}

	public Numero removeNumero(Numero numero) {
		getNumeros().remove(numero);
		numero.setTramite(null);

		return numero;
	}
	
	public boolean isHabilitado() {
		return habilitado;
	}

	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}
	
	public boolean isHoja() {
		return esHoja;
	}

	public void setHoja(boolean habilitado) {
		this.habilitado = habilitado;
	}

}