
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
import com.sarp.dao.controllers.DAOPuestoController;
import com.sarp.dao.factory.DAOServiceFactory;
import com.sarp.enumerados.EstadoPuesto;
import com.sarp.exceptions.ContextException;
import com.sarp.factory.Factory;
import com.sarp.json.modeler.JSONNumero;
import com.sarp.json.modeler.JSONPuesto;
import com.sarp.managers.QueuesManager;
import com.sarp.service.response.maker.RequestMaker;
import com.sarp.service.response.maker.ResponseMaker;

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
		RequestMaker reqMaker = RequestMaker.getInstance();
		
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
		RequestMaker reqMaker = RequestMaker.getInstance();
		BusinessPuesto bPuesto = reqMaker.requestPuesto(puesto);
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
		//RequestMaker reqMaker = RequestMaker.getInstance();
		ResponseMaker respMaker = ResponseMaker.getInstance();

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
					
					//Traigo los tramites que puede atender el puesto para ese Sector
					ArrayList<BusinessTramite> tramitesSectorEnPuesto = controladorPuesto.obtenerTramitesDeSector(puestoSend.getNombreMaquina(), randomSector.getSectorId());
					
					//Pido un numero a la cola del sector para un tramite que pueda atender 
				
					numeroReturn =queueController.llamarProximoNumero(randomSector.getSectorId(), tramitesSectorEnPuesto) ; 
					//Si me da null es porque no hay ningun numero que pueda atender en este momento
					if ( numeroReturn != null){
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
					
					//llamo al display
					DisplayService dispService = DisplayService.getInstance();
					dispService.llamarEnDisplay( puestoSend.getNumeroPuesto().toString(), numeroReturn);
					
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

	public void atrasarNumero(JSONPuesto puesto) throws Exception {

		DAOServiceFactory daoServiceFactory = DAOServiceFactory.getInstance();
		DAOPuestoController controladorPuesto = daoServiceFactory.getDAOPuestoController();

		// Traigo el puesto desde la base
		BusinessPuesto puestoSend = controladorPuesto.obtenerPuesto(puesto.getNombreMaquina());
		if (puestoSend.getEstado() == EstadoPuesto.LLAMANDO) {
			// Traigo el numero que se esta atendiendo desde la base
			BusinessNumero numeroActual = controladorPuesto.obtenerNumeroActualPuesto(puestoSend.getNombreMaquina());
			QueuesManager managerQueues = QueuesManager.getInstance();

			// Pido el manejador de la cola del sector
			BusinessSectorQueue colaSector = managerQueues.obtenerColaSector(numeroActual.getCodSector());

			// Atraso el numero
			colaSector.agregarNumeroAtrasado(numeroActual);

			// Modifico el estado del puesto
			puestoSend.setEstado(EstadoPuesto.DISPONIBLE);
			controladorPuesto.modificarPuesto(puestoSend);
			// falta crear la operacion siguiente q desasocia el nro del puesto
			//controladorPuesto.removerNumeroActual(puestoSend.getNombreMaquina());

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
			QueuesManager managerQueues = QueuesManager.getInstance();

			// Pido el manejador de la cola del sector			
			BusinessSectorQueue colaSector = managerQueues.obtenerColaSector(numeroActual.getCodSector());

			// Atraso el numero
			colaSector.agregarNumeroAtrasado(numeroActual);

			// Modifico el estado del puesto
			puestoSend.setEstado(EstadoPuesto.DISPONIBLE);
			// falta esto igual q caso atrasar
			// controladorPuesto.removerNumeroActual(puestoSend.getNombreMaquina());
			controladorPuesto.modificarPuesto(puestoSend);
		} else {
			throw new ContextException("El puesto no se encuentra en estado ATENDIENDO");
		}

	}

}