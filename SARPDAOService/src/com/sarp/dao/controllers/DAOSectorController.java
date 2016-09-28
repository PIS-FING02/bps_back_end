package com.sarp.dao.controllers;

import java.util.LinkedList;
import java.util.List;

<<<<<<< HEAD
import javax.ejb.DuplicateKeyException;
import javax.persistence.EntityManager;
import javax.persistence.RollbackException;

import org.eclipse.persistence.exceptions.i18n.SessionLoaderExceptionResource;
=======
import javax.persistence.EntityManager;
import javax.persistence.RollbackException;

>>>>>>> e9b74c8... Avanzo en asociaciones de entidades

import com.sarp.classes.BusinessDisplay;
import com.sarp.classes.BusinessPuesto;
import com.sarp.classes.BusinessSector;
import com.sarp.classes.BusinessTramite;
import com.sarp.dao.factory.DAOFactory;
import com.sarp.dao.factory.EMFactory;
import com.sarp.dao.model.Display;
import com.sarp.dao.model.Puesto;
import com.sarp.dao.model.Sector;
import com.sarp.dao.model.Tramite;
import com.sarp.dao.repository.DAODisplay;
import com.sarp.dao.repository.DAOPuesto;
import com.sarp.dao.repository.DAOSector;
import com.sarp.dao.repository.DAOTramite;



public class DAOSectorController {
	
	private DAOFactory factory = DAOFactory.getInstance();
		
	public void crearSector(BusinessSector sector) throws RollbackException{
		EntityManager em = EMFactory.getEntityManager();
		DAOSector sectorRepository = factory.getSectorRepository(em);
		
		em.getTransaction().begin();
		sectorRepository.insertSector(sector.getSectorId(), sector.getNombre(),sector.getRuta());
		em.getTransaction().commit();
		em.close();
<<<<<<< HEAD
	}
	
	public BusinessSector obtenerSector(int codigoSector) throws Exception{
		EntityManager em = EMFactory.getEntityManager();
		DAOSector sectorRepository = factory.getSectorRepository(em);
		
		Sector s = sectorRepository.selectSector(codigoSector);
		em.close();
		BusinessSector ret = new BusinessSector(s.getCodigo(), s.getNombre(), s.getDisplay().getCodigo(), s.getRutaSector());
		return ret;	
	}
	
	public void eliminarSector(int codigo) throws Exception {
=======
	}
	
	public BusinessSector obtenerSector(String codigoSector) throws Exception{
		EntityManager em = EMFactory.getEntityManager();
		DAOSector sectorRepository = factory.getSectorRepository(em);
		
		Sector s = sectorRepository.selectSector(codigoSector);
		em.close();
		BusinessSector ret = new BusinessSector(s.getCodigo(), s.getNombre(),s.getRutaSector());
		return ret;	
	}
	
	public void eliminarSector(String codigo) throws Exception {
>>>>>>> e9b74c8... Avanzo en asociaciones de entidades
		EntityManager em = EMFactory.getEntityManager();
		DAOFactory factory = DAOFactory.getInstance();
		DAOSector sectorRepository = factory.getSectorRepository(em);
		
		em.getTransaction().begin();
		sectorRepository.deleteSector(codigo);
		em.getTransaction().commit();
		em.close();
	}

	public List<BusinessSector> listarSectores() {
		EntityManager em = EMFactory.getEntityManager();
		DAOSector sectorRepository = factory.getSectorRepository(em);
		
		List<Sector> list = sectorRepository.selectSectores();	
		em.close();
		List<BusinessSector> ret = new LinkedList<BusinessSector>();
		for (Sector s : list){

			BusinessSector dt = new BusinessSector(s.getCodigo(),s.getNombre(),s.getRutaSector());
			ret.add(dt);
		}
		return ret;
	}
	
	public void modificarSector(BusinessSector s) throws Exception{
		EntityManager em = EMFactory.getEntityManager();
		DAOSector sectorRepository = factory.getSectorRepository(em);
<<<<<<< HEAD
		
		em.getTransaction().begin();
		sectorRepository.updateSector(s.getSectorId(), s.getNombre(), s.getRuta());
		em.getTransaction().commit();
		em.close();
	}
	
	public void asociarSectorPuesto(int codigoSector, String nombreMaquina) throws Exception{
		EntityManager em = EMFactory.getEntityManager();
		DAOSector sectorRepository = factory.getSectorRepository(em);
		DAOPuesto puestoRepository = factory.getPuestoRepository(em);
		
		Sector s = sectorRepository.selectSector(codigoSector);
		Puesto p = puestoRepository.selectPuesto(nombreMaquina);
		
		if(s.getPuestos().contains(p)){
			throw new Exception("El puesto de " + nombreMaquina + " y el sector " + codigoSector + " ya estan asociados");
		}
		em.getTransaction().begin();
		sectorRepository.asociarSectorPuesto(s,p);
		em.getTransaction().commit();
		em.close();
	}
	
	public void desasociarSectorPuesto(int codigoSector, String nombreMaquina) throws Exception{
		EntityManager em = EMFactory.getEntityManager();
		DAOSector sectorRepository = factory.getSectorRepository(em);
		DAOPuesto puestoRepository = factory.getPuestoRepository(em);
		
		Sector s = sectorRepository.selectSector(codigoSector);
		Puesto p = puestoRepository.selectPuesto(nombreMaquina);
		
		if(!s.getPuestos().contains(p)){
			throw new Exception("El puesto de " + nombreMaquina + " y el sector " + codigoSector + " no estan asociados");
		}
		em.getTransaction().begin();
		sectorRepository.desasociarSectorPuesto(s,p);
		em.getTransaction().commit();
		em.close();
	}
	
	public List<BusinessPuesto> obtenerPuestosSector(Integer sectorID) throws Exception {
		EntityManager em = EMFactory.getEntityManager();
		DAOSector sectorRepository = factory.getSectorRepository(em);
		
		Sector s = sectorRepository.selectSector(sectorID);
=======
		
		em.getTransaction().begin();
		sectorRepository.updateSector(s.getSectorId(), s.getNombre(), s.getRuta());
		em.getTransaction().commit();
		em.close();
	}
	
	public void asociarSectorPuesto(String codigoSector, String nombreMaquina) throws Exception{
		EntityManager em = EMFactory.getEntityManager();
		DAOSector sectorRepository = factory.getSectorRepository(em);
		DAOPuesto puestoRepository = factory.getPuestoRepository(em);
		
		Sector s = sectorRepository.selectSector(codigoSector);
		Puesto p = puestoRepository.selectPuesto(nombreMaquina);
		
		if(s.getPuestos().contains(p)){
			throw new Exception("El puesto de " + nombreMaquina + " y el sector " + codigoSector + " ya estan asociados");
		}
		em.getTransaction().begin();
		sectorRepository.asociarSectorPuesto(s,p);
		em.getTransaction().commit();
		em.close();
	}
	
	public void desasociarSectorPuesto(String codigoSector, String nombreMaquina) throws Exception{
		EntityManager em = EMFactory.getEntityManager();
		DAOSector sectorRepository = factory.getSectorRepository(em);
		DAOPuesto puestoRepository = factory.getPuestoRepository(em);
		
		Sector s = sectorRepository.selectSector(codigoSector);
		Puesto p = puestoRepository.selectPuesto(nombreMaquina);
		
		if(!s.getPuestos().contains(p)){
			throw new Exception("El puesto de " + nombreMaquina + " y el sector " + codigoSector + " no estan asociados");
		}
		em.getTransaction().begin();
		sectorRepository.desasociarSectorPuesto(s,p);
		em.getTransaction().commit();
		em.close();
	}
	
	public List<BusinessPuesto> obtenerPuestosSector(String codigoSector) throws Exception {
		EntityManager em = EMFactory.getEntityManager();
		DAOSector sectorRepository = factory.getSectorRepository(em);
		
		Sector s = sectorRepository.selectSector(codigoSector);
>>>>>>> e9b74c8... Avanzo en asociaciones de entidades
		em.close();
		List<Puesto> list = s.getPuestos();
		List<BusinessPuesto> ret = new LinkedList<BusinessPuesto>();
		for(Puesto p : list){
			BusinessPuesto bp = new BusinessPuesto(p.getNombreMaquina(), p.getUsuarioId(), p.getUsuarioId());
			ret.add(bp);
		}	
		return ret;
	}
	
<<<<<<< HEAD
	public List<BusinessTramite> obtenerTramitesSector(Integer sectorID) throws Exception {
=======
	public List<BusinessTramite> obtenerTramitesSector(String sectorID) throws Exception {
>>>>>>> e9b74c8... Avanzo en asociaciones de entidades
		EntityManager em = EMFactory.getEntityManager();
		DAOSector sectorRepository = factory.getSectorRepository(em);
		
		Sector s = sectorRepository.selectSector(sectorID);
		em.close();
		List<Tramite> list = s.getTramites();
		List<BusinessTramite> ret = new LinkedList<BusinessTramite>();
		for(Tramite t : list){
			BusinessTramite bt = new BusinessTramite(t.getCodigo(), t.getNombre());
			ret.add(bt);
		}	
		return ret;
	}
	
<<<<<<< HEAD
	public BusinessDisplay obtenerDisplaySector(Integer sectorID) throws Exception {
=======
	public BusinessDisplay obtenerDisplaySector(String sectorID) throws Exception {
>>>>>>> e9b74c8... Avanzo en asociaciones de entidades
		EntityManager em = EMFactory.getEntityManager();
		DAOSector sectorRepository = factory.getSectorRepository(em);
		
		Sector s = sectorRepository.selectSector(sectorID);
		em.close();
		Display d = s.getDisplay();
		BusinessDisplay ret = new BusinessDisplay(d.getCodigo(), d.getRutaArchivo());
		return ret;
	}
	
<<<<<<< HEAD
	public void asignarDisplaySector(int codigoSector, int codigoDisplay) throws Exception{
=======
	public void asignarDisplaySector(String codigoSector, int codigoDisplay) throws Exception{
>>>>>>> e9b74c8... Avanzo en asociaciones de entidades
		EntityManager em = EMFactory.getEntityManager();
		DAODisplay displayRepository = factory.getDisplayRepository(em);
		DAOSector sectorRepository = factory.getSectorRepository(em);
		
		Display d = displayRepository.selectDisplay(codigoDisplay);
		Sector s = sectorRepository.selectSector(codigoSector);
		em.getTransaction().begin();
		displayRepository.asociarDisplaySector(d,s);
		em.getTransaction().commit();
		em.close();
	}
	
<<<<<<< HEAD
	public void asociarTramiteSector(int codigoTramite, int codigoSector) throws Exception{
=======
	public void asociarTramiteSector(int codigoTramite, String codigoSector) throws Exception{
>>>>>>> e9b74c8... Avanzo en asociaciones de entidades
		EntityManager em = EMFactory.getEntityManager();
		DAOTramite tramiteRepository = factory.getTramiteRepository(em);
		DAOSector sectorRepository = factory.getSectorRepository(em);
		
		Sector s = sectorRepository.selectSector(codigoSector);
		Tramite t = tramiteRepository.selectTramite(codigoTramite);
		if(s.getTramites().contains(t)){
			throw new Exception("El tramite " + codigoTramite + " y el sector " + codigoSector + " ya estan asociados");
		}
		em.getTransaction().begin();
		tramiteRepository.asociarTramiteSector(s, t);
		em.getTransaction().commit();
		em.close();
	}
	
}
