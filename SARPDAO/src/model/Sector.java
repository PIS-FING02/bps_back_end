package model;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.LinkedList;
import java.util.List;


/**
 * The persistent class for the sector database table.
 * 
 */
@Entity
@XmlRootElement
@NamedQuery(name="Sector.findAll", query="SELECT s FROM Sector s")
public class Sector implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	private Integer sectorid;

	private String nombre;

	//bi-directional many-to-many association to Tramite
	@ManyToMany(mappedBy="sectors",fetch = FetchType.LAZY)
	private List<Tramite> tramites;

	public Sector() {
		List<Tramite> tramites = new LinkedList<Tramite>();
		this.tramites = tramites;
	}

	public Integer getSectorid() {
		return this.sectorid;
	}

	public void setSectorid(Integer sectorid) {
		this.sectorid = sectorid;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public List<Tramite> getTramites() {
		return this.tramites;
	}

	public void setTramites(List<Tramite> tramites) {
		this.tramites = tramites;
	}

}