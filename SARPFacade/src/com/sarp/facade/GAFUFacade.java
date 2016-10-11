package com.sarp.facade;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.xml.registry.infomodel.User;
import javax.xml.ws.BindingProvider;

import com.sarp.classes.BusinessNodeGAFU;

import uy.gub.bps.apph.wsgafuservice.v001.AreaFuncional;
import uy.gub.bps.apph.wsgafuservice.v001.ParamObtenerArbolAreaFuncional;
import uy.gub.bps.apph.wsgafuservice.v001.ResultObtenerArbolAreaFuncional;
import uy.gub.bps.apph.wsgafuservice.v001.SOAPException_Exception;
import uy.gub.bps.apph.wsgafuservice.v001.WsGafuService;
import uy.gub.bps.apph.wsgafuservice.v001.WsGafuServiceService;


public class GAFUFacade {
	
	
	private  String endpoint;
	private  String areaFuncional;
	private  String codSistema;
	private static GAFUFacade instancia;
	
	
	
	public GAFUFacade(){
		
		/*String path_os;
		String[] result;
		String path;
		String location = GAFUFacade.class.getProtectionDomain().getCodeSource().getLocation().getPath().toString();
		result = location.split("/standalone");
		path_os = "/modules/conf/config_base.properties";
		path= result[0] + path_os;
		Properties prop = new Properties();
		InputStream input;
		try {
			input = new FileInputStream(path);
			prop.load(input);
			this.endpoint= prop.getProperty("ENDPOINT").toString();
			this.areaFuncional =prop.getProperty("AREA_FUNCIONAL").toString();
			this.codSistema = prop.getProperty("SISTEMA").toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
		this.endpoint = "http://52.52.100.160:8080/GAFU/WsGafuService";
		this.areaFuncional = "BPS";
		this.codSistema = "GAP";
	}
	
	public static GAFUFacade getInstance(){
		if (instancia == null){
			instancia = new GAFUFacade();
			return instancia;
		}else{
			return instancia;
		}
	}
	
	private  ResultObtenerArbolAreaFuncional obtenerSectoresGAFU(){
		WsGafuServiceService service1 = new WsGafuServiceService();
		WsGafuService port1 = service1.getWsGafuServicePort();
		((BindingProvider) port1).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,this.endpoint);
		ParamObtenerArbolAreaFuncional paramObtenerArbolAreaFuncional = new ParamObtenerArbolAreaFuncional();
		paramObtenerArbolAreaFuncional.setCodAreaFuncional(this.areaFuncional);
		paramObtenerArbolAreaFuncional.setCodSistema(this.codSistema);
		try {
			return port1.obtenerArbolAreaFuncional(paramObtenerArbolAreaFuncional);
		} catch (SOAPException_Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private  BusinessNodeGAFU crearArbol(BusinessNodeGAFU af_padre, AreaFuncional af_hijo){
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

	public  BusinessNodeGAFU crearArbolGAFU(){
		
		ResultObtenerArbolAreaFuncional result;
		result = obtenerSectoresGAFU();
		return crearArbol(null,result.getAreaFuncional());
	}

}
