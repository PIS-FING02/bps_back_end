package DAOService;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the sector database table.
 * 
 */
@Entity
@NamedQuery(name="Sector.findAll", query="SELECT s FROM Sector s")
public class Sector implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer sectorid;

	private String nombre;

	//bi-directional many-to-one association to Display
	@OneToMany(mappedBy="sectorBean")
	private List<Display> displays;

	//bi-directional many-to-many association to Puesto
	@ManyToMany
	@JoinTable(
		name="sector_puesto"
		, joinColumns={
			@JoinColumn(name="sector")
			}
		, inverseJoinColumns={
			@JoinColumn(name="puesto")
			}
		)
	private List<Puesto> puestos;

	public Sector() {
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

	public List<Display> getDisplays() {
		return this.displays;
	}

	public void setDisplays(List<Display> displays) {
		this.displays = displays;
	}

	public Display addDisplay(Display display) {
		getDisplays().add(display);
		display.setSectorBean(this);

		return display;
	}

	public Display removeDisplay(Display display) {
		getDisplays().remove(display);
		display.setSectorBean(null);

		return display;
	}

	public List<Puesto> getPuestos() {
		return this.puestos;
	}

	public void setPuestos(List<Puesto> puestos) {
		this.puestos = puestos;
	}

}