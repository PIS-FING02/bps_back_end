package com.sarp.dao.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the numero_tramite database table.
 * 
 */
@Entity
@Table(name="numero_tramite")
@NamedQuery(name="NumeroTramite.findAll", query="SELECT n FROM NumeroTramite n")
public class NumeroTramite implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private NumeroTramitePK id;

	@Column(name="resultado_final")
	private String resultadoFinal;

	//bi-directional many-to-one association to Numero
	@ManyToOne
	@JoinColumn(name="internal_id")
	private Numero numero;

	//bi-directional many-to-one association to Tramite
	@ManyToOne
	@JoinColumn(name="codigo_tramite")
	private Tramite tramite;

	public NumeroTramite() {
	}

	public NumeroTramitePK getId() {
		return this.id;
	}

	public void setId(NumeroTramitePK id) {
		this.id = id;
	}

	public String getResultadoFinal() {
		return this.resultadoFinal;
	}

	public void setResultadoFinal(String resultadoFinal) {
		this.resultadoFinal = resultadoFinal;
	}

	public Numero getNumero() {
		return this.numero;
	}

	public void setNumero(Numero numero) {
		this.numero = numero;
	}

	public Tramite getTramite() {
		return this.tramite;
	}

	public void setTramite(Tramite tramite) {
		this.tramite = tramite;
	}

}