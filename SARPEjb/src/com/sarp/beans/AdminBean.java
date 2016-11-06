package com.sarp.beans;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.ejb.Stateless;

import com.sarp.classes.BusinessSector;
import com.sarp.classes.BusinessSectorRol;
import com.sarp.controllers.AdminActionsController;
import com.sarp.controllers.GAFUController;
import com.sarp.factory.Factory;
import com.sarp.json.modeler.JSONDisplay;
import com.sarp.json.modeler.JSONMetricasEstadoNumero;
import com.sarp.json.modeler.JSONMetricasNumero;
import com.sarp.json.modeler.JSONMetricasPuesto;
import com.sarp.json.modeler.JSONPuesto;
import com.sarp.json.modeler.JSONPuestoSector;
import com.sarp.json.modeler.JSONPuestoTramite;
import com.sarp.json.modeler.JSONSector;
import com.sarp.json.modeler.JSONSectorDisplay;
import com.sarp.json.modeler.JSONTramite;
import com.sarp.json.modeler.JSONTramiteSector;


/**
 * Session Bean implementation class AdminBean
 */
@Stateless
@LocalBean
public class AdminBean {

    /**
     * Default constructor. 
     */
	
	private static AdminActionsController ctrl;
	private static GAFUController gafu;
    public AdminBean() {
        // TODO Auto-generated constructor stub
    	Factory fac = Factory.getInstance();
		ctrl = fac.getAdminActionsController();
		gafu = fac.GAFUController();
    }
    
    
    
    public void altaPuesto(JSONPuesto puesto) throws Exception {
		// Se delega a ctrlice la implementacion
		ctrl.altaPuesto(puesto);
	}

	public void bajaPuesto(JSONPuesto puesto) throws Exception {
		ctrl.bajaPuesto(puesto);
	}

	public void modificarPuesto(JSONPuesto puesto) throws Exception {
		ctrl.modificarPuesto(puesto);
	}

	public List<JSONPuesto> listarPuestos(String idSector) throws Exception {
		return ctrl.listarPuestos(idSector);
	}

	public List<JSONTramite> listarTramites() {
		return ctrl.listarTramites();
	}

	public List<JSONTramite> listarTramitesPuesto(String nombreMaquina) throws Exception{
		return ctrl.listarTramitesPuesto(nombreMaquina);
	}
	public List<JSONTramite> listarTramitesPosibles(String nombreMaquina) throws Exception{
		return ctrl.listarTramitesPosibles(nombreMaquina);
	}

	//public void modificarEstadoPuesto(String nombreMaquina, String estado) {
		// TODO Auto-generated method stub

	//}

	/** Alta, Baja & Modificacion Tramite **/

	public void altaTramite(JSONTramite tramite) throws Exception {
		ctrl.altaTramite(tramite);
	}

	public void bajaTramite(JSONTramite tramite) throws Exception {
		ctrl.bajaTramite(tramite);
	}

	public void modificarTramite(JSONTramite tramite) throws Exception {
		ctrl.modificarTramite(tramite);
	}

	public void asignarTramitePuesto(JSONPuestoTramite puestoTramite) throws Exception {
		ctrl.asignarTramitePuesto(puestoTramite);
	}
	
	public void desasignarTramitePuesto(JSONPuestoTramite puestoTramite) throws Exception {
		ctrl.desasignarTramitePuesto(puestoTramite);
	}

	/** Alta, Baja & Modificacion Sector **/

	public void altaModificacionSector(BusinessSector sector) throws Exception {
		ctrl.altaModificacionSector(sector);
	}

	public void asignarTramiteSector(JSONTramiteSector tramSec) throws Exception {
		ctrl.asignarTramiteSector(tramSec);
	}
	
	public void desasignarTramiteSector(JSONTramiteSector tramSec) throws Exception {
		ctrl.desasignarTramiteSector(tramSec);
	}

	public void asignarPuestoSector(JSONPuestoSector puestoSec) throws Exception {
		ctrl.asignarPuestoSector(puestoSec);
	}
	
	public void desasignarPuestoSector(JSONPuestoSector puestoSec) throws Exception {
		ctrl.desasignarPuestoSector(puestoSec);
	}

	public void bajaLogicaSector(String idSector) throws Exception {
		ctrl.bajaLogicaSector(idSector);
	}

	public List<JSONSector> listarSectores() throws Exception {
		return ctrl.listarSectores();
	}
	
	public List<JSONSector> listarSectores(List<BusinessSectorRol> respde) throws Exception {
		return ctrl.listarSectores(respde);
	}

	public void actualizarGAFU() throws Exception{
		gafu.actualizarArbolGAFU();
	}

	/*
	 * public void asignarSectorDisplayAdmin(JSONSectorDisplay secDisplay)
	 * throws Exception{ ctrlice ctrlice = new ctrlice();
	 * ctrlice.asignarSectorDisplayAdmin(secDisplay);
	 * 
	 * }
	 */

	/*********************** DISPLAY ***********************************/
	public void altaDisplay(String idDisplay) throws Exception {
		ctrl.altaDisplay(idDisplay);
	}

	public void bajaDisplay(String idDisplay) throws Exception {
		ctrl.bajaDisplay(idDisplay);

	}

	public List<JSONDisplay> listarDisplays(String sectorid) throws Exception {
		return ctrl.listarDisplays(sectorid);

	}

	public void borrarTodoElSistema() {
		ctrl.borrarTodoElSistema();
	}

	public void asignarSectorDisplay(JSONSectorDisplay secDisplay) throws Exception {
		ctrl.asignarSectorDisplay(secDisplay);
	}

	public void desasignarSectorDisplay(JSONSectorDisplay secDisplay) throws Exception {
		ctrl.desasignarSectorDisplay(secDisplay);
	}

	
	public List<JSONTramite> listarTramitesSector(String idSector) {

		return ctrl.listarTramitesSector(idSector);

	}

	/************* Colas ****************/

	public void reinicializarColas() throws Exception {
		try {
			ctrl.reinicializarColas();
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void recuperarColas() throws Exception {
		try {
			ctrl.recuperarColas();
		} catch (Exception e) {
			throw e;
		}
	}
	
	/************* Metricas ****************/

	public List<JSONMetricasPuesto> listarMetricasPuestos(String nombreMaquina) {
		return ctrl.listarMetricasPuestos(nombreMaquina);
	}

	public List<JSONMetricasEstadoNumero> listarMetricasEstadoNumero(Integer internalId) {
		return ctrl.listarMetricasEstadoNumero(internalId);
	}

	public List<JSONMetricasNumero> listarMetricasNumero() {
		return ctrl.listarMetricasNumero();
	}
	
	public JSONMetricasNumero listarMetricasDeNumero(Integer internalId) {
		return ctrl.listarMetricasDeNumero(internalId);
	}

}
