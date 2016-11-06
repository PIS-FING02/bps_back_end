package com.sarp.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

/**
 * The persistent class for the datos_complementarios database table.
 * 
 */
@Entity
@Table(name="datos_complementarios")
@NamedQuery(name="DatosComplementario.findAll", query="SELECT d FROM DatosComplementario d")
public class DatosComplementario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="doc_identidad")
	private String docIdentidad;

	@Column(name = "date_created", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp dateCreated;

	@Version
	@Column(name="last_updated")
	private Timestamp lastUpdated;

	@Column(name="nombre_completo")
	private String nombreCompleto;

	@Column(name="tipo_doc")
	private String tipoDoc;

	//bi-directional one-to-one association to Numero
	@Id
	@OneToOne
	@JoinColumn(name="internal_id")
	private Numero numero;

	public DatosComplementario() {
	}

	public String getDocIdentidad() {
		return this.docIdentidad;
	}

	public void setDocIdentidad(String docIdentidad) {
		this.docIdentidad = docIdentidad;
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

	public String getNombreCompleto() {
		return this.nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public String getTipoDoc() {
		return this.tipoDoc;
	}

	public void setTipoDoc(String tipoDoc) {
		this.tipoDoc = tipoDoc;
	}

	public Numero getNumero() {
		return this.numero;
	}

	public void setNumero(Numero numero) {
		this.numero = numero;
	}

}