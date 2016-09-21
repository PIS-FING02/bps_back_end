package com.sarp.dao.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the numero database table.
 * 
 */
@Entity
@NamedQuery(name="Numero.findAll", query="SELECT n FROM Numero n")
public class Numero implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="internal_id")
	private Integer internalId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_created")
	private Date dateCreated;

	@Column(name="es_sae")
	private Boolean esSae;

	private String estado;

	@Column(name="external_id")
	private String externalId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date hora;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_updated")
	private Date lastUpdated;

	private Integer prioridad;

	//bi-directional one-to-one association to DatosComplementario
	@OneToOne(mappedBy="numero", fetch=FetchType.EAGER)
	private DatosComplementario datosComplementario;

	//bi-directional many-to-one association to Tramite
	@ManyToOne
	@JoinColumn(name="codigo_tramite")
	private Tramite tramite;

	//bi-directional many-to-many association to Puesto
	@ManyToMany(mappedBy="numeros")
	private List<Puesto> puestos;

	public Numero() {
	}

	public Integer getInternalId() {
		return this.internalId;
	}

	public void setInternalId(Integer internalId) {
		this.internalId = internalId;
	}

	public Date getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Boolean getEsSae() {
		return this.esSae;
	}

	public void setEsSae(Boolean esSae) {
		this.esSae = esSae;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getExternalId() {
		return this.externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public Date getHora() {
		return this.hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

	public Date getLastUpdated() {
		return this.lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public Integer getPrioridad() {
		return this.prioridad;
	}

	public void setPrioridad(Integer prioridad) {
		this.prioridad = prioridad;
	}

	public DatosComplementario getDatosComplementario() {
		return this.datosComplementario;
	}

	public void setDatosComplementario(DatosComplementario datosComplementario) {
		this.datosComplementario = datosComplementario;
	}

	public Tramite getTramite() {
		return this.tramite;
	}

	public void setTramite(Tramite tramite) {
		this.tramite = tramite;
	}

	public List<Puesto> getPuestos() {
		return this.puestos;
	}

	public void setPuestos(List<Puesto> puestos) {
		this.puestos = puestos;
	}

}