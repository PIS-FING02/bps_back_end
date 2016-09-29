
package com.sarp.service;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sarp.classes.BusinessNumero;
import com.sarp.controllers.AttentionsController;
import com.sarp.dao.controllers.DAONumeroController;
import com.sarp.factory.Factory;


@RequestScoped
@Path("/numberService")
public class NumberService {
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<BusinessNumero> listAllMembers() {
		DAONumeroController ctrl = new DAONumeroController();
		return ctrl.listarNumeros();
    }
	
	@POST
	@Path("/solicitarNumero")
	@Consumes(MediaType.APPLICATION_JSON)
	public String SolicitarNumero(BusinessNumero num){
		//System.out.println("hola desde altaNumero");
		Factory fac = Factory.getInstance();
		AttentionsController ctrl = fac.getAttentionsController();
		ctrl.solicitarNumero(num);
		return "se recibio : "+num.getInternalId().toString();
	}

}