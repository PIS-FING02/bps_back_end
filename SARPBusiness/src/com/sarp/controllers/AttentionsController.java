package com.sarp.controllers;


import com.sarp.classes.BusinessNumero;
import com.sarp.json.modeler.JSONPuesto;
import com.sarp.services.AttentionService;
import com.sarp.services.NumberService;
import com.sarp.services.UserService;

public class AttentionsController {
	
	public void solicitarNumero(BusinessNumero num){
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
	
	

}
