package DAOService;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the rol database table.
 * 
 */
@Entity
@NamedQuery(name="Rol.findAll", query="SELECT r FROM Rol r")
public class Rol implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String nombre;

	//bi-directional many-to-many association to Funcionario
	@ManyToMany
	@JoinTable(
		name="rol_func"
		, joinColumns={
			@JoinColumn(name="rol")
			}
		, inverseJoinColumns={
			@JoinColumn(name="funcionario")
			}
		)
	private List<Funcionario> funcionarios;

	public Rol() {
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Funcionario> getFuncionarios() {
		return this.funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

}