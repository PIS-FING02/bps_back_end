package com.sarp.dao.controllers;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.RollbackException;

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
import com.sarp.dao.repository.DAONumero;
import com.sarp.dao.repository.DAOPuesto;
import com.sarp.dao.repository.DAOSector;

public class DAOPuestoController {

	private DAOFactory factory = DAOFactory.getInstance();

	public void crearPuesto(BusinessPuesto p) {
		if(p.getNombreMaquina() == null || p.getNombreMaquina().equals("")){
			throw new RollbackException("El nombre de maquina no puede ser vacio");
		}
		EntityManager em = EMFactory.getEntityManager();
		DAOPuesto puestoRepository = factory.getPuestoRepository(em);
		em.getTransaction().begin();
		puestoRepository.insertPuesto(p.getNombreMaquina(), p.getUsuarioId(), p.getNumeroPuesto());

		em.getTransaction().commit();
		em.close();
	}

	public BusinessPuesto obtenerPuesto(String nombreMaquina) throws RollbackException {
		EntityManager em = EMFactory.getEntityManager();
		DAOPuesto puestoRepository = factory.getPuestoRepository(em);

		Puesto p = puestoRepository.selectPuesto(nombreMaquina);
		em.close();
		BusinessPuesto ret = new BusinessPuesto(p.getNombreMaquina(), p.getUsuarioId(), p.getEstado(), p.getNumero());
		return ret;
	}

	public void eliminarPuesto(String nombreMaquina) throws RollbackException {
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
		for (Puesto p : lista) {
			BusinessPuesto bp = new BusinessPuesto(p.getNombreMaquina(), p.getUsuarioId(), p.getEstado() != null ? p.getEstado().toString() : "",p.getNumero());
			ret.add(bp);
		}
		return ret;
	}

	public void modificarPuesto(BusinessPuesto puesto) throws RollbackException {
		EntityManager em = EMFactory.getEntityManager();
		DAOPuesto puestoRepository = factory.getPuestoRepository(em);

		em.getTransaction().begin();
		puestoRepository.updatePuesto(puesto.getNombreMaquina(), puesto.getEstado() != null ? puesto.getEstado().toString() : "", puesto.getUsuarioId(),
				puesto.getNumeroPuesto());

		em.getTransaction().commit();
		em.close();
	}

	public List<BusinessSector> obtenerSectoresPuesto(String nombreMaquina) throws RollbackException {
		EntityManager em = EMFactory.getEntityManager();
		DAOPuesto puestoRepository = factory.getPuestoRepository(em);

		Puesto p = puestoRepository.selectPuesto(nombreMaquina);
		em.close();
		List<Sector> list = p.getSectors();
		List<BusinessSector> ret = new LinkedList<BusinessSector>();
		for (Sector s : list) {
			BusinessSector bs = new BusinessSector(s.getCodigo(), s.getNombre(), s.getRutaSector());
			ret.add(bs);
		}
		return ret;
	}

	public void asociarNumeroPuesto(String nombreMaquina, int codigoNumero) throws RollbackException {
		EntityManager em = EMFactory.getEntityManager();
		DAONumero numeroRepository = factory.getNumeroRepository(em);
		DAOPuesto puestoRepository = factory.getPuestoRepository(em);

		Puesto p = puestoRepository.selectPuesto(nombreMaquina);
		Numero n = numeroRepository.selectNumero(codigoNumero);
		if(n.getPuestos().contains(p)){
			em.close();
			throw new RollbackException("El puesto de " + nombreMaquina + " y el numero " + codigoNumero + " ya estan asociados");
		}
		em.getTransaction().begin();
		numeroRepository.asociarNumeroPuesto(n, p);
		em.getTransaction().commit();
		em.close();
	}

	public void desasociarNumeroPuesto(String nombreMaquina, int codigoNumero) throws RollbackException {
		EntityManager em = EMFactory.getEntityManager();
		DAONumero numeroRepository = factory.getNumeroRepository(em);
		DAOPuesto puestoRepository = factory.getPuestoRepository(em);

		Puesto p = puestoRepository.selectPuesto(nombreMaquina);
		Numero n = numeroRepository.selectNumero(codigoNumero);
		if(!n.getPuestos().contains(p)){
			em.close();
			throw new RollbackException("El puesto de " + nombreMaquina + " y el numero " + codigoNumero + " no estan asociados");
		}
		em.getTransaction().begin();
		numeroRepository.desasociarNumeroPuesto(n, p);
		em.getTransaction().commit();
		em.close();
	}

	public void asociarNumeroPuestoActual(String nombreMaquina, int codigoNumero) throws RollbackException {
		EntityManager em = EMFactory.getEntityManager();
		DAONumero numeroRepository = factory.getNumeroRepository(em);
		DAOPuesto puestoRepository = factory.getPuestoRepository(em);

		Puesto p = puestoRepository.selectPuesto(nombreMaquina);
		Numero n = numeroRepository.selectNumero(codigoNumero);	
		em.getTransaction().begin();
		numeroRepository.asociarNumeroPuestoActual(n, p);
		em.getTransaction().commit();
		em.close();
	}

	public void desasociarNumeroPuestoActual(String nombreMaquina) throws RollbackException {

		EntityManager em = EMFactory.getEntityManager();
		DAONumero numeroRepository = factory.getNumeroRepository(em);
		DAOPuesto puestoRepository = factory.getPuestoRepository(em);

		Puesto p = puestoRepository.selectPuesto(nombreMaquina);
		Numero n = p.getNumero_puesto();
		if(n != null){
			em.getTransaction().begin();
			numeroRepository.desasociarNumeroPuestoActual(n, p);
			em.getTransaction().commit();
			em.close();
		}
	}

	public List<BusinessNumero> obtenerNumerosPuesto(String nombreMaquina) throws RollbackException {
		EntityManager em = EMFactory.getEntityManager();
		DAOPuesto puestoRepository = factory.getPuestoRepository(em);

		Puesto p = puestoRepository.selectPuesto(nombreMaquina);
		em.close();
		List<Numero> list = p.getNumeros();
		List<BusinessNumero> ret = new LinkedList<BusinessNumero>();
		for (Numero n : list) {
			GregorianCalendar c = new GregorianCalendar();
			c.setTime(n.getHora());
			BusinessNumero res = new BusinessNumero(n.getInternalId(), n.getExternalId(), c, n.getEstado(),
					n.getPrioridad());
			ret.add(res);
		}
		return ret;
	}

	public List<BusinessTramite> obtenerTramitesPuesto(String nombreMaquina) throws RollbackException {
		EntityManager em = EMFactory.getEntityManager();
		DAOPuesto puestoRepository = factory.getPuestoRepository(em);

		Puesto p = puestoRepository.selectPuesto(nombreMaquina);
		em.close();
		List<Tramite> list = p.getTramites();
		List<BusinessTramite> ret = new LinkedList<BusinessTramite>();
		for (Tramite t : list) {
			BusinessTramite bt = new BusinessTramite(t.getCodigo(), t.getNombre());
			ret.add(bt);
		}
		return ret;
	}

	public BusinessNumero obtenerNumeroActualPuesto(String nombreMaquina) throws RollbackException {
		EntityManager em = EMFactory.getEntityManager();
		DAOPuesto puestoRepository = factory.getPuestoRepository(em);

		Puesto p = puestoRepository.selectPuesto(nombreMaquina);
		em.close();
		Numero n = p.getNumero_puesto();
		if(n != null){			
			GregorianCalendar c = new GregorianCalendar();
			c.setTime(n.getHora());
			BusinessNumero res = new BusinessNumero(n.getInternalId(), n.getExternalId(), c, n.getEstado(),
					n.getPrioridad());
			return res;
		}
		return null;
	}

	public ArrayList<BusinessTramite> obtenerTramitesDeSector(String nombreMaquina, String sectorId) throws RollbackException {
		EntityManager em = EMFactory.getEntityManager();
		DAOSector sectorRepository = factory.getSectorRepository(em);
		DAOPuesto puestoRepository = factory.getPuestoRepository(em);
		
		Sector s = sectorRepository.selectSector(sectorId);
		Puesto p = puestoRepository.selectPuesto(nombreMaquina);
		em.close();
		if(!s.getPuestos().contains(p)){	
			//El Puesto tiene que pertenecer al Sector
			throw new RollbackException("El puesto de " + nombreMaquina + " y el sector " + sectorId + " no estan asociados");
		}
		else{
			//Busco los tramites asociados tanto al Sector como al Puesto
			List<Tramite> list = new LinkedList<Tramite>(s.getTramites());
			list.retainAll(p.getTramites());		
			
			ArrayList<BusinessTramite> ret = new ArrayList<BusinessTramite>();
			for (Tramite t : list){
				BusinessTramite dt = new BusinessTramite(t.getCodigo(), t.getNombre());
				ret.add(dt);
			}	
			return ret;
		}
	
	}

}