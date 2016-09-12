package model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.LinkedList;
import java.util.List;


/**
 * The persistent class for the tramite database table.
 * 
 */
@Entity
@NamedQuery(name="Tramite.findAll", query="SELECT t FROM Tramite t")
public class Tramite implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	private Integer codigo;

	private String nombre;

	//bi-directional many-to-many association to Sector
	@ManyToMany
	@JoinTable(
		name="tramite_sector"
		, joinColumns={
			@JoinColumn(name="tramite")
			}
		, inverseJoinColumns={
			@JoinColumn(name="sector")
			}
		)
	private List<Sector> sectors;

	public Tramite() {
		List<Sector> sectors = new LinkedList<Sector>();
		this.sectors = sectors;
	}

	public Tramite(Sector s) {
		this.sectors.add(s);
	}

	public Integer getCodigo() {
		return this.codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Sector> getSectors() {
		return this.sectors;
	}

	public void setSectors(List<Sector> sectors) {
		this.sectors = sectors;
	}

}