package com.sarp.dao.factory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EMFactory {
	
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgresUnit");
	
	private EMFactory(){ }   
	
	/* Se trabaja con un único EntityManagerFactory que crea varios EntityManager, uno por transacción */
	public static EntityManager getEntityManager() {
	    return emf.createEntityManager();
	}
	   	
	
}
