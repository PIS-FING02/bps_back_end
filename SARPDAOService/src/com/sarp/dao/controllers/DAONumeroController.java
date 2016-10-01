package com.sarp.dao.controllers;

import java.util.GregorianCalendar;

import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManager;
import com.sarp.classes.BusinessDatoComplementario;
import com.sarp.classes.BusinessNumero;
import com.sarp.classes.BusinessPuesto;

import com.sarp.classes.BusinessTramite;
import com.sarp.dao.factory.DAOFactory;
import com.sarp.dao.factory.EMFactory;
import com.sarp.dao.model.DatosComplementario;
import com.sarp.dao.model.Numero;
import com.sarp.dao.model.Puesto;
import com.sarp.dao.model.Tramite;
import com.sarp.dao.repository.DAONumero;

import com.sarp.dao.repository.DAOTramite;

public class DAONumeroController {
	
	private DAOFactory factory = DAOFactory.getInstance();

	public Integer crearNumero(BusinessNumero numero, int tramite, BusinessDatoComplementario dc) throws Exception{
		EntityManager em = EMFactory.getEntityManager();
		DAONumero numeroRepository = factory.getNumeroRepository(em);
		DAOTramite tramiteRespository = factory.getTramiteRepository(em);		

		Tramite t = tramiteRespository.selectTramite(tramite);	
		em.getTransaction().begin();
		Numero n = numeroRepository.insertNumero(t, numero.getExternalId(), numero.getHora().getTime(), numero.getPrioridad(), numero.getEstado());
		if(dc != null){
			numeroRepository.insertDatoComplementario(n, dc.getDocIdentidad(),dc.getNombreCompleto(),dc.getTipoDoc());
		}
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
	
	public BusinessDatoComplementario obtenerDatosNumero(int id) throws Exception{
		EntityManager em = EMFactory.getEntityManager();
		DAONumero numeroRepository = factory.getNumeroRepository(em);
		
		DatosComplementario dc = numeroRepository.selectNumero(id).getDatosComplementario();
		em.close();
		
		BusinessDatoComplementario dato = new BusinessDatoComplementario(dc.getDocIdentidad(), dc.getNombreCompleto(), dc.getTipoDoc());
		return dato;
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
	
	
	
	public List<BusinessPuesto> obtenerPuestosNumero(Integer codigoNumero) throws Exception {
		EntityManager em = EMFactory.getEntityManager();
		DAONumero numeroRepository = factory.getNumeroRepository(em);
		
		Numero n = numeroRepository.selectNumero(codigoNumero);
		em.close();
		List<Puesto> list = n.getPuestos();
		List<BusinessPuesto> ret = new LinkedList<BusinessPuesto>();
		for(Puesto p : list){
			BusinessPuesto bp = new BusinessPuesto(p.getNombreMaquina(), p.getUsuarioId(), p.getEstado(),p.getNumero());
			ret.add(bp);
		}	
		return ret;
	}
	
	
	
	public BusinessPuesto obtenerPuestoActualNumero(Integer codigoNumero) throws Exception {
		EntityManager em = EMFactory.getEntityManager();
		DAONumero numeroRepository = factory.getNumeroRepository(em);
		
		Numero n = numeroRepository.selectNumero(codigoNumero);
		em.close();
		Puesto p = n.getPuesto();
		BusinessPuesto res = new BusinessPuesto(p.getNombreMaquina(), p.getUsuarioId(), p.getEstado(),p.getNumero());
		return res;
	}

}