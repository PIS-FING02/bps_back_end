package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the sectores database table.
 * 
 */
@Entity
@Table(name="sectores")
@NamedQuery(name="Sector.findAll", query="SELECT s FROM Sector s")
public class Sector implements Serializable {
	private static final long serialVersionUID = 1L;
	

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String nombre;

	public Sector() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}