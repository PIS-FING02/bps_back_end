package com.sarp.service;

import java.util.List;

import com.sarp.utils.UtilService;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;
import org.jboss.resteasy.spi.UnauthorizedException;

import com.sarp.beans.AdminBean;
import com.sarp.json.modeler.JSONMetricasEstadoNumero;
import com.sarp.json.modeler.JSONMetricasNumero;
import com.sarp.json.modeler.JSONMetricasPuesto;

@RequestScoped
@Path("/metricsService")
public class MetricsService {

	@EJB
	private AdminBean adminBean = new AdminBean();
	
	private static Logger logger = Logger.getLogger(MetricsService.class);
	
	private String CONSULTOR = UtilService.getStringProperty("CONSULTOR");
	
	@GET
  	@Path("/listarMetricasPuesto")
	@Produces(MediaType.APPLICATION_JSON)
	/* Si nombreMaquina es null, devuelvo todas las metricas, sino se filtran las metricas */
	public Response listarMetricasPuestos(@HeaderParam("user-rol") String userRol, @QueryParam("nombreMaquina") String nombreMaquina) {
		try{
			if (userRol.equals(CONSULTOR)){
				List<JSONMetricasPuesto> listaMetricasPuestos = adminBean.listarMetricasPuestos(nombreMaquina);
				return Response.ok(listaMetricasPuestos).build();
			}
			else{
				throw new UnauthorizedException("Permisos insuficientes: " + CONSULTOR);
			}
		}catch(Exception e){
			logger.error(e.toString() + ". GET listarMetricasPuesto - params: user-rol:" + userRol + ", nombreMaquina: "+ nombreMaquina);
			return Response.ok("ERROR: " + e.getMessage()).build();
		}
	}
	
	@GET
  	@Path("/listarMetricasEstadoNumero")
	@Produces(MediaType.APPLICATION_JSON)
	/* Si internalId es null, devuelvo todas las metricas, sino se filtran las metricas */
	public Response listarMetricasEstadoNumero(@HeaderParam("user-rol") String userRol, @QueryParam("internalId") Integer internalId) {
		try{
			if (userRol.equals(CONSULTOR)){
				List<JSONMetricasEstadoNumero> listaMetricasEstadoNumero = adminBean.listarMetricasEstadoNumero(internalId);
				return Response.ok(listaMetricasEstadoNumero).build();
			}
			else{
				throw new UnauthorizedException("Permisos insuficientes: " + CONSULTOR);
			}
		}catch(Exception e){
			logger.error(e.toString() + ". GET listarMetricasEstadoNumero - params: user-rol:" + userRol + ", internalId: "+ internalId);
			return Response.ok("ERROR: " + e.getMessage()).build();
		}
	}

	@GET
	@Path("/listarMetricasDeNumero")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarMetricasDeNumero(@HeaderParam("user-rol") String userRol, @QueryParam("internalId") Integer internalId) {
		try{
			if (userRol.equals(CONSULTOR)){
				JSONMetricasNumero listaMetricasNumero = adminBean.listarMetricasDeNumero(internalId);
				return Response.ok(listaMetricasNumero).build();
			}
			else{
				throw new UnauthorizedException("Permisos insuficientes: " + CONSULTOR);
			}
		}catch(Exception e){
			logger.error(e.toString() + ". GET listarMetricasDeNumero - params: user-rol:" + userRol + ", internalId: "+ internalId);
			return Response.ok("ERROR: " + e.getMessage()).build();
		}
	}
	
	@GET
	@Path("/listarMetricasNumero")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarMetricasNumero(@HeaderParam("user-rol") String userRol) {
		try{
			if (userRol.equals(CONSULTOR)){
				List<JSONMetricasNumero> listaMetricasNumero = adminBean.listarMetricasNumero();
				return Response.ok(listaMetricasNumero).build();
			}
			else{
				throw new UnauthorizedException("Permisos insuficientes: " + CONSULTOR);
			}
		}catch(Exception e){
			logger.error(e.toString() + ". GET listarMetricasNumero - params: user-rol:" + userRol);
			return Response.ok("ERROR: " + e.getMessage()).build();
		}
	}
	
}
