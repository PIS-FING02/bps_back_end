package com.sarp.dao.model;

import java.io.Serializable;
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
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer codigo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_created")
	private Date dateCreated;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_updated")
	private Date lastUpdated;

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

}