package com.sarp.dao.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

<<<<<<< HEAD
@Generated(value="Dali", date="2016-09-30T21:03:44.106-0300")
=======
@Generated(value="Dali", date="2016-09-30T20:19:22.056-0300")
>>>>>>> 0663b99... Corrijo proyecto
@StaticMetamodel(Display.class)
public class Display_ {
	public static volatile SingularAttribute<Display, Integer> codigo;
	public static volatile SingularAttribute<Display, Date> dateCreated;
	public static volatile SingularAttribute<Display, Date> lastUpdated;
	public static volatile SingularAttribute<Display, String> rutaArchivo;
	public static volatile ListAttribute<Display, Sector> sectors;
}
