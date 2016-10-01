package com.sarp.dao.controllers;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;

import com.sarp.classes.BusinessDatoComplementario;
import com.sarp.classes.BusinessDisplay;
import com.sarp.classes.BusinessNumero;
import com.sarp.classes.BusinessPuesto;
import com.sarp.classes.BusinessSector;
import com.sarp.classes.BusinessTramite;
import com.sarp.dao.factory.DAOFactory;
import com.sarp.dao.factory.EMFactory;
import com.sarp.dao.model.Numero;
import com.sarp.dao.model.Puesto;


import com.sarp.dao.model.Sector;


import com.sarp.dao.model.Tramite;
import com.sarp.dao.repository.DAODisplay;
import com.sarp.dao.repository.DAONumero;
import com.sarp.dao.repository.DAOPuesto;

import com.sarp.dao.repository.DAOSector;

import com.sarp.dao.repository.DAOTramite;

public class DAONumeroController {
	
	private DAOFactory factory = DAOFactory.getInstance();
	

	public Integer crearNumero(BusinessNumero numero, BusinessDatoComplementario dc, int tramite) throws Exception{
		EntityManager em = EMFactory.getEntityManager();
		DAONumero numeroRepository = factory.getNumeroRepository(em);
		DAOTramite tramiteRespository = factory.getTramiteRepository(em);		

		Tramite t = tramiteRespository.selectTramite(tramite);	
		em.getTransaction().begin();
		Numero n = numeroRepository.insertNumero(t, numero.getExternalId(), numero.getHora().getTime(), numero.getPrioridad(), numero.getEstado());
		em.getTransaction().commit();
		em.close();				
		return n.getInternalId();	
	}

	public List<BusinessNumero> listarNumeros(){
		EntityManager em = EMFactory.getEntityManager();
		DAONumero numeroRepository = factory.getNumeroRepository(em);
		
		List<Numero> list = numeroRepository.selectNumeros();
		em.close();
		
		List<BusinessNumero> ret = new LinkedList<BusinessNumero>();
		for (Numero n : list){
			GregorianCalendar c = new GregorianCalendar();
			c.setTime(n.getHora());
			BusinessNumero numero = new BusinessNumero(n.getInternalId(),n.getExternalId(),c,n.getEstado(),n.getPrioridad());
			ret.add(numero);
		}
		return ret;
	}
	
	public BusinessNumero obtenerNumero(int id) throws Exception{
		EntityManager em = EMFactory.getEntityManager();
		DAONumero numeroRepository = factory.getNumeroRepository(em);
		
		Numero n = numeroRepository.selectNumero(id);
		em.close();
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(n.getHora());
		BusinessNumero numero = new BusinessNumero(n.getInternalId(),n.getExternalId(),c,n.getEstado(),n.getPrioridad());

		return numero;
	}
	
	public void modificarNumero(BusinessNumero numero) throws Exception{
		EntityManager em = EMFactory.getEntityManager();
		DAONumero numeroRepository = factory.getNumeroRepository(em);
		
		em.getTransaction().begin();
		numeroRepository.updateNumero(numero.getInternalId(),numero.getEstado(),numero.getExternalId(),numero.getHora().getTime(),numero.getPrioridad());
		em.getTransaction().commit();
		em.close();
	}
	
	public void eliminarNumero(int codigo) throws Exception{
		EntityManager em = EMFactory.getEntityManager();
		DAONumero numeroRepository = factory.getNumeroRepository(em);
		
		em.getTransaction().begin();
		numeroRepository.deleteNumero(codigo);
		em.getTransaction().commit();
		em.close();
	}
	
	public BusinessTramite obtenerTramiteNumero(Integer codigoNumero) throws Exception {
		EntityManager em = EMFactory.getEntityManager();
		DAONumero numeroRepository = factory.getNumeroRepository(em);
		
		Numero n = numeroRepository.selectNumero(codigoNumero);
		em.close();
		Tramite t = n.getTramite();
		BusinessTramite res = new BusinessTramite(t.getCodigo(), t.getNombre());
		return res;
	}

	public LinkedList<BusinessNumero> listarNumerosDelDia() {
		EntityManager em = EMFactory.getEntityManager();
		DAONumero numeroRepository = factory.getNumeroRepository(em);
		
		List<Numero> list = numeroRepository.selectNumerosDelDia();
		em.close();
		
		LinkedList<BusinessNumero> ret = new LinkedList<BusinessNumero>();
		for (Numero n : list){
			GregorianCalendar c = new GregorianCalendar();
			c.setTime(n.getHora());
			BusinessNumero numero = new BusinessNumero(n.getInternalId(),n.getExternalId(),c,n.getEstado(),n.getPrioridad());
			ret.add(numero);
		}
		return ret;
	}
	
	public void asociarNumeroPuesto(int codigoNumero, String nombreMaquina) throws Exception{
		EntityManager em = EMFactory.getEntityManager();
		DAONumero numeroRepository = factory.getNumeroRepository(em);
		DAOPuesto puestoRepository = factory.getPuestoRepository(em);
		
		Puesto p = puestoRepository.selectPuesto(nombreMaquina);
		Numero n = numeroRepository.selectNumero(codigoNumero);
		em.getTransaction().begin();
		numeroRepository.asociarNumeroPuesto(n,p);
		em.getTransaction().commit();
		em.close();
	}
	
	public List<BusinessPuesto> obtenerPuestosNumero(Integer codigoNumero) throws Exception {
		EntityManager em = EMFactory.getEntityManager();
		DAONumero numeroRepository = factory.getNumeroRepository(em);
		
		Numero n = numeroRepository.selectNumero(codigoNumero);
		em.close();
		List<Puesto> list = n.getPuestos();
		List<BusinessPuesto> ret = new LinkedList<BusinessPuesto>();
		for(Puesto p : list){
			BusinessPuesto bp = new BusinessPuesto(p.getNombreMaquina(), p.getUsuarioId(), p.getEstado());
			ret.add(bp);
		}	
		return ret;
	}
	
	public void asociarNumeroPuestoActual(int codigoNumero, String nombreMaquina) throws Exception{
		EntityManager em = EMFactory.getEntityManager();
		DAONumero numeroRepository = factory.getNumeroRepository(em);
		DAOPuesto puestoRepository = factory.getPuestoRepository(em);
		
		Puesto p = puestoRepository.selectPuesto(nombreMaquina);
		Numero n = numeroRepository.selectNumero(codigoNumero);
		em.getTransaction().begin();
		numeroRepository.asociarNumeroPuestoActual(n,p);
		em.getTransaction().commit();
		em.close();
	}
	
	public BusinessPuesto obtenerPuestoActualNumero(Integer codigoNumero) throws Exception {
		EntityManager em = EMFactory.getEntityManager();
		DAONumero numeroRepository = factory.getNumeroRepository(em);
		
		Numero n = numeroRepository.selectNumero(codigoNumero);
		em.close();
		Puesto p = n.getPuesto();
		BusinessPuesto res = new BusinessPuesto(p.getNombreMaquina(), p.getUsuarioId(), p.getEstado());
		return res;
	}

}