package DAOService;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the puesto database table.
 * 
 */
@Entity
@NamedQuery(name="Puesto.findAll", query="SELECT p FROM Puesto p")
public class Puesto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer codigo;

	private String estado;

	private Boolean isabierto;

	//bi-directional many-to-one association to Funcionario
	@ManyToOne
	@JoinColumn(name="funcionario")
	private Funcionario funcionarioBean;

	//bi-directional many-to-many association to Puesto
	@ManyToMany
	@JoinTable(
		name="puesto_transf"
		, joinColumns={
			@JoinColumn(name="puestotransfiere")
			}
		, inverseJoinColumns={
			@JoinColumn(name="puestorecibe")
			}
		)
	private List<Puesto> puestos1;

	//bi-directional many-to-many association to Puesto
	@ManyToMany(mappedBy="puestos1")
	private List<Puesto> puestos2;

	//bi-directional many-to-many association to Sector
	@ManyToMany(mappedBy="puestos")
	private List<Sector> sectors;

	//bi-directional many-to-many association to Tramite
	@ManyToMany(mappedBy="puestos")
	private List<Tramite> tramites;

	public Puesto() {
	}

	public Integer getCodigo() {
		return this.codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Boolean getIsabierto() {
		return this.isabierto;
	}

	public void setIsabierto(Boolean isabierto) {
		this.isabierto = isabierto;
	}

	public Funcionario getFuncionarioBean() {
		return this.funcionarioBean;
	}

	public void setFuncionarioBean(Funcionario funcionarioBean) {
		this.funcionarioBean = funcionarioBean;
	}

	public List<Puesto> getPuestos1() {
		return this.puestos1;
	}

	public void setPuestos1(List<Puesto> puestos1) {
		this.puestos1 = puestos1;
	}

	public List<Puesto> getPuestos2() {
		return this.puestos2;
	}

	public void setPuestos2(List<Puesto> puestos2) {
		this.puestos2 = puestos2;
	}

	public List<Sector> getSectors() {
		return this.sectors;
	}

	public void setSectors(List<Sector> sectors) {
		this.sectors = sectors;
	}

	public List<Tramite> getTramites() {
		return this.tramites;
	}

	public void setTramites(List<Tramite> tramites) {
		this.tramites = tramites;
	}

}