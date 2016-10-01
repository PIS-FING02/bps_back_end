package com.sarp.managers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.sarp.classes.BusinessNodeGAFU;
import com.sarp.classes.BusinessSector;
import com.sarp.controllers.AdminActionsController;
import com.sarp.facade.GAFUFacade;
import com.sarp.factory.Factory;


public class GAFUManager {
	
	private  BusinessNodeGAFU arbol;
	private  static GAFUManager instancia;
	    
	private GAFUManager() {
			this.arbol =  GAFUFacade.crearArbolGAFU();
	    }

	public void actualizarArbolGAFU() throws Exception{
		Factory fac = Factory.getInstance();
		AdminActionsController ctrl = fac.getAdminActionsController();
		BusinessNodeGAFU nuevo = GAFUFacade.crearArbolGAFU();
		List<BusinessSector> nuevosSectores = arbolToList(nuevo);
		actualizarSectores(nuevosSectores, ctrl);
		this.arbol = nuevo;
	}
	

	private void actualizarSectores(List<BusinessSector> nuevosSectores,AdminActionsController ctrl) throws Exception {
		Iterator<BusinessSector> iterator = nuevosSectores.iterator();
		while (iterator.hasNext()) {
			//System.out.println(iterator.next().getNombre());
			ctrl.altaModificacionSector(iterator.next());
		}
	}

	public List<BusinessSector> arbolToList(BusinessNodeGAFU node){
		ArrayList<BusinessNodeGAFU> hijos = node.getHijos();
		Iterator<BusinessNodeGAFU> iterator = hijos.iterator();
		List<BusinessSector> ret = new ArrayList<BusinessSector>();
		BusinessSector sector = new BusinessSector(node.getCodigo(), node.getNombre(), node.obtenerCamino());
		ret.add(sector);
		while (iterator.hasNext()) {
			ret.addAll(arbolToList(iterator.next()));
		}
		return ret;
	}
	
	public static GAFUManager getInstance(){
			if (instancia == null){
				instancia = new GAFUManager();
				return instancia;
			}else{
				return instancia;
			}
		}
		
	public  void imprimirArbol(String appender) {
			imprimirSubArbol(this.arbol," ");
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
		return this.BusquedaNodoAuxiliar(this.arbol, codigo);
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