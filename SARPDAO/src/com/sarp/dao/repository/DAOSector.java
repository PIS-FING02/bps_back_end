package com.sarp.dao.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.sarp.dao.factory.EMFactory;
import com.sarp.dao.model.DatosComplementario;
import com.sarp.dao.model.Display;
import com.sarp.dao.model.Numero;
import com.sarp.dao.model.Sector;
import com.sarp.dao.model.Tramite;

import java.util.Date;
import java.util.List;

public class DAOSector {
	
	/* Creo en la base una entidad Sector
	 */
	public void insertSector(String rutaSector, String nombre){
		EntityManager em = EMFactory.getEntityManager();
		
		Sector s = new Sector();
		s.setNombre(nombre);
		s.setRutaSector(rutaSector);
		s.setDateCreated(new Date());
		s.setLastUpdated(new Date());
		
		em.getTransaction().begin();
		em.persist(s);
		em.getTransaction().commit();
	}
	
	/* Obtengo la entidad de Sector en la bd con su codigo */
	public Sector selectSector(int codigo) throws Exception{
		EntityManager em = EMFactory.getEntityManager();
		
		Sector s = getSector(em, codigo);
		em.close();
		return s;
    }
	
	/* Obtengo todos los Sectores en la base de datos */
	public List<Sector> selectSectores(){
		EntityManager em = EMFactory.getEntityManager();
		
		List<Sector> res = em.createQuery("select s from Sector s").getResultList();
		em.close();
		return res;
	}
	
	/* Modifico la ruta de un Sector dado por su codigo */
	public void updateSector(int codigo, String nombre, String rutaSector) throws Exception{
		EntityManager em = EMFactory.getEntityManager();
		
		Sector s = getSector(em, codigo);
		s.setNombre(nombre);
		s.setRutaSector(rutaSector);
		s.setLastUpdated(new Date());
		
		em.getTransaction().begin();
		em.persist(s);
		em.getTransaction().commit();
		em.close();
	}
	
	/* elimino un Sector de la base de datos */
	public void deleteSector(int codigo) throws Exception{
		EntityManager em = EMFactory.getEntityManager();
		
		Sector s = getSector(em, codigo);
		
		em.getTransaction().begin();
    	em.remove(s);
		em.getTransaction().commit();
		em.close();
    }
	
	//funcion auxuliar para no usar mas de un EntityManager al obtener un Sector
	public Sector getSector(EntityManager em, int codigo) throws Exception{
		Sector s = em.find(Sector.class, codigo);
		if (s != null){
			return s;
		}
		else{
			throw new Exception("No existe el Sector con código " + codigo);
		}
    }
	
	
}
