package com.sarp.service.response.maker;

import java.util.ArrayList;
import java.util.List;

import com.sarp.classes.BusinessDatoComplementario;
import com.sarp.classes.BusinessDisplay;
import com.sarp.classes.BusinessNumero;
import com.sarp.classes.BusinessPuesto;
import com.sarp.classes.BusinessSector;
import com.sarp.classes.BusinessTramite;
import com.sarp.classes.BusinessTramiteSector;
import com.sarp.json.modeler.JSONDatosComp;
import com.sarp.json.modeler.JSONDisplay;
import com.sarp.json.modeler.JSONNumero;
import com.sarp.json.modeler.JSONNumeroPuesto;
import com.sarp.json.modeler.JSONPuesto;
import com.sarp.json.modeler.JSONSector;
import com.sarp.json.modeler.JSONTramite;
import com.sarp.json.modeler.JSONTramiteSector;

public class ResponseMaker {

	private ResponseMaker(){};
	private static ResponseMaker instance;
	
	public static ResponseMaker getInstance(){
		instance = (instance!= null)? instance : new ResponseMaker(); 
		return instance;
	}
	
	/*     JSONPUESTO      */
	public JSONPuesto puestoAtomResponse(BusinessPuesto bussinesPuesto){
		JSONPuesto jsonPuesto = new JSONPuesto();
		jsonPuesto.setEstado(bussinesPuesto.getEstado() != null ? bussinesPuesto.getEstado().toString() : null);
		jsonPuesto.setNombreMaquina(bussinesPuesto.getNombreMaquina());
		//jsonPuesto.setNumeroAsignado(businessNumero != null ? this.numeroAtomResponse(businessNumero): null);
		jsonPuesto.setUsuarioId(bussinesPuesto.getUsuarioId());
		jsonPuesto.setNumeroPuesto(bussinesPuesto.getNumeroPuesto());
		
		return jsonPuesto;
	}
	
	public JSONPuesto puestoFullResponse(BusinessPuesto bussinesPuesto, List<BusinessSector> businessSectores, List<BusinessTramite> businessTramites, BusinessNumero businessNumero){
		JSONPuesto jsonPuesto = this.puestoAtomResponse(bussinesPuesto);
		JSONNumero jsonNumero = this.numeroAtomResponse(businessNumero);
		jsonPuesto.setNumeroAsignado(jsonNumero);
		List<JSONSector> listJSONSector = new ArrayList<JSONSector>();
		for(BusinessSector businessSector : businessSectores){
			listJSONSector.add(this.sectorAtomResponse(businessSector));
		}
		jsonPuesto.setSectores(listJSONSector);
		
		List<JSONTramite> listJSONTramite = new ArrayList<JSONTramite>();
		for(BusinessTramite businessTramite : businessTramites){
			listJSONTramite.add(this.tramiteAtomResponse(businessTramite));
		}
		jsonPuesto.setTramites(listJSONTramite);

		return jsonPuesto;
	}
	
	/*     JSONNUMERO      */
	public JSONNumero numeroAtomResponse(BusinessNumero businessNumero) {
		JSONNumero jsonNumero = new JSONNumero();
		jsonNumero.setEstado(businessNumero.getEstado());
		jsonNumero.setExternalId(businessNumero.getExternalId());
		jsonNumero.setHora(businessNumero.getHora());
		jsonNumero.setId(businessNumero.getInternalId());
		jsonNumero.setPrioridad(businessNumero.getPrioridad());
		
		return jsonNumero;
	}
	
	public JSONNumero numeroFullResponse(BusinessNumero businessNumero, BusinessDatoComplementario businessDatosComp, BusinessTramiteSector businessTramiteSector, BusinessTramite businessTramite, BusinessSector businessSector ) {
		
		JSONNumero jsonNumero = new JSONNumero();
		jsonNumero.setEstado(businessNumero.getEstado());
		jsonNumero.setExternalId(businessNumero.getExternalId());
		jsonNumero.setHora(businessNumero.getHora());
		jsonNumero.setId(businessNumero.getInternalId());
		jsonNumero.setPrioridad(businessNumero.getPrioridad());
		jsonNumero.setDatosComplementarios(this.datosComplementariosResponse(businessDatosComp));
		jsonNumero.setTramiteSector(this.tramiteSectorResponse(businessTramite, businessSector));
		
		return jsonNumero;
	}

	/*     JSONTramiteSector      */
	public JSONTramiteSector tramiteSectorResponse(BusinessTramite businessTramite, BusinessSector businessSector) {
		JSONTramiteSector jsonTramiteSector = new JSONTramiteSector();
		jsonTramiteSector.setTramite(this.tramiteAtomResponse(businessTramite));
		jsonTramiteSector.setSector(this.sectorAtomResponse(businessSector));
		
		return jsonTramiteSector;
	}

	/*     JSONSector     */
	public JSONSector sectorAtomResponse(BusinessSector businessSector) {
		JSONSector jsonSector = new JSONSector();
		jsonSector.setCodigo(businessSector.getSectorId());
		jsonSector.setNombre(businessSector.getNombre());
		jsonSector.setRutaSector(businessSector.getRuta());
		
		return jsonSector;
	}

	public JSONSector sectorFullResponse(BusinessSector businessSector, List<BusinessTramite> businessTramites, List<BusinessDisplay> businessDisplays, List<BusinessPuesto> businessPuestos) {
		JSONSector jsonSector = this.sectorAtomResponse(businessSector);
		
		jsonSector.setPuestos(createArrayAtomPuestos(businessPuestos));
		jsonSector.setDisplays(createArrayAtomDisplay(businessDisplays));
		jsonSector.setTramites(createArrayAtomTramites(businessTramites));
		
		return jsonSector;
	}
	
	/*     JSONTRAMITE     */
	public JSONTramite tramiteAtomResponse(BusinessTramite businessTramite) {
		JSONTramite jsonTramite = new JSONTramite();
		jsonTramite.setCodigo(businessTramite.getCodigo());
		jsonTramite.setNombre(businessTramite.getNombre());
		
		return jsonTramite;
	}

	public JSONTramite tramiteFullResponse(BusinessTramite businessTramite,  List<BusinessSector> businessSectores, List<BusinessPuesto> businessPuestos) {
		JSONTramite jsonTramite = this.tramiteAtomResponse(businessTramite);
		jsonTramite.setSectores(createArrayAtomSectores(businessSectores));
		jsonTramite.setPuestos(createArrayAtomPuestos(businessPuestos));
		
		return jsonTramite;
	}
	
	/*     JSONDATOSCOMP     */
	public JSONDatosComp datosComplementariosResponse(BusinessDatoComplementario datoComplementario) {
		JSONDatosComp jsonDatosComp = new JSONDatosComp();
		jsonDatosComp.setDocId(datoComplementario.getDocIdentidad());
		jsonDatosComp.setNombreCompleto(datoComplementario.getNombreCompleto());
		jsonDatosComp.setTipoDoc(datoComplementario.getTipoDoc());
		
		return jsonDatosComp;
	}
	
	/*     JSONDISPLAY     */
	public JSONDisplay displayAtomResponse(BusinessDisplay businessDisplay) {
		JSONDisplay jsonDisplay = new JSONDisplay();
		jsonDisplay.setDisplayId(businessDisplay.getCodigo());
		jsonDisplay.setRutaArchivo(businessDisplay.getRutaArchivo());
	
		return jsonDisplay;
	}
	
	public JSONDisplay displayFullResponse(BusinessDisplay businessDisplay, List<BusinessSector> businessSectores) {
		JSONDisplay jsonDisplay = this.displayAtomResponse(businessDisplay);
		jsonDisplay.setSectores(createArrayAtomSectores(businessSectores));
		
		return jsonDisplay;
	}
	
	/* JSONNUMEROPUESTO */
	public JSONNumeroPuesto numeroPuestoResponse(BusinessNumero businessNumero, BusinessPuesto businessPuesto){
		JSONNumeroPuesto jsonNumeroPuesto = new JSONNumeroPuesto();
		jsonNumeroPuesto.setNumero(this.numeroAtomResponse(businessNumero));
		jsonNumeroPuesto.setPuesto(this.puestoAtomResponse(businessPuesto));
		
		return jsonNumeroPuesto;
	}
	
	/* UTILS */
	public List<JSONTramite> createArrayAtomTramites(List<BusinessTramite> businessTramites) {
		List<JSONTramite> listJSONTramite = new ArrayList<JSONTramite>();
		for(BusinessTramite businessTramite : businessTramites){
			listJSONTramite.add(this.tramiteAtomResponse(businessTramite));
		}
		return listJSONTramite;
	}
	
	public List<JSONSector> createArrayAtomSectores(List<BusinessSector> businessSectores) {
		
		List<JSONSector> listJSONSector = new ArrayList<JSONSector>();
		for(BusinessSector businessSector : businessSectores){
			listJSONSector.add(this.sectorAtomResponse(businessSector));
		}
		return listJSONSector;
	}

	public List<JSONPuesto> createArrayAtomPuestos(List<BusinessPuesto> businessPuestos) {
		List<JSONPuesto> listJSONPuesto = new ArrayList<JSONPuesto>();
		for(BusinessPuesto businessPuesto : businessPuestos){
			listJSONPuesto.add(this.puestoAtomResponse(businessPuesto));
		}
		return listJSONPuesto;
	}
	
	private List<JSONDisplay> createArrayAtomDisplay(List<BusinessDisplay> businessDisplays) {
		List<JSONDisplay> listJSONDisplay = new ArrayList<JSONDisplay>();
		for(BusinessDisplay businessDisplay : businessDisplays){
			listJSONDisplay.add(this.displayAtomResponse(businessDisplay));
		}
		return listJSONDisplay;
	}
	
	public List<JSONNumero> createListJSONNumeros(List<BusinessNumero> listaNumeros){
		List<JSONNumero> listaJsonNros = new ArrayList<JSONNumero>();
		for(BusinessNumero bn : listaNumeros){
			listaJsonNros.add(numeroAtomResponse(bn));
		}
		return listaJsonNros;
	}


}
