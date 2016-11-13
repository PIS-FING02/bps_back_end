package com.sarp.dao.controllers;

import javax.persistence.EntityManager;

import com.sarp.dao.factory.EMFactory;
import com.sarp.dao.model.Tramite;

public class DAOAdminController {

    public void resetDataBase(){
        EntityManager em = EMFactory.getEntityManager();
		em.getTransaction().begin();
		em.createNativeQuery("TRUNCATE display CASCADE").executeUpdate();
		em.createNativeQuery("TRUNCATE sector CASCADE").executeUpdate();
		em.createNativeQuery("TRUNCATE tramite CASCADE").executeUpdate();
		em.createNativeQuery("TRUNCATE puesto CASCADE").executeUpdate();
		em.createNativeQuery("TRUNCATE numero CASCADE").executeUpdate();
		em.createNativeQuery("TRUNCATE datos_complementarios CASCADE").executeUpdate();
		em.createNativeQuery("TRUNCATE metricas_puesto CASCADE").executeUpdate();
		em.createNativeQuery("TRUNCATE metricas_numero CASCADE").executeUpdate();
		em.createNativeQuery("TRUNCATE metricas_estado_numero CASCADE").executeUpdate();
		em.createNativeQuery("TRUNCATE numero CASCADE").executeUpdate();
		em.createNativeQuery("ALTER SEQUENCE numero_internal_id_seq restart").executeUpdate();
		Tramite t = new Tramite();
		t.setCodigo("1");
		t.setNombre("Tramite Generico");
		em.persist(t);
		em.getTransaction().commit();
		em.close();
    }
	
}
