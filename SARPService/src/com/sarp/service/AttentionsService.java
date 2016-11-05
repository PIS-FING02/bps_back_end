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

import org.jboss.logging.Logger;
import org.jboss.resteasy.spi.InternalServerErrorException;
import org.jboss.resteasy.spi.NotFoundException;
import org.jboss.resteasy.spi.UnauthorizedException;

import com.sarp.beans.AttentionsBean;
import com.sarp.json.modeler.JSONFinalizarAtencion;
import com.sarp.json.modeler.JSONNumero;
import com.sarp.json.modeler.JSONPuesto;
import com.sarp.json.modeler.JSONSector;
import com.sarp.json.modeler.JSONTramiteSector;
import com.sarp.utils.UtilService;

@RequestScoped
@Path("/attentionsService")
public class AttentionsService {
		
	@EJB
	private AttentionsBean attBean = new AttentionsBean();
	
	private String RECEPCION = /*"RECEPCION";*/UtilService.getStringProperty("RECEPCION");
	private String OPERADOR = /*"OPERADOR";*/UtilService.getStringProperty("OPERADOR");
	private String OPERADORSR = /*"OPERADORSR";*/UtilService.getStringProperty("OPERADOR_SENIOR");

	private static Logger logger = Logger.getLogger(AttentionsService.class);
	
	@PUT
	@Path("/abrirPuesto")
	@Consumes(MediaType.APPLICATION_JSON)
	public String abrirPuesto(@HeaderParam("user-rol") String userRol, JSONPuesto puesto){
		if(userRol.equals(OPERADOR) || userRol.equals(OPERADORSR)){
			try{
				attBean.abrirPuesto(puesto);
				return "OK";
			}catch(Exception e){
				logger.error("abrirPuesto - params: user-rol:" + userRol + ", JSONPuesto: "+ puesto);
				throw new InternalServerErrorException(e);
			}
		}else{
			logger.error("Permisos insuficientes - " + OPERADOR + "/" + OPERADORSR + " - params: user-rol:" + userRol + ", JSONPuesto: " + puesto);
			throw new UnauthorizedException("No tiene permisos suficientes.");
		}
	}
	
	@PUT
	@Path("/cerrarPuesto")
	@Consumes(MediaType.APPLICATION_JSON)
	public String cerrarPuesto(@HeaderParam("user-rol") String userRol, JSONPuesto puesto){
		if(userRol.equals(OPERADOR) || userRol.equals(OPERADORSR)){
			try{
				attBean.cerrarPuesto(puesto);
				return "OK";
			}catch(Exception e){
				logger.error("cerrarPuesto - params: user-rol:" + userRol + ", JSONPuesto: "+ puesto);
				throw new InternalServerErrorException(e);
			}
		}else{
			logger.error("Permisos insuficientes - " + OPERADOR + "/" + OPERADORSR + " - params: user-rol:" + userRol + ", JSONPuesto: " + puesto);
			throw new UnauthorizedException("No tiene permisos suficientes.");
		}
	}
	
	@PUT
	@Path("/comenzarAtencion")
	@Consumes(MediaType.APPLICATION_JSON)
	public String comenzarAtencion(@HeaderParam("user-rol") String userRol, JSONPuesto puesto){
		if(userRol.equals(OPERADOR) || userRol.equals(OPERADORSR)){
			try{
				attBean.comenzarAtencion(puesto);
				return "OK";
			}catch(Exception e){
				logger.error("comenzarAtencion - params: user-rol:" + userRol + ", JSONPuesto: "+ puesto);
				throw new InternalServerErrorException(e);
			}
		}else{
			logger.error("Permisos insuficientes - " + OPERADOR + "/" + OPERADORSR + " - params: user-rol:" + userRol + ", JSONPuesto: " + puesto);
			throw new UnauthorizedException("No tiene permisos suficientes.");
		}
	}
	
	@PUT
	@Path("/finalizarAtencion")
	@Consumes(MediaType.APPLICATION_JSON)
	public String finalizarAtencion(@HeaderParam("user-rol") String userRol, JSONFinalizarAtencion finalizarAtencion){
		if(userRol.equals(OPERADOR) || userRol.equals(OPERADORSR)){
			try{
				attBean.finalizarAtencion(finalizarAtencion);
				return "OK";
			}catch(Exception e){
				logger.error("finalizarAtencion - params: user-rol:" + userRol + ", JSONFinalizarAtencion: "+ finalizarAtencion);
				throw new InternalServerErrorException(e);
			}
		}else{
			logger.error("Permisos insuficientes - " + OPERADOR + "/" + OPERADORSR + " - params: user-rol:" + userRol + ", JSONFinalizarAtencion: " + finalizarAtencion);
			throw new UnauthorizedException("No tiene permisos suficientes.");
		}
	}
	
	@GET
	@Path("/llamarNumero")
	@Produces(MediaType.APPLICATION_JSON)
	public JSONNumero llamarNumero(@HeaderParam("user-rol") String userRol, @HeaderParam("hparam") String puesto){
		if(userRol.equals(OPERADOR) || userRol.equals(OPERADORSR)){
			try{
				JSONNumero num = attBean.llamarNumero(puesto);
				if(num != null){
					return num;
				}else{
					throw new NotFoundException("No hay numero disponible en este momento");
				}
			}catch(Exception e){
				logger.error("llamarNumero - params: user-rol:" + userRol + ", puesto: "+ puesto);
				throw new InternalServerErrorException(e);
			}
		}else{
			logger.error("Permisos insuficientes - " + OPERADOR + "/" + OPERADORSR + " - params: user-rol:" + userRol + ", puesto: " + puesto);
			throw new UnauthorizedException("No tiene permisos suficientes.");
		}
	}
	

	@GET
	@Path("/tramitesRecepcion")
	@Produces(MediaType.APPLICATION_JSON)
	public List<JSONTramiteSector> tramitesRecepcion(@HeaderParam("user-rol") String userRol, @HeaderParam("hparam") String codigoMaquina){
		if(userRol.equals(RECEPCION)){
			try{
				List<JSONTramiteSector> tramitesRecepcion =  attBean.tramitesRecepcion(codigoMaquina);
				return tramitesRecepcion;
				
			}catch(Exception e){
				logger.error("tramitesRecepcion - params: user-rol:" + userRol + ", codigoMaquina: "+ codigoMaquina);
				throw new InternalServerErrorException(e);
			} 
		}else{
			logger.error("Permisos insuficientes - " + RECEPCION + " - params: user-rol:" + userRol + ", codigoMaquina: " + codigoMaquina);
			throw new UnauthorizedException("No tiene permisos suficientes.");
		}
	}

	@PUT
	@Path("/atrasarNumero")
	@Consumes(MediaType.APPLICATION_JSON)
	public String atrasarNumero(@HeaderParam("user-rol") String userRol, JSONPuesto puesto){
		if(userRol.equals(OPERADOR) || userRol.equals(OPERADORSR)){
			try{
				attBean.atrasarNumero(puesto);	
				return "OK";
			}catch(Exception e){
				//La excepcion puede ser por un error interno o por que no se reservo un numero con prioridad??
				logger.error("atrasarNumero - params: user-rol:" + userRol + ", JSONPuesto: "+ puesto);
				throw new InternalServerErrorException(e);
			}
		}else{
			logger.error("Permisos insuficientes - " + OPERADOR + "/" + OPERADORSR + " - params: user-rol:" + userRol + ", JSONPuesto: " + puesto);
			throw new UnauthorizedException("No tiene permisos suficientes.");
		}
	}
	
	@PUT
	@Path("/pausarNumero")
	@Consumes(MediaType.APPLICATION_JSON)
	public String pausarNumero(@HeaderParam("user-rol") String userRol, JSONPuesto puesto){
		if(userRol.equals(OPERADOR) || userRol.equals(OPERADORSR)){
			try{
				attBean.pausarNumero(puesto);
				return "OK";
			}catch(Exception e){
				logger.error("pausarNumero - params: user-rol:" + userRol + ", JSONPuesto: "+ puesto);
				throw new InternalServerErrorException(e);
			}
		}else{
			logger.error("Permisos insuficientes - " + OPERADOR + "/" + OPERADORSR + " - params: user-rol:" + userRol + ", JSONPuesto: " + puesto);
			throw new UnauthorizedException("No tiene permisos suficientes.");
		}
	}
	
	//CERRADO, DIPONIBLE, LLAMANDO, ATENDIENDO;
	
	@GET
	@Path("/fakeNumber")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONNumero listarTramites(@HeaderParam("user-rol") String userRol, @HeaderParam("user") String user) {
		if(userRol.equals(OPERADOR)){
				JSONNumero jnumero = new JSONNumero();
				jnumero.setId(50);
				jnumero.setExternalId("50");
				jnumero.setHora("04/05/1849-20:24");
				jnumero.setEstado("PAUSADO");
				jnumero.setPrioridad(1);
				jnumero.setIdTramite("4990");
				jnumero.setIdSector("MVD_FIS");
				return jnumero;
		}else{
			logger.error("Permisos insuficientes - " + OPERADOR + " - params: user-rol:" + userRol + ", user: " + user);
			throw new UnauthorizedException("No tiene permisos suficientes.");
		}
    }
	
	@GET
	@Path("/llamarPausado")
	@Produces(MediaType.APPLICATION_JSON)
	public JSONNumero LlamarNumeroPausado(@HeaderParam("user-rol") String userRol,  
			@HeaderParam("idNumero") Integer idNumero, 
			@HeaderParam("idPuesto") String idPuesto) {
		if (userRol.equals(OPERADOR) || userRol.equals(OPERADORSR)) {
			try {
				JSONNumero num = attBean.llamarNumeroPausado(idNumero, idPuesto);
				return num;
			} catch (Exception e) {
				logger.error("llamarPausado - params: user-rol:" + userRol + ", idNumero: "+ idNumero + ", idPuesto: "+ idPuesto);
				throw new InternalServerErrorException(e);
			}
		} else {
			logger.error("Permisos insuficientes - " + OPERADOR + "/" + OPERADORSR + " - params: user-rol:" + userRol + ", idNumero: " + idNumero + ", idPuesto" + idPuesto);
			throw new UnauthorizedException("No tiene permisos para realizar esta accion.");
		}
	}
	
	@GET
	@Path("/llamarAtrasado")
	@Produces(MediaType.APPLICATION_JSON)
	public JSONNumero LlamarNumeroAtrasado(@HeaderParam("user-rol") String userRol,  
			@HeaderParam("idNumero") Integer idNumero, 
			@HeaderParam("idPuesto") String idPuesto) {
		if (userRol.equals(OPERADOR) || userRol.equals(OPERADORSR)) {
			try {
				JSONNumero num = attBean.llamarNumeroAtrasado(idNumero, idPuesto);
				return num;
			} catch (Exception e) {
				logger.error("llamarAtrasado - params: user-rol:" + userRol + ", idNumero: "+ idNumero + ", idPuesto: "+ idPuesto);
				throw new InternalServerErrorException(e);
			}
		} else {
			logger.error("Permisos insuficientes - " + OPERADOR + "/" + OPERADORSR + " - params: user-rol:" + userRol + ", idNumero: " + idNumero + ", idPuesto" + idPuesto);
			throw new UnauthorizedException("No tiene permisos para realizar esta accion.");
		}
	}
	
	@GET
	@Path("/llamarNumeroDemanda")
	@Produces(MediaType.APPLICATION_JSON)
	public JSONNumero LlamarNumeroDemanda(@HeaderParam("user-rol") String userRol,  
			@HeaderParam("idNumero") Integer idNumero, 
			@HeaderParam("idPuesto") String idPuesto) {
		if (userRol.equals(OPERADORSR)) {
			try {
				JSONNumero num = attBean.llamarNumeroDemanda(idNumero, idPuesto);
				return num;
			} catch (Exception e) {
				logger.error("llamarNumeroDemanda - params: user-rol:" + userRol + ", idNumero: "+ idNumero + ", idPuesto: "+ idPuesto);
				throw new InternalServerErrorException(e);
			}
		} else {
			logger.error("Permisos insuficientes - " + OPERADORSR + " - params: user-rol:" + userRol + ", idNumero: " + idNumero + ", idPuesto" + idPuesto);
			throw new UnauthorizedException("No tiene permisos para realizar esta accion.");
		}
	}
	
	@PUT
	@Path("/obtenerSectoresDesvio")
	@Produces(MediaType.APPLICATION_JSON)
	public  List<JSONSector> obtenerSectoresDesvio(@HeaderParam("user-rol") String userRol,  
			@HeaderParam("idSector") String idSector) {
		if (userRol.equals(OPERADORSR) || userRol.equals(OPERADOR)) {
			try {
				List<JSONSector> sectoresDesvio = attBean.obtenerSectoresDesvio(idSector);
				
				return sectoresDesvio;
		
			} catch (Exception e) {
				logger.error("obtenerSectoresDesvio - params: user-rol:" + userRol + ", idSector: "+ idSector);
				throw new InternalServerErrorException(e);
			}
		} else {
			logger.error("Permisos insuficientes - " + OPERADOR + "/" + OPERADORSR + " - params: user-rol:" + userRol + ", idSector: " + idSector);
			throw new UnauthorizedException("No tiene permisos para realizar esta accion.");
		}
	}
	
	@PUT
	@Path("/desviarNumero")
	@Consumes(MediaType.APPLICATION_JSON) 
	public  String desviarNumero(@HeaderParam("user-rol") String userRol,  
			JSONFinalizarAtencion finalizarAtencion,
			@HeaderParam("idSectorDesvio") String idSectorDesvio) {
		
		if (userRol.equals(OPERADORSR) || userRol.equals(OPERADOR)) {
			try {
				attBean.desviarNumero(idSectorDesvio, finalizarAtencion);
				
				return "El numero fue desviado con exito";
		
			} catch (Exception e) {
				logger.error("desviarNumero - params: user-rol:" + userRol + ", finalizarAtencion: "+ finalizarAtencion);
				throw new InternalServerErrorException(e);
			}
		} else {
			logger.error("Permisos insuficientes - " + OPERADOR + "/" + OPERADORSR + " - params: user-rol:" + userRol + ", JSONFinalizarAtencion: " + finalizarAtencion + ", idSectorDesvio" + idSectorDesvio);
			throw new UnauthorizedException("No tiene permisos para realizar esta accion.");
		}
	}
	
	@PUT
	@Path("/reLlamarNumero")
	public String reLlamarNumero(@HeaderParam("user-rol") String userRol,  
			@HeaderParam("idPuesto") String idPuesto) {
		
		if (userRol.equals(OPERADORSR) || userRol.equals(OPERADOR)) {
			try {

				attBean.reLlamarNumero(idPuesto);
				return "OK";
			} catch (Exception e) {
				logger.error("reLlamarNumero - params: user-rol:" + userRol + ", idPuesto: "+ idPuesto);
				throw new InternalServerErrorException(e);
			}
		} else {
			logger.error("Permisos insuficientes - " + OPERADOR + "/" + OPERADORSR + " - params: user-rol:" + userRol + ", idPuesto: " + idPuesto);
			throw new UnauthorizedException("No tiene permisos para realizar esta accion.");
		}
	}
}
