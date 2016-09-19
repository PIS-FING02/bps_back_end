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
	
	
	public void crearNumero(Tramite tramite,String nombre){
		System.out.println("hola desde crearNumero1.1");
		EntityManager em = getEntityManagerInstance();
		Numero n = new Numero(tramite);
		System.out.println("hola desde crearNumero1.2");
		DatosComplementario dat = new DatosComplementario();
		System.out.println("hola desde crearNumero1.25");
		//dat.addNumero(n);
		System.out.println("hola desde crearNumero1.3");
		//dat.setNombreCompleto(nombre);
		System.out.println("hola desde crearNumero1.4");
		//n.setDatosComplementario(dat);
		em.getTransaction().begin();
		em.persist(n);
		//em.persist(dat);
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
	
	
	/*public static void main(String[] args){
		 System.out.println("1");
		 EntityManagerFactory factory = Persistence.createEntityManagerFactory("postgresUnit");
    	 System.out.println("2");
         EntityManager em = factory.createEntityManager();       
         System.out.println("3");
         
         CrearNumero(9, "estado9");
         System.out.println("4");
	}*/
	
}
