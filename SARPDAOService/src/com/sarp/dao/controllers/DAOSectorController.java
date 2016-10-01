package com.sarp.dao.controllers;

import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.RollbackException;

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
		em.close();
		List<Puesto> list = s.getPuestos();
		List<BusinessPuesto> ret = new LinkedList<BusinessPuesto>();
		for(Puesto p : list){
			BusinessPuesto bp = new BusinessPuesto(p.getNombreMaquina(), p.getUsuarioId(), p.getUsuarioId(),p.getNumero());
			ret.add(bp);
		}	
		return ret;
	}
	

	public List<BusinessTramite> obtenerTramitesSector(String sectorID) throws Exception {
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
	

	public BusinessDisplay obtenerDisplaySector(String sectorID) throws Exception {
		EntityManager em = EMFactory.getEntityManager();
		DAOSector sectorRepository = factory.getSectorRepository(em);
		
		Sector s = sectorRepository.selectSector(sectorID);
		em.close();
		Display d = s.getDisplay();
		BusinessDisplay ret = new BusinessDisplay(d.getCodigo(), d.getRutaArchivo());
		return ret;
	}
	
	public void asignarDisplaySector(String codigoSector, int codigoDisplay) throws Exception{
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

	public void asociarTramiteSector(int codigoTramite, String codigoSector) throws Exception{
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
