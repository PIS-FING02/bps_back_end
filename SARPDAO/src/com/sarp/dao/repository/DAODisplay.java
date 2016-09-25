package com.sarp.dao.repository;

import javax.persistence.EntityManager;
import com.sarp.dao.factory.EMFactory;
import com.sarp.dao.model.Display;

import java.util.Date;
import java.util.List;

public class DAODisplay {
	
	/* Dada una ruta, creo en la base una entidad Display con esa ruta
	 * ID autogenerado
	 */
	public Integer insertDisplay(String rutaArchivo){
		EntityManager em = EMFactory.getEntityManager();
		
		Display d = new Display();
		d.setRutaArchivo(rutaArchivo);
		d.setDateCreated(new Date());
		d.setLastUpdated(new Date());
		
		em.getTransaction().begin();
		em.persist(d);
		em.getTransaction().commit();
		return d.getCodigo();
	}
	
	/* Obtengo la entidad de Display en la bd con su codigo */
	public Display selectDisplay(int codigo) throws Exception{
		EntityManager em = EMFactory.getEntityManager();
		
		Display d = getDisplay(em, codigo);
		em.close();
		return d;
    }
	
	/* Obtengo todos los displays en la base de datos */
	public List<Display> selectDisplays(){
		EntityManager em = EMFactory.getEntityManager();
		
		List<Display> res = em.createQuery("select d from Display d").getResultList();
		em.close();
		return res;
	}
	
	/* Modifico la ruta de un display dado por su codigo */
	public void updateDisplay(int codigo, String rutaArchivo) throws Exception{
		EntityManager em = EMFactory.getEntityManager();
		
		Display d = getDisplay(em, codigo);
		d.setRutaArchivo(rutaArchivo);
		d.setLastUpdated(new Date());
		
		em.getTransaction().begin();
		em.persist(d);
		em.getTransaction().commit();
		em.close();
	}
	
	/* elimino un display de la base de datos */
	public void deleteDisplay(int codigo) throws Exception{
		EntityManager em = EMFactory.getEntityManager();
		
		Display d = getDisplay(em, codigo);
		
		em.getTransaction().begin();
    	em.remove(d);
		em.getTransaction().commit();
		em.close();
    }
	
	//funcion auxuliar para no usar mas de un EntityManager al obtener un display
	public Display getDisplay(EntityManager em, int codigo) throws Exception{
		Display d = em.find(Display.class, codigo);
		if (d != null){
			return d;
		}
		else{
			throw new Exception("No existe el Display con código " + codigo);
		}
    }
	
}
