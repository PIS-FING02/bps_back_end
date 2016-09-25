package com.sarp.dao.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;


import com.sarp.dao.factory.DAOFactory;

import com.sarp.dao.factory.EMFactory;
import com.sarp.dao.model.DatosComplementario;
import com.sarp.dao.model.Display;
import com.sarp.dao.model.Numero;
import com.sarp.dao.model.Puesto;
import com.sarp.dao.model.Sector;
import com.sarp.dao.model.Tramite;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class DAOTramite {
	


	public Integer insertTramite(String nombre) throws Exception{
		EntityManager em = EMFactory.getEntityManager();
		
		Tramite t = new Tramite();
		t.setNombre(nombre);
		t.setDateCreated(new Date());
		t.setLastUpdated(new Date());
		
		em.getTransaction().begin();	
		em.persist(t);
		em.getTransaction().commit();
		em.close();
		return t.getCodigo();
	}
		
	public Tramite selectTramite(int codigo) throws Exception{
		EntityManager em = EMFactory.getEntityManager();
		
		Tramite t = getTramite(em, codigo);
		return t;
	}
	
	public List<Tramite> selectTramites() {
		EntityManager em = EMFactory.getEntityManager();
		
		em.close();

		return (List<Tramite>) em.createQuery("select t from Tramite t").getResultList();
	}
	
	

	public void deleteTramite(int codigo) throws Exception {
		EntityManager em = EMFactory.getEntityManager();
		
		Tramite t = getTramite(em, codigo);
		
		em.getTransaction().begin();
    	em.remove(t);
		em.getTransaction().commit();
		em.close();
	}

	public void updateTramite(Integer codigo, String nombre) throws Exception {
		EntityManager em = EMFactory.getEntityManager();
		
		Tramite t = getTramite(em, codigo);
		t.setNombre(nombre);
		t.setLastUpdated(new Date());
		
		em.getTransaction().begin();
		em.persist(t);
		em.getTransaction().commit();
		em.close();	
	}
	
	//funcion auxuliar, usada para no usar mas de un EntityManager al obtener un tramite
	public Tramite getTramite(EntityManager em, int codigo) throws Exception{
		Tramite t = em.find(Tramite.class, codigo);
		if (t != null){
			return t;
		}
		else{
			throw new Exception("No existe el Tramite con código " + codigo);
		}
    }

	public void asociarTramitePuesto(Puesto puesto, Tramite tramite) {
		EntityManager em = EMFactory.getEntityManager();
		
		tramite.getPuestos().add(puesto);
		//puesto.getTramites().add(tramite);
		tramite.setLastUpdated(new Date());	
		puesto.setLastUpdated(new Date());
		
		em.getTransaction().begin();
		em.persist(tramite);
		//em.persist(puesto);
		em.getTransaction().commit();
		em.close();
		
	}
	
	public void asociarTramiteSector(Sector sector, Tramite tramite) throws Exception{
		EntityManager em = EMFactory.getEntityManager();
		
		tramite.getSectors().add(sector);
		tramite.setLastUpdated(new Date());	
		sector.setLastUpdated(new Date());
		
		em.getTransaction().begin();
		em.persist(tramite);
		em.getTransaction().commit();
		em.close();
	}
	
	
	
}
