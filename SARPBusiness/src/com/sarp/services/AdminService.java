
package com.sarp.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.RollbackException;

import com.sarp.classes.BusinessDisplay;
import com.sarp.classes.BusinessNumero;
import com.sarp.classes.BusinessPuesto;
import com.sarp.classes.BusinessSector;
import com.sarp.classes.BusinessTramite;
import com.sarp.controllers.QueueController;
import com.sarp.dao.controllers.DAODisplayController;
import com.sarp.dao.controllers.DAOPuestoController;
import com.sarp.dao.controllers.DAOSectorController;
import com.sarp.dao.controllers.DAOTramiteController;
import com.sarp.dao.factory.DAOFactory;
import com.sarp.dao.factory.DAOServiceFactory;
import com.sarp.enumerados.EstadoPuesto;
import com.sarp.factory.Factory;

import com.sarp.json.modeler.JSONPuesto;
import com.sarp.json.modeler.JSONPuestoTramite;
import com.sarp.json.modeler.JSONSector;
import com.sarp.json.modeler.JSONSectorDisplay;
import com.sarp.json.modeler.JSONTramite;
import com.sarp.managers.QueuesManager;
import com.sarp.service.response.maker.RequestMaker;
import com.sarp.service.response.maker.ResponseMaker;
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
			sectorCtrl.crearSector(sector);
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
	
	public List<BusinessDisplay> listarDisplays(String sectorid) throws Exception{
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
		
		return displays;
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
	
	public void asignarTramiteSector(Integer idTramite, String idSector) throws Exception {
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
	
	public void desasignarTramiteSector(Integer idTramite, String idSector) throws Exception {
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
						if (t.getCodigo() ==  tramite.getCodigo() ){
							esta = true;
							break;
						}			
					}
					//y no esta ya asignado 
					for (BusinessTramite t : tramitesYaAsignados){
						if (t.getCodigo() ==  tramite.getCodigo() ){
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

}
