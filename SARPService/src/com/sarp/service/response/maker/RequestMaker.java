package com.sarp.service.response.maker;

<<<<<<< e770eb98c572a37397dbc3aef5e5769898a4f493
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
=======
import com.sarp.classes.BusinessPuesto;
>>>>>>> creacion

public class RequestMaker {
	
	private RequestMaker(){}
	private RequestMaker instance;
	
	public RequestMaker getInstance(){
		instance = (instance!= null) ? instance : new RequestMaker();
		return instance;
	}
	
<<<<<<< e770eb98c572a37397dbc3aef5e5769898a4f493
	public BusinessPuesto requestPuesto(JSONPuesto puesto){
		return new BusinessPuesto(puesto.getNombreMaquina(),puesto.getUsuarioId(),puesto.getEstado(),puesto.getNumeroPuesto());
	}
	
	public BusinessNumero requestNumero(JSONNumero numero){
	
		return new BusinessNumero(numero.getId(),numero.getExternalId(),numero.getHora(),numero.getEstado(),numero.getPrioridad());
	}
	
	public BusinessTramite requestTramite(JSONTramite tramite){
		return new BusinessTramite(tramite.getCodigo(),tramite.getNombre());
	}
	
	public BusinessSector requestSector(JSONSector sector){
		return new BusinessSector(sector.getCodigo(),sector.getNombre(),sector.getRutaSector());
	}
	
	public BusinessDisplay requestDisplay(JSONDisplay display){
		return new BusinessDisplay(display.getDisplayId(),display.getRutaArchivo());
	}
	public BusinessDatoComplementario requestDatoComplementario(JSONNumero numero){
		JSONDatosComp dato = numero.getDatosComplementarios();
		return (dato!=null) ? new BusinessDatoComplementario(dato.getDocId(),dato.getNombreCompleto(),dato.getTipoDoc()) : null;
	}
	
	public BusinessSector requestSectorFromTramSec(JSONTramiteSector tramSec){
		return (tramSec.getSector()!= null) ? requestSector(tramSec.getSector()) : null;
	}
	
	public BusinessTramite requestTramiteFromTramSec(JSONTramiteSector tramSec){
		return (tramSec.getSector()!= null) ? requestTramite(tramSec.getTramite()) : null;
	}
	
	public BusinessNumero requestNumeroFromNumPuesto(JSONNumeroPuesto numPuesto){
		return (numPuesto.getNumero()!= null) ? requestNumero(numPuesto.getNumero()) : null;
	}
	public BusinessPuesto requestPuestoFromNumPuesto(JSONNumeroPuesto numPuesto){
		return (numPuesto.getPuesto()!= null) ? requestPuesto(numPuesto.getPuesto()) : null;
	}
}



=======
	public BusinessPuesto requestPuesto(JSONPuesto){
		
	}
}
>>>>>>> creacion
