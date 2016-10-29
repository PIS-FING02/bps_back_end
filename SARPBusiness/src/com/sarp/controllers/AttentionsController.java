package com.sarp.controllers;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;


import com.sarp.json.modeler.JSONFinalizarAtencion;

import com.sarp.classes.BusinessNumero;
import com.sarp.classes.BusinessSector;
import com.sarp.classes.BusinessTramite;
import com.sarp.dao.controllers.DAONumeroController;
import com.sarp.dao.controllers.DAOPuestoController;
import com.sarp.dao.controllers.DAOSectorController;
import com.sarp.dao.controllers.DAOTramiteController;
import com.sarp.dao.factory.DAOServiceFactory;
import com.sarp.enumerados.EstadoNumero;

import com.sarp.json.modeler.JSONNumero;
import com.sarp.json.modeler.JSONPuesto;
import com.sarp.json.modeler.JSONSector;
import com.sarp.json.modeler.JSONTramiteSector;
import com.sarp.service.response.maker.ResponseMaker;
import com.sarp.services.AttentionService;
import com.sarp.services.NumberService;
import com.sarp.utils.DesvioSectoresUtils;

public class AttentionsController {
	
	private static  AttentionsController instance;
	private AttentionsController(){};
	
	public static AttentionsController getInstance(){
		instance = instance != null ? instance : new AttentionsController();
		return instance;
	}
	
	public String solicitarNumero(JSONNumero num) throws Exception {
		NumberService serv = new NumberService();
		return serv.solicitarNumero(num);
	}
	
	public void abrirPuesto(JSONPuesto puesto) throws Exception{	
		//Se delega a AttentionService la implementacion
		AttentionService attentionService = new AttentionService();
		attentionService.abrirPuesto(puesto);
		
	}
	public void cerrarPuesto(JSONPuesto puesto) throws Exception{	
		//Se delega a AttentionService la implementacion
		AttentionService attentionService = new AttentionService();
		attentionService.cerrarPuesto(puesto);
		
	}
	public void comenzarAtencion(JSONPuesto puesto) throws Exception{	
		//Se delega a AttentionService la implementacion
		AttentionService attentionService = new AttentionService();
		attentionService.comenzarAtencion(puesto);
	}
	public void finalizarAtencion(JSONFinalizarAtencion finalizarAtencion) throws Exception{	
		//Se delega a AttentionService la implementacion
		AttentionService attentionService = new AttentionService();
		attentionService.finalizarAtencion(finalizarAtencion,false);
	}
	
	public List<JSONTramiteSector> tramitesRecepcion(String puesto) throws Exception{
		//Se delega a AttentionService la implementacion
		AttentionService attentionService = new AttentionService();
		return attentionService.tramitesRecepcion(puesto);
	}
	public JSONNumero llamarNumero(String puesto) throws Exception{
		//Se delega a AttentionService la implementacion
		AttentionService attentionService = new AttentionService();
		return attentionService.llamarNumero(puesto);
	}
	
	public void atrasarNumero(JSONPuesto puesto) throws Exception{
		//Se delega a AttentionService la implementacion
		AttentionService attentionService = new AttentionService();
		attentionService.atrasarNumero(puesto);
	}
	
	public void pausarNumero(JSONPuesto puesto) throws Exception{
		//Se delega a AttentionService la implementacion
		AttentionService attentionService = new AttentionService();
		attentionService.pausarNumero(puesto);
	}
	
	public JSONNumero llamarNumeroPausado(Integer idNumero, String idPuesto) throws Exception{
		AttentionService attentionService = new AttentionService();
		return attentionService.llamarNumeroPausado(idNumero, idPuesto);
	}
	
	public JSONNumero llamarNumeroAtrasado(Integer idNumero, String idPuesto) throws Exception{
		AttentionService attentionService = new AttentionService();
		return attentionService.llamarNumeroAtrasado(idNumero, idPuesto);
	}
	
	public JSONNumero llamarNumeroDemanda(Integer idNumero, String idPuesto) throws Exception{
		AttentionService attentionService = new AttentionService();
		return attentionService.llamarNumeroDemanda(idNumero, idPuesto);
	}
	
	public List<JSONSector> obtenerSectoresDesvio(String idSector) throws Exception {
		AttentionService attentionService = new AttentionService();
		return attentionService.obtenerSectoresDesvio(idSector);
	
	}
	
	public void desviarNumero(String idSectorDesvio,JSONFinalizarAtencion finalizarAtencion) throws Exception {
		AttentionService attentionService = new AttentionService();
		attentionService.desviarNumero(idSectorDesvio,finalizarAtencion);	
				
	}
	
	public void reLlamarNumero(String idPuesto) throws Exception {
		AttentionService attentionService = new AttentionService();
		attentionService.reLlamarNumero(idPuesto);	
				
	}


}
