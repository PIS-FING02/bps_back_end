package services.inlayer;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;



import jdk.nashorn.internal.runtime.JSONListAdapter;
import src.ModelEntitiesController.*;
import src.DAOService.*;
@RequestScoped
@Path("/users")
public class UsersService {

	private static EntityManagerFactory factory;
	//@Inyect   deberia hacerse asi creo ---
	//private FuncionarioService fc;
	
	@GET
	@Path("{login}-{nombre}-{apellido}-{password}-{email}")
	public String Login(@PathParam("login") String login,@PathParam("nombre") String nombre,
			@PathParam("apellido") String apellido,@PathParam("password") String password,
			@PathParam("email") String email){
		
		factory = Persistence.createEntityManagerFactory("postgresUnit");
        EntityManager em = factory.createEntityManager();     
        //FuncionarioService funcionarioService = new FuncionarioService(em);
		//Funcionario func = funcionarioService.crearFuncionario(login, nombre, apellido, password, email);
		return "el user es: " + login + " y la contraseña:" + password;
	}
	
	//@GET
    //@Path("/{id:[0-9][0-9]*}")
    //@Produces(MediaType.APPLICATION_JSON)
    //public Funcionario findUser(@PathParam("id") long id) {
    //    //User user = RydelJpaFind.findById(id);
	//	
    //    if (user == null) {
    //        throw new WebApplicationException(Response.Status.NOT_FOUND);
    //    }
    //    return "nabo";
    //}
}
