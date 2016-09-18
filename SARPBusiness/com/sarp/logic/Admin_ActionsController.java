package com.sarp.logic;

import java.util.Calendar;
import com.sarp.services.AdminService;//DaoServices Factory

public class Admin_ActionsController {

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
	
	public void modificarUsuarioPuesto(String nombreMaquina) throws Exception{
		//Se delega a AdminService la implementacion
		AdminService adminService = new AdminService();
		adminService.modificarUsuarioPuesto(nombreMaquina);
	}

	public void modificarEstadoPuesto(String nombreMaquina, Estado estado) throws Exception{
		//Se delega a AdminService la implementacion
		AdminService adminService = new AdminService();
		adminService.modificarEstadoPuesto(nombreMaquina,estado);
	}
	
	public List<BusinessPuesto> listarPuestos(String sector) throws Exception{
		//Se delega a AdminService la implementacion
		AdminService adminService = new AdminService();
		return adminService.listarPuestos(sector);
	}
	

	
}
