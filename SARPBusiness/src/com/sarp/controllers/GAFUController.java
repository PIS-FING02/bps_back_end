package com.sarp.controllers;

import com.sarp.classes.BusinessNodeGAFU;
import com.sarp.managers.GAFUManager;

public class GAFUController {
	
	private GAFUManager gafumgr;
	
	public GAFUController(){
		this.gafumgr = GAFUManager.getInstance();

	}
	
	public void actualizarArbolGAFU(){
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


}

