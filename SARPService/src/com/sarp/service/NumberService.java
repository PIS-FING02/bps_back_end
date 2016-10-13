
package com.sarp.service;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.spi.BadRequestException;

import com.sarp.classes.BusinessNumero;
import com.sarp.controllers.AttentionsController;
import com.sarp.controllers.QueueController;
import com.sarp.dao.controllers.DAONumeroController;
import com.sarp.factory.Factory;
import com.sarp.json.modeler.JSONNumero;


@RequestScoped
@Path("/numberService")
public class NumberService {
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<BusinessNumero> listAllMembers(@HeaderParam("user-rol") String userRol) {
		DAONumeroController ctrl = new DAONumeroController();
		return ctrl.listarNumeros();
    }
	
	@POST
	@Path("/solicitarNumero")
	@Consumes(MediaType.APPLICATION_JSON)
	public String SolicitarNumero(@HeaderParam("user-rol") String userRol, JSONNumero num){
		//System.out.println("hola desde altaNumero");
		Factory fac = Factory.getInstance();
		AttentionsController ctrl = fac.getAttentionsController();
		ctrl.solicitarNumero(num);
		return "se recibio : "+num.toString();
	}
	
	@GET
	@Path("/listarNumeros")
    @Produces(MediaType.APPLICATION_JSON)
    public List<JSONNumero> listarNumeros(@HeaderParam("user-rol") String userRol, String idSector) {
		Factory fac = Factory.getInstance();
		QueueController ctrl = fac.getQueueController();
		if(userRol.equals("Administrador")){
			try{	
				return ctrl.obtenerTodosLosNumeros(idSector);
			}catch(Exception e){
				throw new BadRequestException("Error al listar todos los numeros");
			}
		}else{
			throw new BadRequestException("No tiene permisos para realizar esta accion.");
		}
    }


}