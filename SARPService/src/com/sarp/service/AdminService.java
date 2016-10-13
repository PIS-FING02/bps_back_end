package com.sarp.service;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Qualifier;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.QueryParam;
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
import com.sarp.dao.controllers.DAOTramiteController;
import com.sarp.factory.Factory;
import com.sarp.json.modeler.JSONDisplay;
import com.sarp.json.modeler.JSONPuesto;
import com.sarp.json.modeler.JSONPuestoTramite;
import com.sarp.json.modeler.JSONSector;
import com.sarp.json.modeler.JSONSectorDisplay;
import com.sarp.json.modeler.JSONTramite;
import com.sarp.json.modeler.JSONTramiteSector;

@RequestScoped
@Path("/adminService")
public class AdminService {
	
	@Context ServletContext context;

	
  	@POST
  	@Path("/puestoTest")
  	@Consumes(MediaType.APPLICATION_JSON)
  	public String altaPuesto(JSONPuesto puesto){
  		Factory fac = Factory.getInstance();
  		AdminActionsController ctrl = fac.getAdminActionsController();
  		if(true){
  			try{
  				ctrl.altaPuesto(puesto);
  				return "Puesto "+puesto.getNombreMaquina()+" dado de alta satisfactoriamente";
  			}catch(Exception e){
  				throw new BadRequestException("Error al crear el Puesto");
  			}
  		}else{
  			throw new BadRequestException("No tiene permisos suficientes.");
  		}
  	}
  	
  	@POST
  	@Path("/puesto")
  	@Consumes(MediaType.APPLICATION_JSON)
  	public String altaPuesto(@HeaderParam("user-rol") String userRol, JSONPuesto puesto){
  		Factory fac = Factory.getInstance();
  		AdminActionsController ctrl = fac.getAdminActionsController();
  		if(userRol.equals("ResponsableSector")){
  			try{
  				ctrl.altaPuesto(puesto);
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
  				ctrl.bajaPuesto(puesto);
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
  				
  				ctrl.modificarPuesto(puesto);
  				return "Puesto "+puesto.getNombreMaquina()+" fue dado de baja";
  			}catch(Exception e){
  				throw new BadRequestException("Error al modificar Puesto.");
  			}
  		}else{
  			throw new BadRequestException("No tiene permisos suficientes.");
  		}
  		
  	}
  	
  	@GET
  	@Path("/puestosSector")
      @Produces(MediaType.APPLICATION_JSON)
      public List<JSONPuesto> listarPuestosSector(@HeaderParam("user-rol") String userRol, @QueryParam("id-sector") String idSector) {
  		System.out.println("entro a listar por sector");
  		Factory fac = Factory.getInstance();
  		AdminActionsController ctrl = fac.getAdminActionsController();
  		if(userRol.equals( "ResponsableSector")){
  			try{
  				List<JSONPuesto> listaPuestos = ctrl.listarPuestos(idSector);
  				return listaPuestos;
  				
  			}catch(Exception e){
  				throw new BadRequestException("Error al listar Puestos.");
  			}
  		}else{
  			throw new BadRequestException("No tiene permisos suficientes.");
  		}
      }
  	
  	@GET
  	@Path("/puestos")
      @Produces(MediaType.APPLICATION_JSON)
      public List<JSONPuesto> listarPuestos(@HeaderParam("user-rol") String userRol) {
  		System.out.println("entro a listar");
  		Factory fac = Factory.getInstance();
  		AdminActionsController ctrl = fac.getAdminActionsController();
  		if(userRol.equals( "ResponsableSector")){
  			try{
  				List<JSONPuesto> listaPuestos = ctrl.listarPuestos(null);
  				return listaPuestos;
  				
  			}catch(Exception e){
  				throw new BadRequestException("Error al listar Puestos.");
  			}
  		}else{
  			throw new BadRequestException("No tiene permisos suficientes.");
  		}
      }
  	
	@PUT
  	@Path("/asignarTramiteSector") 
  	@Consumes(MediaType.APPLICATION_JSON)
  	public String asignarTramiteSector(@HeaderParam("user-rol") String userRol, JSONTramiteSector tramiteSector){	
  		Factory fac = Factory.getInstance();
  		AdminActionsController ctrl = fac.getAdminActionsController();
  		if(userRol.equals("ResponsableSector")){
  			try{
  				ctrl.asignarTramiteSector(tramiteSector);
  				return "Se asigno el tramite: "+tramiteSector.getTramite().getNombre()+"al sector"+tramiteSector.getSector().getNombre();
  			}catch(Exception e){
  				throw new BadRequestException("Error al modificar Puesto.");
  			}
  		}else{
  			throw new BadRequestException("No tiene permisos suficientes.");
  		}
  		
  	}
	
	
	@PUT
  	@Path("/asignarPuestoSector")
  	@Consumes(MediaType.APPLICATION_JSON)
  	public String asignarPuestoSector(@HeaderParam("user-rol") String userRol, JSONTramiteSector tramiteSector){	
  		Factory fac = Factory.getInstance();
  		AdminActionsController ctrl = fac.getAdminActionsController();
  		if(userRol.equals("ResponsableSector")){
  			try{
  				//ctrl.asignarPuestoSector(tramiteSector);
  				return "Se asigno el tramite: "+tramiteSector.getTramite().getNombre()+"al sector"+tramiteSector.getSector().getNombre();
  			}catch(Exception e){
  				throw new BadRequestException("Error al modificar Puesto.");
  			}
  		}else{
  			throw new BadRequestException("No tiene permisos suficientes.");
  		}
  		
  	}	
	/*************************** TRAMITES ***************************/
	
	@GET
	@Path("/listarTramites")
    @Produces(MediaType.APPLICATION_JSON)
    public List<JSONTramite> listarTramites(@HeaderParam("user-rol") String userRol) {
		if(userRol.equals("ResponsableSector")){
			Factory fac = Factory.getInstance();
			AdminActionsController aac = fac.getAdminActionsController();
			return aac.listarTramites();
		}else{
			throw new BadRequestException("No tiene permisos suficientes.");
		}
    }
	
	@GET
	@Path("/listarTramitesPuesto?{nombre-maquina}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<JSONTramite> listarTramitesPuesto(@HeaderParam("user-rol") String userRol,@QueryParam ("nombre-maquina") String NombreMaquina ) {
		System.out.println("entro a listar");
		if(userRol.equals("ResponsableSector")){
			Factory fac = Factory.getInstance();
			AdminActionsController aac = fac.getAdminActionsController();
			return aac.listarTramitesPuesto(NombreMaquina);
		}else{
			throw new BadRequestException("No tiene permisos suficientes.");
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
				ctrl.altaTramite(jsonTramite);
				return "El tramite con codigo: "+jsonTramite.getCodigo()+ " y nombre: "+jsonTramite.getNombre()+" fue dado de alta satisfactoriamente";
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
				ctrl.bajaTramite(jsonTramite);
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
				ctrl.modificarTramite(jsonTramite);
				return "El tramite de codigo: "+jsonTramite.getCodigo()+" fue modificado exitosamente";
			}catch(Exception e){
				throw new BadRequestException("Error al modificar el Tramite");
			}
		}else{
			throw new BadRequestException("No tiene permisos para realizar esta accion.");
		}
		
	}

	@PUT
	@Path("/asignarTramitePuesto")
	@Consumes(MediaType.APPLICATION_JSON)
	public String asignarTramitePuesto(@HeaderParam("user-rol") String userRol, JSONPuestoTramite puestoTramite){	
		Factory fac = Factory.getInstance();
		AdminActionsController ctrl = fac.getAdminActionsController();
		if(userRol.equals("ResponsableSector")){
			try{
				ctrl.asignarTramitePuesto(puestoTramite);
				return "Se asigno el tramite"+puestoTramite.getTramite().getNombre()+" al puesto "+puestoTramite.getTramite().getNombre();
			}catch(Exception e){
				throw new BadRequestException("Error al asignar el Tramite al puesto");
			}
		}else{
			throw new BadRequestException("No tiene permisos para realizar esta accion.");
		}
	}
	
	/************************** SECTORES *****************************/
	
	@GET
	@Path("/listarSectores")
    @Produces(MediaType.APPLICATION_JSON)
    public List<JSONSector> listarSectores(@HeaderParam("user-rol") String userRol) {
		Factory fac = Factory.getInstance();
		AdminActionsController aac = fac.getAdminActionsController();
		if(userRol.equals("Administrador")){
			try {
				return aac.listarSectores();
			}catch(Exception e){
				throw new BadRequestException("Error al listar Sectores");
			}
		}else{
			throw new BadRequestException("No tiene permisos suficientes.");
		}
    }
	
	/*@PUT
	@Path("/asignarSectorDisplayAdmin")
    @Produces(MediaType.APPLICATION_JSON)
    public String listarSectores(@HeaderParam("user-rol") String userRol, JSONSectorDisplay secDisp) {
		Factory fac = Factory.getInstance();
		AdminActionsController ctrl = fac.getAdminActionsController();
		if(userRol.equals("Administrador")){
			try{
				ctrl.asignarSectorDisplayAdmin(secDisp);
				return "Display asignado satisfactoriamente";
			}catch(Exception e){
				throw new BadRequestException("Error al asignar Display a Sector");
			}
		}else{
			throw new BadRequestException("No tiene permisos para realizar esta accion.");
		}
		
    }*/
	
	/****************************** DISPLAY ******************************/
	


	@POST
	@Path("/display")
	@Consumes(MediaType.APPLICATION_JSON)
	public String altaDisplay(@HeaderParam("user-rol") String userRol, JSONDisplay display){
		Factory fac = Factory.getInstance();
		AdminActionsController ctrl = fac.getAdminActionsController();
		if ( (userRol.equals("ResponsableSector")) || (userRol.equals("Administrador")) ) {
			try{
				ctrl.altaDisplay(display.getIdDisplay());
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
				ctrl.bajaDisplay(display.getIdDisplay());
				return "Display "+display.getIdDisplay()+" fue dado de baja.";
			}catch(Exception e){
				throw new BadRequestException("Error al dar de baja el Display.");
			}
		}else{
			throw new BadRequestException("No tiene permisos suficientes.");
		}
		
	}
	
	/*@PUT
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
		
	}*/
	
	/* aun no se necesita en caso de necesitarse es necesario crear en la base la operacion listarDisplaySector(sectorid)
	 @GET
	@Path("/displays/{id-sector}")
    @Produces(MediaType.APPLICATION_JSON)
    //este metodo retorna los display de un sector
	public List<BusinessDisplay> listarDisplaySector(@HeaderParam("user-rol") String userRol, @PathParam("id-sector") String idSector) {
		Factory fac = Factory.getInstance();
		AdminActionsController ctrl = fac.getAdminActionsController();
		if ( (userRol.equals( "ResponsableSector")) || (userRol.equals("Administrador")) ){
			try{
				return ctrl.listarDisplays(idSector);	
			}catch(Exception e){
				throw new BadRequestException("Error al listar Display.");
			}
		}else{
			throw new BadRequestException("No tiene permisos suficientes.");
		}
    }
	*/
	
	@GET
	@Path("/displays")
    @Produces(MediaType.APPLICATION_JSON)
    //este metodo retorna los display de un sector
	public List<BusinessDisplay> listarDisplay(@HeaderParam("user-rol") String userRol) {
		Factory fac = Factory.getInstance();
		AdminActionsController ctrl = fac.getAdminActionsController();
		if ( (userRol.equals( "ResponsableSector")) || (userRol.equals("Administrador")) ){
			try{
				return ctrl.listarDisplays(null);	
			}catch(Exception e){
				throw new BadRequestException("Error al listar Display.");
			}
		}else{
			throw new BadRequestException("No tiene permisos suficientes.");
		}
    }
	
	/************ Borrar todo el sistema ***************/
	
	@DELETE
	@Path("/borrarTodo")
    //este metodo retorna los display de un sector
	public String borrarTodo(@HeaderParam("user-rol") String userRol) {
		Factory fac = Factory.getInstance();
		AdminActionsController ctrl = fac.getAdminActionsController();
		if (userRol.equals( "Administrador")){
			try{
				ctrl.borrarTodoElSistema();
				return "El sistema fue borrado con exito";
			}catch(Exception e){
				throw new BadRequestException("Error al borrar todo el sistema.");
			}
		}else{
			throw new BadRequestException("No tiene permisos suficientes.");
		}
    }
	
}
