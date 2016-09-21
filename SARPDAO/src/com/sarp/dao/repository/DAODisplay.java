package com.sarp.dao.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.sarp.dao.model.Display;

import java.util.Date;
import java.util.List;

public class DAODisplay {
	
	private static EntityManager em;
	
	private EntityManager getEntityManagerInstance(){
		if(em == null){
			EntityManagerFactory factory = Persistence.createEntityManagerFactory("postgresUnit");
			em = factory.createEntityManager();
		}
		return em;
	}
	
	/* Dada una ruta, creo en la base una entidad Display con esa ruta */
	public void insertDisplay(String rutaArchivo){
		EntityManager em = getEntityManagerInstance();
		Display d = new Display();
		d.setRutaArchivo(rutaArchivo);
		d.setDateCreated(new Date());
		d.setLastUpdated(new Date());
		
		em.getTransaction().begin();
		em.persist(d);
		em.getTransaction().commit();
	}
	
	/* Obtengo la entidad de Display en la bd con su codigo */
	public Display selectDisplay(int codigo) throws Exception{
		EntityManager em = getEntityManagerInstance();
		Display d = em.find(Display.class, codigo);
		if (d != null){
			return d;
		}
		else{
			throw new Exception("No existe el Display con código " + codigo);
		}
    }
	
	public List<Display> selectDisplays(){
		EntityManager em = getEntityManagerInstance();
		return (List<Display>) em.createQuery("select d from Display d").getResultList();
	}
	
	public void updateDisplay(int codigo, String rutaArchivo) throws Exception{
		EntityManager em = getEntityManagerInstance();
		
		Display d = selectDisplay(codigo);
		d.setRutaArchivo(rutaArchivo);
		d.setLastUpdated(new Date());
		
		em.getTransaction().begin();
		em.persist(d);
		em.getTransaction().commit();
	}
	
	public void deleteDisplay(int codigo) throws Exception{
		EntityManager em = getEntityManagerInstance();	
		
		Display d = selectDisplay(codigo);
		
		em.getTransaction().begin();
    	em.remove(d);
		em.getTransaction().commit();
    }
	
}
