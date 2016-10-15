package com.sarp.service;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.spi.BadRequestException;
import org.jboss.resteasy.spi.InternalServerErrorException;
import org.jboss.resteasy.spi.NotFoundException;
import org.jboss.resteasy.spi.UnauthorizedException;

import com.sarp.controllers.AttentionsController;
import com.sarp.controllers.UserController;
import com.sarp.exceptions.ContextException;
import com.sarp.factory.Factory;
import com.sarp.json.modeler.JSONNumero;
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
				ctrl.abrirPuesto(puesto);
				return "Puesto "+puesto.getNombreMaquina()+" ha sido abierto con exito";
			
			}catch(ContextException e){
				throw new InternalServerErrorException("Error: El puesto ya se encuentra abierto");
			
			}catch(Exception e){	
				throw new InternalServerErrorException("Error al abrir el Puesto");
			}
		}else{
			throw new UnauthorizedException("No tiene permisos suficientes.");
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
				ctrl.cerrarPuesto(puesto);
				return "Puesto "+puesto.getNombreMaquina()+" ha sido cerrado con exito";
			}catch(Exception e){
				throw new InternalServerErrorException("Error: El puesto ya se encuentra cerrado");
			}
		}else{
			throw new UnauthorizedException("No tiene permisos suficientes.");
		}
	}
	@PUT
	@Path("/comenzarAtencion")
	@Consumes(MediaType.APPLICATION_JSON)
	public String comenzarAtencion(@HeaderParam("user-rol") String userRol, JSONPuesto puesto){
		Factory fac = Factory.getInstance();
		AttentionsController ctrl = fac.getAttentionsController();
		if(userRol.equals("Operador") || userRol.equals("OperadorAvanzado")){
			try{
				ctrl.comenzarAtencion(puesto);
				return "Puesto "+puesto.getNombreMaquina()+" comenzo atencion satisfactoriamente";
			}catch(Exception e){
				throw new InternalServerErrorException("Error: El puesto no se encuentra en un estado correcto");
			}
		}else{
			throw new UnauthorizedException("No tiene permisos suficientes.");
		}
	}
	
	@PUT
	@Path("/finalizarAtencion")
	@Consumes(MediaType.APPLICATION_JSON)
	public String finalizarAtencion(@HeaderParam("user-rol") String userRol, JSONPuesto puesto){
		Factory fac = Factory.getInstance();
		AttentionsController ctrl = fac.getAttentionsController();
		if(userRol.equals("Operador") || userRol.equals("OperadorAvanzado")){
			try{
				ctrl.finalizarAtencion(puesto);
				return "Puesto "+puesto.getNombreMaquina()+" finalizo atencion satisfactoriamente";
			}catch(Exception e){
				throw new InternalServerErrorException("Error: El puesto no se encuentra en un estado correcto");
			}
		}else{
			throw new UnauthorizedException("No tiene permisos suficientes.");
		}
	}
	
	@PUT
	@Path("/llamarNumero")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public JSONNumero llamarNumero(@HeaderParam("user-rol") String userRol, JSONPuesto puesto){
		Factory fac = Factory.getInstance();
		AttentionsController ctrl = fac.getAttentionsController();
		if(userRol.equals("Operador") || userRol.equals("OperadorAvanzado")){
			try{
				JSONNumero num = ctrl.llamarNumero(puesto);
				if(num != null){
					return num;
				}else{
					throw new NotFoundException("No hay numero disponible en este momento");
				}
				
			}catch(Exception e){
				//La excepcion puede ser por un error interno o por que no se reservo un numero con prioridad??
				throw new InternalServerErrorException("Error: El puesto no se encuentra en un estado correcto");
			}
		}else{
			throw new UnauthorizedException("No tiene permisos suficientes.");
		}
	}
	
	/*@PUT
	@Path("/atrasarNumero")
	@Consumes(MediaType.APPLICATION_JSON)
	public String atrasarNumero(@HeaderParam("user-rol") String userRol, JSONPuesto puesto){
		Factory fac = Factory.getInstance();
		AttentionsController ctrl = fac.getAttentionsController();
		if(userRol.equals("Operador") || userRol.equals("OperadorAvanzado")){
			try{
				JSONNumero num = null;//ctrl.atrasarNumero(puesto);				
			}catch(Exception e){
				//La excepcion puede ser por un error interno o por que no se reservo un numero con prioridad??
				throw new BadRequestException("Error: El puesto no se encuentra en un estado correcto");
			}
		}else{
			throw new BadRequestException("No tiene permisos suficientes.");
		}
	}
	/*
	
	@PUT
	@Path("/pausarNumero")
	@Consumes(MediaType.APPLICATION_JSON)
	public String pausarNumero(@HeaderParam("user-rol") String userRol, JSONPuesto puesto){
		Factory fac = Factory.getInstance();
		AttentionsController ctrl = fac.getAttentionsController();
		if(userRol.equals("Operador") || userRol.equals("OperadorAvanzado")){
			try{
				ctrl.finalizarAtencion(puesto);
				return "Puesto "+puesto.getNombreMaquina()+" finalizo atencion satisfactoriamente";
			}catch(Exception e){
				throw new BadRequestException("Error: El puesto no se encuentra en un estado correcto");
			}
		}else{
			throw new BadRequestException("No tiene permisos suficientes.");
		}
	}*/
	
	
	//CERRADO, DIPONIBLE, LLAMANDO, ATENDIENDO;
}
