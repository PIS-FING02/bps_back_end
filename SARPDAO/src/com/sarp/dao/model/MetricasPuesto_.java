package com.sarp.dao.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-09-30T21:03:44.138-0300")
@StaticMetamodel(MetricasPuesto.class)
public class MetricasPuesto_ {
	public static volatile SingularAttribute<MetricasPuesto, MetricasPuestoPK> id;
	public static volatile SingularAttribute<MetricasPuesto, Date> dateCreated;
	public static volatile SingularAttribute<MetricasPuesto, Date> lastUpdated;
	public static volatile SingularAttribute<MetricasPuesto, Integer> timeSpent;
}
