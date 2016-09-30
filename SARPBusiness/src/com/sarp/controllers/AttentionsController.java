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
	
	public void abrirPuesto(String nombreMaquina, String usuarioId) throws Exception{	
		//Se delega a AttentionService la implementacion
		AttentionService attentionService = new AttentionService();
		attentionService.abrirPuesto(nombreMaquina,usuarioId);
		
	}
	public void cerrarPuesto(String nombreMaquina) throws Exception{	
		//Se delega a AttentionService la implementacion
		AttentionService attentionService = new AttentionService();
		attentionService.cerrarPuesto(nombreMaquina);
		
	}
	public void comenzarAtencion(String nombreMaquina) throws Exception{	
		//Se delega a AttentionService la implementacion
		AttentionService attentionService = new AttentionService();
		attentionService.comenzarAtencion(nombreMaquina);
	}
	public void finalizarAtencion(String nombreMaquina) throws Exception{	
		//Se delega a AttentionService la implementacion
		AttentionService attentionService = new AttentionService();
		attentionService.finalizarAtencion(nombreMaquina);
		
	}
	

}
