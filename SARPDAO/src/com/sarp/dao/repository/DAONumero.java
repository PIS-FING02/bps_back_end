package com.sarp.dao.repository;

import javax.persistence.EntityManager;
import com.sarp.dao.factory.EMFactory;
import com.sarp.dao.model.DatosComplementario;
import com.sarp.dao.model.Numero;
import com.sarp.dao.model.Tramite;

import java.util.Date;
import java.util.List;

public class DAONumero {
	
	public void insertNumero(Tramite tramite, Integer internalId, String externalId, Date hora, Integer prioridad, String estado, Integer docIdentidad, String nombreCompleto, String tipoDoc){
		EntityManager em = EMFactory.getEntityManager();
		//Creo la nueva entidad Numero y la asocio con el Tramite
		Numero n = new Numero();
		n.setInternalId(internalId);
		n.setExternalId(externalId);
		n.setHora(hora);
		n.setPrioridad(prioridad);
		n.setEstado(estado);
		n.setDateCreated(new Date());
		n.setLastUpdated(new Date());
		tramite.addNumero(n);
		//Creo una nueva entidad de DatoComplementario y las asocio
		DatosComplementario d = new DatosComplementario();
		d.setNumero(n);
		d.setDocIdentidad(docIdentidad);
		d.setNombreCompleto(nombreCompleto);
		d.setTipoDoc(tipoDoc);
		d.setDateCreated(new Date());
		d.setLastUpdated(new Date());
		n.setDatosComplementario(d);
		
		em.getTransaction().begin();
		em.persist(n);
		em.persist(d);
		em.getTransaction().commit();
		em.close();
	}
	
	public Numero selectNumero(int internalId) throws Exception{
		EntityManager em = EMFactory.getEntityManager();
		Numero n = getNumero(em, internalId);
    	return n;
    }
	
	public List<Numero> selectNumeros(){
		EntityManager em = EMFactory.getEntityManager();
		List<Numero> ret = (List<Numero>) em.createQuery("select n from Numero n").getResultList();
		return ret;
	}
	
	public void deleteNumero(int id) throws Exception {
		EntityManager em = EMFactory.getEntityManager();
		
		Numero n = getNumero(em, id);
		
		em.getTransaction().begin();
    	em.remove(n);
		em.getTransaction().commit();
		em.close();
	}

	public void updateNumero(Integer internalId, String estado, String externalId, Date hora, Integer prioridad) throws Exception {
		EntityManager em = EMFactory.getEntityManager();
		Numero n = getNumero(em, internalId);
		n.setEstado(estado);
		n.setExternalId(externalId);
		n.setHora(hora);
		n.setPrioridad(prioridad);
		n.setLastUpdated(new Date());
		
		em.getTransaction().begin();
		em.persist(n);
		em.getTransaction().commit();
		em.close();	
	}
	
	public boolean existsNumero(int internalId){
		EntityManager em = EMFactory.getEntityManager();
		Numero n = em.find(Numero.class, internalId);
		return n != null;
	}
	
	//funcion auxuliar, usada para no usar mas de un EntityManager al obtener un Numero
	public Numero getNumero(EntityManager em, int internalId) throws Exception{
		Numero n = em.find(Numero.class, internalId);
		if (n != null){
			return n;
		}
		else{
			throw new Exception("No existe el Numero con código " + internalId);
		}
    }
}
