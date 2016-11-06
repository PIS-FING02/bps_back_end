package com.sarp.service;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;
import org.jboss.resteasy.spi.InternalServerErrorException;

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
	
	@GET
  	@Path("/listarMetricasPuesto")
	@Produces(MediaType.APPLICATION_JSON)
		/* Si nombreMaquina es null, devuelvo todas las metricas, sino se filtran las metricas */
		public Response listarMetricasPuestos(@QueryParam("nombreMaquina") String nombreMaquina) {
		try{
			List<JSONMetricasPuesto> listaMetricasPuestos = adminBean.listarMetricasPuestos(nombreMaquina);
			return Response.ok(listaMetricasPuestos).build();
		}catch(Exception e){
			logger.error("listarMetricasPuesto - params: nombreMaquina:" + nombreMaquina);
			return Response.ok("ERROR: " + e.getMessage()).build();
		}
	}
	
	@GET
  	@Path("/listarMetricasEstadoNumero")
	@Produces(MediaType.APPLICATION_JSON)
	/* Si internalId es null, devuelvo todas las metricas, sino se filtran las metricas */
	public Response listarMetricasEstadoNumero(@QueryParam("internalId") Integer internalId) {
		try{
			List<JSONMetricasEstadoNumero> listaMetricasEstadoNumero = adminBean.listarMetricasEstadoNumero(internalId);
			return Response.ok(listaMetricasEstadoNumero).build();
		}catch(Exception e){
			logger.error("listarMetricasEstadoNumero - params: internalId:" + internalId);
			return Response.ok("ERROR: " + e.getMessage()).build();
		}
	}

	@GET
	@Path("/listarMetricasDeNumero")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarMetricasDeNumero(@QueryParam("internalId") Integer internalId) {
		try{
			JSONMetricasNumero listaMetricasNumero = adminBean.listarMetricasDeNumero(internalId);
			return Response.ok(listaMetricasNumero).build();
		}catch(Exception e){
			logger.error("listarMetricasDeNumero - params: internalId:" + internalId);
			return Response.ok("ERROR: " + e.getMessage()).build();
		}
	}
	
	@GET
	@Path("/listarMetricasNumero")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarMetricasNumero() {
		try{
			List<JSONMetricasNumero> listaMetricasNumero = adminBean.listarMetricasNumero();
			return Response.ok(listaMetricasNumero).build();
		}catch(Exception e){
			logger.error("listarMetricasNumero - params: ");
			return Response.ok("ERROR: " + e.getMessage()).build();
		}
	}
	
}
