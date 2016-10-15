
package com.sarp.service;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.spi.BadRequestException;
import org.jboss.resteasy.spi.InternalServerErrorException;
import org.jboss.resteasy.spi.UnauthorizedException;

import com.sarp.classes.BusinessNumero;
import com.sarp.controllers.AttentionsController;
import com.sarp.controllers.QueueController;
import com.sarp.dao.controllers.DAONumeroController;
import com.sarp.factory.Factory;
import com.sarp.json.modeler.JSONNumero;


@RequestScoped
@Path("/numberService")
public class NumberService {
	
	@POST
	@Path("/solicitarNumero")
	@Consumes(MediaType.APPLICATION_JSON)
	public String SolicitarNumero(@HeaderParam("user-rol") String userRol, JSONNumero num){
		Factory fac = Factory.getInstance();
		AttentionsController ctrl = fac.getAttentionsController();
		ctrl.solicitarNumero(num);
		return "El numero : "+num.getExternalId()+" se dio de alta exitosamente";
	}
	
	@GET
	@Path("/listarNumeros?idSector")
    @Produces(MediaType.APPLICATION_JSON)
    public List<JSONNumero> listarNumeros(@HeaderParam("user-rol") String userRol, @QueryParam("idSector") String idSector) {
		if(userRol.equals("Administrador")){
			try{
				Factory fac = Factory.getInstance();
				QueueController ctrl = fac.getQueueController();
				return ctrl.obtenerTodosLosNumeros(idSector);
			}catch(Exception e){
				throw new InternalServerErrorException("Error al listar todos los numeros");
			}
		}else{
			throw new UnauthorizedException("No tiene permisos para realizar esta accion.");
		}
    }


}