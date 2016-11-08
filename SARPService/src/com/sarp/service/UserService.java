package com.sarp.service;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;
import com.sarp.utils.UtilService;
import com.sarp.beans.AdminBean;
import com.sarp.beans.AttentionsBean;
import com.sarp.json.modeler.JSONDisplay;
import com.sarp.json.modeler.JSONNumero;
import com.sarp.json.modeler.JSONPuesto;
import com.sarp.json.modeler.JSONPuestoSector;
import com.sarp.json.modeler.JSONPuestoTramite;
import com.sarp.json.modeler.JSONSectorDisplay;
import com.sarp.json.modeler.JSONTramite;
import com.sarp.json.modeler.JSONTramiteSector;

@RequestScoped
@Path("/userService")
public class UserService {
	@EJB
	private AttentionsBean attBean = new AttentionsBean();
	
	@EJB
	private AdminBean adminBean = new AdminBean();
	
	private static Logger logger = Logger.getLogger(UserService.class);
	
	@PUT
	@Path("/initPuestosNum")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response initPuestoNum(JSONPuesto puesto) throws Exception{
		
		//Controllers
		
		
		String idPuesto = puesto.getNombreMaquina();
		String sector = "0";
		
		//PUESTO
	//	ctrlAdmin.altaPuesto(puesto);
		adminBean.modificarPuesto(puesto);
		
		//Tramite
		
		JSONTramite tram1 = new JSONTramite();
		tram1.setNombre("TramiteNombrea");
		//Me aseguro de que haya por lo menos un tramite en la base asi el tramite id 1 funciona
		String tramiteid = "1";
		
		//PuestoSector
		JSONPuestoSector puestoSector = new JSONPuestoSector();
		puestoSector.setNombreMaquina(idPuesto);
		puestoSector.setSectorId(sector);
		
		//PuestoTramite
		
		JSONPuestoTramite puestoTramite = new JSONPuestoTramite();
		puestoTramite.setNombreMaquina(idPuesto);
		puestoTramite.setTramiteId(tramiteid);
		
		//TramiteSector
		JSONTramiteSector tramiteSector = new JSONTramiteSector();
		tramiteSector.setSectorId(sector);
		tramiteSector.setTramiteId(tramiteid);
		
		//Numero
		JSONNumero numero = new JSONNumero();
		numero.setIdSector(sector);
		numero.setIdTramite(tramiteid);
		numero.setHora("16/08/2016-14:25");
		numero.setPrioridad(2);
		
		//Display
		JSONDisplay display = new JSONDisplay();
		display.setIdDisplay("displaySabe3");
		
		//DisplaySector
		JSONSectorDisplay secDisp = new JSONSectorDisplay();
		secDisp.setDisplayId("displaySabe3");
		secDisp.setSectorId(sector);
		
		try{
			
			//ASIGNO 
			//ctrlAdmin.asignarPuestoSector(puestoSector);
			System.out.println("asignarPuestoSector");
			//ctrlAdmin.asignarTramiteSector(tramiteSector);
			System.out.println("asignarTramiteSector");
			//ctrlAdmin.asignarTramitePuesto(puestoTramite);
			System.out.println("asignarTramitePuesto");
			
			//ctrlAdmin.altaDisplay(display.getIdDisplay());
			System.out.println("altaDisplay");
			//ctrlAdmin.asignarSectorDisplay(secDisp);
			System.out.println("asignarSectorDisplay");
			
			//REUNICIALIZO COLAS
			adminBean.reinicializarColas();
			
			//SOLICITO NUMERO
			attBean.solicitarNumero(numero);
			
			//LLAMAR NUMERO
			//JSONNumero numeroFinal = ctrlAttention.llamarNumero(idPuesto);
			//ctrlAdmin.modificarPuesto(puesto);
			//numeroFinal = ctrlAttention.llamarNumero(idPuesto);
			
			//numeroFinal = null;
			//COMMENZAR ATENCION
			//ctrlAttention.comenzarAtencion(puesto);
			
			//FINALIZAR ATENCION
			
			//ctrlAttention.finalizarAtencion(puesto);
			//String a = "sa";
			//List<JSONTramiteSector>  tramitesRecepcion = ctrlAttention.tramitesRecepcion(puesto.getNombreMaquina());
				
			return Response.ok("OK").build();	
		}catch(Exception e){
			logger.error(e.toString() + ". PUT initPuestosNum - params: puesto: "+ puesto);
			return Response.ok("ERROR: " + e.getMessage()).build();			
		}

	}
	
	
	
}
