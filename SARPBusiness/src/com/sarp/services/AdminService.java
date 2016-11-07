
package com.sarp.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.RollbackException;

import com.sarp.classes.BusinessDisplay;
import com.sarp.classes.BusinessMetricasEstadoNumero;
import com.sarp.classes.BusinessMetricasNumero;
import com.sarp.classes.BusinessMetricasPuesto;
import com.sarp.classes.BusinessNumero;
import com.sarp.classes.BusinessPuesto;
import com.sarp.classes.BusinessSector;
import com.sarp.classes.BusinessSectorRol;
import com.sarp.classes.BusinessTramite;
import com.sarp.controllers.QueueController;
import com.sarp.dao.controllers.DAODisplayController;
import com.sarp.dao.controllers.DAONumeroController;
import com.sarp.dao.controllers.DAOPuestoController;
import com.sarp.dao.controllers.DAOSectorController;
import com.sarp.dao.controllers.DAOTramiteController;
import com.sarp.dao.factory.DAOFactory;
import com.sarp.dao.factory.DAOServiceFactory;
import com.sarp.enumerados.EstadoNumero;
import com.sarp.enumerados.EstadoPuesto;
import com.sarp.factory.Factory;
import com.sarp.json.modeler.JSONDisplay;
import com.sarp.json.modeler.JSONMetricasEstadoNumero;
import com.sarp.json.modeler.JSONMetricasNumero;
import com.sarp.json.modeler.JSONMetricasPuesto;
import com.sarp.json.modeler.JSONPuesto;
import com.sarp.json.modeler.JSONPuestoTramite;
import com.sarp.json.modeler.JSONSector;
import com.sarp.json.modeler.JSONSectorDisplay;
import com.sarp.json.modeler.JSONTramite;
import com.sarp.managers.QueuesManager;
import com.sarp.service.response.maker.RequestMaker;
import com.sarp.service.response.maker.ResponseMaker;
import com.sarp.thread.ThreadManager;
import com.sarp.json.modeler.JSONPuesto;
import com.sarp.json.modeler.JSONSector;

import com.sarp.service.response.maker.RequestMaker;

public class AdminService {
	
	/****** Alta, Baja & Modificacion de Puesto ******/
	
	public void altaPuesto(JSONPuesto puesto) throws Exception {

		RequestMaker reqMaker = RequestMaker.getInstance();
		BusinessPuesto bPuesto = reqMaker.requestPuesto(puesto);
		DAOServiceFactory daoServiceFactory = DAOServiceFactory.getInstance();
		DAOPuestoController controladorPuesto = daoServiceFactory.getDAOPuestoController();
		// INSERT en DaoService
		controladorPuesto.crearPuesto(bPuesto);
	}

	public void bajaPuesto(JSONPuesto puesto) throws Exception {

		RequestMaker reqMaker = RequestMaker.getInstance();
		BusinessPuesto bPuesto = reqMaker.requestPuesto(puesto);
		DAOServiceFactory daoServiceFactory = DAOServiceFactory.getInstance();
		DAOPuestoController controladorPuesto = daoServiceFactory.getDAOPuestoController();
		// DELETE en DaoService
		controladorPuesto.eliminarPuesto(bPuesto.getNombreMaquina());
	}

	public void modificarPuesto(JSONPuesto puesto) throws Exception {
		RequestMaker reqMaker = RequestMaker.getInstance();
		BusinessPuesto bPuesto = reqMaker.requestPuesto(puesto);
		// Si se modifico algun campo del puesto entonces se llama a DaoService
		// y se hace Update
		if (bPuesto.getEstado() != null || bPuesto.getUsuarioId() != null) {
			DAOServiceFactory daoServiceFactory = DAOServiceFactory.getInstance();
			DAOPuestoController controladorPuesto = daoServiceFactory.getDAOPuestoController();
			BusinessPuesto puestoSend = controladorPuesto.obtenerPuesto(bPuesto.getNombreMaquina());

			if (bPuesto.getEstado() != null) {
				puestoSend.setEstado(bPuesto.getEstado());
			}
			if (bPuesto.getUsuarioId() != null) {
				puestoSend.setUsuarioId(bPuesto.getUsuarioId());
			}
			if (bPuesto.getNumeroPuesto() != null) {
				puestoSend.setNumeroPuesto(bPuesto.getNumeroPuesto());
			}
			// Se delega a DaoService
			controladorPuesto.modificarPuesto(puestoSend);
		}
	}

	public List<JSONPuesto> listarPuestos(String sector) throws Exception {
		ResponseMaker resMaker = ResponseMaker.getInstance();

		DAOServiceFactory daoServiceFactory = DAOServiceFactory.getInstance();
		DAOPuestoController controladorPuesto = daoServiceFactory.getDAOPuestoController();
		List<BusinessPuesto> puestos;
		// Traigo los puestos de un sector desde DaoService
		// si sector es null entonces traigo todos los puestos del sistema
		if (sector != null) {
			DAOSectorController controladorSector = daoServiceFactory.getDAOSectorController();
			puestos = controladorSector.obtenerPuestosSector(sector);
		} else {
			puestos = controladorPuesto.listarPuestos();
		}

		List<JSONPuesto> puestosJson = resMaker.createArrayAtomPuestos(puestos);

		return puestosJson;

	}

	/****** Alta, Baja & Modificacion de Tramite ******/

	public void altaTramite(JSONTramite tramite) throws Exception {
		/* primero se pide el controlador de tramites mediante la factory */

		DAOServiceFactory factory = DAOServiceFactory.getInstance();
		DAOTramiteController tramCtrl = factory.getDAOTramiteController();
		RequestMaker reqMaker = RequestMaker.getInstance();
		BusinessTramite bTramite = reqMaker.requestTramite(tramite);

		/*
		 * Finalmente se persiste en la base mediante el llamado del controlador
		 */
		tramCtrl.crearTramite(bTramite);
	}

	public void bajaTramite(JSONTramite tramite) throws Exception {
		/* primero se pide el controlador de tramites mediante la factory */

		DAOServiceFactory factory = DAOServiceFactory.getInstance();
		DAOTramiteController tramCtrl = factory.getDAOTramiteController();

		/*
		 * Finalmente se persiste en la base mediante el llamado del controlador
		 */
		tramCtrl.eliminarTramite(tramite.getCodigo());
	}

	public void modificarTramite(JSONTramite tramite) throws Exception {
		/* primero se pide el controlador de tramites mediante la factory */

		DAOServiceFactory factory = DAOServiceFactory.getInstance();
		DAOTramiteController tramCtrl = factory.getDAOTramiteController();

		if (tramite.getNombre() != null) {
			BusinessTramite tram = tramCtrl.obtenerTramite(tramite.getCodigo());
			tram.setNombre(tramite.getNombre());
			/*
			 * Finalmente se persiste en la base mediante el llamado del
			 * controlador
			 */
			tramCtrl.modificarTramite(tram);
		} else {
			throw new Exception("Este puesto no tiene nada que modificarse");
		}

	}

	public List<JSONTramite> listarTramites() {

		ResponseMaker resMaker = ResponseMaker.getInstance();
		DAOServiceFactory factory = DAOServiceFactory.getInstance();
		DAOTramiteController ctrl = factory.getDAOTramiteController();
		List<BusinessTramite> listaTramites = ctrl.listarTramites();
		List<JSONTramite> jsonTram = resMaker.createArrayAtomTramites(listaTramites);

		return jsonTram;

	}

	/****** Alta, Baja & Modificacion de Sector ******/

	public void altaModificacionSector(BusinessSector sector) throws Exception {
		DAOServiceFactory factory = DAOServiceFactory.getInstance();
		DAOSectorController sectorCtrl = factory.getDAOSectorController();
		Factory fac = Factory.getInstance();
		QueueController ctrl = fac.getQueueController();
		try {
			//exite tramire 1? 	HACER JUAN CUANDO ESTE LO DEL CHELO	
			sectorCtrl.crearSector(sector);
			//asignar tramite 1
			ctrl.crearColaSector(sector.getSectorId());
		} catch (RollbackException ex) {
			Throwable a = ex.getCause();
		    if (ex.getCause().getMessage().contains("unique") || ex.getCause().getMessage().contains("duplicate") || ex.getCause().getMessage().contains("unicidad")){
		    	BusinessSector s = sectorCtrl.obtenerSector(sector.getSectorId());
		    	sector.setLastUpdated(s.getLastUpdated());
		    	sectorCtrl.modificarSector(sector);
		    }
		}
	}

	public void bajaLogicaSector(String idSector) throws Exception {
		DAOServiceFactory factory = DAOServiceFactory.getInstance();
		DAOSectorController sectorCtrl = factory.getDAOSectorController();

		sectorCtrl.bajaLogicarSector(idSector);
	}

	public List<JSONSector> listarSectores() throws Exception {
		ResponseMaker resMaker = ResponseMaker.getInstance();
		DAOServiceFactory factory = DAOServiceFactory.getInstance();
		DAOSectorController ctrl = factory.getDAOSectorController();
		List<BusinessSector> listaSectores = ctrl.listarSectores();
		List<JSONSector> jsonSec = resMaker.createArrayAtomSectores(listaSectores);
		return jsonSec;
	}

	public List<JSONSector> listarSectoresPorRol( List<BusinessSectorRol> respde) throws Exception {
		ResponseMaker resMaker = ResponseMaker.getInstance();
		DAOServiceFactory factory = DAOServiceFactory.getInstance();
		DAOSectorController ctrl = factory.getDAOSectorController();
		List<BusinessSector> listaSectores = new ArrayList<BusinessSector>();
		for (BusinessSectorRol rsp : respde){
			listaSectores.add(ctrl.obtenerSector(rsp.getSectorId()));
		}
		List<JSONSector> jsonSec = resMaker.createArrayAtomSectores(listaSectores);
		return jsonSec;
	}
	/************ ABM Y 1 LISTAR DISPLAYS ************/
	
	public void altaDisplay(String idDisplay) throws Exception{	
		DAOServiceFactory factoryServices = DAOServiceFactory.getInstance();
		DAODisplayController controladorDisplay =factoryServices.getDAODisplayController();
		BusinessDisplay display = new BusinessDisplay(idDisplay);
		/*OJO EL CODIGO SE GENERA EN LA BASE TENGO QUE VER COMO VA*/
		//INSERT en DaoService
		controladorDisplay.crearDisplay(display);
		
	}
	
	public void bajaDisplay(String idDisplay) throws Exception{
		DAOServiceFactory factoryServices = DAOServiceFactory.getInstance();
		DAODisplayController controladorDisplay =factoryServices.getDAODisplayController();
		//DELETE en DaoService
		controladorDisplay.eliminarDisplay(idDisplay);
	}
	
	public List<JSONDisplay> listarDisplays(String sectorid) throws Exception{
		DAOServiceFactory factoryServices = DAOServiceFactory.getInstance();
		DAOSectorController controladorsectro = factoryServices.getDAOSectorController();
		DAODisplayController controladorDisplay = factoryServices.getDAODisplayController();
		List<BusinessDisplay> displays;
		//Traigo los puestos de un sector desde DaoService
		//si sector es null entonces traigo todos los puestos del sistema		
		if(sectorid != null){
			displays = controladorsectro.obtenerDisplaysSector(sectorid);
		}else{
			displays = controladorDisplay.listarDisplays();
		}
		
		ResponseMaker resMaker = ResponseMaker.getInstance();
		
		List<JSONDisplay> JsonDisp = resMaker.createArrayAtomDisplay(displays);
		
		return JsonDisp;
	}
	
/*	public void modificarRutaDisplay(Integer idDisplay, String rutaArchivo) throws Exception{
		DAOServiceFactory factoryServices = DAOServiceFactory.getInstance();
		DAODisplayController controladorDisplay =factoryServices.getDAODisplayController();
		//traigo el display con id idDisplay
		BusinessDisplay display = controladorDisplay.obtenerDisplay(idDisplay);
		display.setRutaArchivo(rutaArchivo);
		//UPDATE en DaoService
		controladorDisplay.modificarDisplay(display);
	}*/
			
	/************ ASIGNACIONES ************/
	
	public void asignarTramiteSector(String idTramite, String idSector) throws Exception {
		DAOServiceFactory factory = DAOServiceFactory.getInstance();
		DAOSectorController sectorCtrl = factory.getDAOSectorController();
		sectorCtrl.asociarTramiteSector(idTramite, idSector);
	}

	public void asignarPuestoSector(String sector, String puesto) throws Exception {
		DAOServiceFactory factory = DAOServiceFactory.getInstance();
		DAOSectorController sectorCtrl = factory.getDAOSectorController();
		DAOTramiteController tramiteCtrl = factory.getDAOTramiteController();
		sectorCtrl.asociarSectorPuesto(sector, puesto);
		// se asocian todos los tramites del sector 
		//si no estan asociados, si estan asociados tira exepcion en la base la capturo he ignoro 
		List<BusinessTramite> tramites = sectorCtrl.obtenerTramitesSector(sector);
		for ( BusinessTramite t1 : tramites ){
			try{
				tramiteCtrl.asociarTramitePuesto(t1.getCodigo(), puesto);
			}catch (Exception e){
				//si ya esta asociado paso por aca y no hago nada 
				System.out.println("AdminSeviceBusines - asignarPuestoSector"+ e.getMessage());
			}
			
		}
		
		
	}
	
	public void asignarTramitePuesto(JSONPuestoTramite puestoTramite) throws Exception {
		if (puestoTramite.getNombreMaquina() != null) {
			if (puestoTramite.getTramiteId() != null){
				/* se controla circularidad entre tramite, sectror y puesto */
				
		
				DAOServiceFactory daoServiceFactory = DAOServiceFactory.getInstance();
		
				DAOTramiteController controladorTramite = daoServiceFactory.getDAOTramiteController();
				DAOPuestoController controladorPuesto = daoServiceFactory.getDAOPuestoController();
		
				List<BusinessSector> sectoresPuesto = controladorPuesto.obtenerSectoresPuesto(puestoTramite.getNombreMaquina());
		
				Boolean tieneTramite = false;
				if (sectoresPuesto.isEmpty())
					throw new Exception("El puesto no tiene ningun sector asignado");
		
				for (BusinessSector sectro : sectoresPuesto) {
					if (controladorTramite.existeTramiteSector(sectro.getSectorId(), puestoTramite.getTramiteId())) {
						tieneTramite = true;
						break;
					}
				}
				
				if (tieneTramite) {
					controladorTramite.asociarTramitePuesto(puestoTramite.getTramiteId(),puestoTramite.getNombreMaquina());
				} else {
					throw new Exception("El puesto no tiene un sector que atienda ese tramite");
				}
				
			} else {
				throw new Exception("Debe seleccionar un tramite");
			}
		} else {
			throw new Exception("Debe seleccionar un puesto");
		}
	}
	
		
	public void asignarSectorDisplay(String sector, String display) throws Exception {
		DAOServiceFactory factory = DAOServiceFactory.getInstance();
		DAOSectorController sectorCtrl = factory.getDAOSectorController();
		sectorCtrl.asociarDisplaySector(sector, display);
	}
	
	/******** Desasignar ********/
	
	public void desasignarTramiteSector(String idTramite, String idSector) throws Exception {
		DAOServiceFactory factory = DAOServiceFactory.getInstance();
		DAOSectorController sectorCtrl = factory.getDAOSectorController();
		sectorCtrl.desasociarTramiteSector(idTramite, idSector);
	}

	public void desasignarSectorDisplay(String sector, String display) throws Exception {
		DAOServiceFactory factory = DAOServiceFactory.getInstance();
		DAOSectorController sectorCtrl = factory.getDAOSectorController();
		sectorCtrl.desasociarDisplaySector(sector, display);
	}
	
	public void desasignarTramitePuesto(JSONPuestoTramite puestoTramite) throws Exception {
		DAOServiceFactory factory = DAOServiceFactory.getInstance();
		DAOTramiteController tramiteCtrl = factory.getDAOTramiteController();
		tramiteCtrl.desasociarTramitePuesto(puestoTramite.getTramiteId(),puestoTramite.getNombreMaquina());
	}
	
	public void desasignarPuestoSector(String sector, String puesto) throws Exception {
		DAOServiceFactory factory = DAOServiceFactory.getInstance();
		DAOSectorController sectorCtrl = factory.getDAOSectorController();
		sectorCtrl.desasociarSectorPuesto(sector, puesto);
	}
	/************ LISTAR ***********/
	 
	 public List<JSONTramite> listarTramitesPuesto(String nombreMaquina) {

		ResponseMaker resMaker = ResponseMaker.getInstance();
		DAOServiceFactory factory = DAOServiceFactory.getInstance();
		DAOPuestoController ctrl = factory.getDAOPuestoController();
		List<BusinessTramite> listaTramites = ctrl.obtenerTramitesPuesto(nombreMaquina);
		List<JSONTramite> jsonTram = resMaker.createArrayAtomTramites(listaTramites);
	
		return jsonTram;

	}
		
	 public List<JSONTramite> listarTramitesPosibles(String nombreMaquina)throws Exception {

			ResponseMaker resMaker = ResponseMaker.getInstance();
			DAOServiceFactory daoServiceFactory = DAOServiceFactory.getInstance();
			DAOPuestoController controladorPuesto = daoServiceFactory.getDAOPuestoController();
			DAOSectorController contorladorSector = daoServiceFactory.getDAOSectorController();
			
			List<BusinessTramite> listaTramites = new ArrayList<BusinessTramite>();
			List<BusinessTramite> tramitesdelsector = new ArrayList<BusinessTramite>();
			List<BusinessTramite> tramitesYaAsignados = new ArrayList<BusinessTramite>();
			
			List<BusinessSector> sectoresPuesto = controladorPuesto.obtenerSectoresPuesto(nombreMaquina);
			if (sectoresPuesto.isEmpty())
				throw new Exception("El puesto no tiene ningun sector asignado");
			tramitesYaAsignados = controladorPuesto.obtenerTramitesPuesto(nombreMaquina);
			for (BusinessSector sectro : sectoresPuesto) {
				tramitesdelsector = contorladorSector.obtenerTramitesSector(sectro.getSectorId());
				for (BusinessTramite tramite : tramitesdelsector){
					/* NO SE PORQUE PERO NO ME FUNCIONA
					if (! ( listaTramites.contains(tramite) || tramitesYaAsignados.contains(tramite)) )
						listaTramites.add(tramite);*/
					
					//si no lo agrege antes 
					Boolean esta = false;
					for (BusinessTramite t : listaTramites ){
						if (t.getCodigo().equals(tramite.getCodigo()) ){
							esta = true;
							break;
						}			
					}
					//y no esta ya asignado 
					for (BusinessTramite t : tramitesYaAsignados){
						if (t.getCodigo().equals(tramite.getCodigo()) ){
							esta = true;
							break;
						}	
					}
					//lo agrego
					if (!esta) listaTramites.add(tramite);
				}
				tramitesdelsector.clear();
			}
			
			
			List<JSONTramite> jsonTram = resMaker.createArrayAtomTramites(listaTramites);
					
			return jsonTram;

		} 
	 //listardisplayporsectro es el mismo listar del abm
	 
	 //listarpuestoporsectro es el mismo listar del abm
		
	  
	 public List<JSONTramite> listarTramitesSector(String idSector) {
		 ResponseMaker resMaker = ResponseMaker.getInstance();
			DAOServiceFactory factory = DAOServiceFactory.getInstance();
			DAOSectorController ctrl = factory.getDAOSectorController();
			
			List<BusinessTramite> listaTramites = ctrl.obtenerTramitesSector(idSector);
			List<JSONTramite> jsonTram = resMaker.createArrayAtomTramites(listaTramites);
		
			return jsonTram;
			
		}
	 
	/************ Borrado de sistema ************/
	
	public void borrarTodoElSistema(){
		  DAOServiceFactory factoryServices = DAOServiceFactory.getInstance();
		  factoryServices.getDAOAdminController().resetDataBase();
	}

	/************ Colas ************/
	
	public void reinicializarColas() throws Exception {
		DAOServiceFactory fac = DAOServiceFactory.getInstance();
		DAOSectorController ctrl = fac.getDAOSectorController();
		List<BusinessSector> listaSectores = ctrl.listarSectores();
		try {
			QueuesManager manejador = QueuesManager.getInstance();
			manejador.reinicializarColas(listaSectores);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void recuperarColas() throws Exception {
		DAOServiceFactory fac = DAOServiceFactory.getInstance();
		DAONumeroController daoCtrl = fac.getDAONumeroController();
		List<BusinessNumero> listaNumeros = daoCtrl.listarNumerosDelDia();
		QueueService qServ;
		for(BusinessNumero bn : listaNumeros){
			qServ = new QueueService(bn.getCodSector());
			if(bn.getEstado().equals(EstadoNumero.PAUSADO)){
				qServ.transferirAColaPausados(bn);
			}else if(bn.getEstado().equals(EstadoNumero.ATRASADO)){
				qServ.transferirAColaAtrasados(bn);
			}else if(bn.getEstado().equals(EstadoNumero.PENDIENTE)){
				qServ.agregarNumero(bn);
			}else if(bn.getEstado().equals(EstadoNumero.LLAMADO) || bn.getEstado().equals(EstadoNumero.ATENDIENDO)){
				bn.setEstado(EstadoNumero.PAUSADO);
				daoCtrl.modificarNumero(bn);
				qServ.transferirAColaPausados(bn);
			}else{
				// finalizado, no atendido y desviado no son agregados a la cola
			}
		}
	}
	
	public void cambiarHoraLimpiadoColas(String hora) throws Exception {
		try{
			int h = Integer.parseInt(hora.substring(0, 2));
			int m = Integer.parseInt(hora.substring(3, 5)); 
			if(hora.length() != 5)
				throw new Exception("formato incorrecto");
			if(h > -1 && h < 24 && m > -1 && m < 60){
				ThreadManager th = ThreadManager.getInstance();
				th.cambiarHoraLimpiadoColas(h, m);
			}else{
				throw new Exception("rango de hora erroneo"); 
			}
		}catch(Exception e){
			throw e;
		}
	}
	
	public String obtenerHoraLimpiadoColas() throws Exception {
		try{
			ThreadManager th = ThreadManager.getInstance();
			return th.obtenerHoraLimpiadoColas();
		}catch(Exception e){
			throw e;
		}
	}
	
	/************* Metricas ****************/

	public List<JSONMetricasPuesto> listarMetricasPuestos(String nombreMaquina) {
		ResponseMaker resMaker = ResponseMaker.getInstance();

		DAOServiceFactory daoServiceFactory = DAOServiceFactory.getInstance();
		DAOPuestoController controladorPuesto = daoServiceFactory.getDAOPuestoController();
		List<BusinessMetricasPuesto> metricasPuestos;
		// Traigo los puestos de un sector desde DaoService
		// si sector es null entonces traigo todos los puestos del sistema
		if (nombreMaquina != null) {
			metricasPuestos = controladorPuesto.listarMetricasDePuestos(nombreMaquina);
		} else {
			metricasPuestos = controladorPuesto.listarMetricasPuestos();
		}

		List<JSONMetricasPuesto> metricasPuestosJson = resMaker.createArrayAtomMetricasPuestos(metricasPuestos);

		return metricasPuestosJson;
	}

	public List<JSONMetricasEstadoNumero> listarMetricasEstadoNumero(Integer internalId) {
		ResponseMaker resMaker = ResponseMaker.getInstance();

		DAOServiceFactory daoServiceFactory = DAOServiceFactory.getInstance();
		DAONumeroController controladorNumero = daoServiceFactory.getDAONumeroController();
		List<BusinessMetricasEstadoNumero> metricasEstadoNumero;
		// Traigo los puestos de un sector desde DaoService
		// si sector es null entonces traigo todos los puestos del sistema
		if (internalId != null) {
			metricasEstadoNumero = controladorNumero.listarMetricasEstadoDeNumero(internalId);
		} else {
			metricasEstadoNumero = controladorNumero.listarMetricasEstadoNumero();
		}

		List<JSONMetricasEstadoNumero> metricasEstadoNumeroJson = resMaker.createArrayAtomMetricasEstadoNumero(metricasEstadoNumero);

		return metricasEstadoNumeroJson;
	}

	public List<JSONMetricasNumero> listarMetricasNumero() {
		ResponseMaker resMaker = ResponseMaker.getInstance();

		DAOServiceFactory daoServiceFactory = DAOServiceFactory.getInstance();
		DAONumeroController controladorNumero = daoServiceFactory.getDAONumeroController();
		List<BusinessMetricasNumero> metricasNumero;
		// Traigo los puestos de un sector desde DaoService

		metricasNumero = controladorNumero.listarMetricasNumero();
		
		List<JSONMetricasNumero> metricasNumeroJson = resMaker.createArrayAtomMetricasNumero(metricasNumero);

		return metricasNumeroJson;
	}
	
	public JSONMetricasNumero listarMetricasDeNumero(Integer internalId) {
		ResponseMaker resMaker = ResponseMaker.getInstance();

		DAOServiceFactory daoServiceFactory = DAOServiceFactory.getInstance();
		DAONumeroController controladorNumero = daoServiceFactory.getDAONumeroController();
		BusinessMetricasNumero metricasNumero;
		// Traigo los puestos de un sector desde DaoService

		metricasNumero = controladorNumero.listarMetricasDeNumero(internalId);
		
		JSONMetricasNumero metricasNumeroJson = resMaker.metricasNumeroAtomResponse(metricasNumero);

		return metricasNumeroJson;
	}

}
