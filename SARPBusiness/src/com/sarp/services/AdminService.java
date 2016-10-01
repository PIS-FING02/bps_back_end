
package com.sarp.services;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.RollbackException;

import com.sarp.classes.BusinessDisplay;
import com.sarp.classes.BusinessNumero;
import com.sarp.classes.BusinessPuesto;
import com.sarp.classes.BusinessSector;
import com.sarp.classes.BusinessTramite;
import com.sarp.dao.controllers.DAODisplayController;
import com.sarp.dao.controllers.DAOPuestoController;
import com.sarp.dao.controllers.DAOSectorController;
import com.sarp.dao.controllers.DAOTramiteController;
import com.sarp.dao.factory.DAOFactory;
import com.sarp.dao.factory.DAOServiceFactory;
import com.sarp.enumerados.EstadoPuesto;
import com.sarp.factory.Factory;

import com.sarp.json.modeler.JSONPuesto;
import com.sarp.json.modeler.JSONSector;
import com.sarp.service.response.maker.RequestMaker;


import com.sarp.json.modeler.JSONPuesto;
import com.sarp.json.modeler.JSONSector;

import com.sarp.service.response.maker.RequestMaker;


public class AdminService {
	
	
	public void altaPuesto(JSONPuesto puesto) throws Exception{	

		RequestMaker reqMaker = RequestMaker.getInstance();
		BusinessPuesto bPuesto = reqMaker.requestPuesto(puesto);
		DAOServiceFactory daoServiceFactory = DAOServiceFactory.getInstance();
		DAOPuestoController controladorPuesto = daoServiceFactory.getDAOPuestoController();
		//INSERT en DaoService
		controladorPuesto.crearPuesto(bPuesto);
	}
	
	public void bajaPuesto(JSONPuesto puesto) throws Exception{

		RequestMaker reqMaker = RequestMaker.getInstance();
		BusinessPuesto bPuesto = reqMaker.requestPuesto(puesto);
		DAOServiceFactory daoServiceFactory = DAOServiceFactory.getInstance();
		DAOPuestoController controladorPuesto = daoServiceFactory.getDAOPuestoController();
		//DELETE en DaoService
		controladorPuesto.eliminarPuesto(bPuesto.getNombreMaquina());
	}
	
	public void modificarPuesto(JSONPuesto puesto) throws Exception {
		RequestMaker reqMaker = RequestMaker.getInstance();
		BusinessPuesto bPuesto = reqMaker.requestPuesto(puesto);
		//Si se modifico algun campo del puesto entonces se llama a DaoService y se hace Update
		if(bPuesto.getEstado() != null || bPuesto.getUsuarioId() != null){
			DAOServiceFactory daoServiceFactory = DAOServiceFactory.getInstance();
			DAOPuestoController controladorPuesto = daoServiceFactory.getDAOPuestoController();
			BusinessPuesto puestoSend = controladorPuesto.obtenerPuesto(bPuesto.getNombreMaquina());
			if(bPuesto.getEstado() != null){
				puestoSend.setEstado(bPuesto.getEstado());
			}
			if(bPuesto.getUsuarioId()!= null){
				puestoSend.setUsuarioId(bPuesto.getUsuarioId());
			}
			//Se delega a DaoService
			controladorPuesto.modificarPuesto(puestoSend);
		}
	}
		
	public List<BusinessPuesto> listarPuestos(JSONSector sector) throws Exception{
		RequestMaker reqMaker = RequestMaker.getInstance();
		BusinessSector bSector = reqMaker.requestSector(sector);
		DAOServiceFactory daoServiceFactory = DAOServiceFactory.getInstance();
		DAOPuestoController controladorPuesto = daoServiceFactory.getDAOPuestoController();
		List<BusinessPuesto> puestos;
		//Traigo los puestos de un sector desde DaoService
		//si sector es null entonces traigo todos los puestos del sistema		
		if(bSector != null){
			DAOSectorController controladorSector = daoServiceFactory.getDAOSectorController();
			puestos = controladorSector.obtenerPuestosSector(bSector.getSectorId());
		}else{
			puestos = controladorPuesto.listarPuestos();
		}
		
		return puestos;
		
	}
	
	/****** Alta, Baja & Modificacion de Tramite ******/
	
	public void altaTramite(BusinessTramite tramite) throws Exception{	
		/* primero se pide el controlador de tramites mediante la factory */
		
		DAOServiceFactory factory = DAOServiceFactory.getInstance();
		DAOTramiteController tramCtrl = factory.getDAOTramiteController();
		
		/* Finalmente se persiste en la base mediante el llamado del controlador */
		tramCtrl.crearTramite(tramite);
	}
	
	public void bajaTramite(int codigo) throws Exception{
		/* primero se pide el controlador de tramites mediante la factory */
		
		DAOServiceFactory factory = DAOServiceFactory.getInstance();
		DAOTramiteController tramCtrl = factory.getDAOTramiteController();
		
		/* Finalmente se persiste en la base mediante el llamado del controlador */
		tramCtrl.eliminarTramite(codigo);
	}
	
	public void modificarTramite(BusinessTramite tramite) throws Exception{
		/* primero se pide el controlador de tramites mediante la factory */
		
		DAOServiceFactory factory = DAOServiceFactory.getInstance();
		DAOTramiteController tramCtrl = factory.getDAOTramiteController();
		
		/* Finalmente se persiste en la base mediante el llamado del controlador */
		tramCtrl.modificarTramite(tramite);
	}
	
/** SECTOR **/
	
	public void altaModificacionSector(BusinessSector sector) throws Exception{	
		DAOServiceFactory factory = DAOServiceFactory.getInstance();
		DAOSectorController sectorCtrl = factory.getDAOSectorController();
		try{
			sectorCtrl.crearSector(sector);
		} catch (RollbackException ex){
			sectorCtrl.modificarSector(sector);
		}
	}
	
	public void asignarTramiteSector(Integer idTramite,String idSector) throws Exception{
		DAOServiceFactory factory = DAOServiceFactory.getInstance();
		DAOSectorController sectorCtrl = factory.getDAOSectorController();
		
		sectorCtrl.asociarTramiteSector(idTramite, idSector);
	}
	
	public void asignarPuestoSector(BusinessSector sector, BusinessPuesto puesto) throws Exception{
		DAOServiceFactory factory = DAOServiceFactory.getInstance();
		DAOSectorController sectorCtrl = factory.getDAOSectorController();
		sectorCtrl.asociarSectorPuesto(sector.getSectorId(),puesto.getNombreMaquina());
	}
	

	public void bajaSector(String idSector) throws Exception{
		DAOServiceFactory factory = DAOServiceFactory.getInstance();
		DAOSectorController sectorCtrl = factory.getDAOSectorController();
		
		sectorCtrl.eliminarSector(idSector);
	}
	
	public List<BusinessSector> listarSectores() throws Exception{
		DAOServiceFactory factory = DAOServiceFactory.getInstance();
		DAOSectorController sectorCtrl = factory.getDAOSectorController();
		
		return sectorCtrl.listarSectores();
	}
	
	/*** IMPLEMENTACION DE DISPLAYS****/
			public void altaDisplay(String rutaArchivo) throws Exception{	
				DAOServiceFactory factoryServices = DAOServiceFactory.getInstance();
				DAODisplayController controladorDisplay =factoryServices.getDAODisplayController();
				BusinessDisplay display = new BusinessDisplay(0,rutaArchivo);
				/*OJO EL CODIGO SE GENERA EN LA BASE TENGO QUE VER COMO VA*/
				//INSERT en DaoService
				controladorDisplay.crearDisplay(display);
				
			}
			
			public void bajaDisplay(Integer idDisplay) throws Exception{
				DAOServiceFactory factoryServices = DAOServiceFactory.getInstance();
				DAODisplayController controladorDisplay =factoryServices.getDAODisplayController();
				//DELETE en DaoService
				controladorDisplay.eliminarDisplay(idDisplay);
			}
			
			public List<BusinessDisplay> listarDisplays(String sectorid) throws Exception{
				DAOServiceFactory factoryServices = DAOServiceFactory.getInstance();
				DAODisplayController controladorDisplay = factoryServices.getDAODisplayController();
				List<BusinessDisplay> displays;
				//Traigo los puestos de un sector desde DaoService
				//si sector es null entonces traigo todos los puestos del sistema		
				if(sectorid != null){
					/*Falta implementar en dao*/
					displays = new ArrayList<BusinessDisplay> ();
					//displays = controladorDisplay.listarDisplaySector(sectorid);
				}else{
					displays = controladorDisplay.listarDisplays();
				}
				
				return displays;
			}
			
			public void modificarRutaDisplay(Integer idDisplay, String rutaArchivo) throws Exception{
				DAOServiceFactory factoryServices = DAOServiceFactory.getInstance();
				DAODisplayController controladorDisplay =factoryServices.getDAODisplayController();
				/*traigo el display con id idDisplay*/
				BusinessDisplay display = controladorDisplay.obtenerDisplay(idDisplay);
				display.setRutaArchivo(rutaArchivo);
				//UPDATE en DaoService
				controladorDisplay.modificarDisplay(display);
			}
	
}
