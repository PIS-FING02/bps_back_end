package com.sarp.classes;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.ListIterator;


public class BusinessSectorQueue {
	
	private LinkedList<BusinessNumero> cola;
	private LinkedList<BusinessNumero> atrasados;
	private LinkedList<BusinessNumero> pausados;
	
	public BusinessSectorQueue(){
		this.cola = new LinkedList<BusinessNumero>();
		this.atrasados =new LinkedList<BusinessNumero>();
		this.pausados = new LinkedList<BusinessNumero>();
	}
	
	/*****     Metodos de la Cola      *****/
	
	public synchronized void agregarNumeroCola(BusinessNumero numero){
		
		
	}
	
	public synchronized void agregarNumeroColaBatch(LinkedList<BusinessNumero> numeros){
		this.cola.addAll(numeros);
	}
	
	public synchronized void quitarNumeroCola(int idNumero){
		ListIterator<BusinessNumero> it = this.cola.listIterator();
		while(it.hasNext()){
			BusinessNumero numero = it.next();
			if(numero.getInternalId() == idNumero){
				this.cola.remove(numero);
				break;
			}	
		}	
	}
	
	public synchronized BusinessNumero llamarNumeroCola(){
		return this.cola.poll();
	}
	
	
	/*****     Metodos de Atrasados      *****/
	
	public synchronized void agregarNumeroAtrasado(BusinessNumero numero){
		this.atrasados.addLast(numero);	
	}
	
	public synchronized void quitarNumeroAtrasado(int idNumero){
		ListIterator<BusinessNumero> it = this.atrasados.listIterator();
		while(it.hasNext()){
			BusinessNumero numero = it.next();
			if(numero.getInternalId() == idNumero){
				this.atrasados.remove(numero);
				break;
			}	
		}
		
	}
	
	public synchronized BusinessNumero[] obtenerListaAtrasados(){
		return (BusinessNumero[]) this.atrasados.toArray();
	}
	
	public synchronized BusinessNumero obtenerNumeroAtrasado(int idNumero) throws IOException{
		ListIterator<BusinessNumero> it = this.atrasados.listIterator();
		while(it.hasNext()){
			BusinessNumero numero = it.next();
			if(numero.getInternalId() == idNumero){
				this.atrasados.remove(numero);
				return numero;
			}	
		}
		IOException e = new IOException("No existe un numero atrasado con el id :" + idNumero);
		throw e;
	}
	
	
	/*****     Metodos de Pausados      *****/
	
	public synchronized void agregarNumeroPausado(BusinessNumero numero){
		this.pausados.addLast(numero);	
	}
	
	public synchronized void quitarNumeroPausado(int idNumero){
		ListIterator<BusinessNumero> it = this.pausados.listIterator();
		while(it.hasNext()){
			BusinessNumero numero = it.next();
			if(numero.getInternalId() == idNumero){
				this.pausados.remove(numero);
				break;
			}	
		}	
	}
	
	public synchronized BusinessNumero[] obtenerListaPausados(){
		return (BusinessNumero[]) this.pausados.toArray();
	}
	
	public synchronized BusinessNumero obtenerNumeroPausado(int idNumero) throws IOException{
		ListIterator<BusinessNumero> it = this.pausados.listIterator();
		while(it.hasNext()){
			BusinessNumero numero = it.next();
			if(numero.getInternalId() == idNumero){
				this.pausados.remove(numero);
				return numero;
			}	
		}
		IOException e = new IOException("No existe un numero pausado con el id :" + idNumero);
		throw e;
	}
}
	
	
	
	