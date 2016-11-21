package com.sarp.facade;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.xml.registry.infomodel.User;
import javax.xml.ws.BindingProvider;

import com.sarp.classes.BusinessNodeGAFU;
import com.sarp.classes.BusinessSectorRol;
import com.sarp.utils.UtilService;

import uy.gub.bps.apph.wsgafuservice.v001.AreaFuncional;
import uy.gub.bps.apph.wsgafuservice.v001.ErrorNegocio;
import uy.gub.bps.apph.wsgafuservice.v001.ParamObtenerArbolAreaFuncional;
import uy.gub.bps.apph.wsgafuservice.v001.ParamObtenerAreasFuncionalesUsuario;
import uy.gub.bps.apph.wsgafuservice.v001.ResultObtenerArbolAreaFuncional;
import uy.gub.bps.apph.wsgafuservice.v001.ResultObtenerAreasFuncionalesUsuario;
import uy.gub.bps.apph.wsgafuservice.v001.SOAPException_Exception;
import uy.gub.bps.apph.wsgafuservice.v001.WsGafuService;
import uy.gub.bps.apph.wsgafuservice.v001.WsGafuServiceService;


public class GAFUFacade {
	
	
	private  String endpoint;
	private  String areaFuncional;
	private  String codSistema;
	private static GAFUFacade instancia;
	
	
	
	public GAFUFacade(){
		
		
		this.endpoint = UtilService.getStringProperty("GAFU_ENDPOINT");
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
	
	private  ResultObtenerArbolAreaFuncional obtenerSectoresGAFU() throws MalformedURLException{
		WsGafuServiceService service1 = new WsGafuServiceService(new URL(endpoint+"?wsdl"));
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

	public  BusinessNodeGAFU crearArbolGAFU() throws MalformedURLException{
		
		ResultObtenerArbolAreaFuncional result;
		result = obtenerSectoresGAFU();
		return crearArbol(null,result.getAreaFuncional());
	}
	
	public ResultObtenerAreasFuncionalesUsuario obtenerAreasFuncionalesUsuario( String idUsuario ) throws MalformedURLException {
		WsGafuServiceService service1 = new WsGafuServiceService(new URL(endpoint+"?wsdl"));
		WsGafuService port1 = service1.getWsGafuServicePort();
		((BindingProvider) port1).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,this.endpoint);
		ParamObtenerAreasFuncionalesUsuario paramObtenerAreasFuncionalesUsuario = new ParamObtenerAreasFuncionalesUsuario();
		paramObtenerAreasFuncionalesUsuario.setUsuarioNt(idUsuario);
		paramObtenerAreasFuncionalesUsuario.setCodSistema(this.codSistema);
		
		try {
			ResultObtenerAreasFuncionalesUsuario result = new ResultObtenerAreasFuncionalesUsuario();
			result =  port1.obtenerAreasFuncionalesUsuario(paramObtenerAreasFuncionalesUsuario);
			return result;
		} catch (SOAPException_Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
