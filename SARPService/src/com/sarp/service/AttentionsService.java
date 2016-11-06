package com.sarp.service;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;
import org.jboss.resteasy.spi.NotFoundException;
import org.jboss.resteasy.spi.UnauthorizedException;

import com.sarp.beans.AttentionsBean;
import com.sarp.json.modeler.JSONEstadoPuesto;
import com.sarp.json.modeler.JSONFinalizarAtencion;
import com.sarp.json.modeler.JSONNumero;
import com.sarp.json.modeler.JSONPuesto;
import com.sarp.json.modeler.JSONSector;
import com.sarp.json.modeler.JSONTramiteSector;

@RequestScoped
@Path("/attentionsService")
public class AttentionsService {
		
	@EJB
	private AttentionsBean attBean = new AttentionsBean();
	
	private String RECEPCION = "RECEPCION";//UtilService.getStringProperty("RECEPCION");
	private String OPERADOR = "OPERADOR";//UtilService.getStringProperty("OPERADOR");
	private String OPERADORSR = "OPERADORSR";//UtilService.getStringProperty("OPERADOR_SENIOR");

	private static Logger logger = Logger.getLogger(AttentionsService.class);
	
	@PUT
	@Path("/abrirPuesto")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response abrirPuesto(@HeaderParam("user-rol") String userRol, JSONPuesto puesto){		
		try{
			if(userRol.equals(OPERADOR) || userRol.equals(OPERADORSR)){
				JSONEstadoPuesto estadoPuesto = attBean.abrirPuesto(puesto);
				return Response.ok(estadoPuesto).build();
			}else{
				throw new UnauthorizedException("Permisos insuficientes: " + OPERADOR + "/" + OPERADORSR);
			}
		}catch(Exception e){
			logger.error(e.toString() + ". PUT abrirPuesto - params: user-rol:" + userRol + ", JSONPuesto: "+ puesto);
			return Response.ok("ERROR: " + e.getMessage()).build();
		}		
	}
	
	@PUT
	@Path("/cerrarPuesto")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response cerrarPuesto(@HeaderParam("user-rol") String userRol, JSONPuesto puesto){		
		try{
			if(userRol.equals(OPERADOR) || userRol.equals(OPERADORSR)){
				attBean.cerrarPuesto(puesto);
				return Response.ok("OK").build();
			}else{
				throw new UnauthorizedException("Permisos insuficientes: " + OPERADOR + "/" + OPERADORSR);
			}
		}catch(Exception e){
			logger.error(e.toString() + ". PUT cerrarPuesto - params: user-rol:" + userRol + ", JSONPuesto: "+ puesto);
			return Response.ok("ERROR: " + e.getLocalizedMessage()).build();
		}
		
	}
	
	@PUT
	@Path("/comenzarAtencion")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response comenzarAtencion(@HeaderParam("user-rol") String userRol, JSONPuesto puesto){
		try{
			if(userRol.equals(OPERADOR) || userRol.equals(OPERADORSR)){
				attBean.comenzarAtencion(puesto);
				return Response.ok("OK").build();
			}else{
				throw new UnauthorizedException("Permisos insuficientes: " + OPERADOR + "/" + OPERADORSR);
			}
		}catch(Exception e){
			logger.error(e.toString() + ". PUT comenzarAtencion - params: user-rol:" + userRol + ", JSONPuesto: "+ puesto);
			return Response.ok("ERROR: " + e.getMessage()).build();
		}	
	}
	
	@PUT
	@Path("/finalizarAtencion")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response finalizarAtencion(@HeaderParam("user-rol") String userRol, JSONFinalizarAtencion finalizarAtencion){		
		try{
			if(userRol.equals(OPERADOR) || userRol.equals(OPERADORSR)){
				attBean.finalizarAtencion(finalizarAtencion);
				return Response.ok("OK").build();
			}else{
				throw new UnauthorizedException("Permisos insuficientes: " + OPERADOR + "/" + OPERADORSR);
			}
		}catch(Exception e){
			logger.error(e.toString() + ". PUT finalizarAtencion - params: user-rol:" + userRol + ", JSONFinalizarAtencion: "+ finalizarAtencion);
			return Response.ok("ERROR: " + e.getMessage()).build();
		}	
	}
	
	@GET
	@Path("/llamarNumero")
	@Produces(MediaType.APPLICATION_JSON)
	public Response llamarNumero(@HeaderParam("user-rol") String userRol, @HeaderParam("hparam") String puesto){		
		try{
			if(userRol.equals(OPERADOR) || userRol.equals(OPERADORSR)){
				JSONNumero num = attBean.llamarNumero(puesto);
				if(num != null){
					return Response.ok(num).build();
				}else{
					throw new NotFoundException("No hay numero disponible en este momento");
				}
			}else{
				throw new UnauthorizedException("Permisos insuficientes: " + OPERADOR + "/" + OPERADORSR);
			}
		}catch(Exception e){
			logger.error(e.toString() + ". GET llamarNumero - params: user-rol:" + userRol + ", puesto: "+ puesto);
			return Response.ok("ERROR: " + e.getMessage()).build();
		}	
	}
	
	@GET
	@Path("/tramitesRecepcion")
	@Produces(MediaType.APPLICATION_JSON)
	public Response tramitesRecepcion(@HeaderParam("user-rol") String userRol, @HeaderParam("hparam") String codigoMaquina){	
		try{
			if(userRol.equals(RECEPCION)){
				List<JSONTramiteSector> tramitesRecepcion =  attBean.tramitesRecepcion(codigoMaquina);
				return Response.ok(tramitesRecepcion).build();
			}else{
				throw new UnauthorizedException("Permisos insuficientes: " + RECEPCION);
			}
		}catch(Exception e){
			logger.error(e.toString() + ". GET tramitesRecepcion - params: user-rol:" + userRol + ", codigoMaquina: "+ codigoMaquina);
			return Response.ok("ERROR: " + e.getMessage()).build();
		} 	
	}

	@PUT
	@Path("/atrasarNumero")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response atrasarNumero(@HeaderParam("user-rol") String userRol, JSONPuesto puesto){		
		try{
			if(userRol.equals(OPERADOR) || userRol.equals(OPERADORSR)){
				attBean.atrasarNumero(puesto);	
				return Response.ok("OK").build();
			}else{
				throw new UnauthorizedException("Permisos insuficientes: " + OPERADOR + "/" + OPERADORSR);
			}
		}catch(Exception e){
			//La excepcion puede ser por un error interno o por que no se reservo un numero con prioridad??
			logger.error(e.toString() + ". PUT atrasarNumero - params: user-rol:" + userRol + ", JSONPuesto: "+ puesto);
			return Response.ok("ERROR: " + e.getMessage()).build();
		}		
	}
	
	@PUT
	@Path("/pausarNumero")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response pausarNumero(@HeaderParam("user-rol") String userRol, JSONPuesto puesto){		
		try{
			if(userRol.equals(OPERADOR) || userRol.equals(OPERADORSR)){
				attBean.pausarNumero(puesto);
				return Response.ok("OK").build();
			}else{
				throw new UnauthorizedException("Permisos insuficientes: " + OPERADOR + "/" + OPERADORSR);
			}
		}catch(Exception e){
			logger.error(e.toString() + ". PUT pausarNumero - params: user-rol:" + userRol + ", JSONPuesto: "+ puesto);
			return Response.ok("ERROR: " + e.getMessage()).build();
		}
	}
	
	//CERRADO, DIPONIBLE, LLAMANDO, ATENDIENDO;
	
	@GET
	@Path("/fakeNumber")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarTramites(@HeaderParam("user-rol") String userRol, @HeaderParam("user") String user) {
		try{
			if(userRol.equals(OPERADOR)){		
				JSONNumero jnumero = new JSONNumero();
				jnumero.setId(50);
				jnumero.setExternalId("50");
				jnumero.setHora("04/05/1849-20:24");
				jnumero.setEstado("PAUSADO");
				jnumero.setPrioridad(1);
				jnumero.setIdTramite("4990");
				jnumero.setIdSector("MVD_FIS");
				return Response.ok(jnumero).build();
			}else{
				throw new UnauthorizedException("Permisos insuficientes: " + OPERADOR);
			}
		}
		catch(Exception e){
			logger.error(e.toString() + ". GET fakeNumber - params: user-rol:" + userRol);
			return Response.ok("ERROR: " + e.getMessage()).build();
		}
    }
	
	@GET
	@Path("/llamarPausado")
	@Produces(MediaType.APPLICATION_JSON)
	public Response LlamarNumeroPausado(@HeaderParam("user-rol") String userRol,  
			@HeaderParam("idNumero") Integer idNumero, 
			@HeaderParam("idPuesto") String idPuesto) {		
		try {
			if (userRol.equals(OPERADOR) || userRol.equals(OPERADORSR)) {
				JSONNumero num = attBean.llamarNumeroPausado(idNumero, idPuesto);
				return Response.ok(num).build();
			} else {
				throw new UnauthorizedException("Permisos insuficientes: " + OPERADOR + "/" + OPERADORSR);
			}
		} catch (Exception e) {
			logger.error(e.toString() + ". GET llamarPausado - params: user-rol:" + userRol + ", idNumero: "+ idNumero + ", idPuesto: "+ idPuesto);
			return Response.ok("ERROR: " + e.getMessage()).build();
		}		
	}
	
	@GET
	@Path("/llamarAtrasado")
	@Produces(MediaType.APPLICATION_JSON)
	public Response LlamarNumeroAtrasado(@HeaderParam("user-rol") String userRol,  
			@HeaderParam("idNumero") Integer idNumero, 
			@HeaderParam("idPuesto") String idPuesto) {	
		try {
			if (userRol.equals(OPERADOR) || userRol.equals(OPERADORSR)) {
				JSONNumero num = attBean.llamarNumeroAtrasado(idNumero, idPuesto);
				return Response.ok(num).build();
			} else {
				throw new UnauthorizedException("Permisos insuficientes: " + OPERADOR + "/" + OPERADORSR);
			}
		} catch (Exception e) {
			logger.error(e.toString() + ". GET llamarAtrasado - params: user-rol:" + userRol + ", idNumero: "+ idNumero + ", idPuesto: "+ idPuesto);
			return Response.ok("ERROR: " + e.getMessage()).build();
		}		
	}
	
	@GET
	@Path("/llamarNumeroDemanda")
	@Produces(MediaType.APPLICATION_JSON)
	public Response LlamarNumeroDemanda(@HeaderParam("user-rol") String userRol,  
			@HeaderParam("idNumero") Integer idNumero, 
			@HeaderParam("idPuesto") String idPuesto) {		
		try {
			if (userRol.equals(OPERADORSR)) {
				JSONNumero num = attBean.llamarNumeroDemanda(idNumero, idPuesto);
				return Response.ok(num).build();
			} else {
				throw new UnauthorizedException("Permisos insuficientes: " + OPERADORSR);
			}
		} catch (Exception e) {
			logger.error(e.toString() + ". GET llamarNumeroDemanda - params: user-rol:" + userRol + ", idNumero: "+ idNumero + ", idPuesto: "+ idPuesto);
			return Response.ok("ERROR: " + e.getMessage()).build();
		}		
	}
	
	@PUT
	@Path("/obtenerSectoresDesvio")
	@Produces(MediaType.APPLICATION_JSON)
	public Response obtenerSectoresDesvio(@HeaderParam("user-rol") String userRol,  
			@HeaderParam("idSector") String idSector) {		
		try {
			if (userRol.equals(OPERADORSR) || userRol.equals(OPERADOR)) {
				List<JSONSector> sectoresDesvio = attBean.obtenerSectoresDesvio(idSector);				
				return Response.ok(sectoresDesvio).build();
			} else {
				throw new UnauthorizedException("Permisos insuficientes: " + OPERADOR + "/" + OPERADORSR);
			}
		} catch (Exception e) {
			logger.error(e.toString() + ". PUT obtenerSectoresDesvio - params: user-rol:" + userRol + ", idSector: "+ idSector);
			return Response.ok("ERROR: " + e.getMessage()).build();
		}		
	}
	
	@PUT
	@Path("/desviarNumero")
	@Consumes(MediaType.APPLICATION_JSON) 
	public Response desviarNumero(@HeaderParam("user-rol") String userRol,  
			JSONFinalizarAtencion finalizarAtencion,
			@HeaderParam("idSectorDesvio") String idSectorDesvio) {			
		try {
			if (userRol.equals(OPERADORSR) || userRol.equals(OPERADOR)) {
				attBean.desviarNumero(idSectorDesvio, finalizarAtencion);				
				return Response.ok("OK").build();
			} else {
				throw new UnauthorizedException("Permisos insuficientes: " + OPERADOR + "/" + OPERADORSR);
			}	
		} catch (Exception e) {
			logger.error(e.toString() + ". PUT desviarNumero - params: user-rol:" + userRol + ", finalizarAtencion: "+ finalizarAtencion);
			return Response.ok("ERROR: " + e.getMessage()).build();
		}		
	}
	
	@PUT
	@Path("/reLlamarNumero")
	public Response reLlamarNumero(@HeaderParam("user-rol") String userRol,  
			@HeaderParam("idPuesto") String idPuesto) {				
		try {
			if (userRol.equals(OPERADORSR) || userRol.equals(OPERADOR)) {
				attBean.reLlamarNumero(idPuesto);
				return Response.ok("OK").build();
			} else {
				throw new UnauthorizedException("Permisos insuficientes: " + OPERADOR + "/" + OPERADORSR);
			}
		} catch (Exception e) {
			logger.error(e.toString() + ". PUT reLlamarNumero - params: user-rol:" + userRol + ", idPuesto: "+ idPuesto);
			return Response.ok("ERROR: " + e.getMessage()).build();
		}	
	}
}
