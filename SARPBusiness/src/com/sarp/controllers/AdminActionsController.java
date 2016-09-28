package com.sarp.controllers;

import java.util.List;

import com.sarp.classes.BusinessDisplay;
import com.sarp.classes.BusinessPuesto;
import com.sarp.classes.BusinessSector;
import com.sarp.classes.BusinessTramite;
import com.sarp.dao.controllers.DAODisplayController;
import com.sarp.dao.factory.DAOServiceFactory;
import com.sarp.enumerados.EstadoPuesto;
import com.sarp.services.AdminService;

public class AdminActionsController {

	public void altaPuesto(String nombreMaquina) throws Exception{	
		//Se delega a AdminService la implementacion
		AdminService adminService = new AdminService();
		adminService.altaPuesto(nombreMaquina);
	}
	
	public void bajaPuesto(String nombreMaquina) throws Exception{
		//Se delega a AdminService la implementacion
		AdminService adminService = new AdminService();
		adminService.bajaPuesto(nombreMaquina);
	}
	
	public void modificarPuesto(String nombreMaquina,String estado, String usuarioId) throws Exception{
		//Se delega a AdminService la implementacion
		AdminService adminService = new AdminService();
		adminService.modificarPuesto(nombreMaquina, estado, usuarioId);
	}
	
	public List<BusinessPuesto> listarPuestos(Integer sectorId) throws Exception{
		//Se delega a AdminService la implementacion
		AdminService adminService = new AdminService();
		BusinessSector sector = new BusinessSector();
		sector.setSectorId(sectorId);
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
	
	public void asignarTramiteSector(Integer idSector, Integer idTramite) throws Exception {
		AdminService adminService = new AdminService();
		adminService.asignarTramiteSector(idSector, idTramite);
	}
	
	public void bajaSector(Integer idSector) throws Exception{
		AdminService adminService = new AdminService();
		adminService.bajaSector(idSector);
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
