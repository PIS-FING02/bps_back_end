package src.ModelEntitiesController;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import src.DAOService.Funcionario;
import src.DAOService.Numero;
import src.DAOService.Puesto;
import src.DAOService.Display;

public class NumeroService {
	protected EntityManager em;
	
	public NumeroService(EntityManager em) {
		this.em = em;
	}
	
    public Numero crearNumero(int codigo, String nombre) {  	
    	Numero n = new Numero();
    	n.setCodigo(codigo);
    	n.setNombre(nombre);
    	em.persist(n);
    	return n;
	}
    
    public Numero obtenerNumero(int codigo){
    	return em.find(Numero.class, codigo);
    }
    
    public void eliminarNumero(int codigo){
    	Numero n = obtenerNumero(codigo);
    	if (n != null){
    		em.remove(n);
    	}
    }
	
	
}