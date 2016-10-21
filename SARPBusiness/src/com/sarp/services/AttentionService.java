
package com.sarp.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import com.sarp.classes.BusinessPuesto;
import com.sarp.classes.BusinessNumero;
import com.sarp.classes.BusinessSector;
import com.sarp.classes.BusinessSectorQueue;
import com.sarp.classes.BusinessTramite;
import com.sarp.controllers.QueueController;
import com.sarp.dao.controllers.DAONumeroController;
import com.sarp.dao.controllers.DAOPuestoController;
import com.sarp.dao.factory.DAOServiceFactory;
import com.sarp.enumerados.EstadoPuesto;
import com.sarp.exceptions.ContextException;
import com.sarp.factory.Factory;
import com.sarp.json.modeler.JSONNumero;
import com.sarp.json.modeler.JSONPuesto;
import com.sarp.json.modeler.JSONTramiteSector;
import com.sarp.managers.QueuesManager;


public class AttentionService {

	public void abrirPuesto(JSONPuesto puesto) throws Exception {

		DAOServiceFactory daoServiceFactory = DAOServiceFactory.getInstance();
		DAOPuestoController controladorPuesto = daoServiceFactory.getDAOPuestoController();
		BusinessPuesto bPuesto = controladorPuesto.obtenerPuesto(puesto.getNombreMaquina());

		if (bPuesto.getEstado() == EstadoPuesto.CERRADO && puesto.getUsuarioId() != null) {
			bPuesto.setEstado(EstadoPuesto.DISPONIBLE);
			bPuesto.setUsuarioId(puesto.getUsuarioId());
			// Se delega a DaoService
			controladorPuesto.modificarPuesto(bPuesto);
		} else {
			throw new ContextException("YaAbierto");
		}
	}

	public void cerrarPuesto(JSONPuesto puesto) throws Exception {
		DAOServiceFactory daoServiceFactory = DAOServiceFactory.getInstance();
		DAOPuestoController controladorPuesto = daoServiceFactory.getDAOPuestoController();
		BusinessPuesto bPuesto = controladorPuesto.obtenerPuesto(puesto.getNombreMaquina());

		if (bPuesto.getEstado() != EstadoPuesto.CERRADO && bPuesto.getEstado() != EstadoPuesto.ATENDIENDO) {
			bPuesto.setEstado(EstadoPuesto.CERRADO);
			bPuesto.setUsuarioId(null);
			// Se delega a DaoService
			controladorPuesto.modificarPuesto(bPuesto);
		} else {
			throw new ContextException("YaCerrado");

		}
	}

	public void comenzarAtencion(JSONPuesto puesto) throws Exception {

		DAOServiceFactory daoServiceFactory = DAOServiceFactory.getInstance();
		DAOPuestoController controladorPuesto = daoServiceFactory.getDAOPuestoController();
		BusinessPuesto puestoSend = controladorPuesto.obtenerPuesto(puesto.getNombreMaquina());

		BusinessNumero bNumero = controladorPuesto.obtenerNumeroActualPuesto(puesto.getNombreMaquina());

		if (puestoSend.getEstado() == EstadoPuesto.LLAMANDO) {
			if (bNumero != null) {
				puestoSend.setEstado(EstadoPuesto.ATENDIENDO);
				// Se delega a DaoService
				controladorPuesto.modificarPuesto(puestoSend);
				
			} else {
				throw new ContextException("PuestoSinNumeroAsignado");
			}
		} else {
			throw new ContextException("PuestoNoLlamando");
		}
	}

	public void finalizarAtencion(JSONPuesto puesto) throws Exception {
		DAOServiceFactory daoServiceFactory = DAOServiceFactory.getInstance();
		DAOPuestoController controladorPuesto = daoServiceFactory.getDAOPuestoController();
		BusinessPuesto puestoSend = controladorPuesto.obtenerPuesto(puesto.getNombreMaquina());

		if (puestoSend.getEstado() == EstadoPuesto.ATENDIENDO) {
			puestoSend.setEstado(EstadoPuesto.DISPONIBLE);
			// Se delega a DaoService
			controladorPuesto.modificarPuesto(puestoSend);
			BusinessNumero bNumero = controladorPuesto.obtenerNumeroActualPuesto(puestoSend.getNombreMaquina());
			controladorPuesto.desasociarNumeroPuestoActual(puestoSend.getNombreMaquina());
			controladorPuesto.asociarNumeroPuesto(puestoSend.getNombreMaquina(), bNumero.getInternalId());

		} else {
			throw new ContextException("PuestoNoAtendiendo");
		}

	}

	public JSONNumero llamarNumero(String puesto) throws Exception {

		// RequestMaker reqMaker = RequestMaker.getInstance();

		DAOServiceFactory daoServiceFactory = DAOServiceFactory.getInstance();
		DAOPuestoController controladorPuesto = daoServiceFactory.getDAOPuestoController();
		
		// Traigo el puesto desde la base
		BusinessPuesto puestoSend = controladorPuesto.obtenerPuesto(puesto);
		if (puestoSend.getEstado() == EstadoPuesto.DISPONIBLE) {

			// Traigo todos los sectores del puesto
			List<BusinessSector> sectoresPuesto = controladorPuesto
					.obtenerSectoresPuesto(puestoSend.getNombreMaquina());

			// Si el puesto tiene asignado algun sector procedo procedo con la
			// obtencion de un numero
			if (sectoresPuesto.size() > 0) {

				boolean encontreNum = false;
				Factory factory = Factory.getInstance();
				QueueController queueController = factory.getQueueController();
				JSONNumero numeroReturn = null;

				while (!encontreNum && sectoresPuesto.size() != 0) {
					// Selecciono un sector al azar de los sectore posibles que
					// va a ser de donde llame un numero
					int randomNum = ThreadLocalRandom.current().nextInt(0, sectoresPuesto.size());
					BusinessSector randomSector = sectoresPuesto.get(randomNum);

					// Traigo los tramites que puede atender el puesto para ese
					// Sector
					ArrayList<BusinessTramite> tramitesSectorEnPuesto = controladorPuesto
							.obtenerTramitesDeSector(puestoSend.getNombreMaquina(), randomSector.getSectorId());

					// Pido un numero a la cola del sector para un tramite que
					// pueda atender

					numeroReturn = queueController.llamarProximoNumero(randomSector.getSectorId(),
							tramitesSectorEnPuesto);
					// Si me da null es porque no hay ningun numero que pueda
					// atender en este momento
					if (numeroReturn != null) {
						encontreNum = true;
					} else {
						// quito el sector de la cola de posibles sectores de
						// los cuales voy a pedir numero
						// para luego volver a elegir un sector al azar de la
						// cola restante
						sectoresPuesto.remove(randomNum);
					}
				} // end-while

				// Salgo porque encontre numero o porque no puedo atender ningun
				// tramite
				if (numeroReturn != null) {
					puestoSend.setEstado(EstadoPuesto.LLAMANDO);
					controladorPuesto.modificarPuesto(puestoSend);
					controladorPuesto.asociarNumeroPuestoActual(puestoSend.getNombreMaquina(), numeroReturn.getId());

					// llamo al display
					DisplayService dispService = DisplayService.getInstance();
					dispService.llamarEnDisplay(puestoSend.getNumeroPuesto().toString(), numeroReturn);

					return numeroReturn;
				} else {
					return null;
				}

			} else {
				throw new Exception("No hay Tramites/Sectores asignados al Puesto");
			}
		} else {
			throw new ContextException("El puesto no se encuentra en estado DISPONIBLE");
		}

	}

	public List<JSONTramiteSector> tramitesRecepcion(String puesto) throws Exception {

		DAOServiceFactory daoServiceFactory = DAOServiceFactory.getInstance();
		DAOPuestoController controladorPuesto = daoServiceFactory.getDAOPuestoController();
		
		List<JSONTramiteSector> tramitesRecepcion = new ArrayList<JSONTramiteSector>();
		
		// Traigo el puesto desde la base
		BusinessPuesto puestoSend = controladorPuesto.obtenerPuesto(puesto);
		
			// Traigo todos los sectores del puesto
			List<BusinessSector> sectoresPuesto = controladorPuesto
					.obtenerSectoresPuesto(puestoSend.getNombreMaquina());

			// Si el puesto tiene asignado algun sector traigo sus tramites
			if (sectoresPuesto.size() > 0) {
				
				for(BusinessSector sector : sectoresPuesto){
					
					ArrayList<BusinessTramite> tramitesSectorEnPuesto = controladorPuesto
							.obtenerTramitesDeSector(puestoSend.getNombreMaquina(), sector.getSectorId());
					
					for(BusinessTramite tramite : tramitesSectorEnPuesto){
						
						JSONTramiteSector tramSec = new JSONTramiteSector();
						tramSec.setSectorId(sector.getSectorId());
						tramSec.setSectorNombre(sector.getNombre());
						tramSec.setTramiteId(tramite.getCodigo());
						tramSec.setTramiteNombre(tramite.getNombre());
						
						tramitesRecepcion.add(tramSec);
						
					}
					
				}
			}
			
			return tramitesRecepcion;

	}

	public void atrasarNumero(JSONPuesto puesto) throws Exception {

		DAOServiceFactory daoServiceFactory = DAOServiceFactory.getInstance();
		DAOPuestoController controladorPuesto = daoServiceFactory.getDAOPuestoController();

		// Traigo el puesto desde la base
		BusinessPuesto puestoSend = controladorPuesto.obtenerPuesto(puesto.getNombreMaquina());
		if (puestoSend.getEstado() == EstadoPuesto.LLAMANDO) {
			// Traigo el numero que se esta atendiendo desde la base
			BusinessNumero numeroActual = controladorPuesto.obtenerNumeroActualPuesto(puestoSend.getNombreMaquina());
			
			//se atrasa el numero
			Factory fac = Factory.getInstance();
			QueueController ctrl = fac.getQueueController();
			ctrl.transferirAColaAtrasados(numeroActual.getCodSector(), numeroActual);
			numeroActual.setEstado("ATRASADO");
			
			// Modifico el estado del puesto
			puestoSend.setEstado(EstadoPuesto.DISPONIBLE);
			
			controladorPuesto.modificarPuesto(puestoSend);
			controladorPuesto.desasociarNumeroPuestoActual(puestoSend.getNombreMaquina());

		} else {
			throw new ContextException("El puesto no se encuentra en estado LLAMANDO");
		}

	}

	public void pausarNumero(JSONPuesto puesto) throws Exception {

		DAOServiceFactory daoServiceFactory = DAOServiceFactory.getInstance();
		DAOPuestoController controladorPuesto = daoServiceFactory.getDAOPuestoController();

		// Traigo el puesto desde la base
		BusinessPuesto puestoSend = controladorPuesto.obtenerPuesto(puesto.getNombreMaquina());
		if (puestoSend.getEstado() == EstadoPuesto.ATENDIENDO) {
			// Traigo el numero que se esta atendiendo desde la base
			BusinessNumero numeroActual = controladorPuesto.obtenerNumeroActualPuesto(puestoSend.getNombreMaquina());
			
			//se pausa el numero
			Factory fac = Factory.getInstance();
			QueueController ctrl = fac.getQueueController();
			ctrl.transferirAColaPausados(numeroActual.getCodSector(), numeroActual);
			numeroActual.setEstado("PAUSADO");
			
			// Modifico el estado del puesto
			puestoSend.setEstado(EstadoPuesto.DISPONIBLE);

			controladorPuesto.modificarPuesto(puestoSend);
			controladorPuesto.desasociarNumeroPuestoActual(puestoSend.getNombreMaquina());
		} else {
			throw new ContextException("El puesto no se encuentra en estado ATENDIENDO");
		}
	}
	
	public JSONNumero llamarNumeroPausado(Integer idNumero, String idPuesto) throws Exception {
		DAOServiceFactory fac = DAOServiceFactory.getInstance();
		DAONumeroController ctrlNumero = fac.getDAONumeroController();
		DAOPuestoController ctrlPuesto = fac.getDAOPuestoController();
		Factory facBusiness = Factory.getInstance();
		QueueController ctrlQueue = facBusiness.getQueueController();
		BusinessNumero num = ctrlNumero.obtenerNumero(idNumero);
		ctrlPuesto.asociarNumeroPuestoActual(idPuesto, idNumero);
		return ctrlQueue.obtenerNumeroPausado(num.getCodSector(), idNumero);
	}
	
	public JSONNumero llamarNumeroAtrasado(Integer idNumero, String idPuesto) throws Exception {
		DAOServiceFactory fac = DAOServiceFactory.getInstance();
		DAONumeroController ctrlNumero = fac.getDAONumeroController();
		DAOPuestoController ctrlPuesto = fac.getDAOPuestoController();
		Factory facBusiness = Factory.getInstance();
		QueueController ctrlQueue = facBusiness.getQueueController();
		BusinessNumero num = ctrlNumero.obtenerNumero(idNumero);
		ctrlPuesto.asociarNumeroPuestoActual(idPuesto, idNumero);
		return ctrlQueue.obtenerNumeroAtrasado(num.getCodSector(), idNumero);
	}

}