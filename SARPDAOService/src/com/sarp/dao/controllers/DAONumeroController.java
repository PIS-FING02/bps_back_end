package com.sarp.dao.controllers;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import com.sarp.classes.BusinessDatoComplementario;
import com.sarp.classes.BusinessMetricasEstadoNumero;
import com.sarp.classes.BusinessMetricasNumero;
import com.sarp.classes.BusinessNumero;
import com.sarp.classes.BusinessNumeroTramite;
import com.sarp.classes.BusinessPuesto;
import com.sarp.classes.BusinessSector;
import com.sarp.classes.BusinessTramite;
import com.sarp.dao.factory.DAOFactory;
import com.sarp.dao.factory.EMFactory;
import com.sarp.dao.model.DatosComplementario;
import com.sarp.dao.model.MetricasEstadoNumero;
import com.sarp.dao.model.MetricasNumero;
import com.sarp.dao.model.Numero;
import com.sarp.dao.model.NumeroTramite;
import com.sarp.dao.model.NumeroTramitePK;
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
			Numero n = numeroRepository.insertNumero(t, s, numero.getExternalId(), numero.getHora(), numero.getPrioridad());
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
			BusinessNumero numero = new BusinessNumero(n.getInternalId(),n.getExternalId(),n.getHora(),n.getEstado(),n.getPrioridad(), n.getCodigoTramite(), n.getCodigoSector(),n.isFueAtrasado());
			numero.setCodSector(n.getCodigoSector());
			numero.setCodTramite(n.getCodigoTramite());
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
		BusinessNumero numero = new BusinessNumero(n.getInternalId(),n.getExternalId(),n.getHora(),n.getEstado(),n.getPrioridad(),n.getCodigoTramite(),n.getCodigoSector(),n.isFueAtrasado());
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
		numeroRepository.updateNumero(numero.getInternalId(),numero.getEstado() != null ? numero.getEstado().toString() : "",numero.getExternalId(),numero.getHora(),numero.getPrioridad(),numero.getLastUpdated(),numero.isFueAtrasado());
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
			BusinessNumero numero = new BusinessNumero(n.getInternalId(),n.getExternalId(),n.getHora(),n.getEstado(),n.getPrioridad(), n.getCodigoTramite(), n.getCodigoSector(), n.isFueAtrasado());
			numero.setLastUpdated(n.getLastUpdated());
			numero.setCodSector(n.getCodigoSector());
			numero.setCodTramite(n.getCodigoTramite());
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
	
	public void setearDesvio(Integer numeroDesviado, Integer numeroNuevo){
		EntityManager em = EMFactory.getEntityManager();
		DAONumero numeroRepository = factory.getNumeroRepository(em);
			
		em.getTransaction().begin();
		numeroRepository.setearDesvio(numeroDesviado, numeroNuevo);
		em.getTransaction().commit();
		em.close();
	}
	
	public BusinessNumero obtenerDesvio(Integer internalId) throws RollbackException {
		EntityManager em = EMFactory.getEntityManager();
		DAONumero numeroRepository = factory.getNumeroRepository(em);

		Numero n = numeroRepository.selectNumero(internalId);
		em.close();
		Numero desvio = n.getDesvio();
		if(desvio != null){			
			BusinessNumero res = new BusinessNumero(desvio.getInternalId(), desvio.getExternalId(), desvio.getHora(), desvio.getEstado(),desvio.getPrioridad(),desvio.getCodigoTramite(), desvio.getCodigoSector(), desvio.isFueAtrasado());
			res.setLastUpdated(desvio.getLastUpdated());
			return res;
		}
		return null;
	}
	
	public void asociarNumeroTramite(int codigoTramite, int internalId,String resultadoFinal) throws RollbackException{
		EntityManager em = EMFactory.getEntityManager();
		DAOTramite tramiteRepository = factory.getTramiteRepository(em);
		DAONumero numeroRepository = factory.getNumeroRepository(em);
		
		Numero n = numeroRepository.selectNumero(internalId);
		Tramite t = tramiteRepository.selectTramite(codigoTramite);
		NumeroTramitePK pk = new NumeroTramitePK();
		pk.setCodigoTramite(codigoTramite);
		pk.setInternalId(internalId);
		if(em.find(NumeroTramite.class, pk) != null){
			throw new RollbackException("El Numero con codigo " + internalId + " y el Tramite con codigo " + codigoTramite + " ya estan asociados");
		}
		em.getTransaction().begin();
		numeroRepository.asociarNumeroTramite(n, t, resultadoFinal);
		em.getTransaction().commit();
		em.close();
	}
	
	public ArrayList<BusinessNumeroTramite> obtenerNumeroTramites(Integer codigoNumero) throws RollbackException {
		EntityManager em = EMFactory.getEntityManager();
		DAONumero numeroRepository = factory.getNumeroRepository(em);
		
		Numero n = numeroRepository.selectNumero(codigoNumero);
		em.close();
		ArrayList<NumeroTramite> list = new ArrayList<NumeroTramite>(n.getNumeroTramites());
		ArrayList<BusinessNumeroTramite> ret = new ArrayList<BusinessNumeroTramite>();
		for(NumeroTramite nt : list){
			BusinessNumeroTramite bnt = new BusinessNumeroTramite(nt.getTramite().getCodigo(), nt.getResultadoFinal());
			ret.add(bnt);
		}	
		return ret;
	}
	
	public void modificarNumeroTramite(int codigoTramite, int internalId, String resultadoFinal){
		EntityManager em = EMFactory.getEntityManager();
		DAOTramite tramiteRepository = factory.getTramiteRepository(em);
		DAONumero numeroRepository = factory.getNumeroRepository(em);
		
		NumeroTramitePK pk = new NumeroTramitePK();
		pk.setCodigoTramite(codigoTramite);
		pk.setInternalId(internalId);
		NumeroTramite nt = em.find(NumeroTramite.class, pk);
		if(nt == null){
			throw new RollbackException("El Numero con codigo " + internalId + " y el Tramite con codigo " + codigoTramite + " no estan asociados");
		}
		em.getTransaction().begin();
		numeroRepository.updateNumeroTramite(nt, resultadoFinal);
		em.getTransaction().commit();
		em.close();
	}
	
	/* OBTENER METRICAS */
	public ArrayList<BusinessMetricasEstadoNumero> listarMetricasEstadoNumero() throws RollbackException {
		EntityManager em = EMFactory.getEntityManager();
		DAONumero numeroRepository = factory.getNumeroRepository(em);

		ArrayList<MetricasEstadoNumero> lista = numeroRepository.selectMetricasEstadoNumero();
		em.close();
		ArrayList<BusinessMetricasEstadoNumero> ret = new ArrayList<BusinessMetricasEstadoNumero>();
		for (MetricasEstadoNumero men : lista) {
			BusinessMetricasEstadoNumero bmen = new BusinessMetricasEstadoNumero(men.getId().getInternalId(), men.getId().getEstado(), men.getTimeSpent(), men.getLastUpdated(), men.getId().getDateCreated());
			if(men.getTimeSpent() == null){
				GregorianCalendar c1 = new GregorianCalendar();				
				GregorianCalendar c2 = men.getLastUpdated();
				long diff = c1.getTimeInMillis() - c2.getTimeInMillis();
				long diffSeconds = diff / 1000 % 60;
				long diffMinutes = diff / (60 * 1000) % 60;
				long diffHours = diff / (60 * 60 * 1000) % 24;
				String s = diffHours+":"+diffMinutes+":"+diffSeconds;
				bmen.setTimeSpent(s);
			}else{
				bmen.setTimeSpent(men.getTimeSpent().substring(0,8));
			}
			ret.add(bmen);
		}
		return ret;
	}

	public ArrayList<BusinessMetricasEstadoNumero> listarMetricasEstadoDeNumero(Integer internalId) {
		EntityManager em = EMFactory.getEntityManager();
		DAONumero numeroRepository = factory.getNumeroRepository(em);

		ArrayList<MetricasEstadoNumero> lista = numeroRepository.selectMetricasEstadoDeNumero(internalId);
		em.close();
		ArrayList<BusinessMetricasEstadoNumero> ret = new ArrayList<BusinessMetricasEstadoNumero>();
		for (MetricasEstadoNumero men : lista) {
			BusinessMetricasEstadoNumero bmen = new BusinessMetricasEstadoNumero(men.getId().getInternalId(), men.getId().getEstado(), men.getTimeSpent(), men.getLastUpdated(), men.getId().getDateCreated());
			if(men.getTimeSpent() == null){
				GregorianCalendar c1 = new GregorianCalendar();				
				GregorianCalendar c2 = men.getLastUpdated();
				long diff = c1.getTimeInMillis() - c2.getTimeInMillis();
				long diffSeconds = diff / 1000 % 60;
				long diffMinutes = diff / (60 * 1000) % 60;
				long diffHours = diff / (60 * 60 * 1000) % 24;
				String s = diffHours+":"+diffMinutes+":"+diffSeconds;
				bmen.setTimeSpent(s);
			}else{
				bmen.setTimeSpent(men.getTimeSpent().substring(0,8));
			}
			ret.add(bmen);
		}
		return ret;
	}
	
	public ArrayList<BusinessMetricasNumero> listarMetricasNumero() throws RollbackException {
		EntityManager em = EMFactory.getEntityManager();
		DAONumero numeroRepository = factory.getNumeroRepository(em);

		ArrayList<MetricasNumero> lista = numeroRepository.selectMetricasNumero();
		em.close();
		ArrayList<BusinessMetricasNumero> ret = new ArrayList<BusinessMetricasNumero>();
		for (MetricasNumero mn : lista) {
			BusinessMetricasNumero bmn = new BusinessMetricasNumero(mn.getInternalId(), mn.getExternalId(), mn.getEstado(), mn.getCodigoTramite(), mn.getRutaSector(), mn.getUsuarioAtencion(), mn.getResultadoFinal(), mn.getLastUpdated(), mn.getDateCreated());
			ret.add(bmn);
		}
		return ret;
	}
	
	public BusinessMetricasNumero listarMetricasDeNumero(Integer id) throws RollbackException{
		EntityManager em = EMFactory.getEntityManager();
		DAONumero numeroRepository = factory.getNumeroRepository(em);
		
		MetricasNumero mn = numeroRepository.selectMetricasDeNumero(id);
		em.close();
		BusinessMetricasNumero bmn = new BusinessMetricasNumero(mn.getInternalId(), mn.getExternalId(), mn.getEstado(), mn.getCodigoTramite(), mn.getRutaSector(), mn.getUsuarioAtencion(), mn.getResultadoFinal(), mn.getLastUpdated(), mn.getDateCreated());
		return bmn;
	}

}