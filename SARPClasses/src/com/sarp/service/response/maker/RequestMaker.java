package com.sarp.service.response.maker;

import com.sarp.classes.BusinessDatoComplementario;
import com.sarp.classes.BusinessDisplay;
import com.sarp.classes.BusinessNumero;
import com.sarp.classes.BusinessPuesto;
import com.sarp.classes.BusinessSector;
import com.sarp.classes.BusinessTramite;
import com.sarp.json.modeler.JSONDatosComp;
import com.sarp.json.modeler.JSONDisplay;
import com.sarp.json.modeler.JSONNumero;
import com.sarp.json.modeler.JSONNumeroPuesto;
import com.sarp.json.modeler.JSONPuesto;
import com.sarp.json.modeler.JSONSector;
import com.sarp.json.modeler.JSONTramite;
import com.sarp.json.modeler.JSONTramiteSector;

public class RequestMaker {
	
	private RequestMaker(){}
	private static RequestMaker instance;
	
	public static RequestMaker getInstance(){
		instance = (instance!= null) ? instance : new RequestMaker();
		return instance;
	}
	
	public BusinessPuesto requestPuesto(JSONPuesto puesto){

		return puesto!= null? new BusinessPuesto(puesto.getNombreMaquina(),puesto.getUsuarioId(),puesto.getEstado(),puesto.getNumeroPuesto()) : null;
	}
	
	public BusinessNumero requestNumero(JSONNumero numero){
	
		return numero != null ? new BusinessNumero(numero.getId(),numero.getExternalId(),numero.getHora(),numero.getEstado(),numero.getPrioridad()) : null;
	}
	
	public BusinessTramite requestTramite(JSONTramite tramite){
		return tramite != null ? new BusinessTramite(tramite.getCodigo(),tramite.getNombre()) : null;
	}
	
	public BusinessSector requestSector(JSONSector sector){
		return sector != null ? new BusinessSector(sector.getCodigo(),sector.getNombre(),sector.getRutaSector()) : null;
	}
	
	public BusinessDisplay requestDisplay(JSONDisplay display){
		return display != null ? new BusinessDisplay(display.getDisplayId(),display.getRutaArchivo()) : null;
	}
	public BusinessDatoComplementario requestDatoComplementario(JSONNumero numero){
		if(numero != null){
			JSONDatosComp dato = numero.getDatosComplementarios();
			return (dato!=null) ? new BusinessDatoComplementario(dato.getDocId(),dato.getNombreCompleto(),dato.getTipoDoc()) : null;
		}else{
			return null;
		}
	}
	
	public BusinessSector requestSectorFromTramSec(JSONTramiteSector tramSec){
		if(tramSec != null){
			return (tramSec.getSector()!= null) ? requestSector(tramSec.getSector()) : null;
		}else{
			return null;
		}		
	}
	
	public BusinessTramite requestTramiteFromTramSec(JSONTramiteSector tramSec){
		if(tramSec != null){
			return (tramSec.getSector()!= null) ? requestTramite(tramSec.getTramite()) : null;
		}else{
			return null;
		}
		
	}
	
	public BusinessNumero requestNumeroFromNumPuesto(JSONNumeroPuesto numPuesto){
		if(numPuesto != null){
			return (numPuesto.getNumero()!= null) ? requestNumero(numPuesto.getNumero()) : null;
		}else{
			return null;
		}
		
	}
	public BusinessPuesto requestPuestoFromNumPuesto(JSONNumeroPuesto numPuesto){
		if(numPuesto != null){
			return (numPuesto.getPuesto()!= null) ? requestPuesto(numPuesto.getPuesto()) : null;
		}else{
			return null;
		}
		
	}
}


