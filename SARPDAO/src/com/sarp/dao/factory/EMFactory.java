package com.sarp.dao.factory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EMFactory {
	
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgresUnit");
		
	/* Se trabaja con un �nico EntityManagerFactory que crea varios EntityManager, uno por transacci�n */
	public static EntityManager getEntityManager() {
	    return emf.createEntityManager();
	}
	   	
	
}
