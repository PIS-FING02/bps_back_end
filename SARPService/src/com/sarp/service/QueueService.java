package com.sarp.service;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.jboss.resteasy.spi.BadRequestException;
import com.sarp.controllers.QueueController;
import com.sarp.factory.Factory;
import com.sarp.json.modeler.JSONNumero;
import com.sarp.json.modeler.JSONSector;

@RequestScoped
@Path("/queueService")
public class QueueService {

	@POST
	@Path("/listarNumeros")
    @Consumes(MediaType.APPLICATION_JSON)
    public List<JSONNumero> listarNumeros(@HeaderParam("user-rol") String userRol, JSONSector sector) {
		Factory fac = Factory.getInstance();
		QueueController ctrl = fac.getQueueController();
		if(userRol.equals("Administrador")){
			try{	
				return ctrl.obtenerTodosLosNumeros(sector.getCodigo());
			}catch(Exception e){
				throw new BadRequestException("Error al listar todos los numeros");
			}
		}else{
			throw new BadRequestException("No tiene permisos para realizar esta accion.");
		}
    }
}
