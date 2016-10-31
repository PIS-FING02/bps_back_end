
package com.sarp.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
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

import com.sarp.beans.AdminBean;
import com.sarp.beans.AttentionsBean;
import com.sarp.beans.GafuBean;
import com.sarp.beans.NumberBean;
import com.sarp.classes.BusinessSectorRol;
import com.sarp.controllers.AttentionsController;
import com.sarp.controllers.GAFUController;
import com.sarp.controllers.NumberController;
import com.sarp.factory.Factory;
import com.sarp.json.modeler.JSONNumero;
import com.sarp.json.modeler.JSONSectorCantNum;

@RequestScoped
@Path("/numberService")
public class NumberService {

	private final String ResponsableSectorGAFU = "RESPSEC";

	private final String ConsultorGAFU = "CONSULTOR";
	@EJB
	private AdminBean adminBean = new AdminBean();
	
	@EJB
	private NumberBean numberBean = new NumberBean();
	
	@EJB
	private GafuBean gafuBean = new GafuBean();
	
	@EJB
	private AttentionsBean attBean = new AttentionsBean();
	
	@POST
	@Path("/solicitarNumero")
	@Consumes(MediaType.APPLICATION_JSON)
	public String SolicitarNumero(JSONNumero num) {
		try {
			return attBean.solicitarNumero(num);
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
				return numberBean.listarNumerosSector(idSector);
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
			if (userRol.equals("OPERADOR") || userRol.equals("OPERADORSR")) {
				return numberBean.listarNumerosPausados(idPuesto);
			}else{
				List<JSONNumero> listaNumeros = new ArrayList<JSONNumero>();
				if(userRol.equals("RESPSEC")){ 
					List<BusinessSectorRol> permisos = gafuBean.obtenerSectorRolesUsuario(user, ResponsableSectorGAFU);
					for (BusinessSectorRol sd : permisos)
						if (sd.getSectorId().equals(idSector))
							listaNumeros.addAll(numberBean.listarNumerosPausadosSector(sd.getSectorId()));
				}else{
					if(userRol.equals("CONSULTOR")){ 	
						List<BusinessSectorRol> permisos = gafuBean.obtenerSectorRolesUsuario(user, ConsultorGAFU);
						for (BusinessSectorRol sd : permisos)
							if (sd.getSectorId().equals(idSector))
								listaNumeros.addAll(numberBean.listarNumerosPausadosSector(sd.getSectorId()));
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
			if (userRol.equals("OPERADOR") || userRol.equals("OPERADORSR")) {
					return numberBean.listarNumerosAtrasados(idPuesto);
			}else{
				List<JSONNumero> listaNumeros = new ArrayList<JSONNumero>();
				if(userRol.equals("CONSULTOR") ){ 
					List<BusinessSectorRol> permisos = gafuBean.obtenerSectorRolesUsuario(user, ConsultorGAFU);
					for (BusinessSectorRol sd : permisos)
						if (sd.getSectorId().equals(idSector))
							listaNumeros.addAll(numberBean.listarNumerosAtrasadosSector(sd.getSectorId()));
				}else{
					if (userRol.equals("RESPSEC")){
						List<BusinessSectorRol> permisos = gafuBean.obtenerSectorRolesUsuario(user, ResponsableSectorGAFU);
						for (BusinessSectorRol sd : permisos)
							if (sd.getSectorId().equals(idSector))
								listaNumeros.addAll(numberBean.listarNumerosAtrasadosSector(sd.getSectorId()));
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
				return numberBean.listarNumerosEnEspera(idPuesto);
			} catch (Exception e) {
				throw new InternalServerErrorException(e.getMessage());
			}
		}else if(userRol.equals("RESPSEC")){ 
			try {
				List<JSONNumero> listaNumeros = new ArrayList<JSONNumero>();
				List<BusinessSectorRol> permisos = gafuBean.obtenerSectorRolesUsuario(user, ResponsableSectorGAFU);
				for (BusinessSectorRol sd : permisos)
					if (sd.getSectorId().equals(idSector))
						listaNumeros.addAll( numberBean.listarNumerosEnEsperaSector(sd.getSectorId()));
				return listaNumeros;
			} catch (Exception e) {
				throw new InternalServerErrorException(e.getMessage());
			}
		} else {
			throw new UnauthorizedException("No tiene permisos para realizar esta accion.");
		}
	}
	
	@GET
	@Path("/obtenerCantNumerosEnEspera")
	@Produces(MediaType.APPLICATION_JSON)
	public List<JSONSectorCantNum> obtenerCantNumerosEnEspera(@HeaderParam("user-rol") String userRol, @HeaderParam("user") String user,
			@QueryParam("idPuesto") String idPuesto, @QueryParam("idSector") String idSector) {
		if (userRol.equals("OPERADOR") || userRol.equals("OPERADORSR")) {
			try {
				return numberBean.obtenerCantNumerosEnEspera(idPuesto);
			} catch (Exception e) {
				throw new InternalServerErrorException(e.getMessage());
			}
		}else if(userRol.equals("RESPSEC")){
			/*try {
				List<JSONNumero> listaNumeros = new ArrayList<JSONNumero>();
				List<BusinessSectorRol> permisos = gafuBean.obtenerSectorRolesUsuario(user, ResponsableSectorGAFU);
				for (BusinessSectorRol sd : permisos)
					if (sd.getSectorId().equals(idSector))
						listaNumeros.addAll( numberBean.listarNumerosEnEsperaSector(sd.getSectorId()));
				return listaNumeros;
			} catch (Exception e) {
				throw new InternalServerErrorException(e.getMessage());
			}*/
			throw new InternalServerErrorException("esto esta en standby, xq primero se pidio para op y opSR");
		} else {
			throw new UnauthorizedException("No tiene permisos para realizar esta accion.");
		}
	}
	
}