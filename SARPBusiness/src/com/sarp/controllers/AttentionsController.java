package com.sarp.controllers;


import com.sarp.classes.BusinessNumero;
import com.sarp.services.AttentionService;
import com.sarp.services.NumberService;
import com.sarp.services.UserService;

public class AttentionsController {
	
	public void solicitarNumero(BusinessNumero num){
		NumberService serv = new NumberService();
		serv.solicitarNumero(num);
	}
	
	public void abrirPuesto(String nombreMaquina) throws Exception{	
		//Se delega a AttentionService la implementacion
		AttentionService attentionService = new AttentionService();
		attentionService.abrirPuesto(nombreMaquina);
		
	}
	public void cerrarPuesto(String nombreMaquina) throws Exception{	
		//Se delega a AttentionService la implementacion
		AttentionService attentionService = new AttentionService();
		attentionService.cerrarPuesto(nombreMaquina);
		
	}

}
