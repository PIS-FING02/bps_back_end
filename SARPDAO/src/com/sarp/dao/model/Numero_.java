package com.sarp.dao.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-09-30T21:03:44.185-0300")
@StaticMetamodel(Numero.class)
public class Numero_ {
	public static volatile SingularAttribute<Numero, Integer> internalId;
	public static volatile SingularAttribute<Numero, Date> dateCreated;
	public static volatile SingularAttribute<Numero, String> estado;
	public static volatile SingularAttribute<Numero, String> externalId;
	public static volatile SingularAttribute<Numero, Date> hora;
	public static volatile SingularAttribute<Numero, Date> lastUpdated;
	public static volatile SingularAttribute<Numero, Integer> prioridad;
	public static volatile SingularAttribute<Numero, DatosComplementario> datosComplementario;
	public static volatile SingularAttribute<Numero, Tramite> tramite;
	public static volatile ListAttribute<Numero, Puesto> puestos;
	public static volatile SingularAttribute<Numero, Puesto> puesto;
}
