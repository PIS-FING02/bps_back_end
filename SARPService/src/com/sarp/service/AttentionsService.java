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
import org.jboss.resteasy.spi.InternalServerErrorException;
import org.jboss.resteasy.spi.NotFoundException;

import com.sarp.beans.AttentionsBean;
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
	public Response abrirPuesto(@HeaderParam("user-rol") String userRol, JSONPuesto puesto){
		if(userRol.equals(OPERADOR) || userRol.equals(OPERADORSR)){
			try{
				attBean.abrirPuesto(puesto);
				return Response.ok("OK").build();
			}catch(Exception e){
				logger.error("abrirPuesto - params: user-rol:" + userRol + ", JSONPuesto: "+ puesto);
				return Response.ok("ERROR: " + e.getMessage()).build();
			}
		}else{
			logger.error("Permisos insuficientes - " + OPERADOR + "/" + OPERADORSR + " - params: user-rol:" + userRol + ", JSONPuesto: " + puesto);
			return Response.ok("ERROR: No tiene permisos suficientes.").build();
		}
	}
	
	@PUT
	@Path("/cerrarPuesto")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response cerrarPuesto(@HeaderParam("user-rol") String userRol, JSONPuesto puesto){
		if(userRol.equals(OPERADOR) || userRol.equals(OPERADORSR)){
			try{
				attBean.cerrarPuesto(puesto);
				return Response.ok("OK").build();
			}catch(Exception e){
				logger.error("cerrarPuesto - params: user-rol:" + userRol + ", JSONPuesto: "+ puesto);
				return Response.ok("ERROR: " + e.getMessage()).build();
			}
		}else{
			logger.error("Permisos insuficientes - " + OPERADOR + "/" + OPERADORSR + " - params: user-rol:" + userRol + ", JSONPuesto: " + puesto);
			return Response.ok("ERROR: No tiene permisos suficientes.").build();
		}
	}
	
	@PUT
	@Path("/comenzarAtencion")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response comenzarAtencion(@HeaderParam("user-rol") String userRol, JSONPuesto puesto){
		if(userRol.equals(OPERADOR) || userRol.equals(OPERADORSR)){
			try{
				attBean.comenzarAtencion(puesto);
				return Response.ok("OK").build();
			}catch(Exception e){
				logger.error("comenzarAtencion - params: user-rol:" + userRol + ", JSONPuesto: "+ puesto);
				return Response.ok("ERROR: " + e.getMessage()).build();
			}
		}else{
			logger.error("Permisos insuficientes - " + OPERADOR + "/" + OPERADORSR + " - params: user-rol:" + userRol + ", JSONPuesto: " + puesto);
			return Response.ok("ERROR: No tiene permisos suficientes.").build();
		}
	}
	
	@PUT
	@Path("/finalizarAtencion")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response finalizarAtencion(@HeaderParam("user-rol") String userRol, JSONFinalizarAtencion finalizarAtencion){
		if(userRol.equals(OPERADOR) || userRol.equals(OPERADORSR)){
			try{
				attBean.finalizarAtencion(finalizarAtencion);
				return Response.ok("OK").build();
			}catch(Exception e){
				logger.error("finalizarAtencion - params: user-rol:" + userRol + ", JSONFinalizarAtencion: "+ finalizarAtencion);
				return Response.ok("ERROR: " + e.getMessage()).build();
			}
		}else{
			logger.error("Permisos insuficientes - " + OPERADOR + "/" + OPERADORSR + " - params: user-rol:" + userRol + ", JSONFinalizarAtencion: " + finalizarAtencion);
			return Response.ok("ERROR: No tiene permisos suficientes.").build();
		}
	}
	
	@GET
	@Path("/llamarNumero")
	@Produces(MediaType.APPLICATION_JSON)
	public Response llamarNumero(@HeaderParam("user-rol") String userRol, @HeaderParam("hparam") String puesto){
		if(userRol.equals(OPERADOR) || userRol.equals(OPERADORSR)){
			try{
				JSONNumero num = attBean.llamarNumero(puesto);
				if(num != null){
					return Response.ok(num).build();
				}else{
					throw new NotFoundException("No hay numero disponible en este momento");
				}
			}catch(Exception e){
				logger.error("llamarNumero - params: user-rol:" + userRol + ", puesto: "+ puesto);
				return Response.ok("ERROR: " + e.getMessage()).build();
			}
		}else{
			logger.error("Permisos insuficientes - " + OPERADOR + "/" + OPERADORSR + " - params: user-rol:" + userRol + ", puesto: " + puesto);
			return Response.ok("ERROR: No tiene permisos suficientes.").build();
		}
	}
	

	@GET
	@Path("/tramitesRecepcion")
	@Produces(MediaType.APPLICATION_JSON)
	public Response tramitesRecepcion(@HeaderParam("user-rol") String userRol, @HeaderParam("hparam") String codigoMaquina){
		if(userRol.equals(RECEPCION)){
			try{
				List<JSONTramiteSector> tramitesRecepcion =  attBean.tramitesRecepcion(codigoMaquina);
				return Response.ok(tramitesRecepcion).build();
				
			}catch(Exception e){
				logger.error("tramitesRecepcion - params: user-rol:" + userRol + ", codigoMaquina: "+ codigoMaquina);
				return Response.ok("ERROR: " + e.getMessage()).build();
			} 
		}else{
			logger.error("Permisos insuficientes - " + RECEPCION + " - params: user-rol:" + userRol + ", codigoMaquina: " + codigoMaquina);
			return Response.ok("ERROR: No tiene permisos suficientes.").build();
		}
	}

	@PUT
	@Path("/atrasarNumero")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response atrasarNumero(@HeaderParam("user-rol") String userRol, JSONPuesto puesto){
		if(userRol.equals(OPERADOR) || userRol.equals(OPERADORSR)){
			try{
				attBean.atrasarNumero(puesto);	
				return Response.ok("OK").build();
			}catch(Exception e){
				//La excepcion puede ser por un error interno o por que no se reservo un numero con prioridad??
				logger.error("atrasarNumero - params: user-rol:" + userRol + ", JSONPuesto: "+ puesto);
				return Response.ok("ERROR: " + e.getMessage()).build();
			}
		}else{
			logger.error("Permisos insuficientes - " + OPERADOR + "/" + OPERADORSR + " - params: user-rol:" + userRol + ", JSONPuesto: " + puesto);
			return Response.ok("ERROR: No tiene permisos suficientes.").build();
		}
	}
	
	@PUT
	@Path("/pausarNumero")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response pausarNumero(@HeaderParam("user-rol") String userRol, JSONPuesto puesto){
		if(userRol.equals(OPERADOR) || userRol.equals(OPERADORSR)){
			try{
				attBean.pausarNumero(puesto);
				return Response.ok("OK").build();
			}catch(Exception e){
				logger.error("pausarNumero - params: user-rol:" + userRol + ", JSONPuesto: "+ puesto);
				return Response.ok("ERROR: " + e.getMessage()).build();
			}
		}else{
			logger.error("Permisos insuficientes - " + OPERADOR + "/" + OPERADORSR + " - params: user-rol:" + userRol + ", JSONPuesto: " + puesto);
			return Response.ok("ERROR: No tiene permisos suficientes.").build();
		}
	}
	
	//CERRADO, DIPONIBLE, LLAMANDO, ATENDIENDO;
	
	@GET
	@Path("/fakeNumber")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarTramites(@HeaderParam("user-rol") String userRol, @HeaderParam("user") String user) {
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
			logger.error("Permisos insuficientes - " + OPERADOR + " - params: user-rol:" + userRol + ", user: " + user);
			return Response.ok("ERROR: No tiene permisos suficientes.").build();
		}
    }
	
	@GET
	@Path("/llamarPausado")
	@Produces(MediaType.APPLICATION_JSON)
	public Response LlamarNumeroPausado(@HeaderParam("user-rol") String userRol,  
			@HeaderParam("idNumero") Integer idNumero, 
			@HeaderParam("idPuesto") String idPuesto) {
		if (userRol.equals(OPERADOR) || userRol.equals(OPERADORSR)) {
			try {
				JSONNumero num = attBean.llamarNumeroPausado(idNumero, idPuesto);
				return Response.ok(num).build();
			} catch (Exception e) {
				logger.error("llamarPausado - params: user-rol:" + userRol + ", idNumero: "+ idNumero + ", idPuesto: "+ idPuesto);
				return Response.ok("ERROR: " + e.getMessage()).build();
			}
		} else {
			logger.error("Permisos insuficientes - " + OPERADOR + "/" + OPERADORSR + " - params: user-rol:" + userRol + ", idNumero: " + idNumero + ", idPuesto" + idPuesto);
			return Response.ok("ERROR: No tiene permisos suficientes.").build();
		}
	}
	
	@GET
	@Path("/llamarAtrasado")
	@Produces(MediaType.APPLICATION_JSON)
	public Response LlamarNumeroAtrasado(@HeaderParam("user-rol") String userRol,  
			@HeaderParam("idNumero") Integer idNumero, 
			@HeaderParam("idPuesto") String idPuesto) {
		if (userRol.equals(OPERADOR) || userRol.equals(OPERADORSR)) {
			try {
				JSONNumero num = attBean.llamarNumeroAtrasado(idNumero, idPuesto);
				return Response.ok(num).build();
			} catch (Exception e) {
				logger.error("llamarAtrasado - params: user-rol:" + userRol + ", idNumero: "+ idNumero + ", idPuesto: "+ idPuesto);
				return Response.ok("ERROR: " + e.getMessage()).build();
			}
		} else {
			logger.error("Permisos insuficientes - " + OPERADOR + "/" + OPERADORSR + " - params: user-rol:" + userRol + ", idNumero: " + idNumero + ", idPuesto" + idPuesto);
			return Response.ok("ERROR: No tiene permisos suficientes.").build();
		}
	}
	
	@GET
	@Path("/llamarNumeroDemanda")
	@Produces(MediaType.APPLICATION_JSON)
	public Response LlamarNumeroDemanda(@HeaderParam("user-rol") String userRol,  
			@HeaderParam("idNumero") Integer idNumero, 
			@HeaderParam("idPuesto") String idPuesto) {
		if (userRol.equals(OPERADORSR)) {
			try {
				JSONNumero num = attBean.llamarNumeroDemanda(idNumero, idPuesto);
				return Response.ok(num).build();
			} catch (Exception e) {
				logger.error("llamarNumeroDemanda - params: user-rol:" + userRol + ", idNumero: "+ idNumero + ", idPuesto: "+ idPuesto);
				return Response.ok("ERROR: " + e.getMessage()).build();
			}
		} else {
			logger.error("Permisos insuficientes - " + OPERADORSR + " - params: user-rol:" + userRol + ", idNumero: " + idNumero + ", idPuesto" + idPuesto);
			return Response.ok("ERROR: No tiene permisos suficientes.").build();
		}
	}
	
	@PUT
	@Path("/obtenerSectoresDesvio")
	@Produces(MediaType.APPLICATION_JSON)
	public Response obtenerSectoresDesvio(@HeaderParam("user-rol") String userRol,  
			@HeaderParam("idSector") String idSector) {
		if (userRol.equals(OPERADORSR) || userRol.equals(OPERADOR)) {
			try {
				List<JSONSector> sectoresDesvio = attBean.obtenerSectoresDesvio(idSector);
				
				return Response.ok(sectoresDesvio).build();
		
			} catch (Exception e) {
				logger.error("obtenerSectoresDesvio - params: user-rol:" + userRol + ", idSector: "+ idSector);
				return Response.ok("ERROR: " + e.getMessage()).build();
			}
		} else {
			logger.error("Permisos insuficientes - " + OPERADOR + "/" + OPERADORSR + " - params: user-rol:" + userRol + ", idSector: " + idSector);
			return Response.ok("ERROR: No tiene permisos suficientes.").build();
		}
	}
	
	@PUT
	@Path("/desviarNumero")
	@Consumes(MediaType.APPLICATION_JSON) 
	public  Response desviarNumero(@HeaderParam("user-rol") String userRol,  
			JSONFinalizarAtencion finalizarAtencion,
			@HeaderParam("idSectorDesvio") String idSectorDesvio) {
		
		if (userRol.equals(OPERADORSR) || userRol.equals(OPERADOR)) {
			try {
				attBean.desviarNumero(idSectorDesvio, finalizarAtencion);				
				return Response.ok("OK").build();
		
			} catch (Exception e) {
				logger.error("desviarNumero - params: user-rol:" + userRol + ", finalizarAtencion: "+ finalizarAtencion);
				return Response.ok("ERROR: " + e.getMessage()).build();
			}
		} else {
			logger.error("Permisos insuficientes - " + OPERADOR + "/" + OPERADORSR + " - params: user-rol:" + userRol + ", JSONFinalizarAtencion: " + finalizarAtencion + ", idSectorDesvio" + idSectorDesvio);
			return Response.ok("ERROR: No tiene permisos suficientes.").build();
		}
	}
	
	@PUT
	@Path("/reLlamarNumero")
	public Response reLlamarNumero(@HeaderParam("user-rol") String userRol,  
			@HeaderParam("idPuesto") String idPuesto) {
		
		if (userRol.equals(OPERADORSR) || userRol.equals(OPERADOR)) {
			try {

				attBean.reLlamarNumero(idPuesto);
				return Response.ok("OK").build();
			} catch (Exception e) {
				logger.error("reLlamarNumero - params: user-rol:" + userRol + ", idPuesto: "+ idPuesto);
				return Response.ok("ERROR: " + e.getMessage()).build();
			}
		} else {
			logger.error("Permisos insuficientes - " + OPERADOR + "/" + OPERADORSR + " - params: user-rol:" + userRol + ", idPuesto: " + idPuesto);
			return Response.ok("ERROR: No tiene permisos suficientes.").build();
		}
	}
}
