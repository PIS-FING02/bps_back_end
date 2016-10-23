package com.sarp.dao.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the numero_tramite database table.
 * 
 */
@Embeddable
public class NumeroTramitePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="internal_id", insertable=false, updatable=false)
	private Integer internalId;

	@Column(name="codigo_tramite", insertable=false, updatable=false)
	private Integer codigoTramite;

	public NumeroTramitePK() {
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

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof NumeroTramitePK)) {
			return false;
		}
		NumeroTramitePK castOther = (NumeroTramitePK)other;
		return 
			this.internalId.equals(castOther.internalId)
			&& this.codigoTramite.equals(castOther.codigoTramite);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.internalId.hashCode();
		hash = hash * prime + this.codigoTramite.hashCode();
		
		return hash;
	}
}