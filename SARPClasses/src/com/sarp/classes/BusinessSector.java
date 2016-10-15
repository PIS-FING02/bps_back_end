package com.sarp.classes;

import java.sql.Timestamp;
import java.util.Objects;

public class BusinessSector  {



	//Atributos
	private String sectorId;
	private String nombre;
	private String ruta;
	private Timestamp lastUpdated;

	//Constructores
	public BusinessSector() {}

	public BusinessSector(String sectorid, String nombre,String ruta) {
		this.sectorId = sectorid;
		this.nombre = nombre;
		this.ruta = ruta;
	}
	
	//Operaciones
	public String getSectorId() {
		return sectorId;
	}

	public void setSectorId(String sectorId) {
		this.sectorId = sectorId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRuta() {
		return ruta;
	}
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	
	//

    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof BusinessSector)) {
            return false;
        }
        BusinessSector sector = (BusinessSector) o;
        return sectorId.equals(sector.sectorId) && nombre.equals(sector.getNombre()) && ruta.equals(sector.getRuta());
    }

    @Override
    public int hashCode() {
        return Objects.hash(sectorId,nombre,ruta);
    }


	public Timestamp getLastUpdated() {
		return lastUpdated;
	}


	public void setLastUpdated(Timestamp lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
}
