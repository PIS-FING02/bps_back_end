package com.sarp.dao.repository;

import javax.persistence.EntityManager;
import com.sarp.dao.model.Puesto;
import com.sarp.dao.model.Sector;
import com.sarp.dao.model.Tramite;
import java.util.Date;
import java.util.List;

public class DAOTramite {
	
	/*El EntityManager se setea desde el DAOService, para manejar cada transaccion
	 * con un unico manager
	 */
	private EntityManager em;
	public void setEM(EntityManager em) {
		this.em = em;
	}

	public Tramite insertTramite(String nombre) throws Exception{		
		Tramite t = new Tramite();
		t.setNombre(nombre);
		t.setDateCreated(new Date());
		t.setLastUpdated(new Date());
		
		em.persist(t);
		return t;
	}
		
	public Tramite selectTramite(int codigo) throws Exception{		
		Tramite t = em.find(Tramite.class, codigo);
		if (t != null){
			return t;
		}
		else{
			throw new Exception("No existe el Tramite con código " + codigo);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Tramite> selectTramites() {		
		return (List<Tramite>) em.createQuery("select t from Tramite t").getResultList();
	}
	
	

	public void deleteTramite(int codigo) throws Exception {		
		Tramite t = selectTramite(codigo);
    	em.remove(t);
	}

	public void updateTramite(Integer codigo, String nombre) throws Exception {		
		Tramite t = selectTramite(codigo);
		t.setNombre(nombre);
		t.setLastUpdated(new Date());
		
		em.persist(t);
	}

	public void asociarTramiteSector(Sector sector, Tramite tramite) throws Exception{		
		tramite.getSectors().add(sector);
		sector.getTramites().add(tramite);
		tramite.setLastUpdated(new Date());	
		sector.setLastUpdated(new Date());	
		em.persist(tramite);
		em.persist(sector);
	}
	
	public void desasociarTramiteSector(Sector s, Tramite t) {
		t.getSectors().remove(s);
		t.setLastUpdated(new Date());	
		t.setLastUpdated(new Date());	
		em.persist(t);
		em.persist(s);		
	}
	
	public void asociarTramitePuesto(Puesto puesto, Tramite tramite) {		
		puesto.getTramites().add(tramite);
		tramite.setLastUpdated(new Date());	
		puesto.setLastUpdated(new Date());
		
		em.persist(puesto);	
		em.persist(tramite);
	}

	public void desasociarTramitePuesto(Puesto puesto, Tramite tramite) {
		tramite.getPuestos().remove(puesto);
		puesto.getTramites().remove(tramite);
		tramite.setLastUpdated(new Date());	
		puesto.setLastUpdated(new Date());
		
		em.persist(tramite);
		em.persist(puesto);
	}
	
	
}
