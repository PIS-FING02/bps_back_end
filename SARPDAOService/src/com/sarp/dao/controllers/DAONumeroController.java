package com.sarp.dao.controllers;

import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import com.sarp.classes.BusinessDatoComplementario;
import com.sarp.classes.BusinessNumero;
import com.sarp.classes.BusinessPuesto;
import com.sarp.classes.BusinessSector;
import com.sarp.classes.BusinessTramite;
import com.sarp.dao.factory.DAOFactory;
import com.sarp.dao.factory.EMFactory;
import com.sarp.dao.model.DatosComplementario;
import com.sarp.dao.model.Numero;
import com.sarp.dao.model.Puesto;
import com.sarp.dao.model.Sector;
import com.sarp.dao.model.Tramite;
import com.sarp.dao.repository.DAONumero;
import com.sarp.dao.repository.DAOSector;
import com.sarp.dao.repository.DAOTramite;

public class DAONumeroController {
	
	private DAOFactory factory = DAOFactory.getInstance();

	/* Se crea un Numero para un Tramite y Sector dado, opcionalmente se pueden pasar DatosComplementarios */
	public Integer crearNumero(BusinessNumero numero, int tramite, String sector, BusinessDatoComplementario dc) throws RollbackException{
		EntityManager em = EMFactory.getEntityManager();
		DAONumero numeroRepository = factory.getNumeroRepository(em);
		DAOTramite tramiteRespository = factory.getTramiteRepository(em);
		DAOSector sectorRepository = factory.getSectorRepository(em);

		Tramite t = tramiteRespository.selectTramite(tramite);	
		Sector s = sectorRepository.selectSector(sector);
		if(t.getSectors().contains(s)){
			em.getTransaction().begin();
			Numero n = numeroRepository.insertNumero(t, s, numero.getExternalId(), numero.getHora(), numero.getPrioridad(), numero.getEstado());
			if(dc != null){
				numeroRepository.insertDatoComplementario(n, dc.getDocIdentidad(),dc.getNombreCompleto(),dc.getTipoDoc());
			}
			em.getTransaction().commit();
			em.close();				
			return n.getInternalId();	
		}
		else{
			throw new RollbackException("El tramite " + tramite + " y el sector " + sector + " no están asociados");
		}
	}

	public ArrayList<BusinessNumero> listarNumeros(){
		EntityManager em = EMFactory.getEntityManager();
		DAONumero numeroRepository = factory.getNumeroRepository(em);
		
		ArrayList<Numero> list = numeroRepository.selectNumeros();
		em.close();
		
		ArrayList<BusinessNumero> ret = new ArrayList<BusinessNumero>();
		for (Numero n : list){
			BusinessNumero numero = new BusinessNumero(n.getInternalId(),n.getExternalId(),n.getHora(),n.getEstado(),n.getPrioridad());
			numero.setCodSector(n.getSector().getCodigo());
			numero.setCodTramite(n.getTramite().getCodigo());
			numero.setLastUpdated(n.getLastUpdated());
			ret.add(numero);
		}
		return ret;
	}
	
	public BusinessNumero obtenerNumero(int id) throws RollbackException{
		EntityManager em = EMFactory.getEntityManager();
		DAONumero numeroRepository = factory.getNumeroRepository(em);
		
		Numero n = numeroRepository.selectNumero(id);
		em.close();
		BusinessNumero numero = new BusinessNumero(n.getInternalId(),n.getExternalId(),n.getHora(),n.getEstado(),n.getPrioridad(),n.getTramite().getCodigo(),n.getSector().getCodigo());
		numero.setLastUpdated(n.getLastUpdated());
		return numero;
	}
	
	public BusinessDatoComplementario obtenerDatosNumero(int id) throws RollbackException{
		EntityManager em = EMFactory.getEntityManager();
		DAONumero numeroRepository = factory.getNumeroRepository(em);
		
		DatosComplementario dc = numeroRepository.selectNumero(id).getDatosComplementario();
		em.close();
		
		BusinessDatoComplementario dato = new BusinessDatoComplementario(dc.getDocIdentidad(), dc.getNombreCompleto(), dc.getTipoDoc());
		dato.setLastUpdated(dc.getLastUpdated());
		return dato;
	}
	
	public void modificarNumero(BusinessNumero numero) throws RollbackException{
		EntityManager em = EMFactory.getEntityManager();
		DAONumero numeroRepository = factory.getNumeroRepository(em);
		
		em.getTransaction().begin();
		numeroRepository.updateNumero(numero.getInternalId(),numero.getEstado(),numero.getExternalId(),numero.getHora(),numero.getPrioridad(),numero.getLastUpdated());
		em.getTransaction().commit();
		em.close();
	}
	
	public void eliminarNumero(int codigo) throws RollbackException{
		EntityManager em = EMFactory.getEntityManager();
		DAONumero numeroRepository = factory.getNumeroRepository(em);
		
		em.getTransaction().begin();
		numeroRepository.deleteNumero(codigo);
		em.getTransaction().commit();
		em.close();
	}
	
	public BusinessTramite obtenerTramiteNumero(Integer codigoNumero) throws RollbackException {
		EntityManager em = EMFactory.getEntityManager();
		DAONumero numeroRepository = factory.getNumeroRepository(em);
		
		Numero n = numeroRepository.selectNumero(codigoNumero);
		em.close();
		Tramite t = n.getTramite();
		BusinessTramite res = new BusinessTramite(t.getCodigo(), t.getNombre());
		res.setLastUpdated(t.getLastUpdated());
		return res;
	}
	
	public BusinessSector obtenerSectorNumero(Integer codigoNumero) throws RollbackException {
		EntityManager em = EMFactory.getEntityManager();
		DAONumero numeroRepository = factory.getNumeroRepository(em);
		
		Numero n = numeroRepository.selectNumero(codigoNumero);
		em.close();
		Sector s = n.getSector();
		BusinessSector res = new BusinessSector(s.getCodigo(),s.getNombre(),s.getRutaSector());			
		res.setLastUpdated(s.getLastUpdated());
		return res;
	}

	public ArrayList<BusinessNumero> listarNumerosDelDia() {
		EntityManager em = EMFactory.getEntityManager();
		DAONumero numeroRepository = factory.getNumeroRepository(em);
		
		ArrayList<Numero> list = numeroRepository.selectNumerosDelDia();
		em.close();
		
		ArrayList<BusinessNumero> ret = new ArrayList<BusinessNumero>();
		for (Numero n : list){
			BusinessNumero numero = new BusinessNumero(n.getInternalId(),n.getExternalId(),n.getHora(),n.getEstado(),n.getPrioridad());
			numero.setLastUpdated(n.getLastUpdated());
			numero.setCodSector(n.getSector().getCodigo());
			numero.setCodTramite(n.getTramite().getCodigo());
			ret.add(numero);
		}
		return ret;
	}
		
	public ArrayList<BusinessPuesto> obtenerPuestosNumero(Integer codigoNumero) throws RollbackException {
		EntityManager em = EMFactory.getEntityManager();
		DAONumero numeroRepository = factory.getNumeroRepository(em);
		
		Numero n = numeroRepository.selectNumero(codigoNumero);
		em.close();
		ArrayList<Puesto> list = new ArrayList<Puesto>(n.getPuestos());
		ArrayList<BusinessPuesto> ret = new ArrayList<BusinessPuesto>();
		for(Puesto p : list){
			BusinessPuesto bp = new BusinessPuesto(p.getNombreMaquina(), p.getUsuarioId(), p.getEstado(),p.getNumero());
			bp.setLastUpdated(p.getLastUpdated());
			ret.add(bp);
		}	
		return ret;
	}
	
	public BusinessPuesto obtenerPuestoActualNumero(Integer codigoNumero) throws RollbackException {
		EntityManager em = EMFactory.getEntityManager();
		DAONumero numeroRepository = factory.getNumeroRepository(em);
		
		Numero n = numeroRepository.selectNumero(codigoNumero);
		em.close();
		Puesto p = n.getPuesto();
		if(p != null){
			BusinessPuesto res = new BusinessPuesto(p.getNombreMaquina(), p.getUsuarioId(), p.getEstado(),p.getNumero());
			res.setLastUpdated(p.getLastUpdated());
			return res;
		}
		return null;
	}

}