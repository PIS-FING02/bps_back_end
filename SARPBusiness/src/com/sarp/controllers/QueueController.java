package com.sarp.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.sarp.classes.BusinessNumero;
import com.sarp.classes.BusinessTramite;
import com.sarp.json.modeler.JSONNumero;
import com.sarp.json.modeler.JSONSectorCantNum;
import com.sarp.service.response.maker.ResponseMaker;
import com.sarp.services.QueueService;

public class QueueController {
	
	private static QueueController instance;
	private QueueController(){};
	
	public static QueueController getInstance(){
		instance = instance != null ? instance : new QueueController();
		return instance;
	}
	
	public void crearColaSector(String idSector) throws IOException{
		QueueService qServ = new QueueService(idSector);
		qServ.crearColaSector();
	}
	
	public void borrarColaSector(String idSector) throws IOException {
		QueueService qServ = new QueueService(idSector);
		qServ.borrarColaSector();
	}
	
	public void agregarNumero(String idSector, BusinessNumero numero) throws Exception {
		//Este metodo agrega un nuevo numero a la cola.
		QueueService qServ = new QueueService(idSector);
		qServ.agregarNumero(numero);
	}
	
	public void agregarNumerosBatch(String idSector){
		QueueService qServ = new QueueService(idSector);
		qServ.agregarNumerosBatch();
	}

	public void  transferirAColaAtrasados(String idSector, BusinessNumero numero){
		//Este metodo transfiere un numero de la cola a la lista de atreasados.
		QueueService qServ = new QueueService(idSector);
		qServ.transferirAColaAtrasados(numero);
	}

	public void transferirAColaPausados(String idSector, BusinessNumero numero){
		//Este metodo transfiere un numero de la cola a la lista de pausados.
		QueueService qServ = new QueueService(idSector);
		qServ.transferirAColaPausados(numero);
	}

	public JSONNumero llamarProximoNumero(String idSector, ArrayList<BusinessTramite> tramites){
		QueueService qServ = new QueueService(idSector);
		return qServ.llamarProximoNumero(tramites);
	}
	
	public List<JSONNumero> listarAtrasados(String idSector, List<BusinessTramite> tramites){
		QueueService qServ = new QueueService(idSector);
		return qServ.listarAtrasados(tramites);
	}
	
	public List<JSONNumero> listarPausados(String idSector, List<BusinessTramite> tramites){
		QueueService qServ = new QueueService(idSector);
		return qServ.listarPausados(tramites);
	}
	
	public List<JSONNumero> listarEnEspera(String idSector, List<BusinessTramite> tramites){
		QueueService qServ = new QueueService(idSector);
		return qServ.listarEnEspera(tramites);
	}

	public void quitarNumeroDeCola(String idSector, Integer idNumero) throws Exception{
		QueueService qServ = new QueueService(idSector);
		try{
			qServ.quitarNumeroDeCola(idNumero);
		}catch(Exception e){
			throw e;
		}
	}

	public void quitarNumeroDeAtrasados(String idSector, Integer idNumero){
		QueueService qServ = new QueueService(idSector);
		qServ.quitarNumeroDeAtrasados(idNumero);
	}

	public void quitarNumeroDePausados(String idSector, Integer idNumero){
		QueueService qServ = new QueueService(idSector);
		qServ.quitarNumeroDePausados(idNumero);
	}

	public JSONNumero obtenerNumeroAtrasado(String idSector, Integer idNumero) throws Exception{
		QueueService qServ = new QueueService(idSector);
		return qServ.obtenerNumeroAtrasado(idNumero);
	}
	
	public JSONNumero obtenerNumeroPausado(String idSector, Integer idNumero) throws Exception{
		QueueService qServ = new QueueService(idSector);
		return qServ.obtenerNumeroPausado(idNumero);
	}
	
	public JSONNumero obtenerNumeroDemanda(String idSector, Integer idNumero) throws Exception{
		QueueService qServ = new QueueService(idSector);
		return qServ.obtenerNumeroDemanda(idNumero);
	}
	
	public List<JSONNumero> obtenerTodosLosNumeros(String idSector) throws Exception{
		QueueService qServ = new QueueService(idSector);
		return qServ.obtenerTodosLosNumeros();
	}
	
	public int obtenerProxNumero(String idSector) throws Exception{
		QueueService qServ = new QueueService(idSector);
		return qServ.obtenerProxNumero();
	}
	
	public void restaurarProxNumero(String idSector) throws Exception {
		QueueService qServ = new QueueService(idSector);
		qServ.restaurarProxNumero();
	}
	
	public int obtenerCantNumerosEnEspera(String idSector, List<BusinessTramite> tramites) throws Exception {
		QueueService qServ = new QueueService(idSector);
		return qServ.obtenerCantNumerosEnEspera(tramites);
	}
	
}
