
package com.sarp.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import com.sarp.beans.AdminBean;
import com.sarp.beans.AttentionsBean;
import com.sarp.beans.GafuBean;
import com.sarp.beans.NumberBean;
import com.sarp.classes.BusinessSectorRol;
import com.sarp.json.modeler.JSONNumero;
import com.sarp.json.modeler.JSONSectorCantNum;

@RequestScoped
@Path("/numberService")
public class NumberService {

	private final String ResponsableSectorGAFU = "RESPSEC";

	private final String ConsultorGAFU = "CONSULTOR";
	
	@EJB
	private AdminBean adminBean = new AdminBean();
	
	@EJB
	private NumberBean numberBean = new NumberBean();
	
	@EJB
	private GafuBean gafuBean = new GafuBean();
	
	@EJB
	private AttentionsBean attBean = new AttentionsBean();
	
	private String RESPONSABLE_SECTOR = "RESPSEC";//UtilService.getStringProperty("RESPONSABLE_SECTOR");
	private String ADMINISTRADOR = "ADMIN";//UtilService.getStringProperty("ADMINISTRADOR");
	private String OPERADOR = "OPERADOR";//UtilService.getStringProperty("OPERADOR");
	private String OPERADORSR = "OPERADORSR";//UtilService.getStringProperty("OPERADOR_SENIOR");
	private String CONSULTOR = "CONSULTOR";//UtilService.getStringProperty("CONSULTOR");
	
	private static Logger logger = Logger.getLogger(NumberService.class);
	
	@POST
	@Path("/solicitarNumero")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response SolicitarNumero(JSONNumero num) {
		try {
			return Response.ok(attBean.solicitarNumero(num)).build();
		} catch (Exception e) {
			logger.error("solicitarNumero - params: num: "+ num);
			return Response.ok("ERROR: " + e.getMessage()).build();
		}
	}

	@GET
	@Path("/listarNumerosSector")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarNumerosSector(@HeaderParam("user-rol") String userRol,
			@QueryParam("idSector") String idSector) {
		if (userRol.equals(ADMINISTRADOR)) {
			try {
				return Response.ok(numberBean.listarNumerosSector(idSector)).build();
			} catch (Exception e) {
				logger.error("listarNumerosSector - params: user-rol:" + userRol + ", idSector: "+ idSector);
				return Response.ok("ERROR: " + e.getMessage()).build();
			}
		} else {
			logger.error("Permisos insuficientes - " + ADMINISTRADOR + " - params: user-rol:" + userRol + ", idSector: " + idSector);
			return Response.ok("ERROR: No tiene permisos suficientes.").build();
		}
	}
	
	@GET
	@Path("/listarNumerosPausados")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarNumerosPausados(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user,
			@QueryParam("idPuesto") String idPuesto, @QueryParam("idSector") String idSector) {
		try {
			if (userRol.equals(OPERADOR) || userRol.equals(OPERADORSR)) {
				return Response.ok(numberBean.listarNumerosPausados(idPuesto)).build();
			}else{
				List<JSONNumero> listaNumeros = new ArrayList<JSONNumero>();
				if(userRol.equals(RESPONSABLE_SECTOR)){ 
					List<BusinessSectorRol> permisos = gafuBean.obtenerSectorRolesUsuario(user, ResponsableSectorGAFU);
					for (BusinessSectorRol sd : permisos)
						if (sd.getSectorId().equals(idSector))
							listaNumeros.addAll(numberBean.listarNumerosPausadosSector(sd.getSectorId()));
				}else{
					if(userRol.equals(CONSULTOR)){ 	
						List<BusinessSectorRol> permisos = gafuBean.obtenerSectorRolesUsuario(user, ConsultorGAFU);
						for (BusinessSectorRol sd : permisos)
							if (sd.getSectorId().equals(idSector))
								listaNumeros.addAll(numberBean.listarNumerosPausadosSector(sd.getSectorId()));
					}else{
						logger.error("Permisos insuficientes - " + OPERADOR + "/" + OPERADORSR + "/" + RESPONSABLE_SECTOR + "/" + CONSULTOR + " - params: user-rol:" + userRol + ", idPuesto: " + idPuesto + ", idSector: " + idSector);
						return Response.ok("ERROR: No tiene permisos suficientes.").build();
					}
				}
				return Response.ok(listaNumeros).build();
			}
		} catch (Exception e) {
			logger.error("listarNumerosPausados - params: user-rol:" + userRol + ", idPuesto: " + idPuesto + ", idSector: "+ idSector);
			return Response.ok("ERROR: " + e.getMessage()).build();
		}
	}

	@GET
	@Path("/listarNumerosAtrasados")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarNumerosAtrasados(@HeaderParam("user-rol") String userRol, @HeaderParam("user") String user,
			@QueryParam("idPuesto") String idPuesto, @QueryParam("idSector") String idSector) {
		try {
			if (userRol.equals(OPERADOR) || userRol.equals(OPERADORSR)) {
					return Response.ok(numberBean.listarNumerosAtrasados(idPuesto)).build();
			}else{
				List<JSONNumero> listaNumeros = new ArrayList<JSONNumero>();
				if(userRol.equals(CONSULTOR) ){ 
					List<BusinessSectorRol> permisos = gafuBean.obtenerSectorRolesUsuario(user, ConsultorGAFU);
					for (BusinessSectorRol sd : permisos)
						if (sd.getSectorId().equals(idSector))
							listaNumeros.addAll(numberBean.listarNumerosAtrasadosSector(sd.getSectorId()));
				}else{
					if (userRol.equals(RESPONSABLE_SECTOR)){
						List<BusinessSectorRol> permisos = gafuBean.obtenerSectorRolesUsuario(user, ResponsableSectorGAFU);
						for (BusinessSectorRol sd : permisos)
							if (sd.getSectorId().equals(idSector))
								listaNumeros.addAll(numberBean.listarNumerosAtrasadosSector(sd.getSectorId()));
					}else{
						logger.error("Permisos insuficientes - " + OPERADOR + "/" + OPERADORSR + "/" + RESPONSABLE_SECTOR + "/" + CONSULTOR + " - params: user-rol:" + userRol + ", idPuesto: " + idPuesto + ", idSector: " + idSector);
						return Response.ok("ERROR: No tiene permisos suficientes.").build();
					}
				}
				return Response.ok(listaNumeros).build();
			}
		} catch (Exception e) {
			logger.error("listarNumerosAtrasados - params: user-rol:" + userRol + ", idPuesto: " + idPuesto + ", idSector: "+ idSector);
			return Response.ok("ERROR: " + e.getMessage()).build();
		}
	}
	
	@GET
	@Path("/listarNumerosEnEspera")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarNumerosEnEspera(@HeaderParam("user-rol") String userRol, @HeaderParam("user") String user,
			@QueryParam("idPuesto") String idPuesto, @QueryParam("idSector") String idSector) {
		if (userRol.equals(OPERADOR) || userRol.equals(OPERADORSR)) {
			try {
				return Response.ok(numberBean.listarNumerosEnEspera(idPuesto)).build();
			} catch (Exception e) {
				return Response.ok("ERROR: " + e.getMessage()).build();
			}
		}else if(userRol.equals(RESPONSABLE_SECTOR)){ 
			try {
				List<JSONNumero> listaNumeros = new ArrayList<JSONNumero>();
				List<BusinessSectorRol> permisos = gafuBean.obtenerSectorRolesUsuario(user, ResponsableSectorGAFU);
				for (BusinessSectorRol sd : permisos)
					if (sd.getSectorId().equals(idSector))
						listaNumeros.addAll( numberBean.listarNumerosEnEsperaSector(sd.getSectorId()));
				return Response.ok(listaNumeros).build();
			} catch (Exception e) {
				logger.error("listarNumerosEnEspera - params: user-rol:" + userRol + ", idPuesto: " + idPuesto + ", idSector: "+ idSector);
				return Response.ok("ERROR: " + e.getMessage()).build();
			}
		} else {
			logger.error("Permisos insuficientes - " + OPERADOR + "/" + OPERADORSR + "/" + RESPONSABLE_SECTOR + " - params: user-rol:" + userRol + ", idPuesto: " + idPuesto + ", idSector: " + idSector);
			return Response.ok("ERROR: No tiene permisos suficientes.").build();
		}
	}
	
	@GET
	@Path("/obtenerCantNumerosEnEspera")
	@Produces(MediaType.APPLICATION_JSON)
	public Response obtenerCantNumerosEnEspera(@HeaderParam("user-rol") String userRol, @HeaderParam("user") String user,
			@QueryParam("idPuesto") String idPuesto, @QueryParam("idSector") String idSector) {
		if (userRol.equals(OPERADOR) || userRol.equals(OPERADORSR)) {
			try {
				return Response.ok(numberBean.obtenerCantNumerosEnEspera(idPuesto)).build();
			} catch (Exception e) {
				logger.error("obtenerCantNumerosEnEspera - params: user-rol:" + userRol + ", idPuesto: " + idPuesto + ", idSector: "+ idSector);
				return Response.ok("ERROR: " + e.getMessage()).build();
			}
		}/*else if(userRol.equals(RESPONSABLE_SECTOR)){
			try {
				List<JSONNumero> listaNumeros = new ArrayList<JSONNumero>();
				List<BusinessSectorRol> permisos = gafuBean.obtenerSectorRolesUsuario(user, ResponsableSectorGAFU);
				for (BusinessSectorRol sd : permisos)
					if (sd.getSectorId().equals(idSector))
						listaNumeros.addAll( numberBean.listarNumerosEnEsperaSector(sd.getSectorId()));
				return listaNumeros;
			} catch (Exception e) {
				throw new InternalServerErrorException(e.getMessage());
			}
			throw new InternalServerErrorException("esto esta en standby, xq primero se pidio para op y opSR");
		}*/ else {
			logger.error("Permisos insuficientes - " + OPERADOR + "/" + OPERADORSR + " - params: user-rol:" + userRol + ", idPuesto: " + idPuesto + ", idSector: " + idSector);
			return Response.ok("ERROR: No tiene permisos suficientes.").build();
		}
	}
	
}