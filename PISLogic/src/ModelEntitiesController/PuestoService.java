package src.ModelEntitiesController;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import src.DAOService.Funcionario;
import src.DAOService.Puesto;

public class PuestoService {
	protected EntityManager em;
	
	public PuestoService(EntityManager em) {
		this.em = em;
	}
	
    public Puesto crearPuesto(int codigo, String estado) {
    	Puesto p = new Puesto();
    	p.setCodigo(codigo);
    	p.setEstado(estado);
    	em.persist(p);
    	return p;
	}
    
    public Puesto obtenerPuesto(int codigo){
    	return em.find(Puesto.class, codigo);
    }
	
	  public void eliminarPuesto(int codigo) {
		  Puesto p = obtenerPuesto(codigo);	  
		  if (p != null) {
			  em.remove(p);
		  }
	  }
	  
	  public void modificarPuesto(int codigo, String estado, Boolean isAbierto){
		  Puesto p = obtenerPuesto(codigo);
		  p.setEstado(estado);
		  p.setIsabierto(isAbierto);
		  //p.setFuncionarioBean(funcionarioBean);
		  em.persist(p);	
	  }
	  
	  public void asignarFuncionario(int codigo, Funcionario f){
		  Puesto p = em.find(Puesto.class, codigo);
		  if (!(p.getFuncionarioBean() == f)) { 
			  	p.setFuncionarioBean(f);
	      }
	  }	 	
}