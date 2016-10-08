package com.sarp.dao.repository;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;

import com.sarp.dao.model.Puesto;
import java.util.Date;
import java.util.List;

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
		p.setDateCreated(new Date());
		p.setLastUpdated(new Date());
		
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
	public List<Puesto> selectPuestos(){		
		List<Puesto> res = em.createQuery("select p from Puesto p").getResultList();
		return res;
	}
	
	/* Modifico el estado de un Puesto dado por su nombre */
	public void updatePuesto(String nombreMaquina, String estado, String usuarioId, Integer numero) throws RollbackException{		
		Puesto p = selectPuesto(nombreMaquina);
		p.setEstado(estado);
		p.setNumero(numero);
		p.setUsuarioId(usuarioId);
		p.setLastUpdated(new Date());
		
		em.persist(p);
	}
	
	/* elimino un display de la base de datos */
	public void deletePuesto(String nombreMaquina) throws RollbackException{		
		Puesto p = selectPuesto(nombreMaquina);
    	em.remove(p);
    }	
	
}
