package src.ModelEntitiesController;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import src.DAOService.Funcionario;


public class FuncionarioService {
	
	@PersistenceContext
	private EntityManager em;
	
	
	
    public void crearFuncionario(String login, String nombre, String apellido, String password, String correoelectronico) {
    	Funcionario f = new Funcionario();
    	f.setLogin(login);
    	f.setNombre(nombre);
    	f.setApellido(apellido);
    	f.setPassword(password);
    	f.setCorreoelectronico(correoelectronico);
    	em.persist(f);
	}
    
    public Funcionario obtenerFuncionario(String login){
    	return em.find(Funcionario.class, login);
    }
	
	  public void eliminarFuncionario(String login) {
		  Funcionario f = obtenerFuncionario(login);		  
		  if (f != null) {
			  em.remove(f);
		  }
	  }
	  
	  public void modificarFuncionario(String login, String nombre, String apellido){
		  Funcionario p = obtenerFuncionario(login);
		  p.setNombre(nombre);
		  p.setApellido(apellido);
		  em.persist(p);
	  }
	  
		  
}