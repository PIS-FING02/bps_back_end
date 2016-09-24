package com.sarp.controllers;

import java.util.List;
import com.sarp.classes.BusinessPuesto;
import com.sarp.classes.BusinessSector;
import com.sarp.classes.BusinessTramite;
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
	
	public void modificarTramite(BusinessTramite tramite){
		/* Notar que el AdminService es el de este mismo proyecto (SARPBusiness)
		  y no el AdminService de SARPServices */
		AdminService adminServ = new AdminService();
		adminServ.modificarTramite(tramite);
	}
	
}
