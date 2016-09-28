package com.sarp.dao.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-09-30T21:03:44.201-0300")
@StaticMetamodel(Puesto.class)
public class Puesto_ {
	public static volatile SingularAttribute<Puesto, String> nombreMaquina;
	public static volatile SingularAttribute<Puesto, Date> dateCreated;
	public static volatile SingularAttribute<Puesto, String> estado;
	public static volatile SingularAttribute<Puesto, Date> lastUpdated;
	public static volatile SingularAttribute<Puesto, String> usuarioId;
	public static volatile ListAttribute<Puesto, Numero> numeros;
	public static volatile ListAttribute<Puesto, Sector> sectors;
	public static volatile ListAttribute<Puesto, Tramite> tramites;
	public static volatile SingularAttribute<Puesto, Numero> numero_puesto;
}
