package com.sarp.service.response.maker;

import com.sarp.classes.BusinessDatoComplementario;
import com.sarp.classes.BusinessNumero;
import com.sarp.classes.BusinessPuesto;
import com.sarp.json.modeler.JSONDatosComp;
import com.sarp.json.modeler.JSONNumero;
import com.sarp.json.modeler.JSONPuesto;

public class ResponseMaker {

	private ResponseMaker(){};
	private static ResponseMaker instance;
	
	public static ResponseMaker getInstance(){
		instance = (instance!= null)? instance : new ResponseMaker(); 
		return instance;
	}
	
	public JSONPuesto puestoResponse(BusinessPuesto bussinesPuesto){
		JSONPuesto jsonPuesto = new JSONPuesto();
		jsonPuesto.setEstado(bussinesPuesto.getEstado() != null ? bussinesPuesto.getEstado().toString() : null);
		jsonPuesto.setNombreMaquina(bussinesPuesto.getNombreMaquina());
		jsonPuesto.setNumeroAsignado(bussinesPuesto.getNumeroAsignado() != null ? this.numeroResponse(bussinesPuesto.getNumeroAsignado()): null);
		jsonPuesto.setUsuarioId(bussinesPuesto.getUsuarioId());
		jsonPuesto.setNumeroPuesto(bussinesPuesto.getNumeroPuesto());
		
		return jsonPuesto;
	}

	private JSONNumero numeroResponse(BusinessNumero numeroAsignado) {
		
		JSONNumero jsonNumero = new JSONNumero();
		jsonNumero.setEstado(numeroAsignado.getEstado());
		jsonNumero.setExternalId(numeroAsignado.getExternalId());
		jsonNumero.setHora(numeroAsignado.getHora());
		jsonNumero.setId(numeroAsignado.getInternalId());
		jsonNumero.setPrioridad(numeroAsignado.getPrioridad());
		jsonNumero.setDatosComplementarios(numeroAsignado.getDatoComplementario() != null ? this.datosComplementariosResponse(numeroAsignado.getDatoComplementario()) : null);
		jsonNumero.set
		return null;
	}

	private JSONDatosComp datosComplementariosResponse(BusinessDatoComplementario datoComplementario) {
		// TODO Auto-generated method stub
		return null;
	}
}
