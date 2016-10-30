package com.sarp.managers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Logger;

import com.sarp.classes.BusinessNumero;
import com.sarp.classes.BusinessTramite;
import com.sarp.enumerados.EstadoNumero;
import com.sarp.utils.UtilService;

public class BusinessSectorQueue {

	private LinkedList<BusinessNumero> colaPrioridad1;
	private LinkedList<BusinessNumero> colaPrioridad2;
	private LinkedList<BusinessNumero> atrasados;
	private LinkedList<BusinessNumero> pausados;
	private int proxNumero;
	// private final String idSector;

	public BusinessSectorQueue(String idSec) {
		// this.idSector = idSec;
		this.colaPrioridad1 = new LinkedList<BusinessNumero>();
		this.colaPrioridad2 = new LinkedList<BusinessNumero>();
		this.atrasados = new LinkedList<BusinessNumero>();
		this.pausados = new LinkedList<BusinessNumero>();
		this.proxNumero = 1;
	}

	/***** Metodos de la Cola *****/

	public synchronized void agregarNumeroCola(BusinessNumero numero) throws Exception {
		
		numero.setEstado(EstadoNumero.PENDIENTE);
		switch (numero.getPrioridad()) {
		case 1:
			if (this.colaPrioridad1.isEmpty())
				this.colaPrioridad1.add(numero);
			else {
				ListIterator<BusinessNumero> it = this.colaPrioridad1.listIterator();
				while (it.hasNext()) {
					BusinessNumero actual = it.next();
					if (actual.getHora().get(Calendar.HOUR_OF_DAY) > numero.getHora().get(Calendar.HOUR_OF_DAY)
							|| (actual.getHora().get(Calendar.HOUR_OF_DAY) == numero.getHora().get(Calendar.HOUR_OF_DAY)
									&& actual.getHora().get(Calendar.MINUTE) > numero.getHora().get(Calendar.MINUTE)))
						break;
				}
				this.colaPrioridad1.add(it.previousIndex() + 1, numero);
			}
			break;
		case 2:
			this.colaPrioridad2.addLast(numero);
			break;
		default:
			throw new Exception("La prioridad del numero no pertenece a la jerarquia actual");
		}

	}

	public synchronized void agregarNumeroColaBatch(List<BusinessNumero> numeros) {
		this.colaPrioridad1.addAll(numeros);
	}

	public synchronized void quitarNumeroCola(Integer idNumero) throws Exception {
		ListIterator<BusinessNumero> it = this.colaPrioridad1.listIterator();
		while (it.hasNext()) {
			BusinessNumero numero = it.next();
			if (numero.getInternalId().intValue() == idNumero.intValue()) {
				this.colaPrioridad1.remove(numero);
				return;
			}
		}
		it = this.colaPrioridad2.listIterator();
		while (it.hasNext()) {
			BusinessNumero numero = it.next();
			if (numero.getInternalId().intValue() == idNumero.intValue()) {
				this.colaPrioridad2.remove(numero);
				return;
			}
		}
		throw new Exception("EL numero a quitar no pertenecia a la cola");
	}

	public synchronized BusinessNumero llamarNumeroCola(List<BusinessTramite> tramites) {

		if (!this.atrasados.isEmpty()) {
			ListIterator<BusinessNumero> it = this.atrasados.listIterator();
			while (it.hasNext()) {
				BusinessNumero numero = it.next();
				if (this.horaMayorHoraActual(numero.getHora())) {
					if (this.puedeAtenderNumero(tramites, numero)) {
						this.atrasados.remove(numero);
						numero.setEstado(EstadoNumero.LLAMADO);
						return numero;
					} // - end if puedeAtenderNumero
				} else // - end if horaMayor
					break;
			} // - end while
		}

		if (!this.colaPrioridad1.isEmpty()) {
			ListIterator<BusinessNumero> it = this.colaPrioridad1.listIterator();
			while (it.hasNext()) {
				BusinessNumero numero = it.next();
				if (this.horaMayorHoraActual(numero.getHora())) {
					if (this.puedeAtenderNumero(tramites, numero)) {
						this.colaPrioridad1.remove(numero);
						numero.setEstado(EstadoNumero.LLAMADO);
						return numero;
					} // - end if puedeAtenderNumero
				} else // - end if horaMayor
					break;
			} // - end while
		}
		if (!this.colaPrioridad2.isEmpty()) {
			ListIterator<BusinessNumero> it = this.colaPrioridad2.listIterator();
			while (it.hasNext()) {
				BusinessNumero numero = it.next();
				if (this.puedeAtenderNumero(tramites, numero)) {
					this.colaPrioridad2.remove(numero);
					numero.setEstado(EstadoNumero.LLAMADO);
					return numero;
				}
			}
		}
		return null; // si devuelve null no hay tramites disponibles en el
						// momento
	}

	/********* Metodos auxiliares para el llamado de un numero **********/

	private boolean horaMayorHoraActual(GregorianCalendar hrNumero) {
		GregorianCalendar hrActual = new GregorianCalendar();
		if (hrNumero.get(Calendar.HOUR_OF_DAY) < hrActual.get(Calendar.HOUR_OF_DAY)
				|| (hrNumero.get(Calendar.HOUR_OF_DAY) == hrActual.get(Calendar.HOUR_OF_DAY)
						&& hrNumero.get(Calendar.MINUTE) < hrActual.get(Calendar.MINUTE))) {
			// System.out.println("True en horaMayorHoraActual");
			return true;
		} else {
			// System.out.println("False en horaMayorHoraActual");
			return false;
		}
	}

	private boolean puedeAtenderNumero(List<BusinessTramite> listaTramites, BusinessNumero nro) {
		for (BusinessTramite t : listaTramites) {
			if (t.getCodigo().equals(nro.getCodTramite()))
				return true;
		}
		return false;

	}

	/********* FIN - Metodos auxiliares para el llamado de un numero **********/

	/***** Metodos de Atrasados *****/

	public synchronized void agregarNumeroAtrasado(BusinessNumero numero) {
		Integer tiempoProperties;
		try{
			tiempoProperties = UtilService.getIntegerProperty("LATE_TIME_MINUTES");
		}catch(Exception e){
			tiempoProperties = 30;
		}
		GregorianCalendar horaActual = new GregorianCalendar();
		horaActual.add(Calendar.MINUTE, tiempoProperties);
		numero.setHora(horaActual);
		numero.setEstado(EstadoNumero.ATRASADO);
		this.atrasados.addLast(numero);
	}

	public synchronized void quitarNumeroAtrasado(Integer idNumero) {
		ListIterator<BusinessNumero> it = this.atrasados.listIterator();
		while (it.hasNext()) {
			BusinessNumero numero = it.next();
			if (numero.getInternalId().intValue() == idNumero.intValue()) {
				this.atrasados.remove(numero);
				break;
			}
		}
	}

	public synchronized List<BusinessNumero> obtenerListaAtrasados(List<BusinessTramite> listaTramites) {
		List<BusinessNumero> atrasados = new ArrayList<BusinessNumero>();
		for (BusinessNumero bn : this.atrasados) {
			if (this.puedeAtenderNumero(listaTramites, bn))
				atrasados.add(bn);
		}
		return atrasados;
	}

	public synchronized BusinessNumero obtenerNumeroAtrasado(Integer idNumero) throws Exception {
		ListIterator<BusinessNumero> it = this.atrasados.listIterator();
		while (it.hasNext()) {
			BusinessNumero numero = it.next();
			if (numero.getInternalId().intValue() == idNumero.intValue()) {
				this.atrasados.remove(numero);
				numero.setEstado(EstadoNumero.ATENDIENDO);
				return numero;
			}
		}
		throw new Exception("No existe un numero atrasado con el id :" + idNumero);
	}

	/***** Metodos de Pausados *****/

	public synchronized void agregarNumeroPausado(BusinessNumero numero) {
		numero.setEstado(EstadoNumero.PAUSADO);
		this.pausados.addLast(numero);
	}

	public synchronized void quitarNumeroPausado(Integer idNumero) {
		ListIterator<BusinessNumero> it = this.pausados.listIterator();
		while (it.hasNext()) {
			BusinessNumero numero = it.next();
			if (numero.getInternalId().intValue() == idNumero.intValue()) {
				this.pausados.remove(numero);
				break;
			}
		}
	}

	public synchronized List<BusinessNumero> obtenerListaPausados(List<BusinessTramite> tramites) {
		List<BusinessNumero> pausados = new ArrayList<BusinessNumero>();
		for (BusinessNumero bn : this.pausados) {
			if (this.puedeAtenderNumero(tramites, bn))
				pausados.add(bn);
		}
		return pausados;
	}

	public synchronized BusinessNumero obtenerNumeroPausado(Integer idNumero) throws IOException {
		ListIterator<BusinessNumero> it = this.pausados.listIterator();
		while (it.hasNext()) {
			BusinessNumero numero = it.next();
			if (numero.getInternalId().intValue() == idNumero.intValue()) {
				this.pausados.remove(numero);
				numero.setEstado(EstadoNumero.ATENDIENDO);
				return numero;
			}
		}
		throw new IOException("No existe un numero pausado con el id :" + idNumero);
	}
	
	public synchronized BusinessNumero obtenerNumeroDemanda(Integer idNumero) throws IOException {
		if (!this.colaPrioridad1.isEmpty()) {
			ListIterator<BusinessNumero> it = this.colaPrioridad1.listIterator();
			while (it.hasNext()) {
				BusinessNumero numero = it.next();
				if(numero.getInternalId().intValue() == idNumero.intValue()){
					this.colaPrioridad1.remove(numero);
					numero.setEstado(EstadoNumero.ATENDIENDO);
					return numero;
				}
			} // - end while
		}
		if (!this.colaPrioridad2.isEmpty()) {
			ListIterator<BusinessNumero> it = this.colaPrioridad2.listIterator();
			while (it.hasNext()) {
				BusinessNumero numero = it.next();
				if(numero.getInternalId().intValue() == idNumero.intValue()){
					this.colaPrioridad2.remove(numero);
					numero.setEstado(EstadoNumero.ATENDIENDO);
					return numero;
				}
			}
		}
		throw new IOException("No existe un numero con el id :" + idNumero);
	}

	public synchronized List<BusinessNumero> obtenerListaEnEspera(List<BusinessTramite> tramites) {
		List<BusinessNumero> enEspera = new ArrayList<BusinessNumero>();
		for (BusinessNumero bn : this.colaPrioridad1) {
			if (this.puedeAtenderNumero(tramites, bn))
				enEspera.add(bn);
		}
		for (BusinessNumero bn : this.colaPrioridad2) {
			if (this.puedeAtenderNumero(tramites, bn))
				enEspera.add(bn);
		}
		return enEspera;
	}
	
	public synchronized List<BusinessNumero> listarNumeros() {
		List<BusinessNumero> lista = new ArrayList<BusinessNumero>();
		for (BusinessNumero bn : this.colaPrioridad1)
			lista.add(bn);
		for (BusinessNumero bn : this.colaPrioridad2)
			lista.add(bn);
		for (BusinessNumero bn : this.pausados)
			lista.add(bn);
		for (BusinessNumero bn : this.atrasados)
			lista.add(bn);
		return lista;
	}

	/******** Generador de numeros *******/
	
	public synchronized int obtenerProxNumero(){
		return this.proxNumero++;
	}
	
	public synchronized void restaurarProxNumero(){
		this.proxNumero = 1;
	}
	
	/*********************
	 * Metodos auxiliares de testing
	 *************************************/

	public void printEstadoCola() {
		System.out.println("*** Estado Cola ***");
		System.out.println("Numeros de la cola de prioridad 1");
		for (BusinessNumero bn : this.colaPrioridad1) {
			System.out.println("---ExternalID: " + bn.getExternalId() + ", Prioridad:  " + bn.getPrioridad()
					+ ", Hora: " + bn.getHora().getTime().toString() + ", Estado: " + bn.getEstado());
		}

		System.out.println("\nNumeros de la cola de prioridad 2");
		for (BusinessNumero bn : this.colaPrioridad2) {
			System.out.println("---ExternalID: " + bn.getExternalId() + ", Prioridad:  " + bn.getPrioridad()
					+ ", Hora: " + bn.getHora().getTime().toString() + ", Estado: " + bn.getEstado());
		}
		System.out.println("\nNumeros de la lista Pausados");
		for (BusinessNumero bn : this.pausados) {
			System.out.println("---ExternalID: " + bn.getExternalId() + ", Prioridad:  " + bn.getPrioridad()
					+ ", Hora: " + bn.getHora().getTime().toString() + ", Estado: " + bn.getEstado());
		}
		System.out.println("\nNumeros de la lista Atrasados");
		for (BusinessNumero bn : this.atrasados) {
			System.out.println("---ExternalID: " + bn.getExternalId() + ", Prioridad:  " + bn.getPrioridad()
					+ ", Hora: " + bn.getHora().getTime().toString() + ", Estado: " + bn.getEstado());
		}
		System.out.println("\n*** Fin Estado Cola ***\n");
	}

}
