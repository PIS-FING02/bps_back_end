package com.sarp.service.response.maker;

import java.util.ArrayList;
import java.util.Calendar;
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
		if(bussinesPuesto != null){
			JSONPuesto jsonPuesto = new JSONPuesto();
			jsonPuesto.setEstado(bussinesPuesto.getEstado() != null ? bussinesPuesto.getEstado().toString() : null);
			jsonPuesto.setNombreMaquina(bussinesPuesto.getNombreMaquina());
			//jsonPuesto.setNumeroAsignado(businessNumero != null ? this.numeroAtomResponse(businessNumero): null);
			jsonPuesto.setUsuarioId(bussinesPuesto.getUsuarioId());
			jsonPuesto.setNumeroPuesto(bussinesPuesto.getNumeroPuesto());
			
			return jsonPuesto;
		}else{
			return null;
		}
		
		
		
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
		
		if(businessNumero != null){
			JSONNumero jsonNumero = new JSONNumero();
			jsonNumero.setEstado(businessNumero.getEstado());
			jsonNumero.setExternalId(businessNumero.getExternalId());
			String fecha = Integer.toString(businessNumero.getHora().get(Calendar.DAY_OF_MONTH)).length() > 1 ? Integer.toString(businessNumero.getHora().get(Calendar.DAY_OF_MONTH)) : "0"+Integer.toString(businessNumero.getHora().get(Calendar.DAY_OF_MONTH));
			fecha = fecha +"/" + (Integer.toString(businessNumero.getHora().get(Calendar.MONTH)+1).length() > 1 ? Integer.toString(businessNumero.getHora().get(Calendar.MONTH)+1) : "0"+Integer.toString(businessNumero.getHora().get(Calendar.MONTH)+1));
			fecha = fecha + "/" + Integer.toString(businessNumero.getHora().get(Calendar.YEAR));
			fecha = fecha + "-";
			fecha = fecha + (Integer.toString(businessNumero.getHora().get(Calendar.HOUR_OF_DAY)).length() > 1 ? Integer.toString(businessNumero.getHora().get(Calendar.HOUR_OF_DAY)) : "0"+Integer.toString(businessNumero.getHora().get(Calendar.HOUR_OF_DAY)));
			fecha = fecha + ":" + (Integer.toString(businessNumero.getHora().get(Calendar.MINUTE)).length() > 1 ? Integer.toString(businessNumero.getHora().get(Calendar.MINUTE)) : "0"+Integer.toString(businessNumero.getHora().get(Calendar.MINUTE)));
			jsonNumero.setHora(fecha);
			jsonNumero.setId(businessNumero.getInternalId());
			jsonNumero.setPrioridad(businessNumero.getPrioridad());
			jsonNumero.setIdSector(businessNumero.getCodSector());
			jsonNumero.setIdTramite(businessNumero.getCodTramite());
			
			return jsonNumero;
		}else{
			return null;
		}

	}
	
	public JSONNumero numeroFullResponse(BusinessNumero businessNumero, BusinessDatoComplementario businessDatosComp, BusinessTramiteSector businessTramiteSector, BusinessTramite businessTramite, BusinessSector businessSector ) {
		
		JSONNumero jsonNumero = new JSONNumero();
		jsonNumero.setEstado(businessNumero.getEstado());
		jsonNumero.setExternalId(businessNumero.getExternalId());
		jsonNumero.setHora(businessNumero.getHora().get(Calendar.YEAR) + "/" + businessNumero.getHora().get(Calendar.MONTH) + "/" + businessNumero.getHora().get(Calendar.DAY_OF_MONTH) + "-" + businessNumero.getHora().get(Calendar.HOUR_OF_DAY) + ":" + businessNumero.getHora().get(Calendar.MINUTE));
		jsonNumero.setId(businessNumero.getInternalId());
		jsonNumero.setPrioridad(businessNumero.getPrioridad());
		jsonNumero.setDatosComplementarios(this.datosComplementariosResponse(businessDatosComp));
		jsonNumero.setIdSector(businessSector.getSectorId());
		jsonNumero.setIdTramite(businessTramite.getCodigo());
		
		return jsonNumero;
	}

	/*     JSONTramiteSector      */
	public JSONTramiteSector tramiteSectorResponse(BusinessTramite businessTramite, BusinessSector businessSector) {
		JSONTramiteSector jsonTramiteSector = new JSONTramiteSector();
		jsonTramiteSector.setTramiteId(businessTramite.getCodigo());
		jsonTramiteSector.setSectorId(businessSector.getSectorId());
		
		return jsonTramiteSector;
	}

	/*     JSONSector     */
	public JSONSector sectorAtomResponse(BusinessSector businessSector) {
		
		if(businessSector != null){
			JSONSector jsonSector = new JSONSector();
			jsonSector.setCodigo(businessSector.getSectorId());
			jsonSector.setNombre(businessSector.getNombre());
			jsonSector.setRutaSector(businessSector.getRuta());
			
			return jsonSector;
		}else{
			return null;
		}

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
		if(businessTramite != null){
			JSONTramite jsonTramite = new JSONTramite();
			jsonTramite.setCodigo(businessTramite.getCodigo());
			jsonTramite.setNombre(businessTramite.getNombre());
			
			return jsonTramite;
		}else{
			return null;
		}

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
		if(businessDisplay != null){
			JSONDisplay jsonDisplay = new JSONDisplay();
			jsonDisplay.setIdDisplay(businessDisplay.getIdDisplay());

			return jsonDisplay;
		}else{
			return null;
		}

	}
	
	public JSONDisplay displayFullResponse(BusinessDisplay businessDisplay, List<BusinessSector> businessSectores) {
		JSONDisplay jsonDisplay = this.displayAtomResponse(businessDisplay);
		jsonDisplay.setSectores(createArrayAtomSectores(businessSectores));
		
		return jsonDisplay;
	}
	
	/* JSONNUMEROPUESTO */
	public JSONNumeroPuesto numeroPuestoResponse(BusinessNumero businessNumero, BusinessPuesto businessPuesto){
		JSONNumeroPuesto jsonNumeroPuesto = new JSONNumeroPuesto();
		jsonNumeroPuesto.setNumeroId(businessNumero.getInternalId());
		jsonNumeroPuesto.setNombreMaquina(businessPuesto.getNombreMaquina());
		
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
		if(businessSectores!= null){
			List<JSONSector> listJSONSector = new ArrayList<JSONSector>();
			for(BusinessSector businessSector : businessSectores){
				listJSONSector.add(this.sectorAtomResponse(businessSector));
			}
			return listJSONSector;
		}else{
			return null;
		}
	}

	public List<JSONPuesto> createArrayAtomPuestos(List<BusinessPuesto> businessPuestos) {
		if(businessPuestos != null){
			List<JSONPuesto> listJSONPuesto = new ArrayList<JSONPuesto>();
			for(BusinessPuesto businessPuesto : businessPuestos){
				listJSONPuesto.add(this.puestoAtomResponse(businessPuesto));
			}
			return listJSONPuesto;
		}else{
			return null;
		}

	}
	
	private List<JSONDisplay> createArrayAtomDisplay(List<BusinessDisplay> businessDisplays) {
		if(businessDisplays != null){
			List<JSONDisplay> listJSONDisplay = new ArrayList<JSONDisplay>();
			for(BusinessDisplay businessDisplay : businessDisplays){
				listJSONDisplay.add(this.displayAtomResponse(businessDisplay));
			}
			return listJSONDisplay;
		}else{
			return null;
		}

	}
	
	public List<JSONNumero> createListJSONNumeros(List<BusinessNumero> listaNumeros){
		if(listaNumeros != null){
			List<JSONNumero> listaJsonNros = new ArrayList<JSONNumero>();
			for(BusinessNumero bn : listaNumeros){
				listaJsonNros.add(numeroAtomResponse(bn));
			}
			return listaJsonNros;
		}else{
			return null;
		}

	}


}
