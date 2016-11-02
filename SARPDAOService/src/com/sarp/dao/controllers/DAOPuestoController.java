package com.sarp.dao.controllers;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import com.sarp.classes.BusinessMetricasPuesto;
import com.sarp.classes.BusinessNumero;
import com.sarp.classes.BusinessPuesto;
import com.sarp.classes.BusinessSector;
import com.sarp.classes.BusinessTramite;
import com.sarp.dao.factory.DAOFactory;
import com.sarp.dao.factory.EMFactory;
import com.sarp.dao.model.MetricasPuesto;
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
			throw new RollbackException("El nombre de maquina del puesto no puede ser vacio");
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
		ret.setLastUpdated(p.getLastUpdated());
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

	public ArrayList<BusinessPuesto> listarPuestos() {
		EntityManager em = EMFactory.getEntityManager();
		DAOPuesto puestoRepository = factory.getPuestoRepository(em);

		ArrayList<Puesto> lista = puestoRepository.selectPuestos();
		em.close();
		ArrayList<BusinessPuesto> ret = new ArrayList<BusinessPuesto>();
		for (Puesto p : lista) {
			BusinessPuesto bp = new BusinessPuesto(p.getNombreMaquina(), p.getUsuarioId(), p.getEstado(), p.getNumero());
			bp.setLastUpdated(p.getLastUpdated());
			ret.add(bp);
		}
		return ret;
	}

	public void modificarPuesto(BusinessPuesto puesto) throws RollbackException {
		EntityManager em = EMFactory.getEntityManager();
		DAOPuesto puestoRepository = factory.getPuestoRepository(em);

		em.getTransaction().begin();
		puestoRepository.updatePuesto(puesto.getNombreMaquina(), puesto.getEstado() != null ? puesto.getEstado().toString() : "", puesto.getUsuarioId(),
				puesto.getNumeroPuesto(),puesto.getLastUpdated());

		em.getTransaction().commit();
		em.close();
	}

	public ArrayList<BusinessSector> obtenerSectoresPuesto(String nombreMaquina) throws RollbackException {
		EntityManager em = EMFactory.getEntityManager();
		DAOPuesto puestoRepository = factory.getPuestoRepository(em);

		Puesto p = puestoRepository.selectPuesto(nombreMaquina);
		em.close();
		ArrayList<Sector> list = new ArrayList<Sector>(p.getSectors());
		ArrayList<BusinessSector> ret = new ArrayList<BusinessSector>();
		for (Sector s : list) {
			if(s.isHabilitado()){
				BusinessSector bs = new BusinessSector(s.getCodigo(), s.getNombre(), s.getRutaSector());
				bs.setLastUpdated(s.getLastUpdated());
				ret.add(bs);
			}
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
		if(p.getNumero_puesto() == n){
			em.close();
			throw new RollbackException("El numero actual del puesto " + nombreMaquina + " ya es el numero con id " + codigoNumero);
		}
		em.getTransaction().begin();
		if(n.getPuesto() != null){
			numeroRepository.desasociarNumeroPuestoActual(n, n.getPuesto());
		}
		if(p.getNumero_puesto() != null){
			numeroRepository.desasociarNumeroPuestoActual(p.getNumero_puesto(), p);
		}
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

	public ArrayList<BusinessNumero> obtenerNumerosPuesto(String nombreMaquina) throws RollbackException {
		EntityManager em = EMFactory.getEntityManager();
		DAOPuesto puestoRepository = factory.getPuestoRepository(em);

		Puesto p = puestoRepository.selectPuesto(nombreMaquina);
		em.close();
		ArrayList<Numero> list = new ArrayList<Numero>(p.getNumeros());
		ArrayList<BusinessNumero> ret = new ArrayList<BusinessNumero>();
		for (Numero n : list) {
			BusinessNumero res = new BusinessNumero(n.getInternalId(), n.getExternalId(), n.getHora(), n.getEstado(),n.getPrioridad(), n.getCodigoTramite(), n.getCodigoSector(), n.isFueAtrasado());
			res.setLastUpdated(n.getLastUpdated());
			ret.add(res);
		}
		return ret;
	}

	public ArrayList<BusinessTramite> obtenerTramitesPuesto(String nombreMaquina) throws RollbackException {
		EntityManager em = EMFactory.getEntityManager();
		DAOPuesto puestoRepository = factory.getPuestoRepository(em);

		Puesto p = puestoRepository.selectPuesto(nombreMaquina);
		em.close();
		ArrayList<Tramite> list = new ArrayList<Tramite>(p.getTramites());
		ArrayList<BusinessTramite> ret = new ArrayList<BusinessTramite>();
		for (Tramite t : list) {
			BusinessTramite bt = new BusinessTramite(t.getCodigo(), t.getNombre());
			bt.setLastUpdated(t.getLastUpdated());
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
			BusinessNumero res = new BusinessNumero(n.getInternalId(), n.getExternalId(), n.getHora(), n.getEstado(),n.getPrioridad(),n.getCodigoTramite(), n.getCodigoSector(),n.isFueAtrasado());
			res.setLastUpdated(n.getLastUpdated());
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
			ArrayList<Tramite> list = new ArrayList<Tramite>(s.getTramites());
			list.retainAll(p.getTramites());		
			
			ArrayList<BusinessTramite> ret = new ArrayList<BusinessTramite>();
			for (Tramite t : list){
				BusinessTramite dt = new BusinessTramite(t.getCodigo(), t.getNombre());
				dt.setLastUpdated(t.getLastUpdated());
				ret.add(dt);
			}	
			return ret;
		}	
	}
	
	/* OBTENER METRICAS */
	public ArrayList<BusinessMetricasPuesto> listarMetricasDePuestos(String nombreMaquina) throws RollbackException {
		EntityManager em = EMFactory.getEntityManager();
		DAOPuesto puestoRepository = factory.getPuestoRepository(em);

		ArrayList<MetricasPuesto> lista = puestoRepository.selectMetricasDePuesto(nombreMaquina);
		em.close();
		ArrayList<BusinessMetricasPuesto> ret = new ArrayList<BusinessMetricasPuesto>();
		for (MetricasPuesto mp : lista) {
			BusinessMetricasPuesto bmp = new BusinessMetricasPuesto(mp.getId().getNombreMaquina(), mp.getId().getUsuarioAtencion(), mp.getId().getEstado(), mp.getTimeSpent(), mp.getLastUpdated(), mp.getId().getDateCreated());
			if(mp.getTimeSpent() == null){
				GregorianCalendar c1 = new GregorianCalendar();				
				GregorianCalendar c2 = mp.getLastUpdated();
				long diff = c1.getTimeInMillis() - c2.getTimeInMillis();
				long diffSeconds = diff / 1000 % 60;
				long diffMinutes = diff / (60 * 1000) % 60;
				long diffHours = diff / (60 * 60 * 1000) % 24;
				String s = String.format("%02d", diffHours)+":"+String.format("%02d", diffMinutes)+":"+String.format("%02d", diffSeconds);
		        bmp.setTimeSpent(s);
			}else{
				bmp.setTimeSpent(mp.getTimeSpent().substring(0,8));
			}
			ret.add(bmp);
		}
		return ret;
	}

	public ArrayList<BusinessMetricasPuesto> listarMetricasPuestos() {
		EntityManager em = EMFactory.getEntityManager();
		DAOPuesto puestoRepository = factory.getPuestoRepository(em);

		ArrayList<MetricasPuesto> lista = puestoRepository.selectMetricasPuestos();
		em.close();
		ArrayList<BusinessMetricasPuesto> ret = new ArrayList<BusinessMetricasPuesto>();
		for (MetricasPuesto mp : lista) {
			BusinessMetricasPuesto bmp = new BusinessMetricasPuesto(mp.getId().getNombreMaquina(), mp.getId().getUsuarioAtencion(), mp.getId().getEstado(), mp.getTimeSpent(), mp.getLastUpdated(), mp.getId().getDateCreated());			
			if(mp.getTimeSpent() == null){
				GregorianCalendar c1 = new GregorianCalendar();				
				GregorianCalendar c2 = mp.getLastUpdated();
				long diff = c1.getTimeInMillis() - c2.getTimeInMillis();
				long diffSeconds = diff / 1000 % 60;
				long diffMinutes = diff / (60 * 1000) % 60;
				long diffHours = diff / (60 * 60 * 1000) % 24;
				String s = String.format("%02d", diffHours)+":"+String.format("%02d", diffMinutes)+":"+String.format("%02d", diffSeconds);
		        bmp.setTimeSpent(s);
			}else{
				bmp.setTimeSpent(mp.getTimeSpent().substring(0,8));
			}
			ret.add(bmp);
		}
		return ret;
	}

}