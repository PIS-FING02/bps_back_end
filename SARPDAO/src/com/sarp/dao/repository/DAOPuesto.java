package com.sarp.dao.repository;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import com.sarp.dao.model.Puesto;
import java.sql.Timestamp;
import java.util.ArrayList;

public class DAOPuesto {
	
	/*El EntityManager se setea desde el DAOService, para manejar cada transaccion
	 * con un unico manager
	 */
	private EntityManager em;
	public void setEM(EntityManager em) {
		this.em = em;
	}
	
	/* Creo en la base una entidad Puesto
	 */
	public void insertPuesto(String nombreMaquina, String usuarioId, Integer numero){				
		Puesto p = new Puesto();
		p.setNombreMaquina(nombreMaquina);
		p.setUsuarioId(usuarioId);
		p.setEstado("CERRADO");
		p.setNumero(numero);
		
		em.persist(p);
	}
	
	/* Obtengo la entidad de Puesto en la base de datos con su nombre */
	public Puesto selectPuesto(String nombreMaquina) throws RollbackException{		
		Puesto p = em.find(Puesto.class, nombreMaquina);
		if (p != null){
			return p;
		}
		else{
			throw new RollbackException("No existe el Puesto con nombre " + nombreMaquina);
		}
    }
	
	/* Obtengo todos los Puestos en la base de datos */
	@SuppressWarnings("unchecked")
	public ArrayList<Puesto> selectPuestos(){		
		ArrayList<Puesto> res = new ArrayList<Puesto>(em.createQuery("select p from Puesto p").getResultList());
		return res;
	}
	
	/* Modifico el estado de un Puesto dado por su nombre */
	public void updatePuesto(String nombreMaquina, String estado, String usuarioId, Integer numero, Timestamp lastUpdated) throws RollbackException{		
		Puesto p = selectPuesto(nombreMaquina);
		p.setEstado(estado);
		p.setNumero(numero);
		p.setUsuarioId(usuarioId);
		p.setLastUpdated(lastUpdated); //Se debe hacer para el caso que la entidad haya sido modifcada por otro usuario
		
		em.persist(p);
	}
	
	/* elimino un display de la base de datos */
	public void deletePuesto(String nombreMaquina) throws RollbackException{		
		Puesto p = selectPuesto(nombreMaquina);
		if(p.getNumero_puesto() != null){
			p.getNumero_puesto().setPuesto(null);
		}
    	em.remove(p);
    }
	
}
