package com.sarp.logic;


import com.sarp.services.NumeroService;

import com.sarp.clases.Numero;


import com.sarp.clases.Sector;
import com.sarp.clases.Tramite;


public class AtentionsController {
	public void SolicitarNumero(Numero num){
		NumeroService serv = new NumeroService();
		serv.SolicitarNumero(num);
	}
}
