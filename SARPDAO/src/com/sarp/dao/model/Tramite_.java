package com.sarp.dao.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-09-30T21:03:44.217-0300")
@StaticMetamodel(Tramite.class)
public class Tramite_ {
	public static volatile SingularAttribute<Tramite, Integer> codigo;
	public static volatile SingularAttribute<Tramite, Date> dateCreated;
	public static volatile SingularAttribute<Tramite, Date> lastUpdated;
	public static volatile SingularAttribute<Tramite, String> nombre;
	public static volatile ListAttribute<Tramite, Numero> numeros;
	public static volatile ListAttribute<Tramite, Puesto> puestos;
	public static volatile ListAttribute<Tramite, Sector> sectors;
}
