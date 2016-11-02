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

import org.jboss.logging.Logger;
import org.jboss.resteasy.spi.InternalServerErrorException;
import org.jboss.resteasy.spi.UnauthorizedException;

import com.sarp.beans.AdminBean;
import com.sarp.beans.GafuBean;
import com.sarp.classes.BusinessSectorRol;
import com.sarp.json.modeler.JSONDisplay;
import com.sarp.json.modeler.JSONPuesto;
import com.sarp.json.modeler.JSONPuestoSector;
import com.sarp.json.modeler.JSONPuestoTramite;
import com.sarp.json.modeler.JSONSector;
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
	
	private String RESPONSABLE_SECTOR = UtilService.getStringProperty("RESPONSABLE_SECTOR");
	private String ADMINISTRADOR = UtilService.getStringProperty("ADMINISTRADOR");
	private String OPERADOR = UtilService.getStringProperty("OPERADOR");
	private String OPERADORSR = UtilService.getStringProperty("OPERADOR_SENIOR");
	

	/************ABM PUESTO ***************/	
  	@POST
  	@Path("/puesto")
  	@Consumes(MediaType.APPLICATION_JSON)
  	public String altaPuesto(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user, JSONPuesto puesto){
  		if(userRol.equals(RESPONSABLE_SECTOR)){
  			try{
  				adminBean.altaPuesto(puesto);
  				return "OK";
  			}catch(Exception e){
				logger.error("POST puesto params: user-rol:"+userRol+" JSONPuesto: "+puesto);
  				throw new InternalServerErrorException(e);
  			}
  		}else{
			logger.error("Permisos insuficientes - " + RESPONSABLE_SECTOR + " - params: user-rol:"+userRol+" JSONPuesto: "+puesto);
  			throw new UnauthorizedException("No tiene permisos suficientes.");
  		}
  	}
  	
  	@DELETE
  	@Path("/puesto")
  	@Consumes(MediaType.APPLICATION_JSON)
  	public String bajaPuesto(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user, JSONPuesto puesto){	
  		if(userRol.equals(RESPONSABLE_SECTOR)){
  			try{
  				adminBean.bajaPuesto(puesto);
  				return "OK";
  			}catch(Exception e){
				logger.error("DELETE puesto params: user-rol:"+userRol+" JSONPuesto: "+puesto);
  				throw new InternalServerErrorException(e);
  			}
  		}else{
			logger.error("Permisos insuficientes - " + RESPONSABLE_SECTOR + " - params: user-rol:"+userRol+" JSONPuesto: "+puesto);
  			throw new UnauthorizedException("No tiene permisos suficientes.");
  		}
  	}
  	
  	@PUT
  	@Path("/puesto")
  	@Consumes(MediaType.APPLICATION_JSON)
  	public String modificarPuesto(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user, JSONPuesto puesto){	
  		if(userRol.equals(RESPONSABLE_SECTOR)){
  			try{
  				adminBean.modificarPuesto(puesto);
  				return "OK";
  			}catch(Exception e){
  				throw new InternalServerErrorException(e);
  			}
  		}else{
			logger.error("Permisos insuficientes - " + RESPONSABLE_SECTOR + " - params: user-rol:"+userRol+" JSONPuesto: "+puesto);
  			throw new UnauthorizedException("No tiene permisos suficientes.");
  		}
  		
  	}
  	
  	
  	@GET
  	@Path("/listarPuestos")
      @Produces(MediaType.APPLICATION_JSON)
      public List<JSONPuesto> listarPuestos(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user,@QueryParam("sectorId") String sectorId) {			  		
  		if(userRol.equals(RESPONSABLE_SECTOR)){
	  		if (   !( sectorId == null || sectorId.isEmpty() ) ){

	  			//si me pasan sector quiero todos menos los del sector que pasan 
	  			try{
	  				List <JSONPuesto> listaPuestosSector = adminBean.listarPuestos(sectorId);
	  				List <JSONPuesto> listaPuestos=  adminBean.listarPuestos(null );

	  				listaPuestos.removeAll(listaPuestosSector);
	  				return listaPuestos;
	  			}catch(Exception e){
	  				throw new InternalServerErrorException("Error al listar Puestos: " + e.getMessage());
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
  	  				return listaPuestos;
  	  			}catch(Exception e){
  	  				throw new InternalServerErrorException("Error al listar Puestos: " + e.getMessage());
  	  			}
  			}
  		}else{
			logger.error("Permisos insuficientes - " + RESPONSABLE_SECTOR + " - params: user-rol:"+userRol+" sectorId: "+sectorId);
  			throw new UnauthorizedException("No tiene permisos suficientes.");
  		}
  			
      }
 	
  	
	/******* Alta, Baja & Modificacion de Tramites *******/
	
	@POST
	@Path("/tramite")
	@Consumes(MediaType.APPLICATION_JSON)
	public String altaTramite(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user, JSONTramite jsonTramite){
		if(userRol.equals(ADMINISTRADOR)){
			try{				
				adminBean.altaTramite(jsonTramite);
				return "OK";
			}catch(Exception e){
				throw new InternalServerErrorException("Error al crear el Tramite: " + e.getMessage());
			}
		}else{
			logger.error("Permisos insuficientes - " + ADMINISTRADOR + " - params: user-rol:"+userRol+" JSONTramite: "+jsonTramite);
			throw new UnauthorizedException("No tiene permisos para realizar esta accion.");
		}
		
	}
	
	@DELETE
	@Path("/tramite")
	@Consumes(MediaType.APPLICATION_JSON)
	public String bajaTramite(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user, JSONTramite jsonTramite){	
	
		if(userRol.equals(ADMINISTRADOR)){
			try{
				adminBean.bajaTramite(jsonTramite);
				return "OK";
			}catch(Exception e){
				throw new InternalServerErrorException("Error al eliminar el Tramite: " + e.getMessage());
			}
		}else{
			logger.error("Permisos insuficientes - " + ADMINISTRADOR + " - params: user-rol:"+userRol+" JSONTramite: "+jsonTramite);
			throw new UnauthorizedException("No tiene permisos para realizar esta accion.");
		}
	}
	
	@PUT
	@Path("/tramite")
	@Consumes(MediaType.APPLICATION_JSON)
	public String modificarTramite(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user, JSONTramite jsonTramite){	
		if(userRol.equals(ADMINISTRADOR)){
			try{
				adminBean.modificarTramite(jsonTramite);
				return "OK";
			}catch(Exception e){
				throw new InternalServerErrorException("Error al modificar el Tramite: " + e.getMessage());
			}
		}else{
			logger.error("Permisos insuficientes - " + ADMINISTRADOR + " - params: user-rol:"+userRol+" JSONTramite: "+jsonTramite);
			throw new UnauthorizedException("No tiene permisos para realizar esta accion.");
		}
		
	}
	
	@GET
	@Path("/listarTramites")
    @Produces(MediaType.APPLICATION_JSON)

    public List<JSONTramite> listarTramites(@HeaderParam("user-rol") String userRol, @HeaderParam("user") String user, @QueryParam("sectorId") String sectorId ){
		if(userRol.equals(RESPONSABLE_SECTOR)){
		
			try{
				if (   !( sectorId == null || sectorId.isEmpty() ) ){
	// lista todos los tramites mennos los del sector pasado por parametro
					//DUDA NO SERIA DE LOS QUE TIRENE PERMISO MENOS EL PASADO POR PARAMETRO
	  				List <JSONTramite> listaTramiteSector = adminBean.listarTramitesSector(sectorId);
	  				List <JSONTramite> listaTramite =  adminBean.listarTramites();
	  				listaTramite.removeAll(listaTramiteSector);
	  				return listaTramite;
		  			
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
	  				return listaTramite;
				}
			}catch (Exception e) {
				throw new InternalServerErrorException("Error al listar los Tramite: " + e.getMessage());
			}
		}else{
			if(userRol.equals(ADMINISTRADOR)){
				//si es admin lista todos los tramites 
				try{
	  				return adminBean.listarTramites();
				}catch (Exception e) {
					throw new InternalServerErrorException("Error al listar los Tramite: " + e.getMessage());
				}
			}else{
				logger.error("Permisos insuficientes - " + RESPONSABLE_SECTOR + "-" + ADMINISTRADOR +" - params: user-rol:"+userRol+" sectorId: "+sectorId);
				throw new UnauthorizedException("No tiene permisos suficientes.");
			}
		}
    }
	
	
	
	/************************** SECTORES *****************************/
	
	@GET
	@Path("/listarSectores")
    @Produces(MediaType.APPLICATION_JSON)
    public List<JSONSector> listarSectores(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user) {

		
		if(userRol.equals(ADMINISTRADOR)){
			//listo todos los sectores
			try {
				return adminBean.listarSectores();
			}catch(Exception e){
				throw new InternalServerErrorException("Error al listar Sectores: " + e.getMessage());
			}
		}else{
			//listo todos los sectores para los cuales tiene permisos EN GAFU
			if (userRol.equals(RESPONSABLE_SECTOR)){
				try {
				List<BusinessSectorRol> respde =  gafu.obtenerSectorRolesUsuario(user,ResponsableSectorGAFU);
  			
  				return adminBean.listarSectores();
				}catch(Exception e){
					throw new InternalServerErrorException("Error al listar Sectores: " + e.getMessage());
				}
			}else{
				logger.error("Permisos insuficientes - " + RESPONSABLE_SECTOR + "-" + ADMINISTRADOR +" - params: user-rol:"+userRol);
				throw new UnauthorizedException("No tiene permisos suficientes.");
			}
		}
    }
	

	@PUT
	@Path("/reinicializarColas")
    public String reinicializarColas(@HeaderParam("secret-command") String secretCommand) throws Exception {
		if(secretCommand.equals("MacocoReinicializar")){
			try {
				adminBean.reinicializarColas();
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
				adminBean.reinicializarColas();
				adminBean.recuperarColas();
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
		if(userRol.equals(ADMINISTRADOR)){
			try {
				adminBean.actualizarGAFU();
				return "OK";
			}catch(Exception e){
				throw new InternalServerErrorException("Error al actualizar GAFU" + e.getMessage());
			}
		}else{
			logger.error("Permisos insuficientes - " + ADMINISTRADOR + " - params: user-rol:"+userRol);
			throw new UnauthorizedException("No tiene permisos suficientes.");
		}
    }

	/****************************** Alta, Baja & Modificacion de DISPLAY ******************************/


	@POST
	@Path("/display")
	@Consumes(MediaType.APPLICATION_JSON)
	public String altaDisplay(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user, JSONDisplay display){
		if ( (userRol.equals(RESPONSABLE_SECTOR)) || (userRol.equals(ADMINISTRADOR)) ) {
			try{
				adminBean.altaDisplay(display.getIdDisplay());
				return "OK";
			}catch(Exception e){
				throw new InternalServerErrorException("Error al crear el Display: " + e.getMessage());
			}
		}else{
			logger.error("Permisos insuficientes - " + RESPONSABLE_SECTOR + "-" + ADMINISTRADOR + " - params: user-rol:"+userRol+" JSONDisplay: "+display);
			throw new UnauthorizedException("No tiene permisos suficientes.");
		}
	}
	
	@DELETE
	@Path("/display")
	@Consumes(MediaType.APPLICATION_JSON)
	public String bajaDisplay(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user, JSONDisplay display){	

		if ( (userRol.equals(RESPONSABLE_SECTOR)) || (userRol.equals(ADMINISTRADOR)) ){
			try{
				adminBean.bajaDisplay(display.getIdDisplay());
				return "OK";
			}catch(Exception e){
				throw new InternalServerErrorException("Error al dar de baja el Display: " + e.getMessage());
			}
		}else{
			logger.error("Permisos insuficientes - " + RESPONSABLE_SECTOR + " - params: user-rol:"+userRol+" JSONDisplay: "+display);
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
		if ( userRol.equals(ADMINISTRADOR) ){
			try{
				return adminBean.listarDisplays(null);	
			}catch(Exception e){
				throw new InternalServerErrorException("Error al listar Display: " + e.getMessage());
			}
		}else{
			if (userRol.equals(RESPONSABLE_SECTOR)){
				try{
					if (   !( sectorId == null || sectorId.isEmpty() ) ){

		  				List <JSONDisplay> listaDisplaySector = adminBean.listarDisplays(sectorId);
		  				List <JSONDisplay> listaDisplay=  adminBean.listarDisplays(null);
		  				listaDisplay.removeAll(listaDisplaySector);
		  				return listaDisplay;
			  			
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
		  				return listaDispaly;
					}
				}catch(Exception e){
	  				throw new InternalServerErrorException("Error al listar Puestos: " + e.getMessage());
	  			}
			}else{
				logger.error("Permisos insuficientes - " + RESPONSABLE_SECTOR + "-" + ADMINISTRADOR + " - params: user-rol:"+userRol+" sectorId: "+sectorId);
				throw new UnauthorizedException("No tiene permisos suficientes.");
			}
		}
    }
	
	/************************** ASIGNACIONES *****************************/
	
	
	@POST
  	@Path("/asignarTramiteSector") /*ok*/
	
  	@Consumes(MediaType.APPLICATION_JSON)
  	public String asignarTramiteSector(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user, JSONTramiteSector tramiteSector){	
  		if(userRol.equals(RESPONSABLE_SECTOR)){
  			try{
  				adminBean.asignarTramiteSector(tramiteSector);
  				return "OK";
  			}catch(Exception e){
  				throw new InternalServerErrorException("Error al asignar puesto a tramite: " + e.getMessage());
  			}
  		}else{
			logger.error("Permisos insuficientes - " + RESPONSABLE_SECTOR + " - params: user-rol:"+userRol+" JSONTramiteSector: "+tramiteSector);
  			throw new UnauthorizedException("No tiene permisos suficientes.");
  		}
  		
  	}
	
	@POST
	@Path("/asignarTramitePuesto")/*ok*/
	@Consumes(MediaType.APPLICATION_JSON)
	public String asignarTramitePuesto(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user, JSONPuestoTramite puestoTramite){	
		if(userRol.equals(RESPONSABLE_SECTOR)){
			try{
				adminBean.asignarTramitePuesto(puestoTramite);
				return "OK";
			}catch(Exception e){
				throw new InternalServerErrorException("Error al asignar el Tramite al puesto: " + e.getMessage());
			}
		}else{
			logger.error("Permisos insuficientes - " + RESPONSABLE_SECTOR + " - params: user-rol:"+userRol+" JSONPuestoTramite: "+puestoTramite);
			throw new UnauthorizedException("No tiene permisos para realizar esta accion.");
		}
	}
	
	
	@POST
  	@Path("/asignarPuestoSector")/*ok*/
  	@Consumes(MediaType.APPLICATION_JSON)
  	public String asignarPuestoSector(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user, 
  			JSONPuestoSector puestoSector){	
  		if(userRol.equals(RESPONSABLE_SECTOR)){
  			try{
  				adminBean.asignarPuestoSector(puestoSector);
  				return "OK";
  			}catch(Exception e){
  				throw new InternalServerErrorException("Error al Asignar Puesto a Sector: " + e.getMessage());
  			}
  		}else{
			logger.error("Permisos insuficientes - " + RESPONSABLE_SECTOR + " - params: user-rol:"+userRol+" JSONPuestoSector: "+puestoSector);
  			throw new UnauthorizedException("No tiene permisos suficientes.");
  		}
  		
  	}	

	@POST
	@Path("/asignarSectorDisplay")/*ok*/
    @Produces(MediaType.APPLICATION_JSON)
    public String asignarSectorDisplay(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user, JSONSectorDisplay secDisp) {
		if(userRol.equals(ADMINISTRADOR)){
			try{
				adminBean.asignarSectorDisplay(secDisp);
				return "OK";
			}catch(Exception e){
				throw new InternalServerErrorException("Error al asignar Display a Sector: " + e.getMessage());
			}
		}else{
			logger.error("Permisos insuficientes - " + ADMINISTRADOR + " - params: user-rol:"+userRol+" JSONSectorDisplay: "+secDisp);
			throw new UnauthorizedException("No tiene permisos para realizar esta accion.");
		}
		
    }
	
	/************************** DESASIGNACIONES *****************************/
	
	@DELETE
	@Path("/desasignarSectorDisplay")
    @Produces(MediaType.APPLICATION_JSON)
    public String desasignarSectorDisplay(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user, JSONSectorDisplay secDisp) {
		if(userRol.equals(ADMINISTRADOR)){
			try{
				adminBean.desasignarSectorDisplay(secDisp);
				return "OK";
			}catch(Exception e){
				throw new InternalServerErrorException("Error al desasignar Display a Sector: " + e.getMessage());
			}
		}else{
			logger.error("Permisos insuficientes - " + ADMINISTRADOR + " - params: user-rol:"+userRol+" JSONSectorDisplay: "+secDisp);
			throw new UnauthorizedException("No tiene permisos para realizar esta accion.");
		}
		
    }
	
	@DELETE
  	@Path("/desasignarTramiteSector")
	
  	@Consumes(MediaType.APPLICATION_JSON)
  	public String desasignarTramiteSector(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user, JSONTramiteSector tramiteSector){	
  		if(userRol.equals(RESPONSABLE_SECTOR)){
  			try{
  				adminBean.desasignarTramiteSector(tramiteSector);
  				return "OK";
  			}catch(Exception e){
  				throw new InternalServerErrorException("Error al desasignar puesto a tramite: " + e.getMessage());
  			}
  		}else{
			logger.error("Permisos insuficientes - " + RESPONSABLE_SECTOR + " - params: user-rol:"+userRol+" JSONTramiteSector: "+tramiteSector);
  			throw new UnauthorizedException("No tiene permisos suficientes.");
  		}
  		
  	}
	
	@DELETE
  	@Path("/desasignarPuestoSector")/*ok*/
  	@Consumes(MediaType.APPLICATION_JSON)
  	public String desasignarPuestoSector(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user , JSONPuestoSector puestoSector){	
  		if(userRol.equals(RESPONSABLE_SECTOR)){
  			try{
  				adminBean.desasignarPuestoSector(puestoSector);
  				return "OK";
  			}catch(Exception e){
  				throw new InternalServerErrorException("Error al Asignar Puesto a Sector: " + e.getMessage());
  			}
  		}else{
			logger.error("Permisos insuficientes - " + RESPONSABLE_SECTOR + " - params: user-rol:"+userRol+" JSONPuestoSector: "+puestoSector);
  			throw new UnauthorizedException("No tiene permisos suficientes.");
  		}
  		
  	}
	
	@DELETE
	@Path("/desasignarTramitePuesto")/*ok*/
	@Consumes(MediaType.APPLICATION_JSON)
	public String desasignarTramitePuesto(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user, JSONPuestoTramite puestoTramite){	
		if(userRol.equals(RESPONSABLE_SECTOR)){
			try{
				adminBean.desasignarTramitePuesto(puestoTramite);
				return "OK";
			}catch(Exception e){
				throw new InternalServerErrorException("Error al desasignar el Tramite al puesto: " + e.getMessage());
			}
		}else{
			logger.error("Permisos insuficientes - " + RESPONSABLE_SECTOR + " - params: user-rol:"+userRol+" JSONPuestoTramite: "+puestoTramite);
			throw new UnauthorizedException("No tiene permisos para realizar esta accion.");
		}
	}
	
	/************************** LISTAR POR *****************************/
	

  	  @GET
  	  @Path("/listarPuestosSector")
      @Produces(MediaType.APPLICATION_JSON)
      public List<JSONPuesto> listarPuestosSector(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user, 
    		  @QueryParam("sectorId") String idSector) {
  		if(userRol.equals(RESPONSABLE_SECTOR)){
  			try{
  				List<JSONPuesto> listaPuestos = adminBean.listarPuestos(idSector);
  				return listaPuestos;
  				
  			}catch(Exception e){
  				throw new InternalServerErrorException("Error al listar Puestos del Sector: " + e.getMessage());
  			}
  		}else{
			logger.error("Permisos insuficientes - " + RESPONSABLE_SECTOR + " - params: user-rol:"+userRol+" user: "+user);
  			throw new UnauthorizedException("No tiene permisos suficientes.");
  		}
      }
  	
  	  @GET
  	  @Path("/listarTramitesSector")
      @Produces(MediaType.APPLICATION_JSON)
      public List<JSONTramite> listarTramitesSector(@HeaderParam("user-rol") String userRol,
    		  @HeaderParam("user") String user, 
    		  @QueryParam("sectorId") String idSector) {
  		if(userRol.equals(RESPONSABLE_SECTOR) || userRol.equals(OPERADOR) || userRol.equals(OPERADORSR)){
  			try{
  				List<JSONTramite> listatrm = adminBean.listarTramitesSector(idSector);
  				return listatrm;
  			}catch(Exception e){
  				throw new InternalServerErrorException("Error al listar Tramites del Sector: " + e.getMessage());
  			}
  		}else{
			logger.error("Permisos insuficientes - " + RESPONSABLE_SECTOR + "-" + OPERADOR + "- params: user-rol:"+userRol+" user: "+user);
  			throw new UnauthorizedException("No tiene permisos suficientes.");
  		}
      }
  	  
  	@GET
	@Path("/listarTramitesPuesto")
    @Produces(MediaType.APPLICATION_JSON)
    public List<JSONTramite> listarTramitesPuesto(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user,
    		@QueryParam("nombreMaquina") String nombreMaquina ) {
		System.out.println("entro a listar");
		if(userRol.equals(RESPONSABLE_SECTOR)){
			try{
				return adminBean.listarTramitesPuesto(nombreMaquina);
			}
			catch(Exception e){
				throw new InternalServerErrorException("Error al listar los Tramites del Puesto: " + e.getMessage());
			}
		}else{
			logger.error("Permisos insuficientes - " + RESPONSABLE_SECTOR + " - params: user-rol:"+userRol+" nombreMaquina: "+nombreMaquina);
			throw new UnauthorizedException("No tiene permisos suficientes.");
		}
    }
  	@GET
	@Path("/listarTramitesPosibles")
    @Produces(MediaType.APPLICATION_JSON)
    public List<JSONTramite> listarTramitesPosibles(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user,@QueryParam("nombreMaquina") String nombreMaquina ) {
		///System.out.println("entro a listar");
		if(userRol.equals(RESPONSABLE_SECTOR)){
			try{
				return adminBean.listarTramitesPosibles(nombreMaquina);
			}
			catch(Exception e){
				throw new InternalServerErrorException("Error al listar los posibles tramites del Puesto: " + e.getMessage());
			}
		}else{
			logger.error("Permisos insuficientes - " + RESPONSABLE_SECTOR + " - params: user-rol:"+userRol+" nombreMaquina: "+nombreMaquina);
			throw new UnauthorizedException("No tiene permisos suficientes.");
		}
    }
  	
	@GET
	@Path("/listarDisplaysSector")
	@Produces(MediaType.APPLICATION_JSON)
	//este metodo retorna los display de un sector
	public List<JSONDisplay> listarDisplaySector(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user, @QueryParam("sectorId") String idSector) {
		if ( (userRol.equals(RESPONSABLE_SECTOR)) || (userRol.equals(ADMINISTRADOR)) ){
			try{
				return adminBean.listarDisplays(idSector);	
			}catch(Exception e){
				throw new InternalServerErrorException("Error al listar Displays del Sector: " + e.getMessage());
			}
		}else{
			logger.error("Permisos insuficientes - " + RESPONSABLE_SECTOR + " - params: user-rol:"+userRol+" idSector: "+idSector);
			throw new UnauthorizedException("No tiene permisos suficientes.");
		}
	}
	
	
	/************ Borrar todo el sistema ***************/
	
	@DELETE
	@Path("/borrarRows")
	public String borrarRows(@HeaderParam("secret-command") String secretCommand) {
		if (secretCommand.equals( "MacocoBorrador")){
			try{
				adminBean.borrarTodoElSistema();
				return "OK";
			}catch(Exception e){
				throw new InternalServerErrorException("Error al macoquear: " + e.getMessage());
			}
		}else{
			throw new UnauthorizedException("NO sos macoco");
		}
    }
	
}
