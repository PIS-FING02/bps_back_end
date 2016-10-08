package com.sarp.test.colas;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sarp.classes.BusinessNumero;
import com.sarp.classes.BusinessSectorQueue;
import com.sarp.classes.BusinessTramite;

public class TestBusinessSectorQueue {

	private BusinessSectorQueue cola;
	private int hrActual;
	private int minActual;

	@Before
	public void armarAmbiente() {
		this.cola = new BusinessSectorQueue();
		GregorianCalendar date = new GregorianCalendar();
		this.hrActual = date.get(Calendar.HOUR_OF_DAY);
		this.minActual = date.get(Calendar.MINUTE);
	}

	@Test
	public void pruebaAgregarBatch() {
		System.out.println("************ Prueba agregar numeros batch y llamarlos todos***************");
		System.out.println("Se prueba el agregarNumeroBatch y el llamarNumeroCola\n");

		BusinessNumero nro;
		GregorianCalendar date;
		ArrayList<BusinessNumero> listaNros = new ArrayList<BusinessNumero>();
		for (int i = 1; i < 11; i++) {
			date = new GregorianCalendar();
			date.set(Calendar.HOUR_OF_DAY, this.hrActual - 1);
			date.set(Calendar.MINUTE, 0);
			date.add(Calendar.MINUTE, i);
			nro = new BusinessNumero(i, Integer.toString(i), date, "estado", 1, 1, "1");
			nro.setLastUpdated(new Timestamp(1));
			listaNros.add(nro);
		}

		this.cola.agregarNumeroColaBatch(listaNros);

		ArrayList<BusinessTramite> tramites = new ArrayList<BusinessTramite>();
		BusinessTramite tramite = new BusinessTramite(1, "codT1");
		tramites.add(tramite);
		/*
		 * System.out.println("---------TRAMITES"); for(BusinessTramite t :
		 * tramites){ System.out.println("--Codigo tramite "+t.getCodigo()+
		 * ", Nombre Tramite "+ t.getNombre()); }
		 */

		nro = this.cola.llamarNumeroCola(tramites);
		while (nro != null) {
			System.out.println("Numero: " + nro.getExternalId() + ", hora:" + nro.getHora().getTime().toString());
			nro = this.cola.llamarNumeroCola(tramites);
		}

		System.out.println("\n************ Prueba obtener numeros de la cola con 2 tramites ***************");
		System.out.println("Se prueba que hay numeros con 2 codigos de tramites distintos, y se llama"
				+ "a llamarNumeroCola con solo un tramite en particular.\n");

		listaNros = new ArrayList<BusinessNumero>();
		for (int i = 1; i < 11; i++) {
			date = new GregorianCalendar();
			date.set(Calendar.HOUR_OF_DAY, this.hrActual - 1);
			date.set(Calendar.MINUTE, 0);
			date.add(Calendar.MINUTE, i);
			nro = new BusinessNumero(i, Integer.toString(i), date, "estado", 1, i % 2, "1");
			nro.setLastUpdated(new Timestamp(1));
			listaNros.add(nro);
		}

		this.cola.agregarNumeroColaBatch(listaNros);

		tramites = new ArrayList<BusinessTramite>();
		tramite = new BusinessTramite(1, "codT1");
		tramites.add(tramite);
		nro = this.cola.llamarNumeroCola(tramites);
		while (nro != null) {
			System.out.println("Numero: " + nro.getExternalId() + ", hora:" + nro.getHora().getTime().toString());
			nro = this.cola.llamarNumeroCola(tramites);
		}

		System.out.println("\n************ Prueba obtener numeros de la cola con 2 tramites ***************");
		System.out.println("Se prueba que hay numeros con 2 codigos de tramites distintos, y se llama"
				+ "a llamarNumeroCola con los dos tramites.\n");

		listaNros = new ArrayList<BusinessNumero>();
		for (int i = 1; i < 11; i++) {
			date = new GregorianCalendar();
			date.set(Calendar.HOUR_OF_DAY, this.hrActual);
			date.set(Calendar.MINUTE, 0);
			date.add(Calendar.MINUTE, i);
			nro = new BusinessNumero(i, Integer.toString(i), date, "estado", 1, (i % 2) + 1, "1");
			nro.setLastUpdated(new Timestamp(1));
			listaNros.add(nro);
		}

		this.cola.agregarNumeroColaBatch(listaNros);

		tramites = new ArrayList<BusinessTramite>();
		tramite = new BusinessTramite(1, "codT1");
		tramites.add(tramite);
		tramite = new BusinessTramite(2, "codT2");
		tramites.add(tramite);
		nro = this.cola.llamarNumeroCola(tramites);
		while (nro != null) {
			System.out.println("Numero: " + nro.getExternalId() + ", hora:" + nro.getHora().getTime().toString());
			nro = this.cola.llamarNumeroCola(tramites);
		}

		System.out.println(
				"\n************ Prueba de llamarNumeroCola con un numero con hora posterior a la actual ***************");
		System.out.println("Se prueba que dado un numero ingresado con prioridad 1 con hora posterior a la actual,"
				+ " se llama un numero y este no tiene que ser retornado\n");

		listaNros = new ArrayList<BusinessNumero>();

		date = new GregorianCalendar();
		date.set(Calendar.HOUR_OF_DAY, this.hrActual + 1);
		date.set(Calendar.MINUTE, 0);
		nro = new BusinessNumero(1, "1", date, "estado", 1, 1, "1");
		nro.setLastUpdated(new Timestamp(1));
		listaNros.add(nro);

		this.cola.agregarNumeroColaBatch(listaNros);

		tramites = new ArrayList<BusinessTramite>();
		tramite = new BusinessTramite(1, "codT1");
		tramites.add(tramite);
		nro = this.cola.llamarNumeroCola(tramites);
		if (nro != null)
			System.out.println("ERROR!! No tendria que devolver el numero");
		else
			System.out.println("prueba OK!! Dio null el llamar numero,");

		System.out.println("\n************ Prueba numeros de atril/recepcion ***************");
		System.out.println("Se prueba que se agrega correctamente un numero de atril/recepcion\n");

		date = new GregorianCalendar();
		date.set(Calendar.HOUR_OF_DAY, this.hrActual - 1);
		date.set(Calendar.MINUTE, 0);
		nro = new BusinessNumero(1, "1", date, "estado", 2, 1, "1");
		nro.setLastUpdated(new Timestamp(1));

		this.cola.agregarNumeroCola(nro);

		tramites = new ArrayList<BusinessTramite>();
		tramite = new BusinessTramite(1, "codT1");
		tramites.add(tramite);
		nro = this.cola.llamarNumeroCola(tramites);
		if (nro != null)
			System.out.println("Numero: " + nro.getExternalId() + ", hora:" + nro.getHora().getTime().toString());
		else
			System.out.println("Tendria que devolver el nro agregado con prioridad 2");

		System.out.println("\n************ Prueba con numeros prioridad 1 y 2 al mismo tiempo ***************");
		System.out.println(
				"Se prueba que si se pide un numero, al haber dos de diferentes prioridades devuelva el de mayor prioridad \n");

		this.cola = new BusinessSectorQueue();

		date = new GregorianCalendar();
		date.set(Calendar.HOUR_OF_DAY, this.hrActual - 1);
		date.set(Calendar.MINUTE, 0);
		nro = new BusinessNumero(1, "1", date, "estado", 1, 1, "1");
		nro.setLastUpdated(new Timestamp(1));
		this.cola.agregarNumeroCola(nro);

		date = new GregorianCalendar();
		date.set(Calendar.HOUR_OF_DAY, this.hrActual - 1);
		date.set(Calendar.MINUTE, 0);
		nro = new BusinessNumero(2, "2", date, "estado", 2, 1, "1");
		nro.setLastUpdated(new Timestamp(1));
		this.cola.agregarNumeroCola(nro);

		tramites = new ArrayList<BusinessTramite>();
		tramite = new BusinessTramite(1, "codT1");
		tramites.add(tramite);

		nro = this.cola.llamarNumeroCola(tramites);

		System.out.println("Numero: " + nro.getExternalId() + ", hora:" + nro.getHora().getTime().toString()
				+ ", Prioridad: " + nro.getPrioridad());

		System.out.println(
				"\n************ Prueba con numeros prioridad 1 y 2 con hora prioridad 1 posteiror a al actual ***************");
		System.out.println(
				"Se prueba que si se pide un numero, habiendo 1 en cada cola pero el de la prioridad 1 con hr mayor a la actual\n");

		this.cola = new BusinessSectorQueue();

		date = new GregorianCalendar();
		date.set(Calendar.HOUR_OF_DAY, this.hrActual + 1);
		date.set(Calendar.MINUTE, 0);
		nro = new BusinessNumero(1, "1", date, "estado", 1, 1, "1");
		nro.setLastUpdated(new Timestamp(1));
		this.cola.agregarNumeroCola(nro);

		date = new GregorianCalendar();
		date.set(Calendar.HOUR_OF_DAY, this.hrActual + 1);
		date.set(Calendar.MINUTE, 0);
		nro = new BusinessNumero(2, "2", date, "estado", 2, 1, "1");
		nro.setLastUpdated(new Timestamp(1));
		this.cola.agregarNumeroCola(nro);

		tramites = new ArrayList<BusinessTramite>();
		tramite = new BusinessTramite(1, "codT1");
		tramites.add(tramite);

		nro = this.cola.llamarNumeroCola(tramites);

		System.out.println("Numero: " + nro.getExternalId() + ", hora:" + nro.getHora().getTime().toString()
				+ ", Prioridad: " + nro.getPrioridad());
	}

	@Test
	public void pruebaPausados() {

		System.out.println("\n********* Se prueba la transferencia de numeros a lista pausados ***************");
		System.out.println("Se va a probar pedir un numero pasarlo a pausados e imprimir la lista de pausados\n");

		this.cola = new BusinessSectorQueue();

		GregorianCalendar date = new GregorianCalendar();
		date.set(Calendar.HOUR_OF_DAY, this.hrActual - 1);
		date.set(Calendar.MINUTE, 15);
		BusinessNumero nro = new BusinessNumero(1, "1", date, "estado", 1, 1, "1");
		nro.setLastUpdated(new Timestamp(1));
		this.cola.agregarNumeroCola(nro);

		ArrayList<BusinessTramite> tramites = new ArrayList<BusinessTramite>();
		BusinessTramite tramite = new BusinessTramite(1, "codT1");
		tramites.add(tramite);

		// obtengo el numero para atender
		nro = this.cola.llamarNumeroCola(tramites);
		// lo pauseo
		nro.setEstado("Pausado");
		this.cola.agregarNumeroPausado(nro);
		// obtengo la lista de pausados a ver si se transfirio
		ArrayList<BusinessNumero> pausados = this.cola.obtenerListaPausados(tramites);

		System.out.println("Impresion de la lista de pausados obteniendo por tramites");
		for (BusinessNumero np : pausados) {
			System.out.println("---ExternalID: " + np.getExternalId() + ", Prioridad: " + np.getPrioridad() + ", Hora: "
					+ np.getHora().getTime().toString());
		}
		System.out.println();
		this.cola.printEstadoCola();

		System.out.println(
				"\n********* Se prueba la transferencia de numeros a lista pausados y obtener con diferente tramite la lista pausados ***************");
		System.out.println(
				"Se va a probar pedir un numero pasarlo a pausados e imprimir la lista de pausados pero con diferente tramite\n");

		this.cola = new BusinessSectorQueue();

		date = new GregorianCalendar();
		date.set(Calendar.HOUR_OF_DAY, this.hrActual - 1);
		date.set(Calendar.MINUTE, 18);
		nro = new BusinessNumero(1, "1", date, "estado", 1, 1, "1");
		nro.setLastUpdated(new Timestamp(1));
		this.cola.agregarNumeroCola(nro);

		tramites = new ArrayList<BusinessTramite>();
		tramite = new BusinessTramite(1, "codT1");
		tramites.add(tramite);

		// obtengo el numero para atender
		nro = this.cola.llamarNumeroCola(tramites);
		// lo pauseo
		nro.setEstado("Pausado");
		this.cola.agregarNumeroPausado(nro);

		tramites = new ArrayList<BusinessTramite>();
		tramite = new BusinessTramite(2, "codT2");
		tramites.add(tramite);

		// obtengo la lista de pausados a ver si se transfirio
		pausados = this.cola.obtenerListaPausados(tramites);

		System.out.println("Impresion de la lista de pausados obteniendo por tramites");
		for (BusinessNumero np : pausados) {
			System.out.println("---ExternalID: " + np.getExternalId() + ", Prioridad: " + np.getPrioridad() + ", Hora: "
					+ np.getHora().getTime().toString());
		}
		if (pausados.isEmpty())
			System.out.println("Lista pausados vacia");
		System.out.println();
		this.cola.printEstadoCola();

		System.out.println(
				"\n********* Se prueba la transferencia de numeros a lista pausados y recupera un numero pausado ***************");
		System.out.println(
				"Se van a agregar un par de numeros a la cola, se van a pausar y desp se va a recuperar uno en particular\n");

		this.cola = new BusinessSectorQueue();

		date = new GregorianCalendar();
		date.set(Calendar.HOUR_OF_DAY, this.hrActual - 1);
		date.set(Calendar.MINUTE, 18);
		nro = new BusinessNumero(1, "1", date, "estado", 1, 1, "1");
		nro.setLastUpdated(new Timestamp(1));
		this.cola.agregarNumeroCola(nro);

		date = new GregorianCalendar();
		date.set(Calendar.HOUR_OF_DAY, this.hrActual - 1);
		date.set(Calendar.MINUTE, 19);
		nro = new BusinessNumero(2, "2", date, "estado", 1, 1, "1");
		nro.setLastUpdated(new Timestamp(1));
		this.cola.agregarNumeroCola(nro);

		date = new GregorianCalendar();
		date.set(Calendar.HOUR_OF_DAY, this.hrActual - 1);
		date.set(Calendar.MINUTE, 20);
		nro = new BusinessNumero(3, "3", date, "estado", 1, 1, "1");
		nro.setLastUpdated(new Timestamp(1));
		this.cola.agregarNumeroCola(nro);

		tramites = new ArrayList<BusinessTramite>();
		tramite = new BusinessTramite(1, "codT1");
		tramites.add(tramite);

		// obtengo el numero para atender
		nro = this.cola.llamarNumeroCola(tramites);
		// lo pauseo
		nro.setEstado("Pausado");
		this.cola.agregarNumeroPausado(nro);

		// obtengo el numero para atender
		nro = this.cola.llamarNumeroCola(tramites);
		// lo pauseo
		nro.setEstado("Pausado");
		this.cola.agregarNumeroPausado(nro);

		// obtengo el numero para atender
		nro = this.cola.llamarNumeroCola(tramites);
		// lo pauseo
		nro.setEstado("Pausado");
		this.cola.agregarNumeroPausado(nro);

		// obtengo la lista de pausados a ver si se transfirieron los nros
		pausados = this.cola.obtenerListaPausados(tramites);

		System.out.println("Impresion de la lista de pausados obteniendo por tramites");
		for (BusinessNumero np : pausados) {
			System.out.println("---ExternalID: " + np.getExternalId() + ", Prioridad: " + np.getPrioridad() + ", Hora: "
					+ np.getHora().getTime().toString());
		}
		if (pausados.isEmpty())
			System.out.println("Lista pausados vacia");
		System.out.println();
		this.cola.printEstadoCola();

		int pidoI = 3;
		System.out.println("Obtengo el numero pausado de id " + pidoI);
		try {
			nro = this.cola.obtenerNumeroPausado(pidoI);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("ExternalID: " + nro.getExternalId());
		this.cola.printEstadoCola();

		System.out.println(
				"\n********* Se prueba la transferencia de numeros a lista pausados y se quita un numero de la lista (eliminacion, no obtencion) ***************");
		System.out.println(
				"Se agregan numeros a la cola, luego se pasan a la lista de pausados y se eliminan de la lista (quitarNumeroPausado)\n");

		this.cola = new BusinessSectorQueue();

		date = new GregorianCalendar();
		date.set(Calendar.HOUR_OF_DAY, this.hrActual - 1);
		date.set(Calendar.MINUTE, 18);
		nro = new BusinessNumero(1, "1", date, "estado", 1, 1, "1");
		nro.setLastUpdated(new Timestamp(1));
		this.cola.agregarNumeroCola(nro);

		date = new GregorianCalendar();
		date.set(Calendar.HOUR_OF_DAY, this.hrActual - 1);
		date.set(Calendar.MINUTE, 19);
		nro = new BusinessNumero(2, "2", date, "estado", 1, 1, "1");
		nro.setLastUpdated(new Timestamp(1));
		this.cola.agregarNumeroCola(nro);

		date = new GregorianCalendar();
		date.set(Calendar.HOUR_OF_DAY, this.hrActual - 1);
		date.set(Calendar.MINUTE, 20);
		nro = new BusinessNumero(3, "3", date, "estado", 1, 1, "1");
		nro.setLastUpdated(new Timestamp(1));
		this.cola.agregarNumeroCola(nro);

		tramites = new ArrayList<BusinessTramite>();
		tramite = new BusinessTramite(1, "codT1");
		tramites.add(tramite);

		// obtengo el numero para atender
		nro = this.cola.llamarNumeroCola(tramites);
		// lo pauseo
		nro.setEstado("Pausado");
		this.cola.agregarNumeroPausado(nro);

		// obtengo el numero para atender
		nro = this.cola.llamarNumeroCola(tramites);
		// lo pauseo
		nro.setEstado("Pausado");
		this.cola.agregarNumeroPausado(nro);

		// obtengo el numero para atender
		nro = this.cola.llamarNumeroCola(tramites);
		// lo pauseo
		nro.setEstado("Pausado");
		this.cola.agregarNumeroPausado(nro);

		// obtengo la lista de pausados a ver si se transfirieron los nros
		pausados = this.cola.obtenerListaPausados(tramites);

		System.out.println("Impresion de la lista de pausados obteniendo por tramites");
		for (BusinessNumero np : pausados) {
			System.out.println("---ExternalID: " + np.getExternalId() + ", Prioridad: " + np.getPrioridad() + ", Hora: "
					+ np.getHora().getTime().toString());
		}
		if (pausados.isEmpty())
			System.out.println("Lista pausados vacia");
		System.out.println();
		this.cola.printEstadoCola();

		pidoI = 1;
		System.out.println("Se quita el numero con id " + pidoI + " de la lista de pausados");
		try {
			this.cola.quitarNumeroPausado(pidoI);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		this.cola.printEstadoCola();

	}

	@Test
	public void pruebaAtrasados() {

		System.out.println("\n********* Se prueba la transferencia de numeros a lista atrasados ***************");
		System.out.println("Se agregan numeros a la cola, y luego se pasa como atrasado\n");

		this.cola = new BusinessSectorQueue();
		
		GregorianCalendar date = new GregorianCalendar();
		date.set(Calendar.HOUR_OF_DAY, this.hrActual - 1);
		date.set(Calendar.MINUTE, 18);
		BusinessNumero nro = new BusinessNumero(1, "1", date, "estado", 1, 1, "1");
		nro.setLastUpdated(new Timestamp(1));
		this.cola.agregarNumeroCola(nro);

		ArrayList<BusinessTramite> tramites = new ArrayList<BusinessTramite>();
		BusinessTramite tramite = new BusinessTramite(1, "codT1");
		tramites.add(tramite);
		
		nro = this.cola.llamarNumeroCola(tramites);
		nro.printNumero();
		
		// lo atraso
		nro.setEstado("Atrasado");
		this.cola.agregarNumeroAtrasado(nro);
		
		System.out.println("Luego de atrasar ");
		this.cola.printEstadoCola();
		
		System.out.println("\n********* Se prueba la transferencia de numeros a lista atrasados ***************");
		System.out.println("Se agregan numeros a la cola, y luego se pasa como atrasado y ademas se llama un nuevo numero donde se tiene q recibir el atrasado\n");

		this.cola = new BusinessSectorQueue();
		
		date = new GregorianCalendar();
		date.set(Calendar.HOUR_OF_DAY, this.hrActual - 1);
		date.set(Calendar.MINUTE, 18);
		nro = new BusinessNumero(1, "1", date, "estado", 1, 1, "1");
		nro.setLastUpdated(new Timestamp(1));
		this.cola.agregarNumeroCola(nro);

		tramites = new ArrayList<BusinessTramite>();
		tramite = new BusinessTramite(1, "codT1");
		tramites.add(tramite);
		
		nro = this.cola.llamarNumeroCola(tramites);
		nro.printNumero();
		
		// lo atraso
		nro.setEstado("Atrasado");
		this.cola.agregarNumeroAtrasado(nro);
		
		System.out.println("Luego de atrasar ");
		this.cola.printEstadoCola();
		
		// se llama prox numero 
		nro = this.cola.llamarNumeroCola(tramites);
		
		System.out.println("Se llama numero tiene q venir el atrasado");
		nro.printNumero();
		

	}

}
