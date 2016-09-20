package com.sarp.dao.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.sarp.dao.model.DatosComplementario;
import com.sarp.dao.model.Numero;
import com.sarp.dao.model.Sector;
import com.sarp.dao.model.Tramite;

import java.util.List;

public class DAOSector {
	
	private static EntityManager em;
	
	private EntityManager getEntityManagerInstance(){
		if(em == null){
			EntityManagerFactory factory = Persistence.createEntityManagerFactory("postgresUnit");
			em = factory.createEntityManager();
		}
		return em;
	}
	
	public void crearSector(Integer codigo, String nombre){
		EntityManager em = getEntityManagerInstance();
		Sector s = new Sector();
		s.setCodigo(codigo);
		s.setNombre(nombre);
		em.getTransaction().begin();
		em.persist(s);
		em.getTransaction().commit();
	}
	
	public Sector obtenerSector(int codigo){
		EntityManager em = getEntityManagerInstance();
		return em.find(Sector.class, codigo);
	}

	public List<Sector> listarSectores() {
		EntityManager em = getEntityManagerInstance();
		return (List<Sector>) em.createQuery("select s from Sector s").getResultList();
	}
	
	
}
