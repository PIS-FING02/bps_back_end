package com.sarp.controllers;

import java.util.List;

import com.sarp.classes.BusinessDisplay;
import com.sarp.classes.BusinessPuesto;
import com.sarp.classes.BusinessSector;
import com.sarp.classes.BusinessTramite;
import com.sarp.dao.controllers.DAODisplayController;
import com.sarp.dao.factory.DAOServiceFactory;
import com.sarp.enumerados.EstadoPuesto;
import com.sarp.json.modeler.JSONPuesto;
import com.sarp.json.modeler.JSONSector;
import com.sarp.json.modeler.JSONTramiteSector;
import com.sarp.services.AdminService;

public class AdminActionsController {

	public void altaPuesto(JSONPuesto puesto) throws Exception{	
		//Se delega a AdminService la implementacion
		AdminService adminService = new AdminService();
		adminService.altaPuesto(puesto);
	}
	
	public void bajaPuesto(JSONPuesto puesto) throws Exception{
		//Se delega a AdminService la implementacion
		AdminService adminService = new AdminService();
		adminService.bajaPuesto(puesto);
	}
	
	public void modificarPuesto(JSONPuesto puesto) throws Exception{
		//Se delega a AdminService la implementacion
		AdminService adminService = new AdminService();
		adminService.modificarPuesto(puesto);
	}
	
	public List<BusinessPuesto> listarPuestos(JSONSector sector) throws Exception{
		//Se delega a AdminService la implementacion
		AdminService adminService = new AdminService();
		return adminService.listarPuestos(sector);
	}
	
	public List<BusinessTramite> listarTramites(){
		//TODO FALTA HACER
		return null;
	}

	public void modificarEstadoPuesto(String nombreMaquina, String estado) {
		// TODO Auto-generated method stub
		
	}

	/** Alta, Baja & Modificacion Tramite **/
	
	public void altaTramite(BusinessTramite tramite) throws Exception {
		/* Notar que el AdminService es el de este mismo proyecto (SARPBusiness)
		  y no el AdminService de SARPServices */
		AdminService adminServ = new AdminService();
		adminServ.altaTramite(tramite);
	}
	
	public void bajaTramite(int codigo) throws Exception {
		/* Notar que el AdminService es el de este mismo proyecto (SARPBusiness)
		  y no el AdminService de SARPServices */
		AdminService adminServ = new AdminService();
		adminServ.bajaTramite(codigo);
	}
	
	public void modificarTramite(BusinessTramite tramite) throws Exception{
		/* Notar que el AdminService es el de este mismo proyecto (SARPBusiness)
		  y no el AdminService de SARPServices */
		AdminService adminServ = new AdminService();
		adminServ.modificarTramite(tramite);
	}
	
	/** Alta, Baja & Modificacion Sector **/
	
	public void altaModificacionSector(BusinessSector sector) throws Exception{
		AdminService adminService = new AdminService();
		adminService.altaModificacionSector(sector);
	}
	
	public void asignarTramiteSector(JSONTramiteSector tramSec) throws Exception {
		AdminService adminService = new AdminService();
		adminService.asignarTramiteSector(tramSec.getTramite().getCodigo(),tramSec.getSector().getCodigo());
	}
	
	
	public void asignarPuestoSector(JSONTramiteSector tramSec) throws Exception {
		AdminService adminService = new AdminService();
		adminService.asignarTramiteSector(tramSec.getTramite().getCodigo(),tramSec.getSector().getCodigo());
	}


	public void bajaSector(String idSector) throws Exception{
		AdminService adminService = new AdminService();
		adminService.bajaSector(idSector);
	}
	
	public  List<BusinessSector> listarSectores() throws Exception{
		AdminService adminService = new AdminService();
		return adminService.listarSectores();
	}
	
	/***********************DISPLAY***********************************/
	public void altaDisplay(String rutaArchivo) throws Exception{	
		AdminService adminServ = new AdminService();
		adminServ.altaDisplay(rutaArchivo);
		
	}
	
	public void bajaDisplay(Integer idDisplay) throws Exception{
		AdminService adminServ = new AdminService();
		adminServ.bajaDisplay(idDisplay);
		
	}
	
	public List<BusinessDisplay> listarDisplays(Integer sectorid) throws Exception{
		AdminService adminServ = new AdminService();
		return adminServ.listarDisplays(sectorid);
		
	}
	
	public void modificarRutaDisplay(Integer idDisplay, String rutaArchivo) throws Exception{
		AdminService adminServ = new AdminService();
		adminServ.modificarRutaDisplay(idDisplay, rutaArchivo);
	}

}
