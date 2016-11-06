package com.sarp.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
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
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import com.sarp.beans.AdminBean;
import com.sarp.beans.GafuBean;
import com.sarp.classes.BusinessSectorRol;
import com.sarp.json.modeler.JSONDisplay;
import com.sarp.json.modeler.JSONPuesto;
import com.sarp.json.modeler.JSONPuestoSector;
import com.sarp.json.modeler.JSONPuestoTramite;
import com.sarp.json.modeler.JSONSectorDisplay;
import com.sarp.json.modeler.JSONTramite;
import com.sarp.json.modeler.JSONTramiteSector;
import com.sarp.utils.UtilService;

@RequestScoped
@Path("/adminService")
public class AdminService {
	private final String ResponsableSectorGAFU = "RESPSEC";
	
	private static Logger logger = Logger.getLogger(AttentionsService.class);
	
	private final String ConsultorGAFU = "CONSULTOR";
	@Context ServletContext context;
		
	@EJB
	private static AdminBean adminBean = new AdminBean();
	
	@EJB
	private static GafuBean gafu = new GafuBean();
	
	private String RESPONSABLE_SECTOR = "RESPSEC";//UtilService.getStringProperty("RESPONSABLE_SECTOR");
	private String ADMINISTRADOR = "ADMIN";//UtilService.getStringProperty("ADMINISTRADOR");
	private String OPERADOR = "OPERADOR";//UtilService.getStringProperty("OPERADOR");
	private String OPERADORSR = "OPERADORSR";//UtilService.getStringProperty("OPERADOR_SENIOR");
	private String CONSULTOR = "CONSULTOR";//UtilService.getStringProperty("CONSULTOR");

	/************ABM PUESTO ***************/	
  	
  	@POST
  	@Path("/puesto")
  	@Consumes(MediaType.APPLICATION_JSON)
  	public Response altaPuesto(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user, JSONPuesto puesto){
  		if(userRol.equals(RESPONSABLE_SECTOR)){
  			try{
  				adminBean.altaPuesto(puesto);
  				return Response.ok("OK").build();
  			}catch(Exception e){
				logger.error(e.toString() + ". POST puesto params: user-rol:"+userRol+" JSONPuesto: "+puesto);
				return Response.ok("ERROR: " + e.getMessage()).build();
  			}
  		}else{
			logger.error("Permisos insuficientes - " + RESPONSABLE_SECTOR + " - params: user-rol:" + userRol + ", JSONPuesto: " + puesto);
  			return Response.ok("ERROR: No tiene permisos suficientes.").build();
  		}
  	}
  	
  	@DELETE
  	@Path("/puesto")
  	@Consumes(MediaType.APPLICATION_JSON)
  	public Response bajaPuesto(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user, JSONPuesto puesto){	
  		if(userRol.equals(RESPONSABLE_SECTOR)){
  			try{
  				adminBean.bajaPuesto(puesto);
  				return Response.ok("OK").build();
  			}catch(Exception e){
				logger.error("DELETE puesto params: user-rol:"+userRol+" JSONPuesto: "+puesto);
				return Response.ok("ERROR: " + e.getMessage()).build();
  			}
  		}else{
  			logger.error("Permisos insuficientes - " + RESPONSABLE_SECTOR + " - params: user-rol:" + userRol + ", JSONPuesto: " + puesto);
  			return Response.ok("ERROR: No tiene permisos suficientes.").build();
  		}
  	}
  	
  	@PUT
  	@Path("/puesto")
  	@Consumes(MediaType.APPLICATION_JSON)
  	public Response modificarPuesto(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user, JSONPuesto puesto){	
  		if(userRol.equals(RESPONSABLE_SECTOR)){
  			try{
  				adminBean.modificarPuesto(puesto);
  				return Response.ok("OK").build();
  			}catch(Exception e){
  				return Response.ok("ERROR: " + e.getMessage()).build();
  			}
  		}else{
  			logger.error("Permisos insuficientes - " + RESPONSABLE_SECTOR + " - params: user-rol:" + userRol + ", JSONPuesto: " + puesto);
  			return Response.ok("ERROR: No tiene permisos suficientes.").build();
  		}
  		
  	}
  	
  	
  	@GET
  	@Path("/listarPuestos")
  	@Produces(MediaType.APPLICATION_JSON)
  	public Response listarPuestos(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user,@QueryParam("sectorId") String sectorId) {			  		
  		if(userRol.equals(RESPONSABLE_SECTOR)){
  			if (   !( sectorId == null || sectorId.isEmpty() ) ){
	
	  			//si me pasan sector quiero todos menos los del sector que pasan 
	  			try{
	  				List <JSONPuesto> listaPuestosSector = adminBean.listarPuestos(sectorId);
	  				List <JSONPuesto> listaPuestos=  adminBean.listarPuestos(null );
	
	  				listaPuestos.removeAll(listaPuestosSector);
	  				return Response.ok(listaPuestos).build();
	  			}catch(Exception e){
	  				return Response.ok("ERROR: " + e.getMessage()).build();
	  			}
	  		
			}else{
	
			// si no me pasan sector quiero los puestos de los cuales el es responsable
	  	  		try{
	  				List<BusinessSectorRol> respde =  gafu.obtenerSectorRolesUsuario(user,ResponsableSectorGAFU);
	  				List<JSONPuesto> listaPuestos = new ArrayList<JSONPuesto>();
	  				for  (BusinessSectorRol as : respde){
	  					List<JSONPuesto> listaPuestosSector = new ArrayList<JSONPuesto>();
	  	  				listaPuestosSector =adminBean.listarPuestos(as.getSectorId());
	  	  				List<JSONPuesto> diferencia = new ArrayList<JSONPuesto>();
	  	  				diferencia.addAll(listaPuestosSector);
	  	  				diferencia.retainAll(listaPuestos);
	  	  				listaPuestosSector.removeAll(diferencia);
	  					listaPuestos.addAll(listaPuestosSector);
	  				}
	  				return Response.ok(listaPuestos).build();
	  			}catch(Exception e){
	  				return Response.ok("ERROR: " + e.getMessage()).build();
	  			}
			}
		}else{
			logger.error("Permisos insuficientes - " + RESPONSABLE_SECTOR + " - params: user-rol:" + userRol + ", sectorId: " + sectorId);
			return Response.ok("ERROR: No tiene permisos suficientes.").build();
		}
			
	  }
 	
  	
	/******* Alta, Baja & Modificacion de Tramites *******/
	
	@POST
	@Path("/tramite")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response altaTramite(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user, JSONTramite jsonTramite){
		if(userRol.equals(ADMINISTRADOR)){
			try{				
				adminBean.altaTramite(jsonTramite);
				return Response.ok("OK").build();
			}catch(Exception e){
				return Response.ok("ERROR: " + e.getMessage()).build();
			}
		}else{
			logger.error("Permisos insuficientes - " + ADMINISTRADOR + " - params: user-rol:" + userRol + ", JSONTramite: " + jsonTramite);
			return Response.ok("ERROR: No tiene permisos suficientes.").build();
		}
		
	}
	
	@DELETE
	@Path("/tramite")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response bajaTramite(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user, JSONTramite jsonTramite){	
	
		if(userRol.equals(ADMINISTRADOR)){
			try{
				adminBean.bajaTramite(jsonTramite);
				return Response.ok("OK").build();
			}catch(Exception e){
				return Response.ok("ERROR: " + e.getMessage()).build();
			}
		}else{
			logger.error("Permisos insuficientes - " + ADMINISTRADOR + " - params: user-rol:"+userRol+" JSONTramite: "+jsonTramite);
			return Response.ok("ERROR: No tiene permisos suficientes.").build();
		}
	}
	
	@PUT
	@Path("/tramite")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response modificarTramite(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user, JSONTramite jsonTramite){	
		if(userRol.equals(ADMINISTRADOR)){
			try{
				adminBean.modificarTramite(jsonTramite);
				return Response.ok("OK").build();
			}catch(Exception e){
				return Response.ok("ERROR: " + e.getMessage()).build();
			}
		}else{
			logger.error("Permisos insuficientes - " + ADMINISTRADOR + " - params: user-rol:"+userRol+" JSONTramite: "+jsonTramite);
			return Response.ok("ERROR: No tiene permisos suficientes.").build();
		}
		
	}
	
	@GET
	@Path("/listarTramites")
    @Produces(MediaType.APPLICATION_JSON)

    public Response listarTramites(@HeaderParam("user-rol") String userRol, @HeaderParam("user") String user, @QueryParam("sectorId") String sectorId ){
		if(userRol.equals(RESPONSABLE_SECTOR)){
		
			try{
				if (   !( sectorId == null || sectorId.isEmpty() ) ){
	// lista todos los tramites mennos los del sector pasado por parametro
					//DUDA NO SERIA DE LOS QUE TIRENE PERMISO MENOS EL PASADO POR PARAMETRO
	  				List <JSONTramite> listaTramiteSector = adminBean.listarTramitesSector(sectorId);
	  				List <JSONTramite> listaTramite =  adminBean.listarTramites();
	  				listaTramite.removeAll(listaTramiteSector);
	  				return Response.ok(listaTramite).build();
		  			
				}else{
			//listo los tramites de los sectores que el tiene premiso en gafu
	  				List<BusinessSectorRol> respde =  gafu.obtenerSectorRolesUsuario(user,ResponsableSectorGAFU);
	  				List<JSONTramite> listaTramite = new ArrayList<JSONTramite>();
	  				for  (BusinessSectorRol as : respde){
	  					List<JSONTramite> listaTramitesSector = adminBean.listarTramitesSector(as.getSectorId());
	  					List<JSONTramite> diferencia = new ArrayList<JSONTramite>();
	  	  				diferencia.addAll(listaTramitesSector);
	  	  				diferencia.retainAll(listaTramite);
	  	  				listaTramitesSector.removeAll(diferencia);
	  					listaTramite.addAll( listaTramitesSector );

	  				}
	  				return Response.ok(listaTramite).build();
				}
			}catch (Exception e) {
				return Response.ok("ERROR: " + e.getMessage()).build();
			}
		}else{
			if(userRol.equals(ADMINISTRADOR)){
				//si es admin lista todos los tramites 
				try{
	  				return Response.ok(adminBean.listarTramites()).build();
				}catch (Exception e) {
					return Response.ok("ERROR: " + e.getMessage()).build();
				}
			}else{
				logger.error("Permisos insuficientes - " + RESPONSABLE_SECTOR + "-" + ADMINISTRADOR +" - params: user-rol:"+userRol+" sectorId: "+sectorId);
				return Response.ok("ERROR: No tiene permisos suficientes.").build();
			}
		}
    }
	
	
	
	/************************** SECTORES *****************************/
	
	@GET
	@Path("/listarSectores")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarSectores(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user) {

		
		if(userRol.equals(ADMINISTRADOR)){
			//listo todos los sectores
			try {
				return Response.ok(adminBean.listarSectores()).build();
			}catch(Exception e){
				return Response.ok("ERROR: " + e.getMessage()).build();
			}
		}else{
			//listo todos los sectores para los cuales tiene permisos EN GAFU
			if (userRol.equals(RESPONSABLE_SECTOR)){
				try {
					List<BusinessSectorRol> respde =  gafu.obtenerSectorRolesUsuario(user,ResponsableSectorGAFU);	  			
	  				return Response.ok(adminBean.listarSectores(respde)).build();
				}catch(Exception e){
					return Response.ok("ERROR: " + e.getMessage()).build();
				}
			}else{
				
				if (userRol.equals(CONSULTOR)){
					try {
						List<BusinessSectorRol> respde =  gafu.obtenerSectorRolesUsuario(user,ConsultorGAFU);
		  			
						return Response.ok(adminBean.listarSectores(respde)).build();
					}catch(Exception e){
						return Response.ok("ERROR: " + e.getMessage()).build();
					}
				}else{
					logger.error("Permisos insuficientes - " + RESPONSABLE_SECTOR + "/" + ADMINISTRADOR +" - params: user-rol:"+userRol);
					return Response.ok("ERROR: No tiene permisos suficientes.").build();
				}
			}
		}
    }
	

	@PUT
	@Path("/reinicializarColas")
    public Response reinicializarColas(@HeaderParam("secret-command") String secretCommand){
		if(secretCommand.equals("MacocoReinicializar")){
			try {
				adminBean.reinicializarColas();
				return Response.ok("OK").build();
			}catch(Exception e){
				return Response.ok("ERROR: " + e.getMessage()).build();
			}
		}else{
			return Response.ok("ERROR: No tiene permisos suficientes.").build();
		}
	}			
	
	@POST
	@Path("/recuperarColas")
	public Response recuperarCola(@HeaderParam("secret-command") String secretCommand){
		if(secretCommand.equals("MacocoRecuperar")){
			try {
				adminBean.reinicializarColas();
				adminBean.recuperarColas();
				return Response.ok("OK").build();
			}catch(Exception e){
				return Response.ok("ERROR: " + e.getMessage()).build();
			}
		}else{
			return Response.ok("ERROR: No tiene permisos suficientes.").build();
		}
	}
	
	@PUT
	@Path("/actualizarGAFU")
    public Response actualizarGAFU(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user) {
		if(userRol.equals(ADMINISTRADOR)){
			try {
				adminBean.actualizarGAFU();
				return Response.ok("OK").build();
			}catch(Exception e){
				return Response.ok("ERROR: " + e.getMessage()).build();
			}
		}else{
			logger.error("Permisos insuficientes - " + ADMINISTRADOR + " - params: user-rol:"+userRol);
			return Response.ok("ERROR: No tiene permisos suficientes.").build();
		}
    }

	/****************************** Alta, Baja & Modificacion de DISPLAY ******************************/


	@POST
	@Path("/display")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response altaDisplay(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user, JSONDisplay display){
		if ( (userRol.equals(RESPONSABLE_SECTOR)) || (userRol.equals(ADMINISTRADOR)) ) {
			try{
				adminBean.altaDisplay(display.getIdDisplay());
				return Response.ok("OK").build();
			}catch(Exception e){
				return Response.ok("ERROR: " + e.getMessage()).build();
			}
		}else{
			logger.error("Permisos insuficientes - " + RESPONSABLE_SECTOR + "-" + ADMINISTRADOR + " - params: user-rol:"+userRol+" JSONDisplay: "+display);
			return Response.ok("ERROR: No tiene permisos suficientes.").build();
		}
	}
	
	@DELETE
	@Path("/display")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response bajaDisplay(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user, JSONDisplay display){	

		if ( (userRol.equals(RESPONSABLE_SECTOR)) || (userRol.equals(ADMINISTRADOR)) ){
			try{
				adminBean.bajaDisplay(display.getIdDisplay());
				return Response.ok("OK").build();
			}catch(Exception e){
				return Response.ok("ERROR: " + e.getMessage()).build();
			}
		}else{
			logger.error("Permisos insuficientes - " + RESPONSABLE_SECTOR + " - params: user-rol:"+userRol+" JSONDisplay: "+display);
			return Response.ok("ERROR: No tiene permisos suficientes.").build();
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
	public Response listarDisplay(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user,@QueryParam("sectorId") String sectorId) {
		if ( userRol.equals(ADMINISTRADOR) ){
			try{
				//return adminBean.listarDisplays(null);	
				return Response.ok(adminBean.listarDisplays(null)).build();
			}catch(Exception e){
				return Response.ok("ERROR: " + e.getMessage()).build();
			}
		}else{
			if (userRol.equals(RESPONSABLE_SECTOR)){
				try{
					if (   !( sectorId == null || sectorId.isEmpty() ) ){

		  				List <JSONDisplay> listaDisplaySector = adminBean.listarDisplays(sectorId);
		  				List <JSONDisplay> listaDisplay=  adminBean.listarDisplays(null);
		  				listaDisplay.removeAll(listaDisplaySector);
		  				//return listaDisplay;
		  				return Response.ok(listaDisplay).build();
					}else{

		  				List<BusinessSectorRol> respde =  gafu.obtenerSectorRolesUsuario(user,ResponsableSectorGAFU);
		  				List<JSONDisplay> listaDispaly = new ArrayList<JSONDisplay>();
		  				for  (BusinessSectorRol as : respde){
		  					List<JSONDisplay> listaDisplaySector = adminBean.listarDisplays(as.getSectorId());
		  					List<JSONDisplay> diferencia = new ArrayList<JSONDisplay>();
		  	  				diferencia.addAll(listaDisplaySector);
		  	  				diferencia.retainAll(listaDispaly);
		  	  				listaDisplaySector.removeAll(diferencia);
		  	  				
		  					listaDispaly.addAll( listaDisplaySector);

		  				}
		  				//return listaDispaly;
		  				return Response.ok(listaDispaly).build();
					}
				}catch(Exception e){
					return Response.ok("ERROR: " + e.getMessage()).build();
	  			}
			}else{
				logger.error("Permisos insuficientes - " + RESPONSABLE_SECTOR + "-" + ADMINISTRADOR + " - params: user-rol:"+userRol+" sectorId: "+sectorId);
				//return Response.ok("ERROR: No tiene permisos suficientes.").build();
				return Response.ok("ERROR: No tiene permisos suficientes.").build();
			}
		}
    }
	
	/************************** ASIGNACIONES *****************************/
	
	
	@POST
  	@Path("/asignarTramiteSector") /*ok*/
	
  	@Consumes(MediaType.APPLICATION_JSON)
  	public Response asignarTramiteSector(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user, JSONTramiteSector tramiteSector){	
  		if(userRol.equals(RESPONSABLE_SECTOR)){
  			try{
  				adminBean.asignarTramiteSector(tramiteSector);
  				return Response.ok("OK").build();
  			}catch(Exception e){
  				return Response.ok("ERROR: " + e.getMessage()).build();
  			}
  		}else{
			logger.error("Permisos insuficientes - " + RESPONSABLE_SECTOR + " - params: user-rol:"+userRol+" JSONTramiteSector: "+tramiteSector);
  			return Response.ok("ERROR: No tiene permisos suficientes.").build();
  		}
  		
  	}
	
	@POST
	@Path("/asignarTramitePuesto")/*ok*/
	@Consumes(MediaType.APPLICATION_JSON)
	public Response asignarTramitePuesto(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user, JSONPuestoTramite puestoTramite){	
		if(userRol.equals(RESPONSABLE_SECTOR)){
			try{
				adminBean.asignarTramitePuesto(puestoTramite);
				return Response.ok("OK").build();
			}catch(Exception e){
				return Response.ok("ERROR: " + e.getMessage()).build();
			}
		}else{
			logger.error("Permisos insuficientes - " + RESPONSABLE_SECTOR + " - params: user-rol:"+userRol+" JSONPuestoTramite: "+puestoTramite);
			return Response.ok("ERROR: No tiene permisos suficientes.").build();
		}
	}
	
	
	@POST
  	@Path("/asignarPuestoSector")/*ok*/
  	@Consumes(MediaType.APPLICATION_JSON)
  	public Response asignarPuestoSector(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user, 
  			JSONPuestoSector puestoSector){	
  		if(userRol.equals(RESPONSABLE_SECTOR)){
  			try{
  				adminBean.asignarPuestoSector(puestoSector);
  				return Response.ok("OK").build();
  			}catch(Exception e){
  				return Response.ok("ERROR: " + e.getMessage()).build();
  			}
  		}else{
			logger.error("Permisos insuficientes - " + RESPONSABLE_SECTOR + " - params: user-rol:"+userRol+" JSONPuestoSector: "+puestoSector);
  			return Response.ok("ERROR: No tiene permisos suficientes.").build();
  		}
  		
  	}	

	@POST
	@Path("/asignarSectorDisplay")/*ok*/
    @Produces(MediaType.APPLICATION_JSON)
    public Response asignarSectorDisplay(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user, JSONSectorDisplay secDisp) {
		if(userRol.equals(ADMINISTRADOR)){
			try{
				adminBean.asignarSectorDisplay(secDisp);
				return Response.ok("OK").build();
			}catch(Exception e){
				return Response.ok("ERROR: " + e.getMessage()).build();
			}
		}else{
			logger.error("Permisos insuficientes - " + ADMINISTRADOR + " - params: user-rol:"+userRol+" JSONSectorDisplay: "+secDisp);
			return Response.ok("ERROR: No tiene permisos suficientes.").build();
		}
		
    }
	
	/************************** DESASIGNACIONES *****************************/
	
	@DELETE
	@Path("/desasignarSectorDisplay")
    @Produces(MediaType.APPLICATION_JSON)
    public Response desasignarSectorDisplay(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user, JSONSectorDisplay secDisp) {
		if(userRol.equals(ADMINISTRADOR)){
			try{
				adminBean.desasignarSectorDisplay(secDisp);
				return Response.ok("OK").build();
			}catch(Exception e){
				return Response.ok("ERROR: " + e.getMessage()).build();
			}
		}else{
			logger.error("Permisos insuficientes - " + ADMINISTRADOR + " - params: user-rol:"+userRol+" JSONSectorDisplay: "+secDisp);
			return Response.ok("ERROR: No tiene permisos suficientes.").build();
		}
		
    }
	
	@DELETE
  	@Path("/desasignarTramiteSector")	
  	@Consumes(MediaType.APPLICATION_JSON)
  	public Response desasignarTramiteSector(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user, JSONTramiteSector tramiteSector){	
  		if(userRol.equals(RESPONSABLE_SECTOR)){
  			try{
  				adminBean.desasignarTramiteSector(tramiteSector);
  				return Response.ok("OK").build();
  			}catch(Exception e){
  				return Response.ok("ERROR: " + e.getMessage()).build();
  			}
  		}else{
  			logger.error("Permisos insuficientes - " + RESPONSABLE_SECTOR + " - params: user-rol:" + userRol + ", user: " + user + ", JSONTramiteSector: " + tramiteSector);
  			return Response.ok("ERROR: No tiene permisos suficientes.").build();
  		}
  		
  	}
	
	@DELETE
  	@Path("/desasignarPuestoSector")/*ok*/
  	@Consumes(MediaType.APPLICATION_JSON)
  	public Response desasignarPuestoSector(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user , JSONPuestoSector puestoSector){	
  		if(userRol.equals(RESPONSABLE_SECTOR)){
  			try{
  				adminBean.desasignarPuestoSector(puestoSector);
  				return Response.ok("OK").build();
  			}catch(Exception e){
  				return Response.ok("ERROR: " + e.getMessage()).build();
  			}
  		}else{
  			logger.error("Permisos insuficientes - " + RESPONSABLE_SECTOR + " - params: user-rol:" + userRol + ", user: " + user + ", JSONPuestoSector: " + puestoSector);
  			return Response.ok("ERROR: No tiene permisos suficientes.").build();
  		}
  		
  	}
	
	@DELETE
	@Path("/desasignarTramitePuesto")/*ok*/
	@Consumes(MediaType.APPLICATION_JSON)
	public Response desasignarTramitePuesto(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user, JSONPuestoTramite puestoTramite){	
		if(userRol.equals(RESPONSABLE_SECTOR)){
			try{
				adminBean.desasignarTramitePuesto(puestoTramite);
				return Response.ok("OK").build();
			}catch(Exception e){
				return Response.ok("ERROR: " + e.getMessage()).build();
			}
		}else{
			logger.error("Permisos insuficientes - " + RESPONSABLE_SECTOR + " - params: user-rol:" + userRol + ", user: " + user + ", JSONPuestoTramite: " + puestoTramite);
			return Response.ok("ERROR: No tiene permisos suficientes.").build();
		}
	}
	
	/************************** LISTAR POR *****************************/
	

  	  @GET
  	  @Path("/listarPuestosSector")
      @Produces(MediaType.APPLICATION_JSON)
  	  public Response listarPuestosSector(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user, 
    		  @QueryParam("sectorId") String idSector) {
  		if(userRol.equals(RESPONSABLE_SECTOR)){
  			try{
  				List<JSONPuesto> listaPuestos = adminBean.listarPuestos(idSector);
  				return Response.ok(listaPuestos).build();
  				
  			}catch(Exception e){
  				return Response.ok("ERROR: " + e.getMessage()).build();
  			}
  		}else{
  			logger.error("Permisos insuficientes - " + RESPONSABLE_SECTOR + " - params: user-rol:" + userRol + ", user: " + user + ", idSector: " + idSector);
  			return Response.ok("ERROR: No tiene permisos suficientes.").build();
  		}
      }
  	
  	  @GET
  	  @Path("/listarTramitesSector")
      @Produces(MediaType.APPLICATION_JSON)
     public Response listarTramitesSector(@HeaderParam("user-rol") String userRol,
    		  @HeaderParam("user") String user, 
    		  @QueryParam("sectorId") String idSector) {
  		if(userRol.equals(RESPONSABLE_SECTOR) || userRol.equals(OPERADOR) || userRol.equals(OPERADORSR)){
  			try{
  				List<JSONTramite> listatrm = adminBean.listarTramitesSector(idSector);
  				return Response.ok(listatrm).build();
  			}catch(Exception e){
  				return Response.ok("ERROR: " + e.getMessage()).build();
  			}
  		}else{
  			logger.error("Permisos insuficientes - " + RESPONSABLE_SECTOR + "/" + OPERADOR + "/" + OPERADORSR + " - params: user-rol:" + userRol + ", user: " + user + ", idSector: " + idSector);
  			return Response.ok("ERROR: No tiene permisos suficientes.").build();
  		}
      }
  	  
  	@GET
	@Path("/listarTramitesPuesto")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarTramitesPuesto(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user,
    		@QueryParam("nombreMaquina") String nombreMaquina ) {
		System.out.println("entro a listar");
		if(userRol.equals(RESPONSABLE_SECTOR)){
			try{
				return Response.ok(adminBean.listarTramitesPuesto(nombreMaquina)).build();
			}
			catch(Exception e){
				return Response.ok("ERROR: " + e.getMessage()).build();
			}
		}else{
			logger.error("Permisos insuficientes - " + RESPONSABLE_SECTOR + " - params: user-rol:" + userRol + ", nombreMaquina: " + nombreMaquina);
			return Response.ok("ERROR: No tiene permisos suficientes.").build();
		}
    }
  	@GET
	@Path("/listarTramitesPosibles")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarTramitesPosibles(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user,@QueryParam("nombreMaquina") String nombreMaquina ) {
		///System.out.println("entro a listar");
		if(userRol.equals(RESPONSABLE_SECTOR)){
			try{
				return Response.ok(adminBean.listarTramitesPosibles(nombreMaquina)).build();				
			}
			catch(Exception e){
				return Response.ok("ERROR: " + e.getMessage()).build();
			}
		}else{
			logger.error("Permisos insuficientes - " + RESPONSABLE_SECTOR + " - params: user-rol:" + userRol + ", nombreMaquina: " + nombreMaquina);
			return Response.ok("ERROR: No tiene permisos suficientes.").build();
		}
    }
  	
	@GET
	@Path("/listarDisplaysSector")
	@Produces(MediaType.APPLICATION_JSON)
	//este metodo retorna los display de un sector
	public Response listarDisplaySector(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user, @QueryParam("sectorId") String idSector) {
		if ( (userRol.equals(RESPONSABLE_SECTOR)) || (userRol.equals(ADMINISTRADOR)) ){
			try{
				return Response.ok(adminBean.listarDisplays(idSector)).build();
			}catch(Exception e){
				return Response.ok("ERROR: " + e.getMessage()).build();
			}
		}else{
			logger.error("Permisos insuficientes - " + RESPONSABLE_SECTOR + "/" + ADMINISTRADOR + " - params: user-rol:" + userRol + ", idSector: " + idSector);
			return Response.ok("ERROR: No tiene permisos suficientes.").build();
		}
	}
	
	
	/************ Borrar todo el sistema ***************/
	
	@DELETE
	@Path("/borrarRows")
	public Response borrarRows(@HeaderParam("secret-command") String secretCommand) {
		if (secretCommand.equals( "MacocoBorrador")){
			try{
				adminBean.borrarTodoElSistema();
				return Response.ok("OK").build();
			}catch(Exception e){
				return Response.ok("ERROR: " + e.getMessage()).build();
			}
		}else{
			logger.error("Permisos insuficientes - params: secretCommand:" + secretCommand);
			return Response.ok("ERROR: No tiene permisos suficientes.").build();
		}
    }
	
}
