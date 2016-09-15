package rest;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.controllers.NumeroControlador;
import org.controllers.SectorControlador;
import org.controllers.TramiteControlador;

import dataTypes.Tramite;
import model.Numero;
import model.Sector;
import model.Tramite;


@RequestScoped
@Path("/tramites")
public class TramiteService {
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Tramite> listAllMembers() {
		org.controllers.TramiteControlador ctrl = new TramiteControlador();
		return ctrl.listarTramites();
    }
	
	@POST
	@Path("/{sector}/{nombre}")
	public String altaTramite(
			@PathParam("sector") int sector,
			@PathParam("nombre") String nombre){
		System.out.println("hola desde altaTramite");
		org.controllers.TramiteControlador ctrl = new TramiteControlador();
		try {
			ctrl.crearTramite(sector,nombre);
			return "OK";
		} catch (Exception e) {
			return e.toString();
		}
	}
	
}
