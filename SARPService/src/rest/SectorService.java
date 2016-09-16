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

import classes.Sector;
import classes.Numero;
import classes.Sector;


@RequestScoped
@Path("/sectores")
public class SectorService {
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Sector> listAllMembers() {
		org.controllers.SectorControlador ctrl = new SectorControlador();
		return ctrl.listarSectores();
    }
	
	@POST
	@Path("/{nombre}")
	public String altaSector(
			@PathParam("nombre") String nombre){
		System.out.println("hola desde altaSector");
		org.controllers.SectorControlador ctrl = new SectorControlador();
		try {
			ctrl.crearSector(nombre);
			return "OK";
		} catch (Exception e) {
			return e.toString();
		}
	}
	
}
