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
		if(sector.getSectorId() == null || sector.getSectorId().equals("")){
			throw new RollbackException("El identificador del sector no puede ser vacio");
		}
		EntityManager em = EMFactory.getEntityManager();
		DAOSector sectorRepository = factory.getSectorRepository(em);
		
		em.getTransaction().begin();
		sectorRepository.insertSector(sector.getSectorId(), sector.getNombre(),sector.getRuta());
		em.getTransaction().commit();
		em.close();
	}
	
	public BusinessSector obtenerSector(String codigoSector) throws RollbackException{
		EntityManager em = EMFactory.getEntityManager();
		DAOSector sectorRepository = factory.getSectorRepository(em);
		
		Sector s = sectorRepository.selectSector(codigoSector);
		em.close();
		BusinessSector ret = new BusinessSector(s.getCodigo(), s.getNombre(),s.getRutaSector(), s.isHoja());
		ret.setLastUpdated(s.getLastUpdated());
		return ret;	
	}
	
	public void eliminarSector(String codigo) throws RollbackException {
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
			BusinessSector dt = new BusinessSector(s.getCodigo(),s.getNombre(),s.getRutaSector(), s.isHoja());
			ret.add(dt);
		}
		return ret;
	}
	
	public void modificarSector(BusinessSector s) throws RollbackException{
		EntityManager em = EMFactory.getEntityManager();
		DAOSector sectorRepository = factory.getSectorRepository(em);	

		em.getTransaction().begin();
		sectorRepository.updateSector(s.getSectorId(), s.getNombre(), s.getRuta(), s.getLastUpdated());
		em.getTransaction().commit();
		em.close();
	}
	
	public void bajaLogicarSector(String idSector) throws RollbackException{
		EntityManager em = EMFactory.getEntityManager();
		DAOSector sectorRepository = factory.getSectorRepository(em);	

		em.getTransaction().begin();
		sectorRepository.bajaLogicaSector(idSector);
		em.getTransaction().commit();
		em.close();
	}
	
	public void asociarSectorPuesto(String codigoSector, String nombreMaquina) throws RollbackException{
		EntityManager em = EMFactory.getEntityManager();
		DAOSector sectorRepository = factory.getSectorRepository(em);
		DAOPuesto puestoRepository = factory.getPuestoRepository(em);
		
		Sector s = sectorRepository.selectSector(codigoSector);
		Puesto p = puestoRepository.selectPuesto(nombreMaquina);
		
		if(p.getSectors().contains(s)){
			em.close();
			throw new RollbackException("El puesto de " + nombreMaquina + " y el sector " + codigoSector + " ya estan asociados");
		}
		em.getTransaction().begin();
		sectorRepository.asociarSectorPuesto(s,p);
		em.getTransaction().commit();
		em.close();
	}
	
	public void desasociarSectorPuesto(String codigoSector, String nombreMaquina) throws RollbackException{
		EntityManager em = EMFactory.getEntityManager();
		DAOSector sectorRepository = factory.getSectorRepository(em);
		DAOPuesto puestoRepository = factory.getPuestoRepository(em);
		
		Sector s = sectorRepository.selectSector(codigoSector);
		Puesto p = puestoRepository.selectPuesto(nombreMaquina);
		if(!s.getPuestos().contains(p)){
			em.close();
			throw new RollbackException("El puesto de " + nombreMaquina + " y el sector " + codigoSector + " no estan asociados");
		}
		em.getTransaction().begin();
		sectorRepository.desasociarSectorPuesto(s,p);
		
		//TODO: sacarle al puesto los tramites que no deba tener
		List<Tramite> tramitesDelPuesto = p.getTramites();
		List<Tramite> tramitesQuedan = new LinkedList<Tramite>(); //Los tramites que deben sacarse del puesto
		List<Sector> sectoresDelPuesto = p.getSectors();
		for(Tramite t : tramitesDelPuesto){
			boolean queda = false;
			for(Sector se : sectoresDelPuesto){
				if(se.getTramites().contains(t)){
					queda = true;
					break;
				}
			}
			if(queda){
				tramitesQuedan.add(t);
			}
		}
		p.setTramites(tramitesQuedan);
		
		
		em.getTransaction().commit();
		em.close();
	}
	
	public List<BusinessPuesto> obtenerPuestosSector(String codigoSector) throws RollbackException {
		EntityManager em = EMFactory.getEntityManager();
		DAOSector sectorRepository = factory.getSectorRepository(em);
		
		Sector s = sectorRepository.selectSector(codigoSector);
		em.close();
		List<Puesto> list = s.getPuestos();
		List<BusinessPuesto> ret = new LinkedList<BusinessPuesto>();
		for(Puesto p : list){
			BusinessPuesto bp = new BusinessPuesto(p.getNombreMaquina(), p.getUsuarioId(), p.getEstado() ,p.getNumero());
			bp.setLastUpdated(p.getLastUpdated());
			ret.add(bp);
		}	
		return ret;
	}
	
	public List<BusinessTramite> obtenerTramitesSector(String sectorID) throws RollbackException {
		EntityManager em = EMFactory.getEntityManager();
		DAOSector sectorRepository = factory.getSectorRepository(em);
		
		Sector s = sectorRepository.selectSector(sectorID);
		em.close();
		List<Tramite> list = s.getTramites();
		List<BusinessTramite> ret = new LinkedList<BusinessTramite>();
		for(Tramite t : list){
			BusinessTramite bt = new BusinessTramite(t.getCodigo(), t.getNombre());
			bt.setLastUpdated(t.getLastUpdated());
			ret.add(bt);
		}	
		return ret;
	}
	
	public List<BusinessDisplay> obtenerDisplaysSector(String sectorID) throws RollbackException {
		EntityManager em = EMFactory.getEntityManager();
		DAOSector sectorRepository = factory.getSectorRepository(em);
		
		Sector s = sectorRepository.selectSector(sectorID);
		em.close();
		List<Display> list = s.getDisplays();
		List<BusinessDisplay> ret = new LinkedList<BusinessDisplay>();
		for(Display d : list){
			BusinessDisplay bd = new BusinessDisplay(d.getIdDisplay());
			bd.setLastUpdated(d.getLastUpdated());
			ret.add(bd);
		}	
		return ret;
	}
	
	public void asociarDisplaySector(String codigoSector, String idDisplay) throws RollbackException{
		EntityManager em = EMFactory.getEntityManager();
		DAODisplay displayRepository = factory.getDisplayRepository(em);
		DAOSector sectorRepository = factory.getSectorRepository(em);
		
		Display d = displayRepository.selectDisplay(idDisplay);
		Sector s = sectorRepository.selectSector(codigoSector);
		if(s.getDisplays().contains(d)){
			em.close();
			throw new RollbackException("El display " + idDisplay + " y el sector " + codigoSector + " ya estan asociados");
		}
		em.getTransaction().begin();
		sectorRepository.asignarDisplaySector(d,s);
		em.getTransaction().commit();
		em.close();
	}
	
	public void desasociarDisplaySector(String codigoSector, String idDisplay) throws RollbackException{
		EntityManager em = EMFactory.getEntityManager();
		DAODisplay displayRepository = factory.getDisplayRepository(em);
		DAOSector sectorRepository = factory.getSectorRepository(em);
		
		Display d = displayRepository.selectDisplay(idDisplay);
		Sector s = sectorRepository.selectSector(codigoSector);
		if(!s.getDisplays().contains(d)){
			em.close();
			throw new RollbackException("El display " + idDisplay + " y el sector " + codigoSector + " no estan asociados");
		}
		em.getTransaction().begin();
		sectorRepository.desasignarDisplaySector(d,s);
		em.getTransaction().commit();
		em.close();
	}

	public void asociarTramiteSector(String codigoTramite, String codigoSector) throws RollbackException{
		EntityManager em = EMFactory.getEntityManager();
		DAOTramite tramiteRepository = factory.getTramiteRepository(em);
		DAOSector sectorRepository = factory.getSectorRepository(em);
		
		Sector s = sectorRepository.selectSector(codigoSector);
		Tramite t = tramiteRepository.selectTramite(codigoTramite);
		if(s.getTramites().contains(t)){
			em.close();
			throw new RollbackException("El tramite " + codigoTramite + " y el sector " + codigoSector + " ya estan asociados");
		}
		em.getTransaction().begin();
		tramiteRepository.asociarTramiteSector(s, t);
		em.getTransaction().commit();
		em.close();
	}
	
	public void desasociarTramiteSector(String codigoTramite, String codigoSector) throws RollbackException{
		EntityManager em = EMFactory.getEntityManager();
		DAOTramite tramiteRepository = factory.getTramiteRepository(em);
		DAOSector sectorRepository = factory.getSectorRepository(em);
		
		Sector s = sectorRepository.selectSector(codigoSector);
		Tramite t = tramiteRepository.selectTramite(codigoTramite);
		if(!s.getTramites().contains(t)){
			em.close();
			throw new RollbackException("El tramite " + codigoTramite + " y el sector " + codigoSector + " no estan asociados");
		}
		em.getTransaction().begin();
		tramiteRepository.desasociarTramiteSector(s, t);
		em.getTransaction().commit();
		em.close();
	}
	
}
