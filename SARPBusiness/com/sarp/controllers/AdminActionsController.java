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
	
	public void modificarUsuarioPuesto(String nombreMaquina, String usuarioId) throws Exception{
		//Se delega a AdminService la implementacion
		AdminService adminService = new AdminService();
		adminService.modificarUsuarioPuesto(nombreMaquina, usuarioId);
	}

	public void modificarEstadoPuesto(String nombreMaquina, EstadoPuesto estado) throws Exception{
		//Se delega a AdminService la implementacion
		AdminService adminService = new AdminService();
		adminService.modificarEstadoPuesto(nombreMaquina,estado);
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

	
}
