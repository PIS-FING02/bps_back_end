package com.sarp.dao.repository;

import javax.persistence.EntityManager;
import com.sarp.dao.factory.EMFactory;
import com.sarp.dao.model.Puesto;

import java.util.Date;
import java.util.List;

public class DAOPuesto {
	
	/* Creo en la base una entidad Puesto
	 */
	public void insertPuesto(String nombreMaquina){
		EntityManager em = EMFactory.getEntityManager();
		
		Puesto p = new Puesto();
		p.setNombreMaquina(nombreMaquina);
		p.setDateCreated(new Date());
		p.setLastUpdated(new Date());
		
		em.getTransaction().begin();
		em.persist(p);
		em.getTransaction().commit();
	}
	
	/* Obtengo la entidad de Puesto en la base de datos con su nombre */
	public Puesto selectPuesto(String nombreMaquina) throws Exception{
		EntityManager em = EMFactory.getEntityManager();
		
		Puesto p = getPuesto(em, nombreMaquina);
		em.close();
		return p;
    }
	
	/* Obtengo todos los Puestos en la base de datos */
	public List<Puesto> selectPuestos(){
		EntityManager em = EMFactory.getEntityManager();
		
		List<Puesto> res = em.createQuery("select p from Puesto p").getResultList();
		em.close();
		return res;
	}
	
	/* Modifico el estado de un Puesto dado por su nombre */
	public void updatePuesto(String nombreMaquina, String estado) throws Exception{
		EntityManager em = EMFactory.getEntityManager();
		
		Puesto p = getPuesto(em, nombreMaquina);
		p.setEstado(estado);
		p.setLastUpdated(new Date());
		
		em.getTransaction().begin();
		em.persist(p);
		em.getTransaction().commit();
		em.close();
	}
	
	/* elimino un display de la base de datos */
	public void deletePuesto(String nombreMaquina) throws Exception{
		EntityManager em = EMFactory.getEntityManager();
		
		Puesto p = getPuesto(em, nombreMaquina);
		
		em.getTransaction().begin();
    	em.remove(p);
		em.getTransaction().commit();
		em.close();
    }
	
	//funcion auxuliar para no usar mas de un EntityManager al obtener un display
	public Puesto getPuesto(EntityManager em, String nombreMaquina) throws Exception{
		Puesto p = em.find(Puesto.class, nombreMaquina);
		if (p != null){
			return p;
		}
		else{
			throw new Exception("No existe el Puesto con nombre " + nombreMaquina);
		}
    }
	
}
