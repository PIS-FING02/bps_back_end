package com.sarp.dao.controllers;

import java.sql.Timestamp;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import com.sarp.classes.BusinessDisplay;
import com.sarp.dao.factory.DAOFactory;
import com.sarp.dao.factory.EMFactory;
import com.sarp.dao.model.Display;
import com.sarp.dao.repository.DAODisplay;

public class DAODisplayController {
			
	private DAOFactory factory = DAOFactory.getInstance();
	
	public Integer crearDisplay(BusinessDisplay d){
		EntityManager em = EMFactory.getEntityManager();
		DAODisplay displayRepository = factory.getDisplayRepository(em);
		
		em.getTransaction().begin();
		Display ret = displayRepository.insertDisplay(d.getRutaArchivo());
		em.getTransaction().commit();
		em.close();
		return ret.getCodigo();
	}
	
	public BusinessDisplay obtenerDisplay(int codigo) throws RollbackException{
		EntityManager em = EMFactory.getEntityManager();
		DAODisplay displayRepository = factory.getDisplayRepository(em);
		
		Display d = displayRepository.selectDisplay(codigo);
		em.close();
		BusinessDisplay ret = new BusinessDisplay(d.getCodigo(),d.getRutaArchivo());
		ret.setLastUpdated(d.getLastUpdated());
		return ret;	
	}
		
	public ArrayList<BusinessDisplay> listarDisplays(){
		EntityManager em = EMFactory.getEntityManager();
		DAODisplay displayRepository = factory.getDisplayRepository(em);
		
		ArrayList<Display> lista = displayRepository.selectDisplays();
		em.close();
		
		ArrayList<BusinessDisplay> ret = new ArrayList<BusinessDisplay>();
		for(Display md : lista){
			BusinessDisplay d = new BusinessDisplay(md.getCodigo(),md.getRutaArchivo());
			d.setLastUpdated(md.getLastUpdated());
			ret.add(d);
		}		
		return ret;
	}
	
	public void modificarDisplay(BusinessDisplay d) throws RollbackException{
		EntityManager em = EMFactory.getEntityManager();
		DAODisplay displayRepository = factory.getDisplayRepository(em);
		
		em.getTransaction().begin();
		displayRepository.updateDisplay(d.getCodigo(), d.getRutaArchivo(),d.getLastUpdated());
		em.getTransaction().commit();
		em.close();		
	}
	
	public void eliminarDisplay(int codigo) throws RollbackException{
		EntityManager em = EMFactory.getEntityManager();
		DAODisplay displayRepository = factory.getDisplayRepository(em);
		
		em.getTransaction().begin();
		displayRepository.deleteDisplay(codigo);
		em.getTransaction().commit();
		em.close();
	}

}
