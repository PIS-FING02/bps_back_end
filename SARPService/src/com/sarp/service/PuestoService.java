package com.sarp.service;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Context ;

import com.sarp.classes.BusinessPuesto;
import com.sarp.controllers.AdminActionsController;
import com.sarp.controllers.AtentionsController;
import com.sarp.dao.controllers.NumeroController;
import com.sarp.enumerados.EstadoPuesto;
import com.sarp.factory.Factory;


@RequestScoped
@Path("/puestos")
public class PuestoService {
	
	@Context ServletContext context;

	@POST
	@Path("/altaPuesto")
	@Consumes(MediaType.APPLICATION_JSON)
	public String altaPuesto(String nombreMaquina, String rol){	//hay que cambiar para que no tire string
		Factory fac = Factory.getInstance();
		AdminActionsController ctrl = fac.getAdminActionsController();
		if(rol== "ResponsableSector"){
			try{
				ctrl.altaPuesto(nombreMaquina);
				return "Puesto "+nombreMaquina+" dado de alta satisfactoriamente";
			}catch(Exception e){
				return "404";
			}
		}else{
			return "404";//ver como mandar bad request
		}
		
	}
	
	@DELETE
	@Path("/bajaPuesto")
	@Consumes(MediaType.APPLICATION_JSON)
	public String bajaPuesto(String nombreMaquina, String rol){	//hay que cambiar para que no tire string
		Factory fac = Factory.getInstance();
		AdminActionsController ctrl = fac.getAdminActionsController();
		if(rol== "ResponsableSector"){
			try{
				ctrl.bajaPuesto(nombreMaquina);
				return "Puesto "+nombreMaquina+" fue dado de baja";
			}catch(Exception e){
				return "404";
			}
		}else{
			return "404";//ver como mandar bad request
		}
		
	}
	
	@POST
	@Path("/modificarUsuarioPuesto")
	@Consumes(MediaType.APPLICATION_JSON)
	public String modificarUsuarioPuesto(String nombreMaquina, String usuarioId, String rol){	//hay que cambiar para que no tire string
		Factory fac = Factory.getInstance();
		AdminActionsController ctrl = fac.getAdminActionsController();
		if(rol== "ResponsableSector"){
			try{
				ctrl.modificarUsuarioPuesto(nombreMaquina,UsuarioId);
				return "Puesto "+nombreMaquina+" fue dado de baja";
			}catch(Exception e){
				return "404";
			}
		}else{
			return "404";//ver como mandar bad request
		}
		
	}
	
	@POST
	@Path("/modificarEstadoPuesto")
	@Consumes(MediaType.APPLICATION_JSON)
	public String modificarEstadoPuesto(String nombreMaquina, String estado, String rol){	//hay que cambiar para que no tire string
		Factory fac = Factory.getInstance();
		AdminActionsController ctrl = fac.getAdminActionsController();
		if(rol== "ResponsableSector"){
			try{
				ctrl.modificarEstadoPuesto(nombreMaquina,estado);
				return "Puesto "+nombreMaquina+" fue dado de baja";
			}catch(Exception e){
				return "404";
			}
		}else{
			return "404";//ver como mandar bad request
		}
		
	}
	
	@GET
	@Path("/listarPuestos")
    @Produces(MediaType.APPLICATION_JSON)
    public List<BusinessPuesto> listarPuestos(Integer sectorId, String rol) {
		Factory fac = Factory.getInstance();
		AdminActionsController ctrl = fac.getAdminActionsController();
		if(rol== "ResponsableSector"){
			try{
				List<BusinessPuesto> listaPuestos = ctrl.modificarEstadoPuesto(nombreMaquina,estado);
				return listaPuestos;
				
			}catch(Exception e){
				return null;
			}
		}else{
			return null;//ver como mandar bad request
		}
    }

}
