package com.sarp.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.sarp.classes.BusinessNumero;
import com.sarp.classes.BusinessSector;
import com.sarp.classes.BusinessTramite;
import com.sarp.controllers.AdminActionsController;
import com.sarp.dao.controllers.DAOAdminController;
import com.sarp.dao.controllers.DAONumeroController;
import com.sarp.dao.controllers.DAOSectorController;
import com.sarp.dao.factory.DAOServiceFactory;
import com.sarp.factory.Factory;
import com.sarp.json.modeler.JSONNumero;
import com.sarp.json.modeler.JSONSector;
import com.sarp.json.modeler.JSONSectorCantNum;
import com.sarp.managers.BusinessSectorQueue;
import com.sarp.managers.QueuesManager;
import com.sarp.service.response.maker.RequestMaker;
import com.sarp.service.response.maker.ResponseMaker;

public class QueueService {

	private BusinessSectorQueue cola;
	private String idSector;

	public QueueService(String idSector) {
		this.idSector = idSector;
		this.cola = QueuesManager.getInstance().obtenerColaSector(idSector);
	}

	public void crearColaSector() throws IOException {
		QueuesManager manejador = QueuesManager.getInstance();
		manejador.crearColaSector(this.idSector);
	}

	public void borrarColaSector() throws IOException {
		QueuesManager manejador = QueuesManager.getInstance();
		manejador.borrarColaSector(this.idSector);
	}

	public void agregarNumero(BusinessNumero numero) throws Exception {
		this.cola.agregarNumeroCola(numero);
	}

	public void agregarNumerosBatch() {
		DAOServiceFactory factory = DAOServiceFactory.getInstance();
		DAONumeroController daoNumero = factory.getDAONumeroController();
		List<BusinessNumero> numerosDiarios = daoNumero.listarNumerosDelDia();
		this.cola.agregarNumeroColaBatch(numerosDiarios);
	}

	public void transferirAColaAtrasados(BusinessNumero numero) {
		// Este metodo transfiere un numero de la cola a la lista de atreasados.
		this.cola.agregarNumeroAtrasado(numero);
	}

	public void transferirAColaPausados(BusinessNumero numero) {
		// Este metodo transfiere un numero de la cola a la lista de pausados.
		this.cola.agregarNumeroPausado(numero);
	}

	public JSONNumero llamarProximoNumero(List<BusinessTramite> tramites) {
		ResponseMaker respMaker = ResponseMaker.getInstance();
		BusinessNumero numero = this.cola.llamarNumeroCola(tramites);
		return respMaker.numeroAtomResponse(numero);
	}

	public List<JSONNumero> listarAtrasados(List<BusinessTramite> tramites) {
		ResponseMaker respMaker = ResponseMaker.getInstance();
		List<BusinessNumero> listaNros = this.cola.obtenerListaAtrasados(tramites);
		return respMaker.createListJSONNumeros(listaNros);
	}

	public List<JSONNumero> listarPausados(List<BusinessTramite> tramites) {
		ResponseMaker respMaker = ResponseMaker.getInstance();
		List<BusinessNumero> listaNros = this.cola.obtenerListaPausados(tramites);
		return respMaker.createListJSONNumeros(listaNros);
	}
	
	public List<JSONNumero> listarEnEspera(List<BusinessTramite> tramites) {
		ResponseMaker respMaker = ResponseMaker.getInstance();
		List<BusinessNumero> listaNros = this.cola.obtenerListaEnEspera(tramites);
		return respMaker.createListJSONNumeros(listaNros);
	}

	public void quitarNumeroDeCola(Integer idNumero) throws Exception {
		try {
			this.cola.quitarNumeroCola(idNumero);
		} catch (Exception e) {
			throw e;
		}
	}

	public void quitarNumeroDeAtrasados(Integer idNumero) {
		this.cola.quitarNumeroAtrasado(idNumero);
	}

	public void quitarNumeroDePausados(Integer idNumero) {
		this.cola.quitarNumeroPausado(idNumero);
	}

	public JSONNumero obtenerNumeroAtrasado(Integer idNumero) throws Exception {
		ResponseMaker respMaker = ResponseMaker.getInstance();
		BusinessNumero numero = this.cola.obtenerNumeroAtrasado(idNumero);
		return respMaker.numeroAtomResponse(numero);
	}

	public JSONNumero obtenerNumeroPausado(Integer idNumero) throws IOException {
		ResponseMaker respMaker = ResponseMaker.getInstance();
		BusinessNumero numero = this.cola.obtenerNumeroPausado(idNumero);
		return respMaker.numeroAtomResponse(numero);
	}
	
	public JSONNumero obtenerNumeroDemanda(Integer idNumero) throws IOException {
		ResponseMaker respMaker = ResponseMaker.getInstance();
		BusinessNumero numero = this.cola.obtenerNumeroDemanda(idNumero);
		return respMaker.numeroAtomResponse(numero);
	}
	
	public int obtenerProxNumero() throws Exception{
		return this.cola.obtenerProxNumero();
	}
	
	public void restaurarProxNumero() throws Exception {
		this.cola.restaurarProxNumero();
	}
	
	public int obtenerCantNumerosEnEspera(List<BusinessTramite> tramites) throws Exception {
		return this.cola.obtenerCantNumerosEnEspera(tramites);
	}

	/**** listar numeros *******/
	public List<JSONNumero> obtenerTodosLosNumeros() throws IOException {
		ResponseMaker respMaker = ResponseMaker.getInstance();
		List<BusinessNumero> listaNumeros = this.cola.listarNumeros();
		List<JSONNumero> listaNros = respMaker.createListJSONNumeros(listaNumeros);
		return listaNros;
	}

}
