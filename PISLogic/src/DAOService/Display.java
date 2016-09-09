package DAOService;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the display database table.
 * 
 */
@Entity
@NamedQuery(name="Display.findAll", query="SELECT d FROM Display d")
public class Display implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String nombrepc;

	private String dirarchivo;

	//bi-directional many-to-one association to Sector
	@ManyToOne
	@JoinColumn(name="sector")
	private Sector sectorBean;

	public Display() {
	}

	public String getNombrepc() {
		return this.nombrepc;
	}

	public void setNombrepc(String nombrepc) {
		this.nombrepc = nombrepc;
	}

	public String getDirarchivo() {
		return this.dirarchivo;
	}

	public void setDirarchivo(String dirarchivo) {
		this.dirarchivo = dirarchivo;
	}

	public Sector getSectorBean() {
		return this.sectorBean;
	}

	public void setSectorBean(Sector sectorBean) {
		this.sectorBean = sectorBean;
	}

}