package com.sarp.test.colas;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.sarp.classes.BusinessNumero;
import com.sarp.classes.BusinessSectorQueue;
import com.sarp.classes.BusinessTramite;
import com.sarp.enumerados.EstadoNumero;

public class TestBusinessSectorQueue {

	private BusinessSectorQueue cola;
	private int hrActual;
	private int minActual;

	@Before
	public void armarAmbiente() {
		this.cola = new BusinessSectorQueue("1");
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
			nro = new BusinessNumero(i, Integer.toString(i), date, "estado", 1, 1, "1",null);
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

		this.cola = new BusinessSectorQueue("1");

		listaNros = new ArrayList<BusinessNumero>();
		for (int i = 1; i < 11; i++) {
			date = new GregorianCalendar();
			date.set(Calendar.HOUR_OF_DAY, this.hrActual - 1);
			date.set(Calendar.MINUTE, 0);
			date.add(Calendar.MINUTE, i);
			nro = new BusinessNumero(i, Integer.toString(i), date, "estado", 1, i % 2, "1",null);
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

		this.cola = new BusinessSectorQueue("1");

		listaNros = new ArrayList<BusinessNumero>();
		for (int i = 1; i < 11; i++) {
			date = new GregorianCalendar();
			date.set(Calendar.HOUR_OF_DAY, this.hrActual);
			date.set(Calendar.MINUTE, 0);
			date.add(Calendar.MINUTE, i);
			nro = new BusinessNumero(i, Integer.toString(i), date, "estado", 1, (i % 2) + 1, "1",null);
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

		this.cola = new BusinessSectorQueue("1");

		listaNros = new ArrayList<BusinessNumero>();

		date = new GregorianCalendar();
		date.set(Calendar.HOUR_OF_DAY, this.hrActual + 1);
		date.set(Calendar.MINUTE, 0);
		nro = new BusinessNumero(1, "1", date, "estado", 1, 1, "1",null);
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

		this.cola = new BusinessSectorQueue("1");

		date = new GregorianCalendar();
		date.set(Calendar.HOUR_OF_DAY, this.hrActual - 1);
		date.set(Calendar.MINUTE, 0);
		nro = new BusinessNumero(1, "1", date, "estado", 2, 1, "1",null);
		nro.setLastUpdated(new Timestamp(1));

		try {
			try {
				this.cola.agregarNumeroCola(nro);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		tramites = new ArrayList<BusinessTramite>();
		tramite = new BusinessTramite(1, "codT1");
		tramites.add(tramite);

		System.out.println(
				"Se imprime el estado de la cola para corroborar que esta en la cola el número ingresado de prioridad 2");
		this.cola.printEstadoCola();

		nro = this.cola.llamarNumeroCola(tramites);
		if (nro != null)
			System.out.println("Numero: " + nro.getExternalId() + ", hora:" + nro.getHora().getTime().toString());
		else
			System.out.println("Tendria que devolver el nro agregado con prioridad 2");

		System.out.println("\n************ Prueba con numeros prioridad 1 y 2 al mismo tiempo ***************");
		System.out.println(
				"Se prueba que si se pide un numero, al haber dos de diferentes prioridades devuelva el de mayor prioridad \n");

		this.cola = new BusinessSectorQueue("1");

		date = new GregorianCalendar();
		date.set(Calendar.HOUR_OF_DAY, this.hrActual - 1);
		date.set(Calendar.MINUTE, 0);
		nro = new BusinessNumero(1, "1", date, "estado", 1, 1, "1",null);
		nro.setLastUpdated(new Timestamp(1));
		try {
			try {
				this.cola.agregarNumeroCola(nro);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		date = new GregorianCalendar();
		date.set(Calendar.HOUR_OF_DAY, this.hrActual - 1);
		date.set(Calendar.MINUTE, 0);
		nro = new BusinessNumero(2, "2", date, "estado", 2, 1, "1",null);
		nro.setLastUpdated(new Timestamp(1));
		try {
			this.cola.agregarNumeroCola(nro);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		tramites = new ArrayList<BusinessTramite>();
		tramite = new BusinessTramite(1, "codT1");
		tramites.add(tramite);

		nro = this.cola.llamarNumeroCola(tramites);
		System.out.println("Numero: " + nro.getExternalId() + ", hora:" + nro.getHora().getTime().toString()
				+ ", Prioridad: " + nro.getPrioridad());

		nro = this.cola.llamarNumeroCola(tramites);
		System.out.println("Numero: " + nro.getExternalId() + ", hora:" + nro.getHora().getTime().toString()
				+ ", Prioridad: " + nro.getPrioridad());

		System.out.println(
				"\n************ Prueba con numeros prioridad 1 y 2 con hora prioridad 1 posteiror a al actual ***************");
		System.out.println(
				"Se prueba que si se pide un numero, habiendo 1 en cada cola pero el de la prioridad 1 con hr mayor a la actual\n");

		this.cola = new BusinessSectorQueue("1");

		date = new GregorianCalendar();
		date.set(Calendar.HOUR_OF_DAY, this.hrActual + 1);
		date.set(Calendar.MINUTE, 0);
		nro = new BusinessNumero(1, "1", date, "estado", 1, 1, "1",null);
		nro.setLastUpdated(new Timestamp(1));
		try {
			this.cola.agregarNumeroCola(nro);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		date = new GregorianCalendar();
		date.set(Calendar.HOUR_OF_DAY, this.hrActual + 1);
		date.set(Calendar.MINUTE, 0);
		nro = new BusinessNumero(2, "2", date, "estado", 2, 1, "1",null);
		nro.setLastUpdated(new Timestamp(1));
		try {
			this.cola.agregarNumeroCola(nro);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

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

		this.cola = new BusinessSectorQueue("1");

		GregorianCalendar date = new GregorianCalendar();
		date.set(Calendar.HOUR_OF_DAY, this.hrActual - 1);
		date.set(Calendar.MINUTE, 15);
		BusinessNumero nro = new BusinessNumero(1, "1", date, "estado", 1, 1, "1",null);
		nro.setLastUpdated(new Timestamp(1));
		try {
			this.cola.agregarNumeroCola(nro);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		ArrayList<BusinessTramite> tramites = new ArrayList<BusinessTramite>();
		BusinessTramite tramite = new BusinessTramite(1, "codT1");
		tramites.add(tramite);

		// obtengo el numero para atender
		nro = this.cola.llamarNumeroCola(tramites);
		// lo pauseo
		nro.setEstado(EstadoNumero.PAUSADO);
		this.cola.agregarNumeroPausado(nro);
		// obtengo la lista de pausados a ver si se transfirio
		List<BusinessNumero> pausados = this.cola.obtenerListaPausados(tramites);

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

		this.cola = new BusinessSectorQueue("1");

		date = new GregorianCalendar();
		date.set(Calendar.HOUR_OF_DAY, this.hrActual - 1);
		date.set(Calendar.MINUTE, 18);
		nro = new BusinessNumero(1, "1", date, "estado", 1, 1, "1",null);
		nro.setLastUpdated(new Timestamp(1));
		try {
			this.cola.agregarNumeroCola(nro);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		tramites = new ArrayList<BusinessTramite>();
		tramite = new BusinessTramite(1, "codT1");
		tramites.add(tramite);

		// obtengo el numero para atender
		nro = this.cola.llamarNumeroCola(tramites);
		// lo pauseo
		nro.setEstado(EstadoNumero.PAUSADO);
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

		this.cola = new BusinessSectorQueue("1");

		date = new GregorianCalendar();
		date.set(Calendar.HOUR_OF_DAY, this.hrActual - 1);
		date.set(Calendar.MINUTE, 18);
		nro = new BusinessNumero(1, "1", date, "estado", 1, 1, "1",null);
		nro.setLastUpdated(new Timestamp(1));
		try {
			this.cola.agregarNumeroCola(nro);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		date = new GregorianCalendar();
		date.set(Calendar.HOUR_OF_DAY, this.hrActual - 1);
		date.set(Calendar.MINUTE, 19);
		nro = new BusinessNumero(2, "2", date, "estado", 1, 1, "1",null);
		nro.setLastUpdated(new Timestamp(1));
		try {
			this.cola.agregarNumeroCola(nro);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		date = new GregorianCalendar();
		date.set(Calendar.HOUR_OF_DAY, this.hrActual - 1);
		date.set(Calendar.MINUTE, 20);
		nro = new BusinessNumero(3, "3", date, "estado", 1, 1, "1",null);
		nro.setLastUpdated(new Timestamp(1));
		try {
			this.cola.agregarNumeroCola(nro);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		tramites = new ArrayList<BusinessTramite>();
		tramite = new BusinessTramite(1, "codT1");
		tramites.add(tramite);

		// obtengo el numero para atender
		nro = this.cola.llamarNumeroCola(tramites);
		// lo pauseo
		nro.setEstado(EstadoNumero.PAUSADO);
		this.cola.agregarNumeroPausado(nro);

		// obtengo el numero para atender
		nro = this.cola.llamarNumeroCola(tramites);
		// lo pauseo
		nro.setEstado(EstadoNumero.PAUSADO);
		this.cola.agregarNumeroPausado(nro);

		// obtengo el numero para atender
		nro = this.cola.llamarNumeroCola(tramites);
		// lo pauseo
		nro.setEstado(EstadoNumero.PAUSADO);
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

		this.cola = new BusinessSectorQueue("1");

		date = new GregorianCalendar();
		date.set(Calendar.HOUR_OF_DAY, this.hrActual - 1);
		date.set(Calendar.MINUTE, 18);
		nro = new BusinessNumero(1, "1", date, "estado", 1, 1, "1",null);
		nro.setLastUpdated(new Timestamp(1));
		try {
			this.cola.agregarNumeroCola(nro);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		date = new GregorianCalendar();
		date.set(Calendar.HOUR_OF_DAY, this.hrActual - 1);
		date.set(Calendar.MINUTE, 19);
		nro = new BusinessNumero(2, "2", date, "estado", 1, 1, "1",null);
		nro.setLastUpdated(new Timestamp(1));
		try {
			this.cola.agregarNumeroCola(nro);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		date = new GregorianCalendar();
		date.set(Calendar.HOUR_OF_DAY, this.hrActual - 1);
		date.set(Calendar.MINUTE, 20);
		nro = new BusinessNumero(3, "3", date, "estado", 1, 1, "1",null);
		nro.setLastUpdated(new Timestamp(1));
		try {
			this.cola.agregarNumeroCola(nro);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		tramites = new ArrayList<BusinessTramite>();
		tramite = new BusinessTramite(1, "codT1");
		tramites.add(tramite);

		// obtengo el numero para atender
		nro = this.cola.llamarNumeroCola(tramites);
		// lo pauseo
		nro.setEstado(EstadoNumero.PAUSADO);
		this.cola.agregarNumeroPausado(nro);

		// obtengo el numero para atender
		nro = this.cola.llamarNumeroCola(tramites);
		// lo pauseo
		nro.setEstado(EstadoNumero.PAUSADO);
		this.cola.agregarNumeroPausado(nro);

		// obtengo el numero para atender
		nro = this.cola.llamarNumeroCola(tramites);
		// lo pauseo
		nro.setEstado(EstadoNumero.PAUSADO);
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

		this.cola = new BusinessSectorQueue("1");

		GregorianCalendar date = new GregorianCalendar();
		date.set(Calendar.HOUR_OF_DAY, this.hrActual - 1);
		date.set(Calendar.MINUTE, 18);
		BusinessNumero nro = new BusinessNumero(1, "1", date, "estado", 1, 1, "1",null);
		nro.setLastUpdated(new Timestamp(1));
		try {
			this.cola.agregarNumeroCola(nro);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		ArrayList<BusinessTramite> tramites = new ArrayList<BusinessTramite>();
		BusinessTramite tramite = new BusinessTramite(1, "codT1");
		tramites.add(tramite);

		nro = this.cola.llamarNumeroCola(tramites);
		nro.printNumero();

		// lo atraso
		nro.setEstado(EstadoNumero.ATRASADO);
		this.cola.agregarNumeroAtrasado(nro);

		System.out.println("Luego de atrasar ");
		this.cola.printEstadoCola();

		System.out.println("\n********* Se prueba la transferencia de numeros a lista atrasados ***************");
		System.out.println(
				"Se agregan numeros a la cola, y luego se pasa como atrasado y ademas se llama un nuevo numero donde se tiene q recibir el atrasado\n");

		this.cola = new BusinessSectorQueue("1");

		date = new GregorianCalendar();
		date.set(Calendar.HOUR_OF_DAY, this.hrActual - 1);
		date.set(Calendar.MINUTE, 18);
		nro = new BusinessNumero(1, "1", date, "estado", 1, 1, "1",null);
		nro.setLastUpdated(new Timestamp(1));
		try {
			this.cola.agregarNumeroCola(nro);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		tramites = new ArrayList<BusinessTramite>();
		tramite = new BusinessTramite(1, "codT1");
		tramites.add(tramite);

		nro = this.cola.llamarNumeroCola(tramites);
		nro.printNumero();

		// lo atraso
		nro.setEstado(EstadoNumero.ATRASADO);
		this.cola.agregarNumeroAtrasado(nro);

		System.out.println("Luego de atrasar ");
		this.cola.printEstadoCola();

		// se llama prox numero
		nro = this.cola.llamarNumeroCola(tramites);

		System.out.println("Se llama numero tiene q venir el atrasado");
		nro.printNumero();

		System.out.println("\n********* Se prueba la transferencia de numeros a lista atrasados ***************");
		System.out.println(
				"Se agregan numeros, se pide un numero se atrasa, y se pide un numero, el atrasado deberia quedar mas atras \n");

		this.cola = new BusinessSectorQueue("1");

		date = new GregorianCalendar();
		date.set(Calendar.HOUR_OF_DAY, this.hrActual);
		date.set(Calendar.MINUTE, this.minActual - 10);
		nro = new BusinessNumero(1, "1", date, "estado", 1, 1, "1",null);
		nro.setLastUpdated(new Timestamp(1));
		try {
			this.cola.agregarNumeroCola(nro);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		date = new GregorianCalendar();
		date.set(Calendar.HOUR_OF_DAY, this.hrActual);
		date.set(Calendar.MINUTE, this.minActual - 5);
		nro = new BusinessNumero(2, "2", date, "estado", 1, 1, "1",null);
		nro.setLastUpdated(new Timestamp(1));
		try {
			this.cola.agregarNumeroCola(nro);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		date = new GregorianCalendar();
		date.set(Calendar.HOUR_OF_DAY, this.hrActual);
		date.set(Calendar.MINUTE, this.minActual);
		nro = new BusinessNumero(3, "3", date, "estado", 1, 1, "1",null);
		nro.setLastUpdated(new Timestamp(1));
		try {
			this.cola.agregarNumeroCola(nro);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		tramites = new ArrayList<BusinessTramite>();
		tramite = new BusinessTramite(1, "codT1");
		tramites.add(tramite);

		nro = this.cola.llamarNumeroCola(tramites);
		nro.printNumero();

		// lo atraso
		nro.setEstado(EstadoNumero.ATRASADO);
		this.cola.agregarNumeroAtrasado(nro);

		System.out.println("Luego de atrasar ");
		this.cola.printEstadoCola();

		// se llama prox numero
		nro = this.cola.llamarNumeroCola(tramites);

		System.out.println("Se llama numero tiene q venir uno no atrasado");
		nro.printNumero();

		this.cola.printEstadoCola();

		System.out.println(
				"\n********* Se prueba la transferencia de numeros a lista atrasados y la obtencion de un nro en particular***************");
		System.out.println("Se agregan numeros se atrasan, y se prueba elegir numeros en particular \n");

		this.cola = new BusinessSectorQueue("1");

		date = new GregorianCalendar();
		date.set(Calendar.HOUR_OF_DAY, this.hrActual);
		date.set(Calendar.MINUTE, this.minActual - 10);
		nro = new BusinessNumero(1, "1", date, "estado", 1, 1, "1",null);
		nro.setLastUpdated(new Timestamp(1));
		try {
			this.cola.agregarNumeroCola(nro);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		date = new GregorianCalendar();
		date.set(Calendar.HOUR_OF_DAY, this.hrActual);
		date.set(Calendar.MINUTE, this.minActual - 5);
		nro = new BusinessNumero(2, "2", date, "estado", 1, 1, "1",null);
		nro.setLastUpdated(new Timestamp(1));
		try {
			this.cola.agregarNumeroCola(nro);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		date = new GregorianCalendar();
		date.set(Calendar.HOUR_OF_DAY, this.hrActual);
		date.set(Calendar.MINUTE, this.minActual - 1);
		nro = new BusinessNumero(3, "3", date, "estado", 1, 1, "1",null);
		nro.setLastUpdated(new Timestamp(1));
		try {
			this.cola.agregarNumeroCola(nro);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		tramites = new ArrayList<BusinessTramite>();
		tramite = new BusinessTramite(1, "codT1");
		tramites.add(tramite);

		nro = this.cola.llamarNumeroCola(tramites);
		// lo atraso
		nro.setEstado(EstadoNumero.ATRASADO);
		this.cola.agregarNumeroAtrasado(nro);

		nro = this.cola.llamarNumeroCola(tramites);
		// lo atraso
		nro.setEstado(EstadoNumero.ATRASADO);
		this.cola.agregarNumeroAtrasado(nro);

		nro = this.cola.llamarNumeroCola(tramites);
		// lo atraso
		nro.setEstado(EstadoNumero.ATRASADO);
		this.cola.agregarNumeroAtrasado(nro);

		// reviso q esten los tres nros atrasados
		System.out.println("deberian estar los tres numeros atrasados");
		this.cola.printEstadoCola();

		int pidoNumA = 3;
		try {
			System.out.println("Tiene q devolver el nro de identificador " + pidoNumA);
			nro = this.cola.obtenerNumeroAtrasado(pidoNumA);
			nro.printNumero();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.println("Imprimo la situacion de la cola, la lista de atrasados deberia estar sin el numero con id "
				+ pidoNumA);
		this.cola.printEstadoCola();

		System.out.println("asdasdas");

		System.out.println("****** Prueba de listar todos los numeros de todas las colas ********");
		System.out.println(
				"Se van a agregar diversos numeros en toda la cola, es decir Prioridad1, 2, atrasados y pausados, y se van a listar todos");

		this.cola = new BusinessSectorQueue("1");

		ArrayList<BusinessNumero> listaNros = new ArrayList<BusinessNumero>();
		for (int i = 1; i < 4; i++) {
			date = new GregorianCalendar();
			date.set(Calendar.HOUR_OF_DAY, this.hrActual - 1);
			date.set(Calendar.MINUTE, 0);
			date.add(Calendar.MINUTE, i);
			nro = new BusinessNumero(i, Integer.toString(i), date, "NO_ATENDIDO", 1, 1, "1",null);
			nro.setLastUpdated(new Timestamp(1));
			listaNros.add(nro);
		}
		this.cola.agregarNumeroColaBatch(listaNros);

		date = new GregorianCalendar();
		date.set(Calendar.HOUR_OF_DAY, this.hrActual - 1);
		date.set(Calendar.MINUTE, 0);
		date.add(Calendar.MINUTE, 5);
		nro = new BusinessNumero(5, "5", date, "NO_ATENDIDO", 2, 1, "1",null);
		nro.setLastUpdated(new Timestamp(1));

		try {
			this.cola.agregarNumeroCola(nro);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		date = new GregorianCalendar();
		date.set(Calendar.HOUR_OF_DAY, this.hrActual - 1);
		date.set(Calendar.MINUTE, 0);
		date.add(Calendar.MINUTE, 6);
		nro = new BusinessNumero(6, "6", date, "NO_ATENDIDO", 2, 1, "1",null);
		nro.setLastUpdated(new Timestamp(1));

		try {
			this.cola.agregarNumeroCola(nro);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		date = new GregorianCalendar();
		date.set(Calendar.HOUR_OF_DAY, this.hrActual - 1);
		date.set(Calendar.MINUTE, 0);
		date.add(Calendar.MINUTE, 7);
		nro = new BusinessNumero(7, "7", date, "NO_ATENDIDO", 2, 1, "1",null);
		nro.setLastUpdated(new Timestamp(1));

		try {
			this.cola.agregarNumeroCola(nro);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		date = new GregorianCalendar();
		date.set(Calendar.HOUR_OF_DAY, this.hrActual - 1);
		date.set(Calendar.MINUTE, 0);
		date.add(Calendar.MINUTE, 8);
		nro = new BusinessNumero(8, "8", date, "PAUSADO", 2, 1, "1",null);
		nro.setLastUpdated(new Timestamp(1));

		this.cola.agregarNumeroPausado(nro);

		date = new GregorianCalendar();
		date.set(Calendar.HOUR_OF_DAY, this.hrActual - 1);
		date.set(Calendar.MINUTE, 0);
		date.add(Calendar.MINUTE, 9);
		nro = new BusinessNumero(9, "9", date, "PAUSADO", 1, 1, "1",null);
		nro.setLastUpdated(new Timestamp(1));

		this.cola.agregarNumeroPausado(nro);

		date = new GregorianCalendar();
		date.set(Calendar.HOUR_OF_DAY, this.hrActual - 1);
		date.set(Calendar.MINUTE, 0);
		date.add(Calendar.MINUTE, 10);
		nro = new BusinessNumero(10, "10", date, "ATRASADO", 1, 1, "1",null);
		nro.setLastUpdated(new Timestamp(1));

		this.cola.agregarNumeroAtrasado(nro);

		List<BusinessNumero> listaTodos = this.cola.listarNumeros();

		for (BusinessNumero bn : listaTodos)
			System.out.println(bn.obtenerImpresion());
		System.out.println("fing");

	}

}
