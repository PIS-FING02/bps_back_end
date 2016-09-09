package ModelEntitiesController;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import DAOService.Funcionario;
import DAOService.Puesto;
import DAOService.Display;

public class DisplayService {
	protected EntityManager em;
	
	public DisplayService(EntityManager em) {
		this.em = em;
	}
	
    public Display crearDisplay(String dirarchivo, String nombrepc) {  	
    	Display d = new Display();
    	d.setDirarchivo(dirarchivo);
    	d.setNombrepc(nombrepc);
    	em.persist(d);
    	return d;
	}
    
    /*public Funcionario obtenerFuncionario(String login){
    	return em.find(Funcionario.class, login);
    }
	
	  public void eliminarFuncionario(String login) {
		  em.getTransaction().begin();
		  Funcionario f = obtenerFuncionario(login);		  
		  if (f != null) {
			  em.remove(f);
		  }
		  em.getTransaction().commit();
	  }
	  
	  public void modificarFuncionario(String login, String nombre, String apellido){
		  em.getTransaction().begin();
		  Funcionario p = obtenerFuncionario(login);
		  p.setNombre(nombre);
		  p.setApellido(apellido);
		  em.persist(p);
		  em.getTransaction().commit();
	  }
	  */
	  
	
}