package com.sarp.dao.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.sarp.dao.model.DatosComplementario;
import com.sarp.dao.model.Numero;
import com.sarp.dao.model.Tramite;

import java.util.List;

public class DAONumero {
	
	private static EntityManager em;
	
	private EntityManager getEntityManagerInstance(){
		if(em == null){
			EntityManagerFactory factory = Persistence.createEntityManagerFactory("postgresUnit");
			em = factory.createEntityManager();
		}
		return em;
	}
	
	
	public void crearNumero(Tramite t,String nombre, Integer id){
		EntityManager em = getEntityManagerInstance();
		
		Numero n = new Numero();		
		n.setExternalId(nombre); //TODO revisar
		t.addNumero(n);
		n.setInternalId(id);
		DatosComplementario d = new DatosComplementario();
		d.setNumero(n);
		d.setDocIdentidad(id);
		n.setDatosComplementario(d);
		
		em.getTransaction().begin();
		em.persist(n);
		em.persist(d);
		em.getTransaction().commit();
	}
	
	public Numero obtenerNumero(int codigo){
		EntityManager em = getEntityManagerInstance();
    	return em.find(Numero.class, codigo);
    }
	
	public List<Numero> listarNumeros(){
		EntityManager em = getEntityManagerInstance();
		return (List<Numero>) em.createQuery("select n from Numero n").getResultList();
	}
	
	public void modificarNumero(Numero n,String estado,int prio){
		EntityManager em = getEntityManagerInstance();
		n.setEstado(estado);
		n.setPrioridad(prio);
		em.getTransaction().begin();
		em.persist(n);
		em.getTransaction().commit();
	}
	
	public void eliminarNumero(Numero n){
		EntityManager em = getEntityManagerInstance();	
		em.getTransaction().begin();
    	em.remove(n);
		em.getTransaction().commit();
    }
	
}
