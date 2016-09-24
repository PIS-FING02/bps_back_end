package com.sarp.service;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.spi.BadRequestException;

import com.sarp.classes.BusinessPuesto;
import com.sarp.classes.BusinessSector;
import com.sarp.classes.BusinessTramite;
import com.sarp.controllers.AdminActionsController;
import com.sarp.dao.controllers.DAOSectorController;
import com.sarp.factory.Factory;
import com.sarp.json.modeler.JSONPuesto;

@RequestScoped
@Path("/adminService")
public class AdminService {
	
	@Context ServletContext context;

	@POST
	@Path("/puesto")
	@Consumes(MediaType.APPLICATION_JSON)
	public String altaPuesto(JSONPuesto puesto){
		Factory fac = Factory.getInstance();
		AdminActionsController ctrl = fac.getAdminActionsController();
		if(puesto.getRol().equals("ResponsableSector")){
			try{
				ctrl.altaPuesto(puesto.getNombreMaquina());
				return "Puesto "+puesto.getNombreMaquina()+" dado de alta satisfactoriamente";
			}catch(Exception e){
				throw new BadRequestException("Error al crear el Puesto");
			}
		}else{
			throw new BadRequestException("No tiene permisos suficientes.");
		}
	}
	
	@DELETE
	@Path("/puesto")
	@Consumes(MediaType.APPLICATION_JSON)
	public String bajaPuesto(JSONPuesto puesto){	
		Factory fac = Factory.getInstance();
		AdminActionsController ctrl = fac.getAdminActionsController();
		if(puesto.getRol().equals("ResponsableSector")){
			try{
				ctrl.bajaPuesto(puesto.getNombreMaquina());
				return "Puesto "+puesto.getNombreMaquina()+" fue dado de baja";
			}catch(Exception e){
				throw new BadRequestException("Error al dar de baja el Puesto.");
			}
		}else{
			throw new BadRequestException("No tiene permisos suficientes.");
		}
		
	}
	
	@PUT
	@Path("/puesto")
	@Consumes(MediaType.APPLICATION_JSON)
	public String modificarPuesto(JSONPuesto puesto){	
		Factory fac = Factory.getInstance();
		AdminActionsController ctrl = fac.getAdminActionsController();
		if(puesto.getRol().equals("ResponsableSector")){
			try{
				
				ctrl.modificarPuesto(puesto.getNombreMaquina(),puesto.getEstado(),puesto.getUsuarioId());
				return "Puesto "+puesto.getNombreMaquina()+" fue dado de baja";
			}catch(Exception e){
				throw new BadRequestException("Error al modificar Puesto.");
			}
		}else{
			throw new BadRequestException("No tiene permisos suficientes.");
		}
		
	}
	
	@GET
	@Path("/puestos")
    @Produces(MediaType.APPLICATION_JSON)
    public List<BusinessPuesto> listarPuestos(JSONPuesto puesto) {
		Factory fac = Factory.getInstance();
		AdminActionsController ctrl = fac.getAdminActionsController();
		if(puesto.getRol().equals( "ResponsableSector")){
			try{
				List<BusinessPuesto> listaPuestos = ctrl.listarPuestos(puesto.getSectorId());
				return listaPuestos;
				
			}catch(Exception e){
				throw new BadRequestException("Error al listar Puestos.");
			}
		}else{
			throw new BadRequestException("No tiene permisos suficientes.");
		}
    }

	
	/*************************** TRAMITES ***************************/
	
	@GET
	@Path("/listarTramites")
    @Produces(MediaType.APPLICATION_JSON)
    public List<BusinessTramite> listarTramites(String rol) {
		try{
			Factory factory = Factory.getInstance();
			return factory.getAdminActionsController().listarTramites();
		}catch(Exception e){
			throw new BadRequestException("Error obtiendo tramites");
		}
    }
	
	@POST
	@Path("/{sector}/{nombre}")
	public String altaTramite( @PathParam("sector") int sector, @PathParam("nombre") String nombre){		
		try {
			Factory factory = Factory.getInstance();
			return "OK";
		} catch (Exception e) {
			throw new BadRequestException("Error en alta tramite");
		}
	}

	/************************** SECTORES *****************************/

	@GET
	@Path("/listarSectores")
    @Produces(MediaType.APPLICATION_JSON)
    public List<BusinessSector> listarSectores() {
		com.sarp.dao.controllers.DAOSectorController ctrl = new DAOSectorController();
		return ctrl.listarSectores();
    }
	
	@POST
	@Path("/altaSector/{nombre}/{codigo}")
	public String altaSector(
			@PathParam("nombre") String nombre,
			@PathParam("codigo") Integer codigo){
		System.out.println("hola desde altaSector");
		com.sarp.dao.controllers.DAOSectorController ctrl = new DAOSectorController();
		try {
			ctrl.crearSector(codigo, nombre);
			return "OK";
		} catch (Exception e) {
			return e.toString();
		}
	}
}
