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
				this.colaPrioridad1.add(it.previousIndex(), numero);
			}
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

	public synchronized void agregarNumeroColaBatch(ArrayList<BusinessNumero> numeros) {
		this.colaPrioridad1.addAll(numeros);
	}

	public synchronized void quitarNumeroCola(int idNumero) throws Exception {
		ListIterator<BusinessNumero> it = this.colaPrioridad1.listIterator();
		while (it.hasNext()) {
			BusinessNumero numero = it.next();
			if (numero.getInternalId() == idNumero) {
				this.colaPrioridad1.remove(numero);
				return;
			}
		}
		it = this.colaPrioridad2.listIterator();
		while (it.hasNext()) {
			BusinessNumero numero = it.next();
			if (numero.getInternalId() == idNumero) {
				this.colaPrioridad2.remove(numero);
				return;
			}
		}
		// si no corto la ejecucion antes es porque no existia el numero con el
		// id
		// pasado como parametro
		throw new Exception("No tiene permisos suficientes.");
	}

	public synchronized BusinessNumero llamarNumeroCola(ArrayList<BusinessTramite> tramites) {

		if (!this.colaPrioridad1.isEmpty()) {
			ListIterator<BusinessNumero> it = this.colaPrioridad1.listIterator();
			while (it.hasNext()) {
				BusinessNumero numero = it.next();
				if (this.horaMayorHoraActual(numero.getHora())) {
					if (this.puedeAtenderNumero(tramites, numero)) {
						this.colaPrioridad1.remove(numero);
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
		/*
		 * System.out.println(hrActual.getTime().toString());
		 * System.out.println(hrActual.get(Calendar.HOUR_OF_DAY));
		 * System.out.println(hrActual.get(Calendar.MINUTE));
		 * System.out.println(hrNumero.getTime().toString());
		 * System.out.println(hrNumero.get(Calendar.HOUR_OF_DAY));
		 * System.out.println(hrNumero.get(Calendar.MINUTE));
		 * System.out.println(hrNumero.get(Calendar.HOUR_OF_DAY) + " " +
		 * hrActual.get(Calendar.HOUR_OF_DAY));
		 */
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

	private boolean puedeAtenderNumero(ArrayList<BusinessTramite> listaTramites, BusinessNumero nro) {
		for (BusinessTramite t : listaTramites) {
			if (t.getCodigo() == nro.getCodTramite())
				return true;
		}
		return false;

	}

	/********* FIN - Metodos auxiliares para el llamado de un numero **********/

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

	public synchronized ArrayList<BusinessNumero> obtenerListaAtrasados(ArrayList<BusinessTramite> listaTramites) {
		ArrayList<BusinessNumero> atrasados = new ArrayList<BusinessNumero>();
		for (BusinessNumero bn : this.atrasados) {
			if (this.puedeAtenderNumero(listaTramites, bn))
				atrasados.add(bn);
		}
		return atrasados;
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

	public synchronized ArrayList<BusinessNumero> obtenerListaPausados(ArrayList<BusinessTramite> tramites) {
		ArrayList<BusinessNumero> pausados = new ArrayList<BusinessNumero>();
		for (BusinessNumero bn : this.pausados) {
			if (this.puedeAtenderNumero(tramites, bn))
				pausados.add(bn);
		}
		return pausados;
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

	/*********************
	 * Metodos auxiliares de testing
	 *************************************/

	public void printEstadoCola() {
		System.out.println("*** Estado Cola ***");
		System.out.println("Numeros de la cola de prioridad 1");
		for (BusinessNumero bn : this.colaPrioridad1) {
			System.out.println("---ExternalID: " + bn.getExternalId() + ", Prioridad:  " + bn.getPrioridad() + ", Hora: "
					+ bn.getHora().getTime().toString());
		}

		System.out.println("\nNumeros de la cola de prioridad 2");
		for (BusinessNumero bn : this.colaPrioridad2) {
			System.out.println("---ExternalID: " + bn.getExternalId() + ", Prioridad:  " + bn.getPrioridad() + ", Hora: "
					+ bn.getHora().getTime().toString());
		}
		System.out.println("\nNumeros de la lista Pausados");
		for (BusinessNumero bn : this.pausados) {
			System.out.println("---ExternalID: " + bn.getExternalId() + ", Prioridad:  " + bn.getPrioridad() + ", Hora: "
					+ bn.getHora().getTime().toString());
		}
		System.out.println("\n*** Fin Estado Cola ***\n");
	}

}
