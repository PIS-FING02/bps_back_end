package com.sarp.controllers;


import com.sarp.classes.BusinessNumero;
import com.sarp.services.NumberService;

public class AtentionsController {
	
	public void solicitarNumero(BusinessNumero num){
		NumberService serv = new NumberService();
		serv.solicitarNumero(num);
	}

}
