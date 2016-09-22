package com.sarp.managers;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.xml.ws.BindingProvider;

import com.sarp.classes.BusinessNodeGAFU;

import uy.gub.bps.apph.wsgafuservice.v001.AreaFuncional;
import uy.gub.bps.apph.wsgafuservice.v001.ParamObtenerArbolAreaFuncional;
import uy.gub.bps.apph.wsgafuservice.v001.ResultObtenerArbolAreaFuncional;
import uy.gub.bps.apph.wsgafuservice.v001.SOAPException_Exception;
import uy.gub.bps.apph.wsgafuservice.v001.WsGafuService;
import uy.gub.bps.apph.wsgafuservice.v001.WsGafuServiceService;



public class GAFUManager {
	
	private  BusinessNodeGAFU arbol;
	private  static GAFUManager instancia;
	    
	    public GAFUManager() {
	        this.setArbol(null);
	    }
	    
		public static GAFUManager getInstance(){
			if (instancia == null){
				instancia = new GAFUManager();
				return instancia;
			}else{
				return instancia;
			}
		}

		public  BusinessNodeGAFU getArbol() {
			return this.arbol;
		}

		public  void setArbol(BusinessNodeGAFU arbol) {
			this.arbol = arbol;
		}
		
		public void crearArbolGAFU(){
			WsGafuServiceService service1 = new WsGafuServiceService();
			WsGafuService port1 = service1.getWsGafuServicePort();
			((BindingProvider) port1).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
					"http://52.52.100.160:8080/GAFU/WsGafuService");
			ParamObtenerArbolAreaFuncional paramObtenerArbolAreaFuncional = new ParamObtenerArbolAreaFuncional();
			paramObtenerArbolAreaFuncional.setCodAreaFuncional("BPS");
			paramObtenerArbolAreaFuncional.setCodSistema("GAP");
			ResultObtenerArbolAreaFuncional result;
			try {
				result = port1.obtenerArbolAreaFuncional(paramObtenerArbolAreaFuncional);
				setArbol(crearArbol(null,result.getAreaFuncional()));
			} catch (SOAPException_Exception e) {
				e.printStackTrace();
			}
		}
				
		public BusinessNodeGAFU crearArbol(BusinessNodeGAFU af_padre, AreaFuncional af_hijo){
			Date fecha_desde = null;
			Date fecha_hasta = null;
			if(af_hijo.getFechaDesde()!=null){
				fecha_desde =af_hijo.getFechaDesde().toGregorianCalendar().getTime();
			}
			if(af_hijo.getFechaHasta()!=null){
				fecha_hasta =af_hijo.getFechaHasta().toGregorianCalendar().getTime();
			}
			BusinessNodeGAFU treeRootNode = new BusinessNodeGAFU(af_hijo.getCodigo(),af_hijo.getDescripcion(),fecha_desde,fecha_hasta,af_hijo.getNombre(), af_padre, af_hijo.getRestriccion());
			treeRootNode.setPadre(af_padre);
			List<AreaFuncional> hijos = af_hijo.getHijos();
			Iterator<AreaFuncional> it = hijos.iterator();
			while (it.hasNext()) {
				treeRootNode.addHijo(crearArbol(treeRootNode,it.next()));

			}
			return treeRootNode;
		}
		
}