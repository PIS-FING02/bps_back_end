package com.sarp.controllers;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.sarp.classes.BusinessNumero;
import com.sarp.classes.BusinessSectorQueue;
import com.sarp.classes.BusinessTramite;
import com.sarp.dao.controllers.DAONumeroController;
import com.sarp.dao.factory.DAOServiceFactory;
import com.sarp.managers.QueuesManager;

public class QueueController {
	
	private BusinessSectorQueue cola;
	
	public QueueController(int idSector){
		this.cola = QueuesManager.getInstance().obtenerColaSector(idSector);
	}
	
	//
	public void agregarNumero(BusinessNumero numero){
		//Este metodo agrega un nuevo numero a la cola.
		this.cola.agregarNumeroCola(numero);
	}
	
	public void agregarNumerosBatch(){
		DAOServiceFactory factory = DAOServiceFactory.getInstance();
		DAONumeroController daoNumero = factory.getDAONumeroController();
		LinkedList<BusinessNumero> numerosDiarios = daoNumero.listarNumerosDelDia();
		this.cola.agregarNumeroColaBatch(numerosDiarios);
		
	}

	public void  transferirColaAtrasados(BusinessNumero numero){
		//Este metodo transfiere un numero de la cola a la lista de atreasados.
		this.cola.agregarNumeroAtrasado(numero);
	}

	public void trasnferirColaPausados(BusinessNumero numero){
		//Este metodo transfiere un numero de la cola a la lista de pausados.
		this.cola.agregarNumeroPausado(numero);
	}

	public BusinessNumero llamarProximoNumero(List<BusinessTramiteSector> tramite){
		return this.cola.llamarNumeroCola(tramite);
	}
	
	public BusinessNumero[] listarAtrasados(){
		return this.cola.obtenerListaAtrasados();
	}
	
	public BusinessNumero[] listarPausados(){
		return this.cola.obtenerListaPausados();
	}

	public void quitarNumeroCola(int idNumero){
		this.cola.quitarNumeroCola(idNumero);
	}

	public void quitarNumeroAtrasado(int idNumero){
		this.cola.quitarNumeroAtrasado(idNumero);
	}

	public void quitarNumeroPausado(int idNumero){
		this.cola.quitarNumeroPausado(idNumero);
	}

	public BusinessNumero obtenerNumeroAtrasado(int idNumero) throws IOException{
		return this.cola.obtenerNumeroAtrasado(idNumero);
	}
	
	public BusinessNumero obtenerNumeroPausado(int idNumero) throws IOException{
		return this.cola.obtenerNumeroPausado(idNumero);
	}
	
	





}
