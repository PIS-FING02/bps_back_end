package com.sarp.dao.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the metricas_numero database table.
 * 
 */
@Entity
@Table(name="metricas_numero")
@NamedQuery(name="MetricasNumero.findAll", query="SELECT m FROM MetricasNumero m")
public class MetricasNumero implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="internal_id")
	private Integer internalId;

	@Column(name="codigo_tramite")
	private Integer codigoTramite;

	@Temporal(TemporalType.DATE)
	@Column(name="date_created")
	private Date dateCreated;

	private String estado;

	@Column(name="external_id")
	private Integer externalId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_updated")
	private Date lastUpdated;

	@Column(name="resultado_final")
	private String resultadoFinal;

	@Column(name="ruta_sector")
	private String rutaSector;

	@Column(name="usuario_atencion")
	private Integer usuarioAtencion;

	public MetricasNumero() {
	}

	public Integer getInternalId() {
		return this.internalId;
	}

	public void setInternalId(Integer internalId) {
		this.internalId = internalId;
	}

	public Integer getCodigoTramite() {
		return this.codigoTramite;
	}

	public void setCodigoTramite(Integer codigoTramite) {
		this.codigoTramite = codigoTramite;
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

	public Integer getExternalId() {
		return this.externalId;
	}

	public void setExternalId(Integer externalId) {
		this.externalId = externalId;
	}

	public Date getLastUpdated() {
		return this.lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getResultadoFinal() {
		return this.resultadoFinal;
	}

	public void setResultadoFinal(String resultadoFinal) {
		this.resultadoFinal = resultadoFinal;
	}

	public String getRutaSector() {
		return this.rutaSector;
	}

	public void setRutaSector(String rutaSector) {
		this.rutaSector = rutaSector;
	}

	public Integer getUsuarioAtencion() {
		return this.usuarioAtencion;
	}

	public void setUsuarioAtencion(Integer usuarioAtencion) {
		this.usuarioAtencion = usuarioAtencion;
	}

}