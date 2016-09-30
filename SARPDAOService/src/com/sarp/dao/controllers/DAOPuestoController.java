package com.sarp.dao.controllers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;


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

import com.sarp.dao.repository.DAOPuesto;
import com.sarp.dao.repository.DAOSector;
import com.sarp.dao.repository.DAOTramite;

public class DAOPuestoController {
	
	private DAOFactory factory = DAOFactory.getInstance();

	public void crearPuesto(BusinessPuesto p){
		EntityManager em = EMFactory.getEntityManager();
		DAOPuesto puestoRepository = factory.getPuestoRepository(em);	
		em.getTransaction().begin();
		puestoRepository.insertPuesto(p.getNombreMaquina());
		em.getTransaction().commit();
		em.close();
	}

	public BusinessPuesto obtenerPuesto(String nombreMaquina) throws Exception {
		EntityManager em = EMFactory.getEntityManager();
		DAOPuesto puestoRepository = factory.getPuestoRepository(em);
		
		Puesto p = puestoRepository.selectPuesto(nombreMaquina);
		em.close();
		BusinessPuesto ret = new BusinessPuesto(p.getNombreMaquina(),p.getUsuarioId(),p.getEstado());
		return ret;	
	}

	public void eliminarPuesto(String nombreMaquina) throws Exception {
		EntityManager em = EMFactory.getEntityManager();
		DAOPuesto puestoRepository = factory.getPuestoRepository(em);
		
		em.getTransaction().begin();
		puestoRepository.deletePuesto(nombreMaquina);
		em.getTransaction().commit();
		em.close();
		
	}

	public List<BusinessPuesto> listarPuestos() {
		EntityManager em = EMFactory.getEntityManager();
		DAOPuesto puestoRepository = factory.getPuestoRepository(em);
		
		List<Puesto> lista = puestoRepository.selectPuestos();
		em.close();
		List<BusinessPuesto> ret = new ArrayList<BusinessPuesto>();
		for(Puesto p : lista){
			BusinessPuesto bp = new BusinessPuesto(p.getNombreMaquina(), p.getUsuarioId(), p.getUsuarioId());
			ret.add(bp);
		}		
		return ret;
	}

	public void modificarPuesto(BusinessPuesto puesto) throws Exception {
		EntityManager em = EMFactory.getEntityManager();
		DAOPuesto puestoRepository = factory.getPuestoRepository(em);
		
		em.getTransaction().begin();
		puestoRepository.updatePuesto(puesto.getNombreMaquina(), puesto.getEstado().toString());
		em.getTransaction().commit();
		em.close();
	}
	
	public List<BusinessSector> obtenerSectoresPuesto(String nombreMaquina) throws Exception {
		EntityManager em = EMFactory.getEntityManager();
		DAOPuesto puestoRepository = factory.getPuestoRepository(em);
		
		Puesto p = puestoRepository.selectPuesto(nombreMaquina);
		em.close();
		List<Sector> list = p.getSectors();
		List<BusinessSector> ret = new LinkedList<BusinessSector>();
		for(Sector s : list){
			BusinessSector bs = new BusinessSector(s.getCodigo(), s.getNombre(), s.getRutaSector());
			ret.add(bs);
		}	
		return ret;
	}
	
	public List<BusinessNumero> obtenerNumerosPuesto(String nombreMaquina) throws Exception {
		EntityManager em = EMFactory.getEntityManager();
		DAOPuesto puestoRepository = factory.getPuestoRepository(em);
		
		Puesto p = puestoRepository.selectPuesto(nombreMaquina);
		em.close();
		List<Numero> list = p.getNumeros();
		List<BusinessNumero> ret = new LinkedList<BusinessNumero>();
		for(Numero n : list){

			BusinessNumero res = new BusinessNumero(n.getInternalId(),n.getTramite().getCodigo(),n.getExternalId(),n.getHora(),n.getEstado(),n.getPrioridad());
			ret.add(res);
		}	
		return ret;
	}
	
	public List<BusinessTramite> obtenerTramitesPuesto(String nombreMaquina) throws Exception {
		EntityManager em = EMFactory.getEntityManager();
		DAOPuesto puestoRepository = factory.getPuestoRepository(em);
		
		Puesto p = puestoRepository.selectPuesto(nombreMaquina);
		em.close();
		List<Tramite> list = p.getTramites();
		List<BusinessTramite> ret = new LinkedList<BusinessTramite>();
		for(Tramite t : list){
			BusinessTramite bt = new BusinessTramite(t.getCodigo(), t.getNombre());
			ret.add(bt);
		}	
		return ret;
	}
	
	public BusinessNumero obtenerNumeroActualPuesto(String nombreMaquina) throws Exception {
		EntityManager em = EMFactory.getEntityManager();
		DAOPuesto puestoRepository = factory.getPuestoRepository(em);
		
		Puesto p = puestoRepository.selectPuesto(nombreMaquina);
		em.close();
		Numero n = p.getNumero_puesto();

		BusinessNumero res = new BusinessNumero(n.getInternalId(),n.getTramite().getCodigo(),n.getExternalId(),n.getHora(),n.getEstado(),n.getPrioridad());		
		return res;
	}

}