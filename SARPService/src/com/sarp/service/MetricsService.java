package com.sarp.service;

import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.jboss.resteasy.spi.InternalServerErrorException;
import com.sarp.controllers.AdminActionsController;
import com.sarp.factory.Factory;
import com.sarp.json.modeler.JSONMetricasEstadoNumero;
import com.sarp.json.modeler.JSONMetricasNumero;
import com.sarp.json.modeler.JSONMetricasPuesto;

@RequestScoped
@Path("/metricsService")
public class MetricsService {

	@GET
  	@Path("/listarMetricasPuestos")
	@Produces(MediaType.APPLICATION_JSON)
		/* Si nombreMaquina es null, devuelvo todas las metricas, sino se filtran las metricas */
		public List<JSONMetricasPuesto> listarMetricasPuestos(@QueryParam("nombreMaquina") String nombreMaquina) {
		Factory fac = Factory.getInstance();
		AdminActionsController ctrl = fac.getAdminActionsController();
		try{
			List<JSONMetricasPuesto> listaMetricasPuestos = ctrl.listarMetricasPuestos(nombreMaquina);
			return listaMetricasPuestos;			
		}catch(Exception e){
			throw new InternalServerErrorException("Error al listar Metricas de Puestos: " + e.getMessage());
		}
	}
	
	@GET
  	@Path("/listarMetricasEstadoNumero")
	@Produces(MediaType.APPLICATION_JSON)
	/* Si internalId es null, devuelvo todas las metricas, sino se filtran las metricas */
		public List<JSONMetricasEstadoNumero> listarMetricasEstadoNumero(@QueryParam("internalId") Integer internalId) {
		Factory fac = Factory.getInstance();
		AdminActionsController ctrl = fac.getAdminActionsController();
		try{
			List<JSONMetricasEstadoNumero> listaMetricasEstadoNumero = ctrl.listarMetricasEstadoNumero(internalId);
			return listaMetricasEstadoNumero;			
		}catch(Exception e){
			throw new InternalServerErrorException("Error al listar Metricas de Estado de Numero: " + e.getMessage());
		}
	}

	@GET
	@Path("/listarMetricasDeNumero")
	@Produces(MediaType.APPLICATION_JSON)
	public JSONMetricasNumero listarMetricasDeNumero(@QueryParam("internalId") Integer internalId) {
		Factory fac = Factory.getInstance();
		AdminActionsController ctrl = fac.getAdminActionsController();
		try{
			JSONMetricasNumero listaMetricasNumero = ctrl.listarMetricasDeNumero(internalId);
			return listaMetricasNumero;			
		}catch(Exception e){
			throw new InternalServerErrorException("Error al listar Metricas del Numero " + internalId + ": " + e.getMessage());
		}
	}
	
	@GET
	@Path("/listarMetricasNumero")
	@Produces(MediaType.APPLICATION_JSON)
	public List<JSONMetricasNumero> listarMetricasNumero() {
		Factory fac = Factory.getInstance();
		AdminActionsController ctrl = fac.getAdminActionsController();
		try{
			List<JSONMetricasNumero> listaMetricasNumero = ctrl.listarMetricasNumero();
			return listaMetricasNumero;			
		}catch(Exception e){
			throw new InternalServerErrorException("Error al listar Metricas  de Numero: " + e.getMessage());
		}
	}
	
}
