package com.sarp.controllers;

import java.util.List;

import com.sarp.classes.BusinessNodeGAFU;
import com.sarp.classes.BusinessSectorRol;
import com.sarp.managers.GAFUManager;

public class GAFUController {
	
	private GAFUManager gafumgr;
	
	public GAFUController(){
		this.gafumgr = GAFUManager.getInstance();
	}
	
	public void actualizarArbolGAFU() throws Exception{
		this.gafumgr.actualizarArbolGAFU();
	}
	
	public void imprimirArbol(String appender){
		this.gafumgr.imprimirArbol(appender);
	}

	public void imprimirSubArbol(BusinessNodeGAFU node, String appender){
		this.gafumgr.imprimirSubArbol(node, appender);
	}
	
	public BusinessNodeGAFU BusquedaNodo(String codigo){
		return this.gafumgr.BusquedaNodo(codigo);
	}
	public List<BusinessSectorRol>  obtenerSectorRolesUsuario ( String idUsuario ) throws Exception{
		System.out.println("entro al controler");
		return this.gafumgr.obtenerSectorRolesUsuario(idUsuario);
	}
	public List<BusinessSectorRol>  obtenerSectorRolesUsuario ( String idUsuario, String rol )throws Exception{
		return this.gafumgr.obtenerSectorRolesUsuario(idUsuario,rol);
	}
}

