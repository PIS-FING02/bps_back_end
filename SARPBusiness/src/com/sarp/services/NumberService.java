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
import com.sarp.dao.controllers.DAOSectorController;
import com.sarp.dao.factory.DAOServiceFactory;
import com.sarp.enumerados.EstadoNumero;
import com.sarp.factory.Factory;
import com.sarp.json.modeler.JSONNumero;
import com.sarp.json.modeler.JSONSectorCantNum;
import com.sarp.service.response.maker.RequestMaker;
import com.sarp.service.response.maker.ResponseMaker;

public class NumberService {

	private int horaCargarBatch = 7;//UtilService.getIntegerProperty("COLA_HORA_CARGAR_BATCH");
	private int minCargarBatch = 0;//UtilService.getIntegerProperty("COLA_MINUTOS_CARGAR_BATCH");

	public String solicitarNumero(JSONNumero num) throws Exception {
		RequestMaker reqMaker = RequestMaker.getInstance();
		BusinessNumero numero = reqMaker.requestNumero(num);

		Factory fac = Factory.getInstance();
		QueueController ctrl = fac.getQueueController();
		/*** Generar external id ***/
		
		
		if(num.getPrioridad() == 1 && !num.getExternalId().isEmpty()){
			//SAE
			numero.setExternalId(num.getExternalId());
			
		}else{
			//ATRIL 
			String externalID = numero.getCodSector().length() > 1 ? numero.getCodSector().substring(0, 2)
					: numero.getCodSector();
			numero.setExternalId(externalID + "-" + Integer.toString(ctrl.obtenerProxNumero(numero.getCodSector())));
		}
			
		/** fin generar external id ***/
		
		GregorianCalendar diaActual = new GregorianCalendar();
		
		numero.setEstado(EstadoNumero.PENDIENTE);
		numero.setCodTramite(num.getIdTramite());
		numero.setCodSector(num.getIdSector());
		BusinessDatoComplementario bDatosComplementario = reqMaker.requestDatoComplementario(num);
		DAOServiceFactory daoServiceFactory = DAOServiceFactory.getInstance();
		DAONumeroController controladorNumero = daoServiceFactory.getDAONumeroController();
		
		Integer id = controladorNumero.crearNumero(numero, bDatosComplementario);
		numero = controladorNumero.obtenerNumero(id);

		// se agrega a la cola el numero solicitado
		//this.horaCargarBatch = 0; // esto en un futuro se reemplaza por el
									// config.properties horaCargarBatch
		//this.minCargarBatch = 30; // idem 
		boolean loAgrego = false;
		if (numero.getPrioridad().intValue() == 2) {
			// Si el numero que esta entrando al sistema, tiene prioridad 2 (o
			// sea atril/recepcionista) se agrega a la cola
			loAgrego = true;
		} else if (numero.getHora().get(Calendar.YEAR) == diaActual.get(Calendar.YEAR)
				&& numero.getHora().get(Calendar.MONTH) == diaActual.get(Calendar.MONTH)
				&& numero.getHora().get(Calendar.DAY_OF_MONTH) == diaActual.get(Calendar.DAY_OF_MONTH)) {
			// si es el numero es para el mismo dia
			System.out.println("Hora del numero: "+ numero.getHora().get(Calendar.HOUR_OF_DAY)+":"+numero.getHora().get(Calendar.MINUTE));
			System.out.println("Hora del sistema: "+this.horaCargarBatch+":"+this.minCargarBatch);
			if ((numero.getHora().get(Calendar.HOUR_OF_DAY) > this.horaCargarBatch)
					|| (numero.getHora().get(Calendar.HOUR_OF_DAY) == this.horaCargarBatch
							&& numero.getHora().get(Calendar.MINUTE) > this.minCargarBatch))
				// y si la hora del numero NO es anterior a la hora de inicio el
				// batch
				loAgrego = true;

		}
		if (loAgrego) {
			ctrl.agregarNumero(num.getIdSector(), numero);
		}
		
		return numero.getExternalId();
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
	
	public List<JSONNumero> listarNumerosPausadosSector(String idSector) throws Exception {

		DAOServiceFactory daoFac = DAOServiceFactory.getInstance();
		DAOSectorController daoCtrl = daoFac.getDAOSectorController();
		List<BusinessTramite> listaTramites = daoCtrl.obtenerTramitesSector(idSector);
		Factory fac = Factory.getInstance();
		QueueController ctrl = fac.getQueueController();
		return ctrl.listarPausados(idSector, listaTramites);
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
	
	public List<JSONNumero> listarNumerosAtrasadosSector(String idSector) throws Exception {
		DAOServiceFactory daoFac = DAOServiceFactory.getInstance();
		DAOSectorController daoCtrl = daoFac.getDAOSectorController();
		List<BusinessTramite> listaTramites = daoCtrl.obtenerTramitesSector(idSector);
		Factory fac = Factory.getInstance();
		QueueController ctrl = fac.getQueueController();
		return ctrl.listarAtrasados(idSector, listaTramites);
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
	
	public List<JSONNumero> listarNumerosEnEsperaSector(String idSector) throws Exception {
		DAOServiceFactory daoFac = DAOServiceFactory.getInstance();
		DAOSectorController daoCtrl = daoFac.getDAOSectorController();
		List<BusinessTramite> listaTramites = daoCtrl.obtenerTramitesSector(idSector);
		Factory fac = Factory.getInstance();
		QueueController ctrl = fac.getQueueController();
		return ctrl.listarEnEspera(idSector, listaTramites);
	}
	
	public List<JSONSectorCantNum> obtenerCantNumerosEnEsperaSector(String idPuesto) throws Exception {
		DAOServiceFactory daoFac = DAOServiceFactory.getInstance();
		DAOPuestoController daoCtrl = daoFac.getDAOPuestoController();
		List<BusinessSector> listaSectores = daoCtrl.obtenerSectoresPuesto(idPuesto);
		List<BusinessTramite> listaTramites = daoCtrl.obtenerTramitesPuesto(idPuesto);
		
		Factory fac = Factory.getInstance();
		QueueController ctrl = fac.getQueueController();
		List<JSONSectorCantNum> sectores = new ArrayList<JSONSectorCantNum>();
		ResponseMaker respMak = ResponseMaker.getInstance();
		for (BusinessSector sec : listaSectores) {
			sectores.add(respMak.createJSONSectorCantNum(sec, ctrl.obtenerCantNumerosEnEspera(sec.getSectorId(), listaTramites)));
		}
		return sectores;
	}

}
