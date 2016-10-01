package com.sarp.dao.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

<<<<<<< HEAD
@Generated(value="Dali", date="2016-09-30T21:03:44.217-0300")
=======
@Generated(value="Dali", date="2016-09-30T20:19:22.196-0300")
>>>>>>> 0663b99... Corrijo proyecto
@StaticMetamodel(Sector.class)
public class Sector_ {
	public static volatile SingularAttribute<Sector, String> codigo;
	public static volatile SingularAttribute<Sector, Date> dateCreated;
	public static volatile SingularAttribute<Sector, Date> lastUpdated;
	public static volatile SingularAttribute<Sector, String> nombre;
	public static volatile SingularAttribute<Sector, String> rutaSector;
	public static volatile ListAttribute<Sector, Puesto> puestos;
	public static volatile SingularAttribute<Sector, Display> display;
	public static volatile ListAttribute<Sector, Tramite> tramites;
}
