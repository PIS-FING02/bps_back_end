package com.sarp.services;

import com.sarp.classes.BusinessDatoComplementario;
import com.sarp.classes.BusinessNumero;
import com.sarp.classes.BusinessPuesto;
import com.sarp.controllers.QueueController;
import com.sarp.dao.controllers.DAONumeroController;
import com.sarp.dao.controllers.DAOPuestoController;
import com.sarp.dao.factory.DAOServiceFactory;
import com.sarp.factory.Factory;
import com.sarp.json.modeler.JSONNumero;
import com.sarp.service.response.maker.RequestMaker;

public class NumberService {

	public void solicitarNumero(JSONNumero num){
		RequestMaker reqMaker = RequestMaker.getInstance();
		BusinessNumero numero = reqMaker.requestNumero(num);
		BusinessDatoComplementario bDatosComplementario = reqMaker.requestDatoComplementario(num);
		DAOServiceFactory daoServiceFactory = DAOServiceFactory.getInstance();
		DAONumeroController controladorNumero = daoServiceFactory.getDAONumeroController();
		Integer id =  controladorNumero.crearNumero(numero, num.getIdTramite(), num.getIdSector(), bDatosComplementario);
		
		// se agrega a la cola el numero solicitado
		Factory fac = Factory.getInstance();
		QueueController ctrl = fac.getQueueController();
		ctrl.agregarNumero(num.getIdSector(), numero);
	}
}
