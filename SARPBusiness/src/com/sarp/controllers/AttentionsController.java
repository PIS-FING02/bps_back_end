package com.sarp.controllers;

import com.sarp.json.modeler.JSONNumero;
import com.sarp.json.modeler.JSONPuesto;
import com.sarp.services.AttentionService;
import com.sarp.services.NumberService;

public class AttentionsController {
	
	private static  AttentionsController instance;
	private AttentionsController(){};
	
	public static AttentionsController getInstance(){
		instance = instance != null ? instance : new AttentionsController();
		return instance;
	}
	
	public void solicitarNumero(JSONNumero num) throws Exception {
		NumberService serv = new NumberService();
		serv.solicitarNumero(num);
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
	public void finalizarAtencion(JSONPuesto puesto) throws Exception{	
		//Se delega a AttentionService la implementacion
		AttentionService attentionService = new AttentionService();
		attentionService.finalizarAtencion(puesto);
		
	}
	
	public JSONNumero llamarNumero(JSONPuesto puesto) throws Exception{
		//Se delega a AttentionService la implementacion
		AttentionService attentionService = new AttentionService();
		return attentionService.llamarNumero(puesto);
	}
	
	public void atrasarNumero(JSONPuesto puesto) throws Exception{
		//Se delega a AttentionService la implementacion
		AttentionService attentionService = new AttentionService();
		attentionService.atrasarNumero(puesto);
	}
	
	

}
