package com.sarp.service;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
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
import com.sarp.classes.BusinessDisplay;
import com.sarp.controllers.AdminActionsController;
import com.sarp.dao.controllers.DAOSectorController;
import com.sarp.factory.Factory;
import com.sarp.json.modeler.JSONDisplay;
import com.sarp.json.modeler.JSONPuesto;
import com.sarp.json.modeler.JSONSector;
import com.sarp.json.modeler.JSONTramite;

@RequestScoped
@Path("/adminService")
public class AdminService {
	
	@Context ServletContext context;

  	@POST
  	@Path("/puesto")
  	@Consumes(MediaType.APPLICATION_JSON)
  	public String altaPuesto(@HeaderParam("user-rol") String userRol, JSONPuesto puesto){
  		Factory fac = Factory.getInstance();
  		AdminActionsController ctrl = fac.getAdminActionsController();
  		if(userRol.equals("ResponsableSector")){
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
  	public String bajaPuesto(@HeaderParam("user-rol") String userRol, JSONPuesto puesto){	
  		Factory fac = Factory.getInstance();
  		AdminActionsController ctrl = fac.getAdminActionsController();
  		if(userRol.equals("ResponsableSector")){
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
  	public String modificarPuesto(@HeaderParam("user-rol") String userRol, JSONPuesto puesto){	
  		Factory fac = Factory.getInstance();
  		AdminActionsController ctrl = fac.getAdminActionsController();
  		if(userRol.equals("ResponsableSector")){
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
      public List<BusinessPuesto> listarPuestos(@HeaderParam("user-rol") String userRol, JSONSector sector) {
  		System.out.println("entro a listar");
  		Factory fac = Factory.getInstance();
  		AdminActionsController ctrl = fac.getAdminActionsController();
  		if(userRol.equals( "ResponsableSector")){
  			try{
  				List<BusinessPuesto> listaPuestos = ctrl.listarPuestos(sector.getCodigo());
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
    public List<BusinessTramite> listarTramites(@HeaderParam("user-rol") String userRol) {
		try{
			Factory factory = Factory.getInstance();
			return factory.getAdminActionsController().listarTramites();
		}catch(Exception e){
			throw new BadRequestException("Error obtiendo tramites");
		}
    }
	
	
	/******* Alta, Baja & Modificacion de Tramites *******/
	
	@POST
	@Path("/tramite")
	@Consumes(MediaType.APPLICATION_JSON)
	public String altaTramite(@HeaderParam("user-rol") String userRol, JSONTramite jsonTramite){
		Factory fac = Factory.getInstance();
		AdminActionsController ctrl = fac.getAdminActionsController();
		if(userRol.equals("Administrador")){
			try{
				BusinessTramite tramite = new BusinessTramite(jsonTramite.getCodigo(), jsonTramite.getNombre());				
				ctrl.altaTramite(tramite);
				return "El tramite con codigo: "+tramite.getCodigo()+ " y nombre: "+tramite.getNombre()+" fue dado de alta satisfactoriamente";
			}catch(Exception e){
				throw new BadRequestException("Error al crear el Tramite");
			}
		}else{
			throw new BadRequestException("No tiene permisos para realizar esta accion.");
		}
		
	}
	
	@DELETE
	@Path("/tramite")
	@Consumes(MediaType.APPLICATION_JSON)
	public String bajaTramite(@HeaderParam("user-rol") String userRol, JSONTramite jsonTramite){	
		Factory fac = Factory.getInstance();
		AdminActionsController ctrl = fac.getAdminActionsController();
		if(userRol.equals("Administrador")){
			try{
				ctrl.bajaTramite(jsonTramite.getCodigo());
				return "El tramite de codigo "+jsonTramite.getCodigo()+" fue dado de baja";
			}catch(Exception e){
				throw new BadRequestException("Error al eliminar el Tramite");
			}
		}else{
			throw new BadRequestException("No tiene permisos para realizar esta accion.");
		}
	}
	
	@PUT
	@Path("/tramite")
	@Consumes(MediaType.APPLICATION_JSON)
	public String modificarTramite(@HeaderParam("user-rol") String userRol, JSONTramite jsonTramite){	
		Factory fac = Factory.getInstance();
		AdminActionsController ctrl = fac.getAdminActionsController();
		if(userRol.equals("Administrador")){
			try{
				BusinessTramite tramite = new BusinessTramite(jsonTramite.getCodigo(), jsonTramite.getNombre());
				ctrl.modificarTramite(tramite);
				return "El tramite de codigo: "+tramite.getCodigo()+" fue modificado exitosamente";
			}catch(Exception e){
				throw new BadRequestException("Error al modificar el Tramite");
			}
		}else{
			throw new BadRequestException("No tiene permisos para realizar esta accion.");
		}
		
	}

	/************************** SECTORES *****************************/

	@GET
	@Path("/listarSectores")
    @Produces(MediaType.APPLICATION_JSON)
    public List<BusinessSector> listarSectores(@HeaderParam("user-rol") String userRol) {
		com.sarp.dao.controllers.DAOSectorController ctrl = new DAOSectorController();
		return ctrl.listarSectores();
    }
	
	/****************************** DISPLAY ******************************/
	


	@POST
	@Path("/display")
	@Consumes(MediaType.APPLICATION_JSON)
	public String altaDisplay(@HeaderParam("user-rol") String userRol, JSONDisplay display){
		Factory fac = Factory.getInstance();
		AdminActionsController ctrl = fac.getAdminActionsController();
		if ( (userRol.equals("ResponsableSector")) || (userRol.equals("Administrador")) ) {
			try{
				ctrl.altaDisplay(display.getRutaArchivo());
				return "Display dado de alta satisfactoriamente";
			}catch(Exception e){
				throw new BadRequestException("Error al crear el Display");
			}
		}else{
			throw new BadRequestException("No tiene permisos suficientes.");
		}
	}
	
	@DELETE
	@Path("/display")
	@Consumes(MediaType.APPLICATION_JSON)
	public String bajaDisplay(@HeaderParam("user-rol") String userRol, JSONDisplay display){	
		Factory fac = Factory.getInstance();
		AdminActionsController ctrl = fac.getAdminActionsController();
		if ( (userRol.equals("ResponsableSector")) || (userRol.equals("Administrador")) ){
			try{
				ctrl.bajaPuesto(display.getRutaArchivo());
				return "Display "+display.getDisplayId()+" fue dado de baja.";
			}catch(Exception e){
				throw new BadRequestException("Error al dar de baja el Display.");
			}
		}else{
			throw new BadRequestException("No tiene permisos suficientes.");
		}
		
	}
	
	@PUT
	@Path("/display")
	@Consumes(MediaType.APPLICATION_JSON)
	public String modificarDisplay(@HeaderParam("user-rol") String userRol, JSONDisplay display){	
		Factory fac = Factory.getInstance();
		AdminActionsController ctrl = fac.getAdminActionsController();
		if ( (userRol.equals("ResponsableSector")) || (userRol.equals("Administrador")) ){
			try{
				ctrl.modificarRutaDisplay(display.getDisplayId(),display.getRutaArchivo());
				return "Display "+display.getDisplayId()+" fue dado de baja.";
			}catch(Exception e){
				throw new BadRequestException("Error al modificar Display.");
			}
		}else{
			throw new BadRequestException("No tiene permisos suficientes.");
		}
		
	}
	
	@GET
	@Path("/display")
    @Produces(MediaType.APPLICATION_JSON)
    public List<BusinessDisplay> listarDisplay(@HeaderParam("user-rol") String userRol, JSONDisplay display) {
		Factory fac = Factory.getInstance();
		AdminActionsController ctrl = fac.getAdminActionsController();
		if ( (userRol.equals( "ResponsableSector")) || (userRol.equals("Administrador")) ){
			try{
				return ctrl.listarDisplays(display.getSectorId());	
			}catch(Exception e){
				throw new BadRequestException("Error al listar Display.");
			}
		}else{
			throw new BadRequestException("No tiene permisos suficientes.");
		}
    }

}
