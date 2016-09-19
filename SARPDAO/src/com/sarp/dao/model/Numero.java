package com.sarp.dao.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.Date;


/**
 * The persistent class for the numero database table.
 * 
 */
@Entity
@NamedQuery(name="Numero.findAll", query="SELECT n FROM Numero n")
@XmlRootElement
public class Numero implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	@Column(name="internal_id")
	private Integer internalId;

	@Column(name="date_created")
	private Integer dateCreated;

	@Column(name="es_sae")
	private Boolean esSae;

	private String estado;

	@Column(name="external_id")
	private String externalId;

	@Temporal(TemporalType.DATE)
	private Date hora;

	private Integer prioridad;

	private Integer puestoid;
	
	//bi-directional many-to-one association to DatosComplementario
	@ManyToOne
	@JoinColumn(name="datos_complementariosid")
	private DatosComplementario datosComplementario;

	//bi-directional many-to-one association to Tramite
	@ManyToOne
	@JoinColumn(name="tramiteid")
	private Tramite tramite;
	
	public Numero(){}
	public Numero(Tramite t) {
		this.tramite = t;
	}

	public Integer getInternalId() {
		return this.internalId;
	}

	public void setInternalId(Integer internalId) {
		this.internalId = internalId;
	}

	public Integer getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Integer dateCreated) {
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

	public Integer getPrioridad() {
		return this.prioridad;
	}

	public void setPrioridad(Integer prioridad) {
		this.prioridad = prioridad;
	}

	public Integer getPuestoid() {
		return this.puestoid;
	}

	public void setPuestoid(Integer puestoid) {
		this.puestoid = puestoid;
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

}