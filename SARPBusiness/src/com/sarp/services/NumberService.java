package com.sarp.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import com.sarp.classes.BusinessDatoComplementario;
import com.sarp.classes.BusinessNumero;
import com.sarp.classes.BusinessSector;
import com.sarp.classes.BusinessTramite;
import com.sarp.controllers.QueueController;
import com.sarp.dao.controllers.DAONumeroController;
import com.sarp.dao.controllers.DAOPuestoController;
import com.sarp.dao.factory.DAOServiceFactory;
import com.sarp.factory.Factory;
import com.sarp.json.modeler.JSONNumero;
import com.sarp.service.response.maker.RequestMaker;

public class NumberService {

	private int horaCargarBatch;
	private int minCargarBatch;

	/*
	 * public NumberService(){ String[] result; String path; String location =
	 * NumberService.class.getProtectionDomain().getCodeSource().getLocation().
	 * getPath().toString(); result = location.split("/standalone"); path=
	 * result[0] + "/modules/conf/config_cola.properties"; Properties prop = new
	 * Properties(); InputStream input; try { input = new FileInputStream(path);
	 * prop.load(input); this.horaCargarBatch =
	 * Integer.parseInt(prop.getProperty("HORA_CARGAR_BATCH"));
	 * this.minCargarBatch =
	 * Integer.parseInt(prop.getProperty("MIN_CARGAR_BATCH")); } catch
	 * (FileNotFoundException e) { e.printStackTrace(); } catch (IOException e)
	 * { e.printStackTrace(); } }
	 */

	public void solicitarNumero(JSONNumero num) throws Exception {
		RequestMaker reqMaker = RequestMaker.getInstance();
		BusinessNumero numero = reqMaker.requestNumero(num);
		/*** Generar external id ***/
		GregorianCalendar diaActual = new GregorianCalendar();
		String externalID = numero.getCodSector().length() > 1 ? numero.getCodSector().substring(0, 2)
				: numero.getCodSector();
		externalID = externalID + "-" + (Integer.toString(numero.getCodTramite()).length() > 1
				? Integer.toString(numero.getCodTramite()).substring(0, 2) : Integer.toString(numero.getCodTramite()));
		externalID = externalID + "-" + Integer.toString(diaActual.get(Calendar.HOUR_OF_DAY))
				+ Integer.toString(diaActual.get(Calendar.MINUTE)) + Integer.toString(diaActual.get(Calendar.SECOND));
		numero.setExternalId(externalID);
		/** fin generar external id ***/
		numero.setEstado("DISPONIBLE");
		BusinessDatoComplementario bDatosComplementario = reqMaker.requestDatoComplementario(num);
		DAOServiceFactory daoServiceFactory = DAOServiceFactory.getInstance();
		DAONumeroController controladorNumero = daoServiceFactory.getDAONumeroController();
		Integer id = controladorNumero.crearNumero(numero, num.getIdTramite(), num.getIdSector(), bDatosComplementario);
		numero = controladorNumero.obtenerNumero(id);

		// se agrega a la cola el numero solicitado
		this.horaCargarBatch = 7; // esto en un futuro se reemplaza por el
									// config.properties horaCargarBatch
		this.minCargarBatch = 30; // idem
		boolean loAgrego = false;
		if (numero.getPrioridad() == 2) {
			// Si el numero que esta entrando al sistema, tiene prioridad 2 (o
			// sea atril/recepcionista) se agrega a la cola
			loAgrego = true;
		} else if (numero.getHora().get(Calendar.YEAR) == diaActual.get(Calendar.YEAR)
				&& numero.getHora().get(Calendar.MONTH) == diaActual.get(Calendar.MONTH)
				&& numero.getHora().get(Calendar.DAY_OF_MONTH) == diaActual.get(Calendar.DAY_OF_MONTH)) {
			// si es el numero es para el mismo dia

			if (!(numero.getHora().get(Calendar.HOUR) < this.horaCargarBatch)
					|| (numero.getHora().get(Calendar.HOUR) == this.horaCargarBatch
							&& numero.getHora().get(Calendar.MINUTE) > this.minCargarBatch))
				// y si la hora del numero NO es anterior a la hora de inicio el
				// batch
				loAgrego = true;

		}
		if (loAgrego) {
			Factory fac = Factory.getInstance();
			QueueController ctrl = fac.getQueueController();
			ctrl.agregarNumero(num.getIdSector(), numero);
		}
	}

	public List<JSONNumero> listarNumerosSector(String idSector) throws Exception {
		Factory fac = Factory.getInstance();
		QueueController ctrl = fac.getQueueController();
		return ctrl.obtenerTodosLosNumeros(idSector);
	}

	public List<JSONNumero> listarNumerosPausados(String idPuesto) throws Exception {

		DAOServiceFactory daoFac = DAOServiceFactory.getInstance();
		DAOPuestoController daoCtrl = daoFac.getDAOPuestoController();
		List<BusinessSector> listaSectores = daoCtrl.obtenerSectoresPuesto(idPuesto);
		List<BusinessTramite> listaTramites = daoCtrl.obtenerTramitesPuesto(idPuesto);
		List<JSONNumero> listaNumeros = new ArrayList<JSONNumero>();
		Factory fac = Factory.getInstance();
		QueueController ctrl = fac.getQueueController();
		for (BusinessSector sec : listaSectores) {
			List<JSONNumero> numeros = ctrl.listarPausados(sec.getSectorId(), listaTramites);
			listaNumeros.addAll(numeros);
		}
		return listaNumeros;
	}
	
	public List<JSONNumero> listarNumerosAtrasados(String idPuesto) throws Exception {

		DAOServiceFactory daoFac = DAOServiceFactory.getInstance();
		DAOPuestoController daoCtrl = daoFac.getDAOPuestoController();
		List<BusinessSector> listaSectores = daoCtrl.obtenerSectoresPuesto(idPuesto);
		List<BusinessTramite> listaTramites = daoCtrl.obtenerTramitesPuesto(idPuesto);
		List<JSONNumero> listaNumeros = new ArrayList<JSONNumero>();
		Factory fac = Factory.getInstance();
		QueueController ctrl = fac.getQueueController();
		for (BusinessSector sec : listaSectores) {
			List<JSONNumero> numeros = ctrl.listarAtrasados(sec.getSectorId(), listaTramites);
			listaNumeros.addAll(numeros);
		}
		return listaNumeros;
	}
	
	public List<JSONNumero> listarNumerosEnEspera(String idPuesto) throws Exception {

		DAOServiceFactory daoFac = DAOServiceFactory.getInstance();
		DAOPuestoController daoCtrl = daoFac.getDAOPuestoController();
		List<BusinessSector> listaSectores = daoCtrl.obtenerSectoresPuesto(idPuesto);
		List<BusinessTramite> listaTramites = daoCtrl.obtenerTramitesPuesto(idPuesto);
		List<JSONNumero> listaNumeros = new ArrayList<JSONNumero>();
		Factory fac = Factory.getInstance();
		QueueController ctrl = fac.getQueueController();
		for (BusinessSector sec : listaSectores) {
			List<JSONNumero> numeros = ctrl.listarEnEspera(sec.getSectorId(), listaTramites);
			listaNumeros.addAll(numeros);
		}
		return listaNumeros;
	}
	

}
