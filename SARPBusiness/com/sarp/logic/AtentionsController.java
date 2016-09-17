package com.sarp.logic;

import com.sarp.services.NumeroService;

import classes.Numero;


public class AtentionsController {
	public void SolicitarNumero(Numero num){
		NumeroService serv = new NumeroService();
		serv.SolicitarNumero(num);
	}
}
