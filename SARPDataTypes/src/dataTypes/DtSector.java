package dataTypes;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DtSector implements Serializable {
	private Integer sectorid;

	private String nombre;

	public Integer getSectorid() {
		return sectorid;
	}

	public void setSectorid(Integer sectorid) {
		this.sectorid = sectorid;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public DtSector(Integer sectorid, String nombre) {
		super();
		this.sectorid = sectorid;
		this.nombre = nombre;
	}
	
	
}
