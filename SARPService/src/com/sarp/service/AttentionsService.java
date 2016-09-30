package com.sarp.service;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.spi.BadRequestException;

import com.sarp.controllers.AttentionsController;
import com.sarp.controllers.UserController;
import com.sarp.exceptions.ContextException;
import com.sarp.factory.Factory;
import com.sarp.json.modeler.JSONPuesto;

@RequestScoped
@Path("/attentionsService")
public class AttentionsService {


	@PUT
	@Path("/abrirPuesto")
	@Consumes(MediaType.APPLICATION_JSON)
	public String abrirPuesto(@HeaderParam("user-rol") String userRol, JSONPuesto puesto){
		Factory fac = Factory.getInstance();
		AttentionsController ctrl = fac.getAttentionsController();
		if(userRol.equals("Operador") || userRol.equals("OperadorAvanzado")){
			try{
				ctrl.abrirPuesto(puesto.getNombreMaquina(),puesto.getUsuarioId());
				return "Puesto "+puesto.getNombreMaquina()+" ha sido abierto con exito";
			
			}catch(ContextException e){
				throw new BadRequestException("Error: El puesto ya se encuentra abierto");
			
			}catch(Exception e){	
				throw new BadRequestException("Error al abrir el Puesto");
			}
		}else{
			throw new BadRequestException("No tiene permisos suficientes.");
		}
	}
	
	@PUT
	@Path("/cerrarPuesto")
	@Consumes(MediaType.APPLICATION_JSON)
	public String cerrarPuesto(@HeaderParam("user-rol") String userRol, JSONPuesto puesto){
		Factory fac = Factory.getInstance();
		AttentionsController ctrl = fac.getAttentionsController();
		if(userRol.equals("Operador") || userRol.equals("OperadorAvanzado")){
			try{
				ctrl.cerrarPuesto(puesto.getNombreMaquina());
				return "Puesto "+puesto.getNombreMaquina()+" ha sido cerrado con exito";
			}catch(Exception e){
				throw new BadRequestException("Error: El puesto ya se encuentra cerrado");
			}
		}else{
			throw new BadRequestException("No tiene permisos suficientes.");
		}
	}
	
	
	//CERRADO, DIPONIBLE, LLAMANDO, ATENDIENDO;
}
