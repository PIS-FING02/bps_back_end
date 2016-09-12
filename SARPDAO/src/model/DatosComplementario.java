package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the datos_complementarios database table.
 * 
 */
@Entity
@Table(name="datos_complementarios")
@NamedQuery(name="DatosComplementario.findAll", query="SELECT d FROM DatosComplementario d")
public class DatosComplementario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="doc_identidad")
	private String docIdentidad;

	@Temporal(TemporalType.DATE)
	@Column(name="date_created")
	private Date dateCreated;

	@Temporal(TemporalType.DATE)
	@Column(name="last_update")
	private Date lastUpdate;

	@Column(name="nombre_completo")
	private String nombreCompleto;

	@Column(name="tipo_doc")
	private String tipoDoc;

	//bi-directional many-to-one association to Numero
	@OneToMany(mappedBy="datosComplementario")
	private List<Numero> numeros;

	public DatosComplementario() {
	}
	public DatosComplementario(Numero n) {
		this.numeros.add(n);
	}

	public String getDocIdentidad() {
		return this.docIdentidad;
	}

	public void setDocIdentidad(String docIdentidad) {
		this.docIdentidad = docIdentidad;
	}

	public Date getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getNombreCompleto() {
		return this.nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public String getTipoDoc() {
		return this.tipoDoc;
	}

	public void setTipoDoc(String tipoDoc) {
		this.tipoDoc = tipoDoc;
	}

	public List<Numero> getNumeros() {
		return this.numeros;
	}

	public void setNumeros(List<Numero> numeros) {
		this.numeros = numeros;
	}

	public Numero addNumero(Numero numero) {
		getNumeros().add(numero);
		numero.setDatosComplementario(this);

		return numero;
	}

	public Numero removeNumero(Numero numero) {
		getNumeros().remove(numero);
		numero.setDatosComplementario(null);

		return numero;
	}

}