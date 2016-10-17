package com.sarp.dao.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.GregorianCalendar;
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
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="internal_id")
	private Integer internalId;

	@Column(name = "date_created", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp dateCreated;

	private String estado;

	@Column(name="external_id")
	private String externalId;

	@Temporal(TemporalType.TIMESTAMP)
	private GregorianCalendar hora;

	@Version
	@Column(name="last_updated")
	private Timestamp lastUpdated;

	private Integer prioridad;

	//bi-directional one-to-one association to DatosComplementario
	@OneToOne(mappedBy="numero", fetch=FetchType.EAGER)
	private DatosComplementario datosComplementario;

	//bi-directional many-to-one association to Tramite
	@ManyToOne
	@JoinColumn(name="codigo_tramite")
	private Tramite tramite;
	
	//bi-directional many-to-one association to Tramite
	@ManyToOne
	@JoinColumn(name="codigo_sector")
	private Sector sector;

	//bi-directional many-to-many association to Puesto
	@ManyToMany(mappedBy="numeros")
	private List<Puesto> puestos;
	
	//bi-directional one-to-one association to Puesto
	@OneToOne
	@JoinColumn(name="puesto_asignado")
	private Puesto puesto;

	public Numero() {
	}

	public Integer getInternalId() {
		return this.internalId;
	}

	public void setInternalId(Integer internalId) {
		this.internalId = internalId;
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

	public GregorianCalendar getHora() {
		return this.hora;
	}

	public void setHora(GregorianCalendar hora) {
		this.hora = hora;
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
	
	public Sector getSector() {
		return this.sector;
	}

	public void setSector(Sector sector) {
		this.sector = sector;
	}

	public List<Puesto> getPuestos() {
		return this.puestos;
	}

	public void setPuestos(List<Puesto> puestos) {
		this.puestos = puestos;
	}
	
	public Puesto getPuesto() {
		return this.puesto;
	}

	public void setPuesto(Puesto puesto) {
		this.puesto = puesto;
	}
	
	public Integer getCodigoTramite(){
		if(this.getTramite() != null){
			return this.getTramite().getCodigo();
		}
		return null;
	}
	
	public String getCodigoSector(){
		if(this.getSector() != null){
			return this.getSector().getCodigo();
		}
		return null;
	}
	
	public String getNombreMaquinaPuestoActual(){
		if(this.getPuesto() != null){
			return this.getPuesto().getNombreMaquina();
		}
		return null;
	}

}