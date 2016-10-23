package com.sarp.dao.controllers;

import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import com.sarp.classes.BusinessPuesto;
import com.sarp.classes.BusinessSector;
import com.sarp.classes.BusinessTramite;
import com.sarp.dao.factory.DAOFactory;
import com.sarp.dao.factory.EMFactory;
import com.sarp.dao.model.Puesto;
import com.sarp.dao.model.Sector;
import com.sarp.dao.model.Tramite;
import com.sarp.dao.repository.DAOPuesto;
import com.sarp.dao.repository.DAOSector;
import com.sarp.dao.repository.DAOTramite;

//import org.dao.repository.NumeroRepository;

public class DAOTramiteController {
	
	private DAOFactory factory = DAOFactory.getInstance();
	
	public Integer crearTramite(BusinessTramite tramite) throws RollbackException{
		EntityManager em = EMFactory.getEntityManager();
		DAOTramite tramiteRepository = factory.getTramiteRepository(em);
		
		em.getTransaction().begin();
		Tramite res = tramiteRepository.insertTramite(tramite.getNombre());
		em.getTransaction().commit();
		em.close();
		return res.getCodigo();
	}
	
	public List<BusinessTramite> listarTramites() {
		EntityManager em = EMFactory.getEntityManager();
		DAOTramite tramiteRepository = factory.getTramiteRepository(em);
		
		List<Tramite> list = tramiteRepository.selectTramites();
		em.close();
		List<BusinessTramite> ret = new LinkedList<BusinessTramite>();
		for (Tramite t : list){
			BusinessTramite dt = new BusinessTramite(t.getCodigo(), t.getNombre());
			dt.setLastUpdated(t.getLastUpdated());
			ret.add(dt);
		}	
		return ret;
	}
	
	public BusinessTramite obtenerTramite(int codigo) throws RollbackException{
		EntityManager em = EMFactory.getEntityManager();
		DAOTramite tramiteRepository = factory.getTramiteRepository(em);
		
		Tramite t = tramiteRepository.selectTramite(codigo);
		em.close();
		BusinessTramite ret = new BusinessTramite(t.getCodigo(),t.getNombre());
		ret.setLastUpdated(t.getLastUpdated());
		return ret;	
	}
	
	public void modificarTramite(BusinessTramite t) throws RollbackException{
		EntityManager em = EMFactory.getEntityManager();
		DAOTramite tramiteRepository = factory.getTramiteRepository(em);
		
		em.getTransaction().begin();
		tramiteRepository.updateTramite(t.getCodigo(),t.getNombre(),t.getLastUpdated());
		em.getTransaction().commit();
		em.close();
	}
	
	public void eliminarTramite(int codigo) throws RollbackException{
		EntityManager em = EMFactory.getEntityManager();
		DAOTramite tramiteRepository = factory.getTramiteRepository(em);
		
		em.getTransaction().begin();
		tramiteRepository.deleteTramite(codigo);
		em.getTransaction().commit();
		em.close();
	}	
	
	public List<BusinessSector> obtenerSectoresTramite(int codigoTramite) throws RollbackException {
		EntityManager em = EMFactory.getEntityManager();
		DAOTramite tramiteRepository = factory.getTramiteRepository(em);
		
		Tramite t = tramiteRepository.selectTramite(codigoTramite);
		em.close();
		List<Sector> list = t.getSectors();
		List<BusinessSector> ret = new LinkedList<BusinessSector>();
		for(Sector s : list){
			BusinessSector bs = new BusinessSector(s.getCodigo(), s.getNombre(), s.getRutaSector());
			bs.setLastUpdated(s.getLastUpdated());
			ret.add(bs);
		}	
		return ret;
	}
	
	/* Operaciones para la relacion Tramite-Puesto */
	public void asociarTramitePuesto(int codigoTramite, String nombreMaquina) throws RollbackException{
		EntityManager em = EMFactory.getEntityManager();
		DAOTramite tramiteRepository = factory.getTramiteRepository(em);
		DAOPuesto puestoRepository = factory.getPuestoRepository(em);
		
		Puesto p = puestoRepository.selectPuesto(nombreMaquina);
		Tramite t = tramiteRepository.selectTramite(codigoTramite);
		if(t.getPuestos().contains(p)){
			em.close();
			throw new RollbackException("El puesto de " + nombreMaquina + " y el tramite " + codigoTramite + " ya estan asociados");
		}
		em.getTransaction().begin();
		tramiteRepository.asociarTramitePuesto(p, t);
		em.getTransaction().commit();
		em.close();
	}
	
	public void desasociarTramitePuesto(int codigoTramite, String nombreMaquina) throws RollbackException{
		EntityManager em = EMFactory.getEntityManager();
		DAOTramite tramiteRepository = factory.getTramiteRepository(em);
		DAOPuesto puestoRepository = factory.getPuestoRepository(em);
		
		Puesto p = puestoRepository.selectPuesto(nombreMaquina);
		Tramite t = tramiteRepository.selectTramite(codigoTramite);
		if(!t.getPuestos().contains(p)){
			em.close();
			throw new RollbackException("El puesto de " + nombreMaquina + " y el tramite " + codigoTramite + " no estan asociados");
		}
		em.getTransaction().begin();
		tramiteRepository.desasociarTramitePuesto(p, t);
		em.getTransaction().commit();
		em.close();
	}
	
	public List<BusinessPuesto> obtenerPuestosTramite(int codigoTramite) throws RollbackException {
		EntityManager em = EMFactory.getEntityManager();
		DAOTramite tramiteRepository = factory.getTramiteRepository(em);
		
		Tramite t = tramiteRepository.selectTramite(codigoTramite);
		em.close();
		List<Puesto> list = t.getPuestos();
		List<BusinessPuesto> ret = new LinkedList<BusinessPuesto>();
		for(Puesto p : list){
			BusinessPuesto bp = new BusinessPuesto(p.getNombreMaquina(), p.getUsuarioId(), p.getEstado(),p.getNumero());
			bp.setLastUpdated(p.getLastUpdated());
			ret.add(bp);
		}	
		return ret;
	}
	
	public boolean existeTramiteSector(String sector, Integer tramite){
		EntityManager em = EMFactory.getEntityManager();
		DAOTramite tramiteRepository = factory.getTramiteRepository(em);
		DAOSector sectorRepository = factory.getSectorRepository(em);
		
		Tramite t = tramiteRepository.selectTramite(tramite);
		Sector s = sectorRepository.selectSector(sector);
		return t.getSectors().contains(s);
	}
		

}