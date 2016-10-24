package com.sarp.dao.repository;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import com.sarp.dao.model.Numero;
import com.sarp.dao.model.NumeroTramite;
import com.sarp.dao.model.Puesto;
import com.sarp.dao.model.Sector;
import com.sarp.dao.model.Tramite;
import java.sql.Timestamp;
import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class DAOTramite {
	
	/*El EntityManager se setea desde el DAOService, para manejar cada transaccion
	 * con un unico manager
	 */
	private EntityManager em;
	public void setEM(EntityManager em) {
		this.em = em;
	}

	public Tramite insertTramite(String codigo, String nombre) throws RollbackException{		
		Tramite t = new Tramite();
		t.setCodigo(codigo);
		t.setNombre(nombre);
		
		em.persist(t);
		return t;
	}
		
	public Tramite selectTramite(String codigo) throws RollbackException{		
		Tramite t = em.find(Tramite.class, codigo);
		if (t != null){
			return t;
		}
		else{
			throw new RollbackException("No existe el Tramite con código " + codigo);
		}
	}
	
	public ArrayList<Tramite> selectTramites() {		
		return new ArrayList<Tramite>(em.createQuery("select t from Tramite t").getResultList());
	}
	
	public void deleteTramite(String codigo) throws RollbackException {		
		Tramite t = selectTramite(codigo);
		for (Numero n: t.getNumeros()){
            n.setTramite(null);
        }
		for(NumeroTramite nt : t.getNumeroTramites()){
			em.remove(nt);
		}
    	em.remove(t);
	}

	public void updateTramite(String codigo, String nombre, Timestamp lastUpdated) throws RollbackException {		
		Tramite t = selectTramite(codigo);
		t.setNombre(nombre);
		t.setLastUpdated(lastUpdated); //Se debe hacer para el caso que la entidad haya sido modifcada por otro usuario
		
		em.persist(t);
	}

	public void asociarTramiteSector(Sector sector, Tramite tramite) throws RollbackException{		
		tramite.getSectors().add(sector);
		sector.getTramites().add(tramite);
		em.persist(tramite);
		em.persist(sector);
	}
	
	public void desasociarTramiteSector(Sector s, Tramite t) {
		t.getSectors().remove(s);	
		em.persist(t);
		em.persist(s);		
	}
	
	public void asociarTramitePuesto(Puesto puesto, Tramite tramite) {		
		puesto.getTramites().add(tramite);
		
		em.persist(puesto);	
		em.persist(tramite);
	}

	public void desasociarTramitePuesto(Puesto puesto, Tramite tramite) {
		tramite.getPuestos().remove(puesto);
		puesto.getTramites().remove(tramite);
		
		em.persist(tramite);
		em.persist(puesto);
	}
	
	
}
