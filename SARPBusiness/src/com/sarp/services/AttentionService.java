
package com.sarp.services;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import com.sarp.classes.BusinessPuesto;
import com.sarp.classes.BusinessDatoComplementario;
import com.sarp.classes.BusinessNumero;
import com.sarp.classes.BusinessSector;
import com.sarp.classes.BusinessTramite;
import com.sarp.controllers.QueueController;
import com.sarp.dao.controllers.DAONumeroController;
import com.sarp.dao.controllers.DAOPuestoController;
import com.sarp.dao.controllers.DAOSectorController;
import com.sarp.dao.controllers.DAOTramiteController;
import com.sarp.dao.factory.DAOServiceFactory;
import com.sarp.enumerados.EstadoNumero;
import com.sarp.enumerados.EstadoPuesto;
import com.sarp.exceptions.ContextException;
import com.sarp.factory.Factory;
import com.sarp.json.modeler.JSONDatosComp;
import com.sarp.json.modeler.JSONFinalizarAtencion;
import com.sarp.json.modeler.JSONNumero;
import com.sarp.json.modeler.JSONPuesto;
import com.sarp.json.modeler.JSONTramiteResultado;
import com.sarp.json.modeler.JSONSector;
import com.sarp.json.modeler.JSONTramiteSector;
import com.sarp.managers.BusinessSectorQueue;
import com.sarp.managers.QueuesManager;
import com.sarp.service.response.maker.ResponseMaker;
import com.sarp.service.response.maker.RequestMaker;
import com.sarp.service.response.maker.ResponseMaker;
import com.sarp.utils.DesvioSectoresUtils;



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
				
				// se cambia estado de numero
				bNumero.setEstado(EstadoNumero.ATENDIENDO);
				DAONumeroController daoCtrl = daoServiceFactory.getDAONumeroController();
				daoCtrl.modificarNumero(bNumero);
				
			} else {
				throw new ContextException("PuestoSinNumeroAsignado");
			}
		} else {
			throw new ContextException("PuestoNoLlamando");
		}
	}

	public void finalizarAtencion(JSONFinalizarAtencion finalizarAtencion, boolean esDesvio) throws Exception {
		
		DAOServiceFactory daoServiceFactory = DAOServiceFactory.getInstance();
		DAOPuestoController controladorPuesto = daoServiceFactory.getDAOPuestoController();
		DAONumeroController controladorNumero = daoServiceFactory.getDAONumeroController();
		BusinessPuesto puestoSend = controladorPuesto.obtenerPuesto(finalizarAtencion.getNombreMaquina());
		
		// debe tener resultados de tramite
		if(finalizarAtencion.getTramiteResultado() == null || finalizarAtencion.getTramiteResultado().isEmpty() )
			throw new ContextException("NO_RESULTADOS_TRAMITE");
		
		// debe estar en estado atendiendo
		if (puestoSend.getEstado() != EstadoPuesto.ATENDIENDO) 
			throw new ContextException("PUESTO_NO_ATENDIENDO");
		
		// numero no puede ser null
		if(finalizarAtencion.getId() == null)
			throw new ContextException("NO_ID_NUMERO");
		
		// verifico que numero actual del puesto sea el mismo que el de finalizar
		BusinessNumero bNumero = controladorPuesto.obtenerNumeroActualPuesto(puestoSend.getNombreMaquina());
		if(bNumero.getInternalId().equals(finalizarAtencion.getId()))
			throw new ContextException("NUMERO_ACTUAL_INCONSISTENTE");
		
		//asigno cada resultado-tramite 
		for(JSONTramiteResultado tramiteResultado : finalizarAtencion.getTramiteResultado()){
			controladorNumero.asociarNumeroTramite(tramiteResultado.getCodigo(), finalizarAtencion.getId(), tramiteResultado.getResultadoAtencion());
		}
		
		puestoSend.setEstado(EstadoPuesto.DISPONIBLE);
		// Se delega a DaoService
		controladorPuesto.modificarPuesto(puestoSend);
		controladorPuesto.desasociarNumeroPuestoActual(puestoSend.getNombreMaquina());
		controladorPuesto.asociarNumeroPuesto(puestoSend.getNombreMaquina(), bNumero.getInternalId());
		
		if(!esDesvio){
			//se cambia el estado del numero en caso de no ser desvio
			bNumero.setEstado(EstadoNumero.FINALIZADO);
			controladorNumero.modificarNumero(bNumero);
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
					
					// se cambia el estado del numero en la base
					DAONumeroController daoCtrl = daoServiceFactory.getDAONumeroController();
					BusinessNumero num = daoCtrl.obtenerNumero(numeroReturn.getId());
					num.setEstado(EstadoNumero.LLAMADO);
					daoCtrl.modificarNumero(num);
					
					BusinessDatoComplementario datosComp = daoCtrl.obtenerDatosNumero(num.getInternalId());
					ResponseMaker maker = ResponseMaker.getInstance();
					JSONDatosComp jsonDC = maker.datosComplementariosResponse(datosComp);
					numeroReturn.setDatosComplementarios(jsonDC);
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
			
			if(!numeroActual.isFueAtrasado()){
				ctrl.transferirAColaAtrasados(numeroActual.getCodSector(), numeroActual);
				numeroActual.setFueAtrasado(true);
				numeroActual.setEstado(EstadoNumero.ATRASADO);
			}else{
				numeroActual.setEstado(EstadoNumero.NOATENDIDO);
			}
			DAONumeroController daoCtrl = daoServiceFactory.getDAONumeroController();
			daoCtrl.modificarNumero(numeroActual);
			
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
			
			numeroActual.setEstado(EstadoNumero.PAUSADO);
			DAONumeroController daoCtrl = daoServiceFactory.getDAONumeroController();
			daoCtrl.modificarNumero(numeroActual);
			
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
		
		num.setEstado(EstadoNumero.ATENDIENDO);
		ctrlNumero.modificarNumero(num);
		
		return ctrlQueue.obtenerNumeroPausado(num.getCodSector(), idNumero);
	}
	
	public JSONNumero llamarNumeroAtrasado(Integer idNumero, String idPuesto) throws Exception {
		DAOServiceFactory fac = DAOServiceFactory.getInstance();
		DAONumeroController ctrlNumero = fac.getDAONumeroController();
		DAOPuestoController ctrlPuesto = fac.getDAOPuestoController();
		//fede pijins
		Factory facBusiness = Factory.getInstance();
		QueueController ctrlQueue = facBusiness.getQueueController();
		BusinessNumero num = ctrlNumero.obtenerNumero(idNumero);
		ctrlPuesto.asociarNumeroPuestoActual(idPuesto, idNumero);
		
		num.setEstado(EstadoNumero.ATENDIENDO);
		ctrlNumero.modificarNumero(num);
		
		return ctrlQueue.obtenerNumeroAtrasado(num.getCodSector(), idNumero);
	}
	

	public JSONNumero llamarNumeroDemanda(Integer idNumero, String idPuesto) throws Exception {
		DAOServiceFactory fac = DAOServiceFactory.getInstance();
		DAONumeroController ctrlNumero = fac.getDAONumeroController();
		DAOPuestoController ctrlPuesto = fac.getDAOPuestoController();
		
		Factory facBusiness = Factory.getInstance();
		QueueController ctrlQueue = facBusiness.getQueueController();
		BusinessNumero num = ctrlNumero.obtenerNumero(idNumero);
		ctrlPuesto.asociarNumeroPuestoActual(idPuesto, idNumero);
		
		num.setEstado(EstadoNumero.ATENDIENDO);
		ctrlNumero.modificarNumero(num);
		
		return ctrlQueue.obtenerNumeroDemanda(num.getCodSector(), idNumero);
	}

	public List<JSONSector> obtenerSectoresDesvio(String idSector) throws Exception {
		
		ResponseMaker resMaker = ResponseMaker.getInstance();
		DAOServiceFactory fac = DAOServiceFactory.getInstance();
		DAOSectorController ctrlSector = fac.getDAOSectorController();
		List<BusinessSector> sectoresBusinessReturn = new ArrayList<BusinessSector>();
		
		String desviosSector = DesvioSectoresUtils.getStringProperty(idSector);
		if(desviosSector != null){
			String[] desvios = desviosSector.split(";"); //ATYR4-25MIN
			for(String desvio : desvios){
				try{
			
					String[] sectorHora = desvio.split("-");
					if(sectorHora.length == 2){
						String sectorId = sectorHora[0];
						String sectorHoraSplit  = sectorHora[1].split("MIN")[0];
						Integer minutos = Integer.parseInt(sectorHoraSplit);
						BusinessSector sector = ctrlSector.obtenerSector(sectorId);
						sectoresBusinessReturn.add(sector);
					}else{
						throw new Exception("Sector destino mal configurado en sector origen"+ idSector);
					}
				
					
				}catch(Exception e){
					
					System.out.print(e.getMessage());	
				}
			}
				
		}
		List<JSONSector> sectoresReturn = resMaker.createArrayAtomSectores(sectoresBusinessReturn);
		
		return sectoresReturn;
	}
	
public void desviarNumero(String idSectorDesvio,JSONFinalizarAtencion finalizarAtencion) throws Exception {
		
		// falta vincularlo con los tramites/ resultado fin atencion
		ResponseMaker resMaker = ResponseMaker.getInstance();
		DAOServiceFactory fac = DAOServiceFactory.getInstance();
		DAOSectorController ctrlSector = fac.getDAOSectorController();
		DAOPuestoController ctrlPuesto = fac.getDAOPuestoController();
		DAOTramiteController ctrlTramite = fac.getDAOTramiteController();
		DAONumeroController ctrlNumero = fac.getDAONumeroController();
		
		String idPuesto = finalizarAtencion.getNombreMaquina();
		BusinessNumero numeroActual = ctrlPuesto.obtenerNumeroActualPuesto(idPuesto);
		boolean seDesvio = false;
		if(numeroActual!= null){
			String desviosSector = DesvioSectoresUtils.getStringProperty(numeroActual.getCodSector());
			if(desviosSector!= null){
				String[] desvios = desviosSector.split(";"); //ATYR4-25MIN
				for(String desvio : desvios){
					try{

						String[] sectorHora = desvio.split("-");
						String sectorId = sectorHora[0];
						if(sectorId.equals(idSectorDesvio)){
							if(sectorHora.length == 2){
								String sectorHoraSplit  = sectorHora[1].split("MIN")[0];
								Integer minutos = Integer.parseInt(sectorHoraSplit);
								GregorianCalendar horaActual = new GregorianCalendar();
								System.out.print(horaActual.getTime());

								horaActual.add(GregorianCalendar.MINUTE, minutos);	
								System.out.print(horaActual.getTime());	
								BusinessTramite tramiteGenerico =  ctrlTramite.obtenerTramite("1");//Tramite generico
								if(tramiteGenerico != null){
									
									BusinessNumero numeroDesviado = new BusinessNumero(null, numeroActual.getExternalId(), horaActual, "PENDIENTE",
											numeroActual.getPrioridad(), "1" ,idSectorDesvio,false);
									
									BusinessDatoComplementario datosComp = ctrlNumero.obtenerDatosNumero(numeroActual.getInternalId());
									//BusinessDatoComplementario datosComp = null;
									Integer idNumDesviado =  ctrlNumero.crearNumero(numeroDesviado,datosComp);

									numeroActual.setEstado(EstadoNumero.DESVIADO);
									ctrlNumero.modificarNumero(numeroActual);
									
									ctrlNumero.setearDesvio(numeroActual.getInternalId(),idNumDesviado);
									seDesvio = true;
								}else{
									throw new Exception("Error: no existe en el sistema el numero generico para desviar");
								}
							}else{
								throw new Exception("Sector destino mal configurado en sector origen"+ idSectorDesvio);
							}

							break;
						}
						
					}catch(Exception e){
						
						System.out.print(e.getMessage());	
					}		
				}
				if(!seDesvio){
					throw new Exception("Error: El numero no pudo ser desviado al sector elegido");
				}else{
					finalizarAtencion(finalizarAtencion,true);
				}
			}else{
				throw new Exception("Error: El sector actual no tiene ningun sector configurado al que pueda desviar");
				
			}
		}else{
			throw new Exception("Error: El puesto seleccionado no tiene un numero asignado");
		}
		
	}

	public void reLlamarNumero(String puesto) throws Exception {

		DAOServiceFactory daoServiceFactory = DAOServiceFactory.getInstance();
		DAOPuestoController controladorPuesto = daoServiceFactory.getDAOPuestoController();
		
		// Traigo el puesto desde la base
		BusinessPuesto puestoSend = controladorPuesto.obtenerPuesto(puesto);
		if (puestoSend.getEstado() == EstadoPuesto.LLAMANDO) {
			
				DAONumeroController daoCtrl = daoServiceFactory.getDAONumeroController();
				//Si el puesto tiene numero asociado
				if(puestoSend.getNumeroPuesto()!= null){
					BusinessNumero num = daoCtrl.obtenerNumero(puestoSend.getNumeroPuesto());
					ResponseMaker respMaker = ResponseMaker.getInstance();
					JSONNumero numeroDisplay = respMaker.numeroAtomResponse(num);
						// llamo al display
						DisplayService dispService = DisplayService.getInstance();
						dispService.llamarEnDisplay(puestoSend.getNumeroPuesto().toString(), numeroDisplay);
							
				}else{
					throw new ContextException("El puesto no tiene ningun numero asociado que re-llamar");
				}
				
		} else {
			throw new ContextException("El puesto no se encuentra en estado LLAMANDO");
		}
		
	}
	
	
	
	


}