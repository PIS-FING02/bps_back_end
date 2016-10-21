package com.sarp.classes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class BusinessSectorQueue {

	private LinkedList<BusinessNumero> colaPrioridad1;
	private LinkedList<BusinessNumero> colaPrioridad2;
	private LinkedList<BusinessNumero> atrasados;
	private LinkedList<BusinessNumero> pausados;
	// private final String idSector;

	public BusinessSectorQueue(String idSec) {
		// this.idSector = idSec;
		this.colaPrioridad1 = new LinkedList<BusinessNumero>();
		this.colaPrioridad2 = new LinkedList<BusinessNumero>();
		this.atrasados = new LinkedList<BusinessNumero>();
		this.pausados = new LinkedList<BusinessNumero>();

	}

	/***** Metodos de la Cola *****/

	public synchronized void agregarNumeroCola(BusinessNumero numero) throws Exception {

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
			if (t.getCodigo() == nro.getCodTramite())
				return true;
		}
		return false;

	}

	/********* FIN - Metodos auxiliares para el llamado de un numero **********/

	/***** Metodos de Atrasados *****/

	public synchronized void agregarNumeroAtrasado(BusinessNumero numero) {

		/*
		 * String[] result; String path; String location =
		 * GAFUFacade.class.getProtectionDomain().getCodeSource().getLocation().
		 * getPath().toString(); result = location.split("/standalone");
		 * 
		 * path= result[0] + "/modules/conf/config_cola.properties"; Properties
		 * prop = new Properties(); InputStream input; try { input = new
		 * FileInputStream(path); prop.load(input); tiempoAtrasado=
		 * prop.getProperty(this.idSector).toString(); } catch
		 * (FileNotFoundException e) { e.printStackTrace(); } catch (IOException
		 * e) { e.printStackTrace(); }
		 */

		Integer tiempoProperties = 2;
		GregorianCalendar horaActual = new GregorianCalendar();
		horaActual.add(Calendar.MINUTE, tiempoProperties);
		numero.setHora(horaActual);
		this.atrasados.addLast(numero);
	}

	public synchronized void quitarNumeroAtrasado(Integer idNumero) {
		ListIterator<BusinessNumero> it = this.atrasados.listIterator();
		while (it.hasNext()) {
			BusinessNumero numero = it.next();
			if (numero.getInternalId() == idNumero) {
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
			if (numero.getInternalId() == idNumero) {
				this.atrasados.remove(numero);
				return numero;
			}
		}
		throw new Exception("No existe un numero atrasado con el id :" + idNumero);
	}

	/***** Metodos de Pausados *****/

	public synchronized void agregarNumeroPausado(BusinessNumero numero) {
		this.pausados.addLast(numero);
	}

	public synchronized void quitarNumeroPausado(Integer idNumero) {
		ListIterator<BusinessNumero> it = this.pausados.listIterator();
		while (it.hasNext()) {
			BusinessNumero numero = it.next();
			if (numero.getInternalId() == idNumero) {
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
			if (numero.getInternalId() == idNumero) {
				this.pausados.remove(numero);
				return numero;
			}
		}
		throw new IOException("No existe un numero pausado con el id :" + idNumero);
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
	
	public List<BusinessNumero> listarNumeros() {
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
