package com.sarp.service;

import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import org.jboss.resteasy.spi.InternalServerErrorException;
import org.jboss.resteasy.spi.NotFoundException;
import org.jboss.resteasy.spi.UnauthorizedException;
import com.sarp.controllers.AttentionsController;
import com.sarp.exceptions.ContextException;
import com.sarp.factory.Factory;
import com.sarp.json.modeler.JSONNumero;
import com.sarp.json.modeler.JSONPuesto;
import com.sarp.json.modeler.JSONTramiteSector;

@RequestScoped
@Path("/attentionsService")
public class AttentionsService {

	@PUT
	@Path("/abrirPuesto")
	@Consumes(MediaType.APPLICATION_JSON)
	public String abrirPuesto(@HeaderParam("user-rol") String userRol, JSONPuesto puesto){
		if(userRol.equals("Operador") || userRol.equals("OperadorSenior")){
			try{
				Factory fac = Factory.getInstance();
				AttentionsController ctrl = fac.getAttentionsController();
				ctrl.abrirPuesto(puesto);
				return "OK";
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
		if(userRol.equals("Operador") || userRol.equals("OperadorSenior")){
			try{
				ctrl.cerrarPuesto(puesto);
				return "OK";
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
		if(userRol.equals("Operador") || userRol.equals("OperadorSenior")){
			try{
				Factory fac = Factory.getInstance();
				AttentionsController ctrl = fac.getAttentionsController();
				ctrl.comenzarAtencion(puesto);
				return "OK";
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
				return "OK";
			}catch(Exception e){
				throw new InternalServerErrorException("Error: El puesto no se encuentra en un estado correcto");
			}
		}else{
			throw new UnauthorizedException("No tiene permisos suficientes.");
		}
	}
	
	@GET
	@Path("/llamarNumero")
	@Produces(MediaType.APPLICATION_JSON)
	public JSONNumero llamarNumero(@HeaderParam("user-rol") String userRol, @HeaderParam("hparam") String puesto){
		if(userRol.equals("Operador") || userRol.equals("OperadorSenior")){
			try{
				Factory fac = Factory.getInstance();
				AttentionsController ctrl = fac.getAttentionsController();
				JSONNumero num = ctrl.llamarNumero(puesto);
				if(num != null){
					return num;
				}else{
					throw new NotFoundException("No hay numero disponible en este momento");
				}
			}catch(Exception e){
				throw new InternalServerErrorException("Error: "+e.getMessage());
			}
		}else{
			throw new UnauthorizedException("No tiene permisos suficientes.");
		}
	}
	

	@GET
	@Path("/tramitesRecepcion")
	@Produces(MediaType.APPLICATION_JSON)
	public List<JSONTramiteSector> tramitesRecepcion(@HeaderParam("user-rol") String userRol, @HeaderParam("hparam") String codigoMaquina){
		Factory fac = Factory.getInstance();
		AttentionsController ctrl = fac.getAttentionsController();
		if(userRol.equals("Recepcionista")){
			try{
				List<JSONTramiteSector> tramitesRecepcion =  ctrl.tramitesRecepcion(codigoMaquina);
				return tramitesRecepcion;
				
			}catch(Exception e){
				throw new InternalServerErrorException("Error: "+e.getMessage());
			} 
		}else{
			throw new UnauthorizedException("No tiene permisos suficientes.");
		}
	}

	@PUT
	@Path("/atrasarNumero")
	@Consumes(MediaType.APPLICATION_JSON)
	public String atrasarNumero(@HeaderParam("user-rol") String userRol, JSONPuesto puesto){
		if(userRol.equals("Operador") || userRol.equals("OperadorSenior")){
			try{
				Factory fac = Factory.getInstance();
				AttentionsController ctrl = fac.getAttentionsController();
				ctrl.atrasarNumero(puesto);	
				return "OK";
			}catch(Exception e){
				//La excepcion puede ser por un error interno o por que no se reservo un numero con prioridad??
				throw new InternalServerErrorException("No se pudo atrasar el numero "+e.getMessage());
			}
		}else{
			throw new UnauthorizedException("No tiene permisos suficientes.");
		}
	}
	
	@PUT
	@Path("/pausarNumero")
	@Consumes(MediaType.APPLICATION_JSON)
	public String pausarNumero(@HeaderParam("user-rol") String userRol, JSONPuesto puesto){
		if(userRol.equals("Operador") || userRol.equals("OperadorSenior")){
			Factory fac = Factory.getInstance();
			AttentionsController ctrl = fac.getAttentionsController();
			try{
				ctrl.pausarNumero(puesto);
				return "OK";
			}catch(Exception e){
				throw new InternalServerErrorException("No se pudo pausar el numero "+e.getMessage());
			}
		}else{
			throw new UnauthorizedException("No tiene permisos suficientes.");
		}
	}
	
	//CERRADO, DIPONIBLE, LLAMANDO, ATENDIENDO;
	
	@GET
	@Path("/fakeNumber")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONNumero listarTramites(@HeaderParam("user-rol") String userRol, @HeaderParam("user") String user) {
		if(userRol.equals("Operador")){
				JSONNumero jnumero = new JSONNumero();
				jnumero.setId(50);
				jnumero.setExternalId("50");
				jnumero.setHora("04/05/1849-20:24");
				jnumero.setEstado("PAUSADO");
				jnumero.setPrioridad(1);
				jnumero.setIdTramite(4990);
				jnumero.setIdSector("MVD_FIS");
				return jnumero;
		}else{
			throw new UnauthorizedException("No tiene permisos suficientes.");
		}
    }
	
	@GET
	@Path("/llamarPausado")
	@Produces(MediaType.APPLICATION_JSON)
	public JSONNumero LlamarNumeroPausado(@HeaderParam("user-rol") String userRol,  
			@HeaderParam("idNumero") Integer idNumero, 
			@HeaderParam("idPuesto") String idPuesto) {
		if (userRol.equals("Operador") || userRol.equals("OperadorSenior")) {
			try {
				Factory fac = Factory.getInstance();
				AttentionsController ctrl = fac.getAttentionsController();
				JSONNumero num = ctrl.llamarNumeroPausado(idNumero, idPuesto);
				return num;
			} catch (Exception e) {
				throw new InternalServerErrorException("Error al soliticar numero pausado: "+e.getMessage());
			}
		} else {
			throw new UnauthorizedException("No tiene permisos para realizar esta accion.");
		}
	}
	
	@GET
	@Path("/llamarAtrasado")
	@Produces(MediaType.APPLICATION_JSON)
	public JSONNumero LlamarNumeroAtrasado(@HeaderParam("user-rol") String userRol,  
			@HeaderParam("idNumero") Integer idNumero, 
			@HeaderParam("idPuesto") String idPuesto) {
		if (userRol.equals("Operador") || userRol.equals("OperadorSenior")) {
			try {
				Factory fac = Factory.getInstance();
				AttentionsController ctrl = fac.getAttentionsController();
				JSONNumero num = ctrl.llamarNumeroAtrasado(idNumero, idPuesto);
				return num;
			} catch (Exception e) {
				throw new InternalServerErrorException("Error al soliticar numero atrasado: "+e.getMessage());
			}
		} else {
			throw new UnauthorizedException("No tiene permisos para realizar esta accion.");
		}
	}
	
}
