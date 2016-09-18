package com.sarp.classes;

public class BusinessNumero {
	private Integer numero;

	private String emitido;

	private Boolean essae;

	private String estado;

	private Integer prioridad;

	private Integer puesto;

	private Boolean seredirecciona;

	private String serie;

	private Integer tiempodeatendido;

	private Integer tramite;
	
	private Integer sector;
	

	private BusinessDatoComplementario datoComplementario;


	
	public BusinessNumero() {
	}
	public BusinessNumero(int id,int t,int sec) {
		
		this.numero = id;
		this.emitido = "E";
		this.essae = false;
		this.estado = "E";
		this.prioridad = 1;
		this.seredirecciona = false;
		this.tramite = t;
		this.sector = sec;
		
	}

	public Integer getNumero() {
		return this.numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	
	public void setSector(Integer sec) {
		this.sector = sec;
	}

	public String getEmitido() {
		return this.emitido;
	}

	public void setEmitido(String emitido) {
		this.emitido = emitido;
	}

	public Boolean getEssae() {
		return this.essae;
	}

	public void setEssae(Boolean essae) {
		this.essae = essae;
	}
	
	public void getSector(Integer sec) {
		this.sector = sec;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Integer getPrioridad() {
		return this.prioridad;
	}

	public void setPrioridad(Integer prioridad) {
		this.prioridad = prioridad;
	}

	public Integer getPuesto() {
		return this.puesto;
	}

	public void setPuesto(Integer puesto) {
		this.puesto = puesto;
	}

	public Boolean getSeredirecciona() {
		return this.seredirecciona;
	}

	public void setSeredirecciona(Boolean seredirecciona) {
		this.seredirecciona = seredirecciona;
	}

	public String getSerie() {
		return this.serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public Integer getTiempodeatendido() {
		return this.tiempodeatendido;
	}

	public void setTiempodeatendido(Integer tiempodeatendido) {
		this.tiempodeatendido = tiempodeatendido;
	}

	public Integer getTramite() {
		return this.tramite;
	}

	public void setTramite(Integer tramite) {
		this.tramite = tramite;
	}
}
