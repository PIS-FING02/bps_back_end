package com.sarp.controllers;

import java.util.List;

import com.sarp.json.modeler.JSONNumero;
import com.sarp.json.modeler.JSONSectorCantNum;
import com.sarp.services.NumberService;

public class NumberController {

	public List<JSONNumero> listarNumerosSector(String idSector) throws Exception {
		NumberService serv = new NumberService();
		return serv.listarNumerosSector(idSector);
	}
	
	public List<JSONNumero> listarNumerosPausados(String idPuesto) throws Exception {
		NumberService serv = new NumberService();
		return serv.listarNumerosPausados(idPuesto);
	}
	
	public List<JSONNumero> listarNumerosPausadosSector(String idSector) throws Exception {
		NumberService serv = new NumberService();
		return serv.listarNumerosPausadosSector(idSector);
	}
	
	public List<JSONNumero> listarNumerosAtrasados(String idPuesto) throws Exception {
		NumberService serv = new NumberService();
		return serv.listarNumerosAtrasados(idPuesto);
	}
	
	public List<JSONNumero> listarNumerosAtrasadosSector(String idSector) throws Exception {
		NumberService serv = new NumberService();
		return serv.listarNumerosAtrasadosSector(idSector);
	}
	
	public List<JSONNumero> listarNumerosEnEspera(String idPuesto) throws Exception {
		NumberService serv = new NumberService();
		return serv.listarNumerosEnEspera(idPuesto);
	}
	
	public List<JSONNumero> listarNumerosEnEsperaSector(String idSector) throws Exception {
		NumberService serv = new NumberService();
		return serv.listarNumerosEnEsperaSector(idSector);
	}
	
	public List<JSONSectorCantNum> obtenerCantNumerosEnEspera(String idPuesto) throws Exception {
		NumberService serv = new NumberService();
		return serv.obtenerCantNumerosEnEsperaSector(idPuesto);
	}
}
