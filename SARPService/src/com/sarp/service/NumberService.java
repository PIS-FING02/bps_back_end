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
import com.sarp.controllers.AtentionsController;
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
	@Path("/{sec}/{tram}/{nombre}")
	public String altaNumero(
			@PathParam("sec") int sec,
			@PathParam("tram") int tram,
			@PathParam("nombre") String nombre){
		System.out.println("hola desde altaNumero");
		DAONumeroController ctrl = new DAONumeroController();
		try {
			ctrl.crearNumero(sec,tram, nombre);
			return "OK";
		} catch (Exception e) {
			return e.toString();
		}
	}
	
	
	

	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public String SolicitarNumero(BusinessNumero num){
		//System.out.println("hola desde altaNumero");
		Factory fac = Factory.getInstance();
		AtentionsController ctrl = fac.getAtentionsController();
		ctrl.solicitarNumero(num);
		return "se recibio : "+num.getNumero().toString()+num.getEmitido().toString();

	}
	
	@GET
	@Path("/{id:[0-9][0-9]*}/{estado}")
	public String modificacionNumero(
			@PathParam("id") int id, 
			@PathParam("estado") String estado){
		//NumeroControlador ctrl = new NumeroControlador();
		//ctrl.modificarNumero(id, estado, "serie modificada", 9);
		return "Numero " + id + " modificado con exito"; 
	}
	

}
