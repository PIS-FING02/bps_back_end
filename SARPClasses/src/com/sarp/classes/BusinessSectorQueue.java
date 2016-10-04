package com.sarp.classes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.ListIterator;

public class BusinessSectorQueue {

	private LinkedList<BusinessNumero> colaPrioridad1;
	private LinkedList<BusinessNumero> colaPrioridad2;
	private LinkedList<BusinessNumero> atrasados;
	private LinkedList<BusinessNumero> pausados;

	public BusinessSectorQueue() {
		this.colaPrioridad1 = new LinkedList<BusinessNumero>();
		this.colaPrioridad2 = new LinkedList<BusinessNumero>();
		this.atrasados = new LinkedList<BusinessNumero>();
		this.pausados = new LinkedList<BusinessNumero>();
	}

	/***** Metodos de la Cola *****/

	public synchronized void agregarNumeroCola(BusinessNumero numero) {
		
		switch (numero.getPrioridad()) {
		case 1:
			ListIterator<BusinessNumero> it = this.colaPrioridad1.listIterator();
			while (it.hasNext()) {
				BusinessNumero actual = it.next();
				if (actual.getHora().get(Calendar.HOUR_OF_DAY) > numero.getHora().get(Calendar.HOUR_OF_DAY)
						|| (actual.getHora().get(Calendar.HOUR_OF_DAY) == numero.getHora().get(Calendar.HOUR_OF_DAY)
								&& actual.getHora().get(Calendar.MINUTE) > numero.getHora().get(Calendar.MINUTE)))
					break;
			}
			this.colaPrioridad1.add(it.previousIndex(), numero);
			break;
		case 2:
			this.colaPrioridad2.addLast(numero);
			break;

		default:
			// se tiene q hablar si se mete en atril o se tira excepcion por mal
			// prioridad
			break;
		}

	}

	public synchronized void agregarNumeroColaBatch(LinkedList<BusinessNumero> numeros) {
		this.colaPrioridad1.addAll(numeros);
	}

	public synchronized void quitarNumeroCola(int idNumero, int prioridad) {
		ListIterator<BusinessNumero> it;
		switch (prioridad) {
		case 1:
			it = this.colaPrioridad1.listIterator();
			break;
		case 2:
			it = this.colaPrioridad2.listIterator();
			break;
		default:
			// se paso mal la prioridad.... q hacemos?
			it = this.colaPrioridad2.listIterator();
			break;
		}
		while (it.hasNext()) {
			BusinessNumero numero = it.next();
			if (numero.getInternalId() == idNumero) {
				if(prioridad == 1)
					this.colaPrioridad1.remove(numero);
				else if(prioridad == 2)
					this.colaPrioridad2.remove(numero);
				else
					//ocurre un error... q hacer??
				break;
			}
		}
	}

	public synchronized BusinessNumero llamarNumeroCola(BusinessTramite[] tramites) {
		if(!this.colaPrioridad1.isEmpty()){
			ListIterator<BusinessNumero> it = this.colaPrioridad1.listIterator();
			while(it.hasNext()){
				BusinessNumero numero = it.next();
				if(this.horaMayorHoraActual(numero.getHora())){
					if(this.puedeAtenderNumero(tramites, numero)){
						this.colaPrioridad1.remove(numero);
						return numero;
					} // - end if puedeAtenderNumero
				}else // - end if horaMayor
					break;
			} // - end while
		}
		if(!this.colaPrioridad2.isEmpty()){
			ListIterator<BusinessNumero> it = this.colaPrioridad2.listIterator();
			while(it.hasNext()){
				BusinessNumero numero = it.next();
				if(this.puedeAtenderNumero(tramites, numero)){
					this.colaPrioridad2.remove(numero);
					return numero;
				}
			}
		}
		return null; // si devuelve null no hay tramites disponibles en el momento
	}
	
	/********* Metodos auxiliares para el llamado de un numero  **********/
	
	private boolean horaMayorHoraActual(GregorianCalendar hrNumero){
		GregorianCalendar hrActual = new GregorianCalendar();
		if (hrNumero.get(Calendar.HOUR_OF_DAY) > hrActual.get(Calendar.HOUR_OF_DAY)
				|| (hrNumero.get(Calendar.HOUR_OF_DAY) == hrActual.get(Calendar.HOUR_OF_DAY)
				&& hrNumero.get(Calendar.MINUTE) > hrActual.get(Calendar.MINUTE)))
			return true;
		else
			return false;
	}

	private boolean puedeAtenderNumero(BusinessTramite[] tramites, BusinessNumero nro){
		for(int i=0; i<tramites.length; i++)
			if(tramites[i].getCodigo() == nro.getCodTramite())
				return true;
		return false;
	}
	
	/********* FIN - Metodos auxiliares para el llamado de un numero  **********/
	
	/***** Metodos de Atrasados *****/

	public synchronized void agregarNumeroAtrasado(BusinessNumero numero) {
		this.atrasados.addLast(numero);
	}

	public synchronized void quitarNumeroAtrasado(int idNumero) {
		ListIterator<BusinessNumero> it = this.atrasados.listIterator();
		while (it.hasNext()) {
			BusinessNumero numero = it.next();
			if (numero.getInternalId() == idNumero) {
				this.atrasados.remove(numero);
				break;
			}
		}

	}

	public synchronized BusinessNumero[] obtenerListaAtrasados(BusinessTramite[] tramites) {
		BusinessNumero[] atrasadosAux = (BusinessNumero[]) this.pausados.toArray();
		ArrayList<BusinessNumero> atrasados = new ArrayList<BusinessNumero>();
		for(int i=0; i<atrasadosAux.length; i++){
			if(this.puedeAtenderNumero(tramites, atrasadosAux[i]))
				pausados.add(atrasadosAux[i]);
		}
		return ((BusinessNumero[]) atrasados.toArray());
	}

	public synchronized BusinessNumero obtenerNumeroAtrasado(int idNumero) throws IOException {
		ListIterator<BusinessNumero> it = this.atrasados.listIterator();
		while (it.hasNext()) {
			BusinessNumero numero = it.next();
			if (numero.getInternalId() == idNumero) {
				this.atrasados.remove(numero);
				return numero;
			}
		}
		IOException e = new IOException("No existe un numero atrasado con el id :" + idNumero);
		throw e;
	}

	/***** Metodos de Pausados *****/

	public synchronized void agregarNumeroPausado(BusinessNumero numero) {
		this.pausados.addLast(numero);
	}

	public synchronized void quitarNumeroPausado(int idNumero) {
		ListIterator<BusinessNumero> it = this.pausados.listIterator();
		while (it.hasNext()) {
			BusinessNumero numero = it.next();
			if (numero.getInternalId() == idNumero) {
				this.pausados.remove(numero);
				break;
			}
		}
	}

	public synchronized BusinessNumero[] obtenerListaPausados(BusinessTramite[] tramites) {
		BusinessNumero[] pausadosAux = (BusinessNumero[]) this.pausados.toArray();
		ArrayList<BusinessNumero> pausados = new ArrayList<BusinessNumero>();
		for(int i=0; i<pausadosAux.length; i++){
			if(this.puedeAtenderNumero(tramites, pausadosAux[i]))
				pausados.add(pausadosAux[i]);
		}
		return ((BusinessNumero[]) pausados.toArray());
	}

	public synchronized BusinessNumero obtenerNumeroPausado(int idNumero) throws IOException {
		ListIterator<BusinessNumero> it = this.pausados.listIterator();
		while (it.hasNext()) {
			BusinessNumero numero = it.next();
			if (numero.getInternalId() == idNumero) {
				this.pausados.remove(numero);
				return numero;
			}
		}
		IOException e = new IOException("No existe un numero pausado con el id :" + idNumero);
		throw e;
	}

}
