
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
import org.jboss.resteasy.spi.UnauthorizedException;

import com.sarp.beans.AdminBean;
import com.sarp.beans.AttentionsBean;
import com.sarp.beans.GafuBean;
import com.sarp.beans.NumberBean;
import com.sarp.classes.BusinessSectorRol;
import com.sarp.json.modeler.JSONNumero;

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
	
	private String RESPONSABLE_SECTOR = UtilService.getStringProperty("RESPONSABLE_SECTOR");
	private String ADMINISTRADOR = UtilService.getStringProperty("ADMINISTRADOR");
	private String OPERADOR = UtilService.getStringProperty("OPERADOR");
	private String OPERADORSR = UtilService.getStringProperty("OPERADOR_SENIOR");
	private String CONSULTOR = UtilService.getStringProperty("CONSULTOR");
	
	private static Logger logger = Logger.getLogger(NumberService.class);
	
	@POST
	@Path("/solicitarNumero")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response SolicitarNumero(JSONNumero num) {
		try {
			return Response.ok(attBean.solicitarNumero(num)).build();
		} catch (Exception e) {
			logger.error(e.toString() + ". POST solicitarNumero - params: num: "+ num);
			return Response.ok("ERROR: " + e.getMessage()).build();
		}
	}

	@GET
	@Path("/listarNumerosSector")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarNumerosSector(@HeaderParam("user-rol") String userRol,
			@QueryParam("idSector") String idSector) {	
		try {
			if (userRol.equals(ADMINISTRADOR)) {
				return Response.ok(numberBean.listarNumerosSector(idSector)).build();
			} else {
				throw new UnauthorizedException("Permisos insuficientes: " + ADMINISTRADOR);
			}
		} catch (Exception e) {
			logger.error(e.toString() + ". GET listarNumerosSector - params: user-rol:" + userRol + ", idSector: "+ idSector);
			return Response.ok("ERROR: " + e.getMessage()).build();
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
						throw new UnauthorizedException("Permisos insuficientes: " + OPERADOR + "/" + OPERADORSR + "/" + RESPONSABLE_SECTOR + "/" + CONSULTOR);
					}
				}
				return Response.ok(listaNumeros).build();
			}
		} catch (Exception e) {
			logger.error(e.toString() + ". GET listarNumerosPausados - params: user-rol:" + userRol + ", idPuesto: " + idPuesto + ", idSector: "+ idSector);
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
						throw new UnauthorizedException("Permisos insuficientes: " + OPERADOR + "/" + OPERADORSR + "/" + RESPONSABLE_SECTOR + "/" + CONSULTOR);
					}
				}
				return Response.ok(listaNumeros).build();
			}
		} catch (Exception e) {
			logger.error(e.toString() + ". GET listarNumerosAtrasados - params: user-rol:" + userRol + ", idPuesto: " + idPuesto + ", idSector: "+ idSector);
			return Response.ok("ERROR: " + e.getMessage()).build();
		}
	}
	
	@GET
	@Path("/listarNumerosEnEspera")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarNumerosEnEspera(@HeaderParam("user-rol") String userRol, @HeaderParam("user") String user,
			@QueryParam("idPuesto") String idPuesto, @QueryParam("idSector") String idSector) {		
		try {
			if (userRol.equals(OPERADOR) || userRol.equals(OPERADORSR)) {
				return Response.ok(numberBean.listarNumerosEnEspera(idPuesto)).build();
			}else if(userRol.equals(RESPONSABLE_SECTOR)){ 
				List<JSONNumero> listaNumeros = new ArrayList<JSONNumero>();
				List<BusinessSectorRol> permisos = gafuBean.obtenerSectorRolesUsuario(user, ResponsableSectorGAFU);
				for (BusinessSectorRol sd : permisos)
					if (sd.getSectorId().equals(idSector))
						listaNumeros.addAll( numberBean.listarNumerosEnEsperaSector(sd.getSectorId()));
				return Response.ok(listaNumeros).build();
			} else {
				throw new UnauthorizedException("Permisos insuficientes: " + OPERADORSR + "/" + RESPONSABLE_SECTOR);
			}
		} catch (Exception e) {
			logger.error(e.toString() + ". GET listarNumerosEnEspera - params: user-rol:" + userRol + ", idPuesto: " + idPuesto + ", idSector: "+ idSector);
			return Response.ok("ERROR: " + e.getMessage()).build();
		}		
	}
	
	@GET
	@Path("/obtenerCantNumerosEnEspera")
	@Produces(MediaType.APPLICATION_JSON)
	public Response obtenerCantNumerosEnEspera(@HeaderParam("user-rol") String userRol, @HeaderParam("user") String user,
			@QueryParam("idPuesto") String idPuesto, @QueryParam("idSector") String idSector) {		
			try {
				if (userRol.equals(OPERADOR) || userRol.equals(OPERADORSR)) {
					return Response.ok(numberBean.obtenerCantNumerosEnEspera(idPuesto)).build();
				}
				else {
					throw new UnauthorizedException("Permisos insuficientes: " + OPERADOR + "/" + OPERADORSR);
				}
			} catch (Exception e) {
				logger.error(e.toString() + ". GET obtenerCantNumerosEnEspera - params: user-rol:" + userRol + ", idPuesto: " + idPuesto + ", idSector: "+ idSector);
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
		}*/ 	
}