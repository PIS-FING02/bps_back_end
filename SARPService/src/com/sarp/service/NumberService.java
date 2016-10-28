
package com.sarp.service;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.jboss.resteasy.spi.InternalServerErrorException;
import org.jboss.resteasy.spi.UnauthorizedException;

import com.sarp.classes.BusinessSectorRol;
import com.sarp.controllers.AttentionsController;
import com.sarp.controllers.GAFUController;
import com.sarp.controllers.NumberController;
import com.sarp.factory.Factory;
import com.sarp.json.modeler.JSONNumero;

@RequestScoped
@Path("/numberService")
public class NumberService {

	private final String ResponsableSectorGAFU = "RESPSEC";

	private final String ConsultorGAFU = "CONSULTOR";
		
	
	@POST
	@Path("/solicitarNumero")
	@Consumes(MediaType.APPLICATION_JSON)
	public String SolicitarNumero(JSONNumero num) {
		try {
			Factory fac = Factory.getInstance();
			AttentionsController ctrl = fac.getAttentionsController();
			return ctrl.solicitarNumero(num);
		} catch (Exception e) {
			throw new InternalServerErrorException("Error al soliticar numero"+e.getMessage());
		}
	}

	@GET
	@Path("/listarNumerosSector")
	@Produces(MediaType.APPLICATION_JSON)
	public List<JSONNumero> listarNumerosSector(@HeaderParam("user-rol") String userRol,
			@QueryParam("idSector") String idSector) {
		if (userRol.equals("ADMIN")) {
			try {
				Factory fac = Factory.getInstance();
				NumberController ctrl = fac.getNumberController();
				return ctrl.listarNumerosSector(idSector);
			} catch (Exception e) {
				throw new InternalServerErrorException(e.getMessage());
			}
		} else {
			throw new UnauthorizedException("No tiene permisos para realizar esta accion.");
		}
	}
	
	@GET
	@Path("/listarNumerosPausados")
	@Produces(MediaType.APPLICATION_JSON)
	public List<JSONNumero> listarNumerosPausados(@HeaderParam("user-rol") String userRol,@HeaderParam("user") String user,
			@QueryParam("idPuesto") String idPuesto, @QueryParam("idSector") String idSector) {
		try {
			Factory fac = Factory.getInstance();
			NumberController ctrl = fac.getNumberController();
			if (userRol.equals("OPERADOR") || userRol.equals("OPERADORSR")) {
				return ctrl.listarNumerosPausados(idPuesto);
			}else{
				GAFUController controladorGAFU = fac.GAFUController();
				List<JSONNumero> listaNumeros = new ArrayList<JSONNumero>();
				if(userRol.equals("RESPSEC")){ 
					List<BusinessSectorRol> permisos = controladorGAFU.obtenerSectorRolesUsuario(user, ResponsableSectorGAFU);
					for (BusinessSectorRol sd : permisos)
						if (sd.getSectorId().equals(idSector))
							listaNumeros.addAll(ctrl.listarNumerosPausadosSector(sd.getSectorId()));
				}else{
					if(userRol.equals("CONSULTOR")){ 	
						List<BusinessSectorRol> permisos = controladorGAFU.obtenerSectorRolesUsuario(user, ConsultorGAFU);
						for (BusinessSectorRol sd : permisos)
							if (sd.getSectorId().equals(idSector))
								listaNumeros.addAll(ctrl.listarNumerosPausadosSector(sd.getSectorId()));
					}else
						throw new UnauthorizedException("No tiene permisos para realizar esta accion.");
				}
				return listaNumeros;
			}
		} catch (Exception e) {
			throw new InternalServerErrorException(e.getMessage());
		}
	}

	@GET
	@Path("/listarNumerosAtrasados")
	@Produces(MediaType.APPLICATION_JSON)
	public List<JSONNumero> listarNumerosAtrasados(@HeaderParam("user-rol") String userRol, @HeaderParam("user") String user,
			@QueryParam("idPuesto") String idPuesto, @QueryParam("idSector") String idSector) {
		try {
			Factory fac = Factory.getInstance();
			NumberController ctrl = fac.getNumberController();
			if (userRol.equals("OPERADOR") || userRol.equals("OPERADORSR")) {
					return ctrl.listarNumerosAtrasados(idPuesto);
			}else{
				GAFUController controladorGAFU = fac.GAFUController();
				List<JSONNumero> listaNumeros = new ArrayList<JSONNumero>();
				if(userRol.equals("CONSULTOR") ){ 
					List<BusinessSectorRol> permisos = controladorGAFU.obtenerSectorRolesUsuario(user, ConsultorGAFU);
					for (BusinessSectorRol sd : permisos)
						if (sd.getSectorId().equals(idSector))
							listaNumeros.addAll(ctrl.listarNumerosAtrasadosSector(sd.getSectorId()));
				}else{
					if (userRol.equals("RESPSEC")){
						List<BusinessSectorRol> permisos = controladorGAFU.obtenerSectorRolesUsuario(user, ResponsableSectorGAFU);
						for (BusinessSectorRol sd : permisos)
							if (sd.getSectorId().equals(idSector))
								listaNumeros.addAll(ctrl.listarNumerosAtrasadosSector(sd.getSectorId()));
					}else
						throw new UnauthorizedException("No tiene permisos para realizar esta accion.");
				}
				return listaNumeros;
			}
		} catch (Exception e) {
			throw new InternalServerErrorException(e.getMessage());
		}
	}
	
	@GET
	@Path("/listarNumerosEnEspera")
	@Produces(MediaType.APPLICATION_JSON)
	public List<JSONNumero> listarNumerosEnEspera(@HeaderParam("user-rol") String userRol, @HeaderParam("user") String user,
			@QueryParam("idPuesto") String idPuesto, @QueryParam("idSector") String idSector) {
		if (userRol.equals("OPERADOR") || userRol.equals("OPERADORSR")) {
			try {
				Factory fac = Factory.getInstance();
				NumberController ctrl = fac.getNumberController();
				return ctrl.listarNumerosEnEspera(idPuesto);
			} catch (Exception e) {
				throw new InternalServerErrorException(e.getMessage());
			}
		}else if(userRol.equals("RESPSEC")){ 
			try {
				Factory fac = Factory.getInstance();
				NumberController ctrl = fac.getNumberController();
				GAFUController controladorGAFU = fac.GAFUController();
				List<JSONNumero> listaNumeros = new ArrayList<JSONNumero>();
				List<BusinessSectorRol> permisos = controladorGAFU.obtenerSectorRolesUsuario(user, ResponsableSectorGAFU);
				for (BusinessSectorRol sd : permisos)
					if (sd.getSectorId().equals(idSector))
						listaNumeros.addAll( ctrl.listarNumerosEnEsperaSector(sd.getSectorId()));
				return listaNumeros;
			} catch (Exception e) {
				throw new InternalServerErrorException(e.getMessage());
			}
		} else {
			throw new UnauthorizedException("No tiene permisos para realizar esta accion.");
		}
	}
}