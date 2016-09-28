package com.sarp.dao.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-09-30T21:03:44.049-0300")
@StaticMetamodel(DatosComplementario.class)
public class DatosComplementario_ {
	public static volatile SingularAttribute<DatosComplementario, Integer> docIdentidad;
	public static volatile SingularAttribute<DatosComplementario, Date> dateCreated;
	public static volatile SingularAttribute<DatosComplementario, Date> lastUpdated;
	public static volatile SingularAttribute<DatosComplementario, String> nombreCompleto;
	public static volatile SingularAttribute<DatosComplementario, String> tipoDoc;
	public static volatile SingularAttribute<DatosComplementario, Numero> numero;
}
