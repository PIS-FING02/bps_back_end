package com.sarp.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.sarp.classes.BusinessNodeGAFU;
import com.sarp.managers.GAFUManager;

public class GAFUController {
	
	private GAFUManager gafumgr;
	
	public GAFUController(){
		this.gafumgr = GAFUManager.getInstance();

	}
	
	public void crearArbolGAFU(){
		this.gafumgr.crearArbolGAFU();
	}
	
	public  void imprimirArbol(String appender) {
			imprimirSubArbol(this.gafumgr.getArbol()," ");
		}
	
	public  void imprimirSubArbol(BusinessNodeGAFU node, String appender) {
		   System.out.print(appender+appender+node.getCodigo());
		   System.out.println(" "+node.getNombre());
		   ArrayList<BusinessNodeGAFU> hijos = node.getHijos();
		   Iterator<BusinessNodeGAFU> iterator = hijos.iterator();
		   while (iterator.hasNext()) {
			    imprimirSubArbol(iterator.next(), appender + appender);
		   }
	}

	public BusinessNodeGAFU BusquedaNodo(String codigo){
		return this.BusquedaNodoAuxiliar(this.gafumgr.getArbol(), codigo);
	}
	
	private BusinessNodeGAFU BusquedaNodoAuxiliar(BusinessNodeGAFU node, String codigo){
		if(node.getCodigo().equals(codigo)){ 
			return node; 
		}
		else { 
			BusinessNodeGAFU res; 
		List<BusinessNodeGAFU> hijos = node.getHijos();
		Iterator<BusinessNodeGAFU> it = hijos.iterator();
		while (it.hasNext()) {
			res=BusquedaNodoAuxiliar(it.next(),codigo);
			if(res != null){ 
				return res; 
			} 
		} 
		return null;
	}
	}
	
}

