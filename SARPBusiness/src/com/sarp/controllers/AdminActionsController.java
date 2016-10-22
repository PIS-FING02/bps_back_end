package com.sarp.controllers;

import java.io.IOException;
import java.util.List;

import com.sarp.classes.BusinessDisplay;
import com.sarp.classes.BusinessSector;
import com.sarp.json.modeler.JSONMetricasPuesto;
import com.sarp.json.modeler.JSONPuesto;
import com.sarp.json.modeler.JSONPuestoTramite;
import com.sarp.json.modeler.JSONPuestoSector;
import com.sarp.json.modeler.JSONSector;
import com.sarp.json.modeler.JSONTramiteSector;
import com.sarp.managers.QueuesManager;
import com.sarp.json.modeler.JSONSectorDisplay;
import com.sarp.json.modeler.JSONTramite;
import com.sarp.services.AdminService;

public class AdminActionsController {

	private static AdminActionsController instance;

	private AdminActionsController() {
	};

	public static AdminActionsController getInstance() {
		instance = instance != null ? instance : new AdminActionsController();
		return instance;
	}

	public void altaPuesto(JSONPuesto puesto) throws Exception {
		// Se delega a AdminService la implementacion
		AdminService adminService = new AdminService();
		adminService.altaPuesto(puesto);
	}

	public void bajaPuesto(JSONPuesto puesto) throws Exception {
		// Se delega a AdminService la implementacion
		AdminService adminService = new AdminService();
		adminService.bajaPuesto(puesto);
	}

	public void modificarPuesto(JSONPuesto puesto) throws Exception {
		// Se delega a AdminService la implementacion
		AdminService adminService = new AdminService();
		adminService.modificarPuesto(puesto);
	}

	public List<JSONPuesto> listarPuestos(String idSector) throws Exception {

		// Se delega a AdminService la implementacion
		AdminService adminService = new AdminService();
		return adminService.listarPuestos(idSector);
	}

	public List<JSONTramite> listarTramites() {
		AdminService adServ = new AdminService();
		return adServ.listarTramites();
	}

	public List<JSONTramite> listarTramitesPuesto(String nombreMaquina) throws Exception{
		AdminService adServ = new AdminService();
		return adServ.listarTramitesPuesto(nombreMaquina);
	}
	public List<JSONTramite> listarTramitesPosibles(String nombreMaquina) throws Exception{
		AdminService adServ = new AdminService();
		return adServ.listarTramitesPosibles(nombreMaquina);
	}

	//public void modificarEstadoPuesto(String nombreMaquina, String estado) {
		// TODO Auto-generated method stub

	//}

	/** Alta, Baja & Modificacion Tramite **/

	public void altaTramite(JSONTramite tramite) throws Exception {
		/*
		 * Notar que el AdminService es el de este mismo proyecto (SARPBusiness)
		 * y no el AdminService de SARPServices
		 */
		AdminService adminServ = new AdminService();
		adminServ.altaTramite(tramite);
	}

	public void bajaTramite(JSONTramite tramite) throws Exception {
		/*
		 * Notar que el AdminService es el de este mismo proyecto (SARPBusiness)
		 * y no el AdminService de SARPServices
		 */
		AdminService adminServ = new AdminService();
		adminServ.bajaTramite(tramite);
	}

	public void modificarTramite(JSONTramite tramite) throws Exception {
		/*
		 * Notar que el AdminService es el de este mismo proyecto (SARPBusiness)
		 * y no el AdminService de SARPServices
		 */
		AdminService adminServ = new AdminService();
		adminServ.modificarTramite(tramite);
	}

	public void asignarTramitePuesto(JSONPuestoTramite puestoTramite) throws Exception {
		AdminService adminService = new AdminService();
		adminService.asignarTramitePuesto(puestoTramite);
	}
	
	public void desasignarTramitePuesto(JSONPuestoTramite puestoTramite) throws Exception {
		AdminService adminService = new AdminService();
		adminService.desasignarTramitePuesto(puestoTramite);
	}

	/** Alta, Baja & Modificacion Sector **/

	public void altaModificacionSector(BusinessSector sector) throws Exception {
		AdminService adminService = new AdminService();
		adminService.altaModificacionSector(sector);
	}

	public void asignarTramiteSector(JSONTramiteSector tramSec) throws Exception {
		AdminService adminService = new AdminService();
		adminService.asignarTramiteSector(tramSec.getTramiteId(), tramSec.getSectorId());
	}
	
	public void desasignarTramiteSector(JSONTramiteSector tramSec) throws Exception {
		AdminService adminService = new AdminService();
		adminService.desasignarTramiteSector(tramSec.getTramiteId(), tramSec.getSectorId());
	}

	public void asignarPuestoSector(JSONPuestoSector puestoSec) throws Exception {
		AdminService adminService = new AdminService();
		adminService.asignarPuestoSector(puestoSec.getSectorId(), puestoSec.getNombreMaquina());
	}
	
	public void desasignarPuestoSector(JSONPuestoSector puestoSec) throws Exception {
		AdminService adminService = new AdminService();
		adminService.desasignarPuestoSector(puestoSec.getSectorId(), puestoSec.getNombreMaquina());
	}

	public void bajaLogicaSector(String idSector) throws Exception {
		AdminService adminService = new AdminService();
		adminService.bajaLogicaSector(idSector);
	}

	public List<JSONSector> listarSectores() throws Exception {
		AdminService adminService = new AdminService();
		return adminService.listarSectores();
	}

	public void actualizarGAFU() throws Exception{
		GAFUController GAFUctrl = new GAFUController();
		GAFUctrl.actualizarArbolGAFU();
	}

	/*
	 * public void asignarSectorDisplayAdmin(JSONSectorDisplay secDisplay)
	 * throws Exception{ AdminService adminService = new AdminService();
	 * adminService.asignarSectorDisplayAdmin(secDisplay);
	 * 
	 * }
	 */

	/*********************** DISPLAY ***********************************/
	public void altaDisplay(String idDisplay) throws Exception {
		AdminService adminServ = new AdminService();
		adminServ.altaDisplay(idDisplay);

	}

	public void bajaDisplay(String idDisplay) throws Exception {
		AdminService adminServ = new AdminService();
		adminServ.bajaDisplay(idDisplay);

	}

	public List<BusinessDisplay> listarDisplays(String sectorid) throws Exception {
		AdminService adminServ = new AdminService();
		return adminServ.listarDisplays(sectorid);

	}

	/*
	 * public void modificarRutaDisplay(Integer idDisplay, String rutaArchivo)
	 * throws Exception{ AdminService adminServ = new AdminService();
	 * adminServ.modificarRutaDisplay(idDisplay, rutaArchivo);
	 * 
	 * }
	 */
	public void borrarTodoElSistema() {
		AdminService adminServ = new AdminService();
		adminServ.borrarTodoElSistema();
	}

	public void asignarSectorDisplay(JSONSectorDisplay secDisplay) throws Exception {
		AdminService adminService = new AdminService();
		adminService.asignarSectorDisplay(secDisplay.getSectorId(), secDisplay.getDisplayId());
	}

	public void desasignarSectorDisplay(JSONSectorDisplay secDisplay) throws Exception {
		AdminService adminService = new AdminService();
		adminService.desasignarSectorDisplay(secDisplay.getSectorId(), secDisplay.getDisplayId());
	}

	
	public List<JSONTramite> listarTramitesSector(String idSector) {

		AdminService adServ = new AdminService();
		return adServ.listarTramitesSector(idSector);

	}

	/************* Colas ****************/

	public void reinicializarColas() throws Exception {
		try {
			AdminService adminServ = new AdminService();
			adminServ.reinicializarColas();
		} catch (Exception e) {
			throw e;
		}
	}
	
	/************* Metricas ****************/

	public List<JSONMetricasPuesto> listarMetricasPuestos(String nombreMaquina) {
		AdminService adminService = new AdminService();
		return adminService.listarMetricasPuestos(nombreMaquina);
	}

}
