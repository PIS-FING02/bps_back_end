package com.sarp.controllers;

import java.util.List;

import com.sarp.json.modeler.JSONNumero;
import com.sarp.services.NumberService;

public class NumberController {

	public List<JSONNumero> listarNumeros(String idSector) throws Exception {
		NumberService serv = new NumberService();
		return serv.listarNumeros(idSector);
	}
	
	public List<JSONNumero> listarNumerosPausados(String idPuesto) throws Exception {
		NumberService serv = new NumberService();
		return serv.listarNumerosPausados(idPuesto);
	}
}
