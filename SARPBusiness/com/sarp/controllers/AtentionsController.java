package com.sarp.controllers;


import com.sarp.classes.BusinessNumero;
import com.sarp.classes.BusinessSector;
import com.sarp.classes.BusinessTramite;
import com.sarp.services.NumeroService;


public class AtentionsController {
	public void SolicitarNumero(BusinessNumero num){
		NumeroService serv = new NumeroService();
		serv.SolicitarNumero(num);
	}
}
