
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
import com.sarp.service.response.maker.RequestMaker;
import com.sarp.service.response.maker.ResponseMaker;
import com.sarp.json.modeler.JSONPuesto;
import com.sarp.json.modeler.JSONSector;

import com.sarp.service.response.maker.RequestMaker;

public class AdminService {

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

	public List<JSONTramite> listarTramitesPuesto(String nombreMaquina) {

		ResponseMaker resMaker = ResponseMaker.getInstance();
		DAOServiceFactory factory = DAOServiceFactory.getInstance();
		DAOPuestoController ctrl = factory.getDAOPuestoController();
		List<BusinessTramite> listaTramites = ctrl.obtenerTramitesPuesto(nombreMaquina);
		List<JSONTramite> jsonTram = resMaker.createArrayAtomTramites(listaTramites);

		return jsonTram;

	}

	public void asignarTramitePuesto(JSONPuestoTramite puestoTramite) throws Exception {

		/* se controla circularidad entre tramite, sectror y puesto */
		RequestMaker reqMaker = RequestMaker.getInstance();

		DAOServiceFactory daoServiceFactory = DAOServiceFactory.getInstance();

		DAOTramiteController controladorTramite = daoServiceFactory.getDAOTramiteController();
		DAOPuestoController controladorPuesto = daoServiceFactory.getDAOPuestoController();

		List<BusinessSector> sectoresPuesto = controladorPuesto
				.obtenerSectoresPuesto(puestoTramite.getPuesto().getNombreMaquina());

		Boolean tieneTramite = false;

		for (BusinessSector sectro : sectoresPuesto) {
			if (controladorTramite.existeTramiteSector(sectro.getSectorId(), puestoTramite.getTramite().getCodigo())) {
				tieneTramite = true;
				break;
			}
		}

		if (tieneTramite) {
			if ((puestoTramite.getPuesto() != null) && (puestoTramite.getTramite() != null)) {
				if ((!puestoTramite.getPuesto().getNombreMaquina().isEmpty())
						&& (puestoTramite.getTramite().getCodigo() != null
								|| puestoTramite.getTramite().getCodigo() != 0)) {
					controladorTramite.asociarTramitePuesto(puestoTramite.getTramite().getCodigo(),
							puestoTramite.getPuesto().getNombreMaquina());
				}
			} else {
				throw new Exception("JSONPuestoTramite corrupto");
			}
		} else {
			throw new Exception("El puesto no tiene un sector que atienda ese tramite");
		}

	};

	/** SECTOR **/

	public void altaModificacionSector(BusinessSector sector) throws Exception {
		DAOServiceFactory factory = DAOServiceFactory.getInstance();
		DAOSectorController sectorCtrl = factory.getDAOSectorController();
		Factory fac = Factory.getInstance();
		QueueController ctrl = fac.getQueueController();
		try {
			sectorCtrl.crearSector(sector);
			ctrl.crearColaSector(sector.getSectorId());
		} catch (RollbackException ex) {
			sectorCtrl.modificarSector(sector);
		}
	}

	public void asignarTramiteSector(Integer idTramite, String idSector) throws Exception {
		DAOServiceFactory factory = DAOServiceFactory.getInstance();
		DAOSectorController sectorCtrl = factory.getDAOSectorController();

		sectorCtrl.asociarTramiteSector(idTramite, idSector);
	}

	public void asignarPuestoSector(JSONSector sector, JSONPuesto puesto) throws Exception {
		DAOServiceFactory factory = DAOServiceFactory.getInstance();
		DAOSectorController sectorCtrl = factory.getDAOSectorController();
		sectorCtrl.asociarSectorPuesto(sector.getCodigo(), puesto.getNombreMaquina());
	}

	public void bajaSector(String idSector) throws Exception {
		DAOServiceFactory factory = DAOServiceFactory.getInstance();
		DAOSectorController sectorCtrl = factory.getDAOSectorController();

		sectorCtrl.eliminarSector(idSector);
	}

	public List<JSONSector> listarSectores() throws Exception {
		ResponseMaker resMaker = ResponseMaker.getInstance();
		DAOServiceFactory factory = DAOServiceFactory.getInstance();
		DAOSectorController ctrl = factory.getDAOSectorController();
		List<BusinessSector> listaSectores = ctrl.listarSectores();
		List<JSONSector> jsonSec = resMaker.createArrayAtomSectores(listaSectores);

		return jsonSec;
	}

	public void asignarSectorDisplayAdmin(JSONSectorDisplay secDisplay) throws Exception {
		RequestMaker reqMaker = RequestMaker.getInstance();
		BusinessSector bSector = reqMaker.requestSector(secDisplay.getSector());
		BusinessDisplay bDisplay = reqMaker.requestDisplay(secDisplay.getDisplay());
		DAOServiceFactory daoServiceFactory = DAOServiceFactory.getInstance();
		DAOSectorController controladorSector = daoServiceFactory.getDAOSectorController();
		if (bSector != null && bDisplay != null && bSector.getSectorId() != null && bDisplay.getCodigo() != null) {
			controladorSector.asignarDisplaySector(bSector.getSectorId(), bDisplay.getCodigo());
		} else {
			throw new Exception("JSONSectorDisplay corrupto");
		}
		// DELETE en DaoService
	};

	/*** IMPLEMENTACION DE DISPLAYS ****/
	public void altaDisplay(String rutaArchivo) throws Exception {
		DAOServiceFactory factoryServices = DAOServiceFactory.getInstance();
		DAODisplayController controladorDisplay = factoryServices.getDAODisplayController();
		BusinessDisplay display = new BusinessDisplay(0, rutaArchivo);
		/* OJO EL CODIGO SE GENERA EN LA BASE TENGO QUE VER COMO VA */
		// INSERT en DaoService
		controladorDisplay.crearDisplay(display);

	}

	public void bajaDisplay(Integer idDisplay) throws Exception {
		DAOServiceFactory factoryServices = DAOServiceFactory.getInstance();
		DAODisplayController controladorDisplay = factoryServices.getDAODisplayController();
		// DELETE en DaoService
		controladorDisplay.eliminarDisplay(idDisplay);
	}

	public List<BusinessDisplay> listarDisplays(String sectorid) throws Exception {
		DAOServiceFactory factoryServices = DAOServiceFactory.getInstance();
		DAODisplayController controladorDisplay = factoryServices.getDAODisplayController();
		List<BusinessDisplay> displays;
		// Traigo los puestos de un sector desde DaoService
		// si sector es null entonces traigo todos los puestos del sistema
		if (sectorid != null) {
			/* Falta implementar en dao */
			displays = new ArrayList<BusinessDisplay>();
			// displays = controladorDisplay.listarDisplaySector(sectorid);
		} else {
			displays = controladorDisplay.listarDisplays();
		}

		return displays;
	}

	public void modificarRutaDisplay(Integer idDisplay, String rutaArchivo) throws Exception {
		DAOServiceFactory factoryServices = DAOServiceFactory.getInstance();
		DAODisplayController controladorDisplay = factoryServices.getDAODisplayController();
		/* traigo el display con id idDisplay */
		BusinessDisplay display = controladorDisplay.obtenerDisplay(idDisplay);
		display.setRutaArchivo(rutaArchivo);
		// UPDATE en DaoService
		controladorDisplay.modificarDisplay(display);
	}

	public void borrarTodoElSistema(){
		DAOServiceFactory factoryServices = DAOServiceFactory.getInstance();
		factoryServices.getDAOAdminController().resetDataBase();
	}
	
}
