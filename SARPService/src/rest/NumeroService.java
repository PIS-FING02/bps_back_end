package rest;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.controllers.NumeroControlador;

import com.sarp.logic.AtentionsController;
import com.sarp.logic.Factory;

import classes.Numero;



@RequestScoped
@Path("/numeros")
public class NumeroService {
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Numero> listAllMembers() {
		NumeroControlador ctrl = new NumeroControlador();
		return ctrl.listarNumeros();
    }
	
	@POST
	@Path("/{sec}/{tram}/{nombre}")
	public String altaNumero(
			@PathParam("sec") int sec,
			@PathParam("tram") int tram,
			@PathParam("nombre") String nombre){
		System.out.println("hola desde altaNumero");
		NumeroControlador ctrl = new NumeroControlador();
		try {
			ctrl.crearNumero(sec,tram, nombre);
			return "OK";
		} catch (Exception e) {
			return e.toString();
		}
	}
	
	
	

	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public String SolicitarNumero(Numero num){
		//System.out.println("hola desde altaNumero");
		Factory fac = Factory.GetInstance();
		AtentionsController ctrl = fac.GetAtentionsController();
		ctrl.SolicitarNumero(num);
		return "se recibio : "+num.getNumero().toString()+num.getEmitido().toString();

	}
	
	@GET
	@Path("/{id:[0-9][0-9]*}/{estado}")
	public String modificacionNumero(
			@PathParam("id") int id, 
			@PathParam("estado") String estado){
		//NumeroControlador ctrl = new NumeroControlador();
		//ctrl.modificarNumero(id, estado, "serie modificada", 9);
		return "Numero " + id + " modificado con exito"; 
	}
	

}
