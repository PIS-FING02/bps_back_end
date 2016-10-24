package com.sarp.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import org.jboss.resteasy.spi.InternalServerErrorException;
import org.jboss.resteasy.spi.UnauthorizedException;
import com.sarp.classes.BusinessDisplay;
import com.sarp.classes.BusinessNodeGAFU;
import com.sarp.classes.BusinessSectorRol;
import com.sarp.controllers.AdminActionsController;
import com.sarp.controllers.GAFUController;
import com.sarp.factory.Factory;
import com.sarp.json.modeler.JSONDisplay;
import com.sarp.json.modeler.JSONPuesto;
import com.sarp.json.modeler.JSONPuestoSector;
import com.sarp.json.modeler.JSONPuestoTramite;
import com.sarp.json.modeler.JSONSector;
import com.sarp.json.modeler.JSONSectorDisplay;
import com.sarp.json.modeler.JSONTramite;
import com.sarp.json.modeler.JSONTramiteSector;
import com.sun.istack.internal.Nullable;

@RequestScoped
@Path("/adminService")
public class AdminService {
	private final String ResponsableSectorGAFU = "GAP_ADMINAF";
	
	private final String ConsultorGAFU = "GAP_CONSAF";
	@Context ServletContext context;

	/************ABM PUESTO ***************/	
  	@POST
  	@Path("/puesto")
  	@Consumes(MediaType.APPLICATION_JSON)
  	public String altaPuesto(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user, JSONPuesto puesto){
  		Factory fac = Factory.getInstance();
  		AdminActionsController ctrl = fac.getAdminActionsController();
  		if(userRol.equals("ResponsableSector")){
  			try{
  				ctrl.altaPuesto(puesto);
  				return "OK";
  			}catch(Exception e){
  				throw new InternalServerErrorException("Error al crear el Puesto: " + e.getMessage());
  			}
  		}else{
  			throw new UnauthorizedException("No tiene permisos suficientes.");
  		}
  	}
  	
  	@DELETE
  	@Path("/puesto")
  	@Consumes(MediaType.APPLICATION_JSON)
  	public String bajaPuesto(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user, JSONPuesto puesto){	
  		Factory fac = Factory.getInstance();
  		AdminActionsController ctrl = fac.getAdminActionsController();
  		if(userRol.equals("ResponsableSector")){
  			try{
  				ctrl.bajaPuesto(puesto);
  				return "OK";
  			}catch(Exception e){
  				throw new InternalServerErrorException("Error al dar de baja el Puesto: " + e.getMessage());
  			}
  		}else{
  			throw new UnauthorizedException("No tiene permisos suficientes.");
  		}
  	}
  	
  	@PUT
  	@Path("/puesto")
  	@Consumes(MediaType.APPLICATION_JSON)
  	public String modificarPuesto(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user, JSONPuesto puesto){	
  		Factory fac = Factory.getInstance();
  		AdminActionsController ctrl = fac.getAdminActionsController();
  		if(userRol.equals("ResponsableSector")){
  			try{
  				
  				ctrl.modificarPuesto(puesto);
  				return "OK";
  			}catch(Exception e){
  				throw new InternalServerErrorException("Error al modificar Puesto: " + e.getMessage());
  			}
  		}else{
  			throw new UnauthorizedException("No tiene permisos suficientes.");
  		}
  		
  	}
  	
  	
  	@GET
  	@Path("/listarPuestos")
      @Produces(MediaType.APPLICATION_JSON)
      public List<JSONPuesto> listarPuestos(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user,@QueryParam("sectorId") String sectorId) {
	  	Factory fac = Factory.getInstance();
	  	AdminActionsController ctrl = fac.getAdminActionsController();
			  		
	  	if(userRol.equals( "ResponsableSector")){
	  		if (   !( sectorId == null || sectorId.isEmpty() ) ){
	  			try{
	  				List <JSONPuesto> listaPuestosSector = ctrl.listarPuestos(sectorId);
	  				List <JSONPuesto> listaPuestos=  ctrl.listarPuestos(null );
	  				
	  				listaPuestos.removeAll(listaPuestosSector);
	  				return listaPuestos;
	  			}catch(Exception e){
	  				throw new InternalServerErrorException("Error al listar Puestos: " + e.getMessage());
	  			}
	  		
  			}else{
  				GAFUController controladorGAFU = fac.GAFUController();
	  	  		try{
  	  				List<BusinessSectorRol> respde =  controladorGAFU.obtenerSectorRolesUsuario(user,ResponsableSectorGAFU);
  	  				List<JSONPuesto> listaPuestos = new ArrayList<JSONPuesto>();
  	  				for  (BusinessSectorRol as : respde){
  	  					 listaPuestos.addAll( ctrl.listarPuestos(as.getSectorId()) );
  	  				}
  	  				return listaPuestos;
  	  			}catch(Exception e){
  	  				throw new InternalServerErrorException("Error al listar Puestos: " + e.getMessage());
  	  			}
  			}
  		}else{
  			throw new UnauthorizedException("No tiene permisos suficientes.");
  		}
  			
      }
 	
  	
	/******* Alta, Baja & Modificacion de Tramites *******/
	
	@POST
	@Path("/tramite")
	@Consumes(MediaType.APPLICATION_JSON)
	public String altaTramite(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user, JSONTramite jsonTramite){
		if(userRol.equals("Administrador")){
			try{				
				Factory fac = Factory.getInstance();
				AdminActionsController ctrl = fac.getAdminActionsController();
				ctrl.altaTramite(jsonTramite);
				return "OK";
			}catch(Exception e){
				throw new InternalServerErrorException("Error al crear el Tramite: " + e.getMessage());
			}
		}else{
			throw new UnauthorizedException("No tiene permisos para realizar esta accion.");
		}
		
	}
	
	@DELETE
	@Path("/tramite")
	@Consumes(MediaType.APPLICATION_JSON)
	public String bajaTramite(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user, JSONTramite jsonTramite){	
	
		if(userRol.equals("Administrador")){
			try{
				Factory fac = Factory.getInstance();
				AdminActionsController ctrl = fac.getAdminActionsController();
				ctrl.bajaTramite(jsonTramite);
				return "OK";
			}catch(Exception e){
				throw new InternalServerErrorException("Error al eliminar el Tramite: " + e.getMessage());
			}
		}else{
			throw new UnauthorizedException("No tiene permisos para realizar esta accion.");
		}
	}
	
	@PUT
	@Path("/tramite")
	@Consumes(MediaType.APPLICATION_JSON)
	public String modificarTramite(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user, JSONTramite jsonTramite){	
		if(userRol.equals("Administrador")){
			try{
				Factory fac = Factory.getInstance();
				AdminActionsController ctrl = fac.getAdminActionsController();
				ctrl.modificarTramite(jsonTramite);
				return "OK";
			}catch(Exception e){
				throw new InternalServerErrorException("Error al modificar el Tramite: " + e.getMessage());
			}
		}else{
			throw new UnauthorizedException("No tiene permisos para realizar esta accion.");
		}
		
	}

	@GET
	@Path("/listarTramites")
    @Produces(MediaType.APPLICATION_JSON)
    public List<JSONTramite> listarTramites(@HeaderParam("user-rol") String userRol, @HeaderParam("user") String user, @QueryParam("sectroId") String sectorId ){
		 
		if(userRol.equals("ResponsableSector")){
			Factory fac = Factory.getInstance();
			AdminActionsController ctrl = fac.getAdminActionsController();
			try{
				if (   !( sectorId == null || sectorId.isEmpty() ) ){
	  				List <JSONTramite> listaTramiteSector = ctrl.listarTramitesSector(sectorId);
	  				List <JSONTramite> listaTramite =  ctrl.listarTramites();
	  				listaTramite.removeAll(listaTramiteSector);
	  				return listaTramite;
		  			
				}else{
					GAFUController controladorGAFU = fac.GAFUController();
	  				List<BusinessSectorRol> respde =  controladorGAFU.obtenerSectorRolesUsuario(user,ResponsableSectorGAFU);
	  				List<JSONTramite> listaTramite = new ArrayList<JSONTramite>();
	  				for  (BusinessSectorRol as : respde){
	  					listaTramite.addAll( ctrl.listarTramitesSector(as.getSectorId()) );
	  				}
	  				return listaTramite;
				}
			}catch (Exception e) {
				throw new InternalServerErrorException("Error al listar los Tramite: " + e.getMessage());
			}
		}else{
			throw new UnauthorizedException("No tiene permisos suficientes.");
		}
    }
	
	
	
	/************************** SECTORES *****************************/
	
	@GET
	@Path("/listarSectores")
    @Produces(MediaType.APPLICATION_JSON)
    public List<JSONSector> listarSectores(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user) {

		Factory fac = Factory.getInstance();
		AdminActionsController aac = fac.getAdminActionsController();
		if(userRol.equals("Administrador")){
			try {
				return aac.listarSectores();
			}catch(Exception e){
				throw new InternalServerErrorException("Error al listar Sectores: " + e.getMessage());
			}
		}else{
			if (userRol.equals("ResponsableSector")){
				GAFUController controladorGAFU = fac.GAFUController();
				try {
				List<BusinessSectorRol> respde =  controladorGAFU.obtenerSectorRolesUsuario(user,ResponsableSectorGAFU);
  			
  				return aac.listarSectores(respde);
				}catch(Exception e){
					throw new InternalServerErrorException("Error al listar Sectores: " + e.getMessage());
				}
			}else{
				throw new UnauthorizedException("No tiene permisos suficientes.");
			}
		}
    }
	

	@PUT
	@Path("/reinicializarColas")
    public String reinicializarColas(@HeaderParam("secret-command") String secretCommand) throws Exception {
		if(secretCommand.equals("MacocoReinicializar")){
			try {
				Factory fac = Factory.getInstance();
				AdminActionsController aac = fac.getAdminActionsController();
				aac.reinicializarColas();
				return "OK";
			}catch(Exception e){
				throw new InternalServerErrorException("error al reiniciar la cola: " + e.getMessage());
			}
		}else{
			throw new UnauthorizedException("No tiene permisos suficientes.");
		}
	}			
	
	@POST
	@Path("/recuperarColas")
	public String recuperarCola(@HeaderParam("secret-command") String secretCommand) throws Exception {
		if(secretCommand.equals("MacocoRecuperar")){
			try {
				Factory fac = Factory.getInstance();
				AdminActionsController aac = fac.getAdminActionsController();
				aac.reinicializarColas();
				aac.recuperarColas();
				return "OK";
			}catch(Exception e){
				throw new InternalServerErrorException("error al reiniciar la cola: " + e.getMessage());
			}
		}else{
			throw new UnauthorizedException("No tiene permisos suficientes.");
		}
	}
	
	@PUT
	@Path("/actualizarGAFU")
    public String actualizarGAFU(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user) {
		if(userRol.equals("Administrador")){
			Factory fac = Factory.getInstance();
			AdminActionsController aac = fac.getAdminActionsController();
			try {
				aac.actualizarGAFU();
				return "OK";
			}catch(Exception e){
				throw new InternalServerErrorException("Error al actualizar GAFU" + e.getMessage());
			}
		}else{
			throw new UnauthorizedException("No tiene permisos suficientes.");
		}
    }

	/****************************** Alta, Baja & Modificacion de DISPLAY ******************************/


	@POST
	@Path("/display")
	@Consumes(MediaType.APPLICATION_JSON)
	public String altaDisplay(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user, JSONDisplay display){
		Factory fac = Factory.getInstance();
		AdminActionsController ctrl = fac.getAdminActionsController();
		if ( (userRol.equals("ResponsableSector")) || (userRol.equals("Administrador")) ) {
			try{
				ctrl.altaDisplay(display.getIdDisplay());
				return "OK";
			}catch(Exception e){
				throw new InternalServerErrorException("Error al crear el Display: " + e.getMessage());
			}
		}else{
			throw new UnauthorizedException("No tiene permisos suficientes.");
		}
	}
	
	@DELETE
	@Path("/display")
	@Consumes(MediaType.APPLICATION_JSON)
	public String bajaDisplay(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user, JSONDisplay display){	
		Factory fac = Factory.getInstance();
		AdminActionsController ctrl = fac.getAdminActionsController();
		if ( (userRol.equals("ResponsableSector")) || (userRol.equals("Administrador")) ){
			try{
				ctrl.bajaDisplay(display.getIdDisplay());
				return "OK";
			}catch(Exception e){
				throw new InternalServerErrorException("Error al dar de baja el Display: " + e.getMessage());
			}
		}else{
			throw new UnauthorizedException("No tiene permisos suficientes.");
		}
		
	}
	
	/*@PUT
	@Path("/display")
	@Consumes(MediaType.APPLICATION_JSON)
	public String modificarDisplay(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user, JSONDisplay display){	
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
	

	
	@GET
	@Path("/displays")
    @Produces(MediaType.APPLICATION_JSON)
    //este metodo retorna los display de un sector
	public List<JSONDisplay> listarDisplay(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user,@QueryParam("sectorId") String sectorId) {
		Factory fac = Factory.getInstance();
		AdminActionsController ctrl = fac.getAdminActionsController();
		if ( userRol.equals("Administrador") ){
			try{
				return ctrl.listarDisplays(null);	
			}catch(Exception e){
				throw new InternalServerErrorException("Error al listar Display: " + e.getMessage());
			}
		}else{
			if (userRol.equals( "ResponsableSector")){
				try{
					if (   !( sectorId == null || sectorId.isEmpty() ) ){
		  				List <JSONDisplay> listaDisplaySector = ctrl.listarDisplays(sectorId);
		  				List <JSONDisplay> listaDisplay=  ctrl.listarDisplays(null);
		  				listaDisplay.removeAll(listaDisplaySector);
		  				return listaDisplay;
			  			
					}else{
						GAFUController controladorGAFU = fac.GAFUController();
		  				List<BusinessSectorRol> respde =  controladorGAFU.obtenerSectorRolesUsuario(user,ResponsableSectorGAFU);
		  				List<JSONDisplay> listaDispaly = new ArrayList<JSONDisplay>();
		  				for  (BusinessSectorRol as : respde){
		  					listaDispaly.addAll( ctrl.listarDisplays(as.getSectorId()) );
		  				}
		  				return listaDispaly;
					}
				}catch(Exception e){
	  				throw new InternalServerErrorException("Error al listar Puestos: " + e.getMessage());
	  			}
			}else{
				throw new UnauthorizedException("No tiene permisos suficientes.");
			}
		}
    }
	
	/************************** ASIGNACIONES *****************************/
	
	
	@POST
  	@Path("/asignarTramiteSector") /*ok*/
	
  	@Consumes(MediaType.APPLICATION_JSON)
  	public String asignarTramiteSector(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user, JSONTramiteSector tramiteSector){	
  		Factory fac = Factory.getInstance();
  		AdminActionsController ctrl = fac.getAdminActionsController();
  		if(userRol.equals("ResponsableSector")){
  		
  			
  			try{
  				ctrl.asignarTramiteSector(tramiteSector);
  				return "OK";
  			}catch(Exception e){
  				throw new InternalServerErrorException("Error al asignar puesto a tramite: " + e.getMessage());
  			}
  		}else{
  			throw new UnauthorizedException("No tiene permisos suficientes.");
  		}
  		
  	}
	
	@POST
	@Path("/asignarTramitePuesto")/*ok*/
	@Consumes(MediaType.APPLICATION_JSON)
	public String asignarTramitePuesto(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user, JSONPuestoTramite puestoTramite){	
		Factory fac = Factory.getInstance();
		AdminActionsController ctrl = fac.getAdminActionsController();
		if(userRol.equals("ResponsableSector")){
			try{
				ctrl.asignarTramitePuesto(puestoTramite);
				return "OK";
			}catch(Exception e){
				throw new InternalServerErrorException("Error al asignar el Tramite al puesto: " + e.getMessage());
			}
		}else{
			throw new UnauthorizedException("No tiene permisos para realizar esta accion.");
		}
	}
	
	
	@POST
  	@Path("/asignarPuestoSector")/*ok*/
  	@Consumes(MediaType.APPLICATION_JSON)
  	public String asignarPuestoSector(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user, 
  			JSONPuestoSector puestoSector){	
  		Factory fac = Factory.getInstance();
  		AdminActionsController ctrl = fac.getAdminActionsController();
  		if(userRol.equals("ResponsableSector")){
  			try{
  				ctrl.asignarPuestoSector(puestoSector);
  				return "OK";
  			}catch(Exception e){
  				throw new InternalServerErrorException("Error al Asignar Puesto a Sector: " + e.getMessage());
  			}
  		}else{
  			throw new UnauthorizedException("No tiene permisos suficientes.");
  		}
  		
  	}	

	@POST
	@Path("/asignarSectorDisplay")/*ok*/
    @Produces(MediaType.APPLICATION_JSON)
    public String asignarSectorDisplay(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user, JSONSectorDisplay secDisp) {
		Factory fac = Factory.getInstance();
		AdminActionsController ctrl = fac.getAdminActionsController();
		if(userRol.equals("Administrador")){
			try{
				ctrl.asignarSectorDisplay(secDisp);
				return "OK";
			}catch(Exception e){
				throw new InternalServerErrorException("Error al asignar Display a Sector: " + e.getMessage());
			}
		}else{
			throw new UnauthorizedException("No tiene permisos para realizar esta accion.");
		}
		
    }
	
	/************************** DESASIGNACIONES *****************************/
	
	@DELETE
	@Path("/desasignarSectorDisplay")
    @Produces(MediaType.APPLICATION_JSON)
    public String desasignarSectorDisplay(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user, JSONSectorDisplay secDisp) {
		Factory fac = Factory.getInstance();
		AdminActionsController ctrl = fac.getAdminActionsController();
		if(userRol.equals("Administrador")){
			try{
				ctrl.desasignarSectorDisplay(secDisp);
				return "OK";
			}catch(Exception e){
				throw new InternalServerErrorException("Error al desasignar Display a Sector: " + e.getMessage());
			}
		}else{
			throw new UnauthorizedException("No tiene permisos para realizar esta accion.");
		}
		
    }
	
	@DELETE
  	@Path("/desasignarTramiteSector")
	
  	@Consumes(MediaType.APPLICATION_JSON)
  	public String desasignarTramiteSector(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user, JSONTramiteSector tramiteSector){	
  		Factory fac = Factory.getInstance();
  		AdminActionsController ctrl = fac.getAdminActionsController();
  		if(userRol.equals("ResponsableSector")){
  			try{
  				ctrl.desasignarTramiteSector(tramiteSector);
  				return "OK";
  			}catch(Exception e){
  				throw new InternalServerErrorException("Error al desasignar puesto a tramite: " + e.getMessage());
  			}
  		}else{
  			throw new UnauthorizedException("No tiene permisos suficientes.");
  		}
  		
  	}
	
	@DELETE
  	@Path("/desasignarPuestoSector")/*ok*/
  	@Consumes(MediaType.APPLICATION_JSON)
  	public String desasignarPuestoSector(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user , JSONPuestoSector puestoSector){	
  		Factory fac = Factory.getInstance();
  		AdminActionsController ctrl = fac.getAdminActionsController();
  		if(userRol.equals("ResponsableSector")){
  			try{
  				ctrl.desasignarPuestoSector(puestoSector);
  				return "OK";
  			}catch(Exception e){
  				throw new InternalServerErrorException("Error al Asignar Puesto a Sector: " + e.getMessage());
  			}
  		}else{
  			throw new UnauthorizedException("No tiene permisos suficientes.");
  		}
  		
  	}
	
	@DELETE
	@Path("/desasignarTramitePuesto")/*ok*/
	@Consumes(MediaType.APPLICATION_JSON)
	public String desasignarTramitePuesto(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user, JSONPuestoTramite puestoTramite){	
		Factory fac = Factory.getInstance();
		AdminActionsController ctrl = fac.getAdminActionsController();
		if(userRol.equals("ResponsableSector")){
			try{
				ctrl.desasignarTramitePuesto(puestoTramite);
				return "OK";
			}catch(Exception e){
				throw new InternalServerErrorException("Error al desasignar el Tramite al puesto: " + e.getMessage());
			}
		}else{
			throw new UnauthorizedException("No tiene permisos para realizar esta accion.");
		}
	}
	
	/************************** LISTAR POR *****************************/
	

  	  @GET
  	  @Path("/listarPuestosSector")
      @Produces(MediaType.APPLICATION_JSON)
      public List<JSONPuesto> listarPuestosSector(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user, 
    		  @QueryParam("sectorId") String idSector) {
  		
  		Factory fac = Factory.getInstance();
  		AdminActionsController ctrl = fac.getAdminActionsController();
  		if(userRol.equals( "ResponsableSector")){
  			try{
  				List<JSONPuesto> listaPuestos = ctrl.listarPuestos(idSector);
  				return listaPuestos;
  				
  			}catch(Exception e){
  				throw new InternalServerErrorException("Error al listar Puestos del Sector: " + e.getMessage());
  			}
  		}else{
  			throw new UnauthorizedException("No tiene permisos suficientes.");
  		}
      }
  	
  	  @GET
  	  @Path("/listarTramitesSector")
      @Produces(MediaType.APPLICATION_JSON)
      public List<JSONTramite> listarTramitesSector(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user, 
    		  @QueryParam("sectorId") String idSector) {
  		
  		Factory fac = Factory.getInstance();
  		AdminActionsController ctrl = fac.getAdminActionsController();
  		if(userRol.equals( "ResponsableSector")){
  			try{
  				List<JSONTramite> listatrm = ctrl.listarTramitesSector(idSector);
  				return listatrm;
  				
  			}catch(Exception e){
  				throw new InternalServerErrorException("Error al listar Tramites del Sector: " + e.getMessage());
  			}
  		}else{
  			throw new UnauthorizedException("No tiene permisos suficientes.");
  		}
      }
  	  
  	@GET
	@Path("/listarTramitesPuesto")
    @Produces(MediaType.APPLICATION_JSON)
    public List<JSONTramite> listarTramitesPuesto(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user,
    		@QueryParam("nombreMaquina") String NombreMaquina ) {
		System.out.println("entro a listar");
		if(userRol.equals("ResponsableSector")){
			Factory fac = Factory.getInstance();
			AdminActionsController aac = fac.getAdminActionsController();
			try{
				return aac.listarTramitesPuesto(NombreMaquina);
			}
			catch(Exception e){
				throw new InternalServerErrorException("Error al listar los Tramites del Puesto: " + e.getMessage());
			}
		}else{
			throw new UnauthorizedException("No tiene permisos suficientes.");
		}
    }
  	@GET
	@Path("/listarTramitesPosibles")
    @Produces(MediaType.APPLICATION_JSON)
    public List<JSONTramite> listarTramitesPosibles(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user,@QueryParam("nombreMaquina") String NombreMaquina ) {
		///System.out.println("entro a listar");
		if(userRol.equals("ResponsableSector")){
			Factory fac = Factory.getInstance();
			AdminActionsController aac = fac.getAdminActionsController();
			try{
				return aac.listarTramitesPosibles(NombreMaquina);
			}
			catch(Exception e){
				throw new InternalServerErrorException("Error al listar los posibles tramites del Puesto: " + e.getMessage());
			}
		}else{
			throw new UnauthorizedException("No tiene permisos suficientes.");
		}
    }
  	
	@GET
	@Path("/listarDisplaysSector")
	@Produces(MediaType.APPLICATION_JSON)
	//este metodo retorna los display de un sector
	public List<JSONDisplay> listarDisplaySector(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user, @QueryParam("sectorId") String idSector) {
		Factory fac = Factory.getInstance();
		AdminActionsController ctrl = fac.getAdminActionsController();
		if ( (userRol.equals( "ResponsableSector")) || (userRol.equals("Administrador")) ){
			try{
				return ctrl.listarDisplays(idSector);	
			}catch(Exception e){
				throw new InternalServerErrorException("Error al listar Displays del Sector: " + e.getMessage());
			}
		}else{
			throw new UnauthorizedException("No tiene permisos suficientes.");
		}
	}
	
	
	/************ Borrar todo el sistema ***************/
	
	@DELETE
	@Path("/borrarRows")
	public String borrarRows(@HeaderParam("secret-command") String secretCommand) {
		Factory fac = Factory.getInstance();
		AdminActionsController ctrl = fac.getAdminActionsController();
		if (secretCommand.equals( "MacocoBorrador")){
			try{
				ctrl.borrarTodoElSistema();
				return "OK";
			}catch(Exception e){
				throw new InternalServerErrorException("Error al macoquear: " + e.getMessage());
			}
		}else{
			throw new UnauthorizedException("NO sos macoco");
		}
    }
	
}
