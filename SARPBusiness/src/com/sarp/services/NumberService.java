package com.sarp.services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;

import com.sarp.classes.BusinessDatoComplementario;
import com.sarp.classes.BusinessNumero;
import com.sarp.controllers.QueueController;
import com.sarp.dao.controllers.DAONumeroController;
import com.sarp.dao.factory.DAOServiceFactory;
import com.sarp.factory.Factory;
import com.sarp.json.modeler.JSONNumero;
import com.sarp.service.response.maker.RequestMaker;

public class NumberService {

	private int horaCargarBatch;
	private int minCargarBatch;
	
	/*public NumberService(){
		String[] result;
		String path;
		String location = NumberService.class.getProtectionDomain().getCodeSource().getLocation().getPath().toString();
		result = location.split("/standalone");
		path= result[0] + "/modules/conf/config_cola.properties";
		Properties prop = new Properties();
		InputStream input;
		try {
			input = new FileInputStream(path);
			prop.load(input);
			this.horaCargarBatch = Integer.parseInt(prop.getProperty("HORA_CARGAR_BATCH"));
			this.minCargarBatch = Integer.parseInt(prop.getProperty("MIN_CARGAR_BATCH"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
	
	public void solicitarNumero(JSONNumero num) throws Exception {
		RequestMaker reqMaker = RequestMaker.getInstance();
		BusinessNumero numero = reqMaker.requestNumero(num);
		BusinessDatoComplementario bDatosComplementario = reqMaker.requestDatoComplementario(num);
		DAOServiceFactory daoServiceFactory = DAOServiceFactory.getInstance();
		DAONumeroController controladorNumero = daoServiceFactory.getDAONumeroController();
		
		Integer id = controladorNumero.crearNumero(numero, num.getIdTramite(), num.getIdSector(), bDatosComplementario);
		numero = controladorNumero.obtenerNumero(id);
		numero.setExternalId(id.toString());//DEBERIA DEVOLVER DE LA BASE UN BUSINESS NUMERO 
		controladorNumero.modificarNumero(numero);
		
		// se agrega a la cola el numero solicitado
		this.horaCargarBatch = 7; // esto en un futuro se reemplaza por el config.properties horaCargarBatch
		this.minCargarBatch = 30; // idem
		GregorianCalendar diaActual = new GregorianCalendar();
		boolean loAgrego = false;
		
		if (numero.getPrioridad() == 2) {
			// Si el numero que esta entrando al sistema, tiene prioridad 2 (o sea atril/recepcionista) se agrega a la cola
			loAgrego = true;
		}else if(numero.getHora().get(Calendar.YEAR) == diaActual.get(Calendar.YEAR) &&
				numero.getHora().get(Calendar.MONTH) == diaActual.get(Calendar.MONTH) && 
				numero.getHora().get(Calendar.DAY_OF_MONTH) == diaActual.get(Calendar.DAY_OF_MONTH)){
			// si es el numero es para el mismo dia
			
			if(!(numero.getHora().get(Calendar.HOUR) <  this.horaCargarBatch) || 
					(numero.getHora().get(Calendar.HOUR) ==  this.horaCargarBatch  && numero.getHora().get(Calendar.MINUTE) >  this.minCargarBatch))
				// y si la hora del numero NO es anterior a la hora de inicio el batch
				loAgrego = true;

		}
		if(loAgrego){
			Factory fac = Factory.getInstance();
			QueueController ctrl = fac.getQueueController();
			ctrl.agregarNumero(num.getIdSector(), numero);
		}
	}
}
