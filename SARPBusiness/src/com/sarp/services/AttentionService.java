package com.sarp.services;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.sarp.classes.BusinessPuesto;
import com.sarp.classes.BusinessNumero;
import com.sarp.classes.BusinessSector;
import com.sarp.classes.BusinessSectorQueue;
import com.sarp.classes.BusinessTramite;
import com.sarp.dao.controllers.DAONumeroController;
import com.sarp.dao.controllers.DAOPuestoController;
import com.sarp.dao.factory.DAOFactory;
import com.sarp.dao.factory.DAOServiceFactory;
import com.sarp.enumerados.EstadoPuesto;
import com.sarp.exceptions.ContextException;
import com.sarp.factory.Factory;
import com.sarp.json.modeler.JSONNumero;
import com.sarp.json.modeler.JSONPuesto;
import com.sarp.json.modeler.JSONSector;
import com.sarp.json.modeler.JSONTramite;
import com.sarp.managers.QueuesManager;
import com.sarp.service.response.maker.RequestMaker;
import com.sarp.service.response.maker.ResponseMaker;

public class AttentionService {

	public void abrirPuesto(JSONPuesto puesto) throws Exception{

		DAOServiceFactory daoServiceFactory = DAOServiceFactory.getInstance();
		DAOPuestoController controladorPuesto = daoServiceFactory.getDAOPuestoController();
		BusinessPuesto bPuesto = controladorPuesto.obtenerPuesto(puesto.getNombreMaquina());

		if(bPuesto.getEstado() == EstadoPuesto.CERRADO && puesto.getUsuarioId()!= null){
			bPuesto.setEstado(EstadoPuesto.DIPONIBLE);
			bPuesto.setUsuarioId(puesto.getUsuarioId());
			//Se delega a DaoService
			controladorPuesto.modificarPuesto(bPuesto);
		}else{
			throw new ContextException("YaAbierto");
		}
	}

	public void cerrarPuesto(JSONPuesto puesto) throws Exception{
		DAOServiceFactory daoServiceFactory = DAOServiceFactory.getInstance();
		DAOPuestoController controladorPuesto = daoServiceFactory.getDAOPuestoController();
		BusinessPuesto bPuesto = controladorPuesto.obtenerPuesto(puesto.getNombreMaquina());

		if(bPuesto.getEstado() != EstadoPuesto.CERRADO){
			bPuesto.setEstado(EstadoPuesto.DIPONIBLE);
			bPuesto.setUsuarioId(null);
			//Se delega a DaoService
			controladorPuesto.modificarPuesto(bPuesto);
		}else{
			throw new ContextException("YaCerrado");

		}
	}

	public void comenzarAtencion(JSONPuesto puesto) throws Exception{
		RequestMaker reqMaker = RequestMaker.getInstance();
		BusinessNumero bNumero = reqMaker.requestNumero(puesto.getNumeroAsignado());

		DAOServiceFactory daoServiceFactory = DAOServiceFactory.getInstance();
		DAOPuestoController controladorPuesto = daoServiceFactory.getDAOPuestoController();
		BusinessPuesto puestoSend = controladorPuesto.obtenerPuesto(puesto.getNombreMaquina());
		if(puestoSend.getEstado() == EstadoPuesto.LLAMANDO){
			if(puesto.getNumeroAsignado() != null){
				puestoSend.setEstado(EstadoPuesto.ATENDIENDO);
				//Se delega a DaoService
				controladorPuesto.modificarPuesto(puestoSend);
				controladorPuesto.asociarNumeroPuestoActual(puestoSend.getNombreMaquina(),bNumero.getInternalId());

			}else{
				throw new ContextException("PuestoSinNumeroAsignado");
			}
		}else{
			throw new ContextException("PuestoNoLlamando");
		}
	}

	public void finalizarAtencion(JSONPuesto puesto) throws Exception{
		RequestMaker reqMaker = RequestMaker.getInstance();
		BusinessPuesto bPuesto = reqMaker.requestPuesto(puesto);
		DAOServiceFactory daoServiceFactory = DAOServiceFactory.getInstance();
		DAOPuestoController controladorPuesto = daoServiceFactory.getDAOPuestoController();
		BusinessPuesto puestoSend = controladorPuesto.obtenerPuesto(puesto.getNombreMaquina());
		if(puestoSend.getEstado() == EstadoPuesto.ATENDIENDO){
			puestoSend.setEstado(EstadoPuesto.DIPONIBLE);
			//Se delega a DaoService
			controladorPuesto.modificarPuesto(bPuesto);
			controladorPuesto.desasociarNumeroPuestoActual(puestoSend.getNombreMaquina());
		}else{
			throw new ContextException("PuestoNoAtendiendo");
		}

	}
	
	public JSONNumero llamarNumero(JSONPuesto puesto) throws Exception{
		RequestMaker reqMaker = RequestMaker.getInstance();
		ResponseMaker respMaker = ResponseMaker.getInstance();
		
		DAOServiceFactory daoServiceFactory = DAOServiceFactory.getInstance();
		DAOPuestoController controladorPuesto = daoServiceFactory.getDAOPuestoController();
		
		//Traigo el puesto desde la base
		BusinessPuesto puestoSend = controladorPuesto.obtenerPuesto(puesto.getNombreMaquina());
		if(puestoSend.getEstado() == EstadoPuesto.DIPONIBLE){

			//Traigo todos los sectores del puesto
			List<BusinessSector> sectoresPuesto = controladorPuesto.obtenerSectoresPuesto(puestoSend.getNombreMaquina());
			
			//Para cada uno de esos sectores necesito los tramites que atiende el puesto
			List<JSONSector> sectoresReturn = new LinkedList<JSONSector>();
			
			for (BusinessSector sector : sectoresPuesto) {
				List<BusinessTramite> tramitesSectorEnPuesto = null;//controladorPuesto.tramitesPuestoSector(puestoSend.getNombreMaquina(),sector.getSectorId());
				JSONSector sectorPuesto = respMaker.sectorFullResponse(sector, tramitesSectorEnPuesto, null, null);
				sectoresReturn.add(sectorPuesto);
			} 
			//Si tengo sectores con tramites asignados al puesto
			if(sectoresReturn.size() > 0){
				boolean encontreNum = false;
				QueuesManager managerQueues = QueuesManager.getInstance();
				BusinessNumero numeroReturn = null;
				while(!encontreNum && sectoresReturn.size()!= 0){
					//Selecciono un sector al azar de los sectore posibles que va a ser de donde llame un numero
					int randomNum = ThreadLocalRandom.current().nextInt(1, sectoresReturn.size() + 1);
					JSONSector randomSector = sectoresReturn.get(randomNum);
					
					//Pido el manejador de la cola del sector
					BusinessSectorQueue colaSector =null;// managerQueues.obtenerColaSector(randomSector.getCodigo());
					
					//Pido numero a la cola con los tramites que puede realizar el puesto para ese sector
					//Me pueden retornar un numero, null (en caso de que no haya numero que puede atender)
					//O una excepcion en caso de que haya reservado un numero para atender luego
					numeroReturn = null;//colaSector.llamarNumeroCola(randomSector.getTramites());
					
					if(true){//numero != null){
						encontreNum = true;
					}else{
						//quito el sector de la cola de posibles sectores de los cuales voy a pedir numero
						//para luego volver a elegir un sector al azar de la cola restante
						sectoresReturn.remove(randomNum);
					}
				}
				//Salgo porque encontre numero o porque no puedo atender ningun tramite
				if(numeroReturn!=null){
					puestoSend.setEstado(EstadoPuesto.LLAMANDO);
					controladorPuesto.modificarPuesto(puestoSend);
					//controladorPuesto.asignarNumeroPuesto(puestoSend.getNombreMaquina(),numeroReturn.getInternalId());
					//ACA TENGO QUE COMUNICARME CON LA PANTALLA PARA ASIGNARLE EL NUMERO
					//LlamarNumero(numeroReturn.getInternalId())
					JSONNumero num = respMaker.numeroAtomResponse(numeroReturn);
					return num;
				}else{
					return null;
				}
				
			}else{
				throw new Exception("No hay Tramites/Sectores asignados al Puesto");
			}
		}else{
			throw new ContextException("El puesto no se encuentra en estado DISPONIBLE");
		}
		
	
	}
	
	public void atrasarNumero(JSONPuesto puesto) throws Exception{
		
		DAOServiceFactory daoServiceFactory = DAOServiceFactory.getInstance();
		DAOPuestoController controladorPuesto = daoServiceFactory.getDAOPuestoController();
		DAONumeroController controladorNumero = daoServiceFactory.getDAONumeroController();
		
		//Traigo el puesto desde la base
		BusinessPuesto puestoSend = controladorPuesto.obtenerPuesto(puesto.getNombreMaquina());
		if(puestoSend.getEstado() == EstadoPuesto.LLAMANDO){
			//Traigo el numero que se esta atendiendo desde la base
			BusinessNumero numeroActual = controladorPuesto.obtenerNumeroActualPuesto(puestoSend.getNombreMaquina());
			QueuesManager managerQueues = QueuesManager.getInstance();
			
			//Pido el manejador de la cola del sector
			BusinessSector sectorNumero = null;//controladorNumero.obtenerSectorNumero(numeroActual.getInternalId());
			BusinessSectorQueue colaSector = null;//managerQueues.obtenerColaSector(sectorNumero.getSectorId());
			
			//Atraso el numero
			colaSector.agregarNumeroAtrasado(numeroActual);	
			
			//Modifico el estado del puesto
			puestoSend.setEstado(EstadoPuesto.DIPONIBLE);
			controladorPuesto.modificarPuesto(puestoSend);
			//controladorPuesto.removerNumeroActual(puestoSend.getNombreMaquina());
			
		}else{
			throw new ContextException("El puesto no se encuentra en estado LLAMANDO");
		}
	
	}
	public void pausarNumero(JSONPuesto puesto) throws Exception{
		
		DAOServiceFactory daoServiceFactory = DAOServiceFactory.getInstance();
		DAOPuestoController controladorPuesto = daoServiceFactory.getDAOPuestoController();
		DAONumeroController controladorNumero = daoServiceFactory.getDAONumeroController();
		
		//Traigo el puesto desde la base
		BusinessPuesto puestoSend = controladorPuesto.obtenerPuesto(puesto.getNombreMaquina());
		if(puestoSend.getEstado() == EstadoPuesto.ATENDIENDO){
			//Traigo el numero que se esta atendiendo desde la base
			BusinessNumero numeroActual = controladorPuesto.obtenerNumeroActualPuesto(puestoSend.getNombreMaquina());
			QueuesManager managerQueues = QueuesManager.getInstance();
			
			//Pido el manejador de la cola del sector
			BusinessSector sectorNumero = null;//controladorNumero.obtenerSectorNumero(numeroActual.getInternalId());
			BusinessSectorQueue colaSector =null;// managerQueues.obtenerColaSector(sectorNumero.getSectorId());
			
			//Atraso el numero
			colaSector.agregarNumeroAtrasado(numeroActual);	
			
			//Modifico el estado del puesto
			puestoSend.setEstado(EstadoPuesto.DIPONIBLE);
			//controladorPuesto.removerNumeroActual(puestoSend.getNombreMaquina());
			controladorPuesto.modificarPuesto(puestoSend);
		}else{
			throw new ContextException("El puesto no se encuentra en estado ATENDIENDO");
		}
	
	}
	
	

	
	
}
