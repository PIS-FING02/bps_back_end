package com.sarp.services;

import com.sarp.classes.BusinessDatoComplementario;
import com.sarp.classes.BusinessNumero;
import com.sarp.classes.BusinessPuesto;
import com.sarp.dao.controllers.DAONumeroController;
import com.sarp.dao.controllers.DAOPuestoController;
import com.sarp.dao.factory.DAOServiceFactory;
import com.sarp.json.modeler.JSONNumero;
import com.sarp.service.response.maker.RequestMaker;

public class NumberService {

	public void solicitarNumero(JSONNumero num){
		//llama a alta numero de rydel
		RequestMaker reqMaker = RequestMaker.getInstance();
		BusinessNumero bNumero = reqMaker.requestNumero(num);
		BusinessDatoComplementario bDatosComplementario = reqMaker.requestDatoComplementario(num);
		DAOServiceFactory daoServiceFactory = DAOServiceFactory.getInstance();
		DAONumeroController controladorNumero = daoServiceFactory.getDAONumeroController();
		//System.out.println("aca explota");
		//System.out.println(bNumero.ge);
		Integer id =  controladorNumero.crearNumero(bNumero, num.getTramiteSector().getTramite().getCodigo(), num.getTramiteSector().getSector().getCodigo(), bDatosComplementario);
	}

}
