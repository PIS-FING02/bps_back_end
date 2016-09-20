package com.sarp.dao.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the datos_complementarios database table.
 * 
 */
@Entity
@Table(name="datos_complementarios")
@NamedQuery(name="DatosComplementario.findAll", query="SELECT d FROM DatosComplementario d")
public class DatosComplementario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="doc_identidad")
	private Integer docIdentidad;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_created")
	private Date dateCreated;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_updated")
	private Date lastUpdated;

	@Column(name="nombre_completo")
	private String nombreCompleto;

	@Column(name="tipo_doc")
	private String tipoDoc;

	//bi-directional one-to-one association to Numero
	@OneToOne
	@JoinColumn(name="internal_id")
	private Numero numero;

	public DatosComplementario() {
	}

	public Integer getDocIdentidad() {
		return this.docIdentidad;
	}

	public void setDocIdentidad(Integer docIdentidad) {
		this.docIdentidad = docIdentidad;
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