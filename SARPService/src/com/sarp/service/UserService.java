package com.sarp.service;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.spi.BadRequestException;
import org.jboss.resteasy.spi.InternalServerErrorException;
import org.jboss.resteasy.spi.UnauthorizedException;

import com.sarp.controllers.AdminActionsController;
import com.sarp.controllers.AttentionsController;
import com.sarp.controllers.UserController;
import com.sarp.exceptions.ContextException;
import com.sarp.factory.Factory;
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
	
	@PUT
	@Path("/initPuestosNum")
	@Consumes(MediaType.APPLICATION_JSON)
	public String initPuestoNum(@HeaderParam("user-rol") String userRol, JSONPuesto puesto) throws Exception{
		
		String idPuesto = puesto.getUsuarioId();
		String sector = "MVD";
		
		//Tramite
		
		JSONTramite tram1 = new JSONTramite();
		tram1.setCodigo(1);
		tram1.setNombre("TramiteNombre1");
		//Me aseguro de que haya por lo menos un tramite en la base asi el tramite id 1 funciona
		Integer tramiteid = 1;
		
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
		tramiteSector.setTramiteId(1);
		
		//Numero
		JSONNumero numero = new JSONNumero();
		numero.setIdSector(sector);
		numero.setIdTramite(1);
		numero.setHora("15/08/2016-12:33");
		numero.setPrioridad(2);
		
		//Display
		JSONDisplay display = new JSONDisplay();
		display.setIdDisplay("displaySabe");
		
		//DisplaySector
		JSONSectorDisplay secDisp = new JSONSectorDisplay();
		secDisp.setDisplayId("displaySabe");
		secDisp.setSectorId(sector);
		
		
		//Controllers
		
		Factory fac = Factory.getInstance();
		AttentionsController ctrlAttention = fac.getAttentionsController();
		AdminActionsController ctrlAdmin = fac.getAdminActionsController();
		
		
		try{
			
			//PUESTO
			ctrlAdmin.altaPuesto(puesto);
			ctrlAdmin.modificarEstadoPuesto(idPuesto, "DISPONIBLE");
			
			//ASIGNO 
			ctrlAdmin.asignarPuestoSector(puestoSector);
			ctrlAdmin.asignarTramitePuesto(puestoTramite);
			ctrlAdmin.asignarTramiteSector(tramiteSector);
			ctrlAdmin.asignarSectorDisplay(secDisp);
			
			//SOLICITO NUMERO
			ctrlAttention.solicitarNumero(numero);
			
			//LLAMAR NUMERO
			JSONNumero numeroFinal = ctrlAttention.llamarNumero(puesto);
			
			
	
			return "OK";
		}catch(ContextException e){
			throw new InternalServerErrorException("Error: El puesto ya se encuentra abierto");
			
		}

	}
	
	
	
}
