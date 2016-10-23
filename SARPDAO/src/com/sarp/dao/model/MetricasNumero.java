package com.sarp.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.GregorianCalendar;


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
	@Column(name="internal_id")
	private Integer internalId;

	@Column(name="codigo_tramite")
	private Integer codigoTramite;

	@Column(name = "date_created")
	@Temporal(TemporalType.TIMESTAMP)
	private GregorianCalendar dateCreated;

	private String estado;

	@Column(name="external_id")
	private String externalId;

	@Column(name="last_updated")
	@Temporal(TemporalType.TIMESTAMP)
	private GregorianCalendar lastUpdated;

	@Column(name="ruta_sector")
	private String rutaSector;

	@Column(name="usuario_atencion")
	private String usuarioAtencion;

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

	public GregorianCalendar getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(GregorianCalendar dateCreated) {
		this.dateCreated = dateCreated;
	}

	public GregorianCalendar getLastUpdated() {
		return this.lastUpdated;
	}

	public void setLastUpdated(GregorianCalendar lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getRutaSector() {
		return this.rutaSector;
	}

	public void setRutaSector(String rutaSector) {
		this.rutaSector = rutaSector;
	}

	public String getUsuarioAtencion() {
		return this.usuarioAtencion;
	}

	public void setUsuarioAtencion(String usuarioAtencion) {
		this.usuarioAtencion = usuarioAtencion;
	}

}