
package com.sarp.service;

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
import com.sarp.controllers.AttentionsController;
import com.sarp.controllers.NumberController;
import com.sarp.factory.Factory;
import com.sarp.json.modeler.JSONNumero;

@RequestScoped
@Path("/numberService")
public class NumberService {

	@POST
	@Path("/solicitarNumero")
	@Consumes(MediaType.APPLICATION_JSON)
	public String SolicitarNumero(JSONNumero num) {
		try {
			Factory fac = Factory.getInstance();
			AttentionsController ctrl = fac.getAttentionsController();
			ctrl.solicitarNumero(num);
			return "El numero se dio de alta exitosamente";
		} catch (Exception e) {
			throw new InternalServerErrorException("Error al soliticar numero");
		}
	}

	@GET
	@Path("/listarSectorNumeros")
	@Produces(MediaType.APPLICATION_JSON)
	public List<JSONNumero> listarNumeros(@HeaderParam("user-rol") String userRol,
			@QueryParam("idSector") String idSector) {
		if (userRol.equals("Administrador")) {
			try {
				Factory fac = Factory.getInstance();
				NumberController ctrl = fac.getNumberController();
				return ctrl.listarNumeros(idSector);
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
	public List<JSONNumero> listarNumerosPausados(@HeaderParam("user-rol") String userRol,
			@QueryParam("idSector") String idSector) {
		if (userRol.equals("Operador") || userRol.equals("OperadorSenior")) {
			try {
				Factory fac = Factory.getInstance();
				NumberController ctrl = fac.getNumberController();
				return ctrl.listarNumerosPausados(idSector);
			} catch (Exception e) {
				throw new InternalServerErrorException(e.getMessage());
			}
		} else {
			throw new UnauthorizedException("No tiene permisos para realizar esta accion.");
		}
	}

}