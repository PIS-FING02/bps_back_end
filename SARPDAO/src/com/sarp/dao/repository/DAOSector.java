package com.sarp.dao.repository;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;

import com.sarp.dao.model.Display;
import com.sarp.dao.model.Puesto;
import com.sarp.dao.model.Sector;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class DAOSector {
	
	/*El EntityManager se setea desde el DAOService, para manejar cada transaccion
	 * con un unico manager
	 */
	private EntityManager em;
	public void setEM(EntityManager em) {
		this.em = em;
	}


	public void insertSector(String codigo, String nombre, String ruta){		
		Sector s = new Sector();
		s.setCodigo(codigo);
		s.setNombre(nombre);
		s.setRutaSector(ruta);
		
		em.persist(s);
	}
	
	/* Obtengo la entidad de Sector en la bd con su codigo */

	public Sector selectSector(String codigo) throws RollbackException{		
		Sector s = em.find(Sector.class, codigo);
		if (s != null){
			return s;
		}
		else{
			throw new RollbackException("No existe el Sector con código " + codigo);
		}
    }
	
	/* Obtengo todos los Sectores en la base de datos */
	@SuppressWarnings("unchecked")
	public ArrayList<Sector> selectSectores(){		
		ArrayList<Sector> res = new ArrayList<Sector>(em.createQuery("select s from Sector s").getResultList());
		return res;
	}
	
	/* Modifico la ruta de un Sector dado por su codigo */

	public void updateSector(String codigo, String nombre, String rutaSector, Timestamp lastUpdated) throws RollbackException{		
		Sector s = selectSector(codigo);
		Timestamp t = s.getLastUpdated();
		s.setNombre(nombre);
		s.setRutaSector(rutaSector);
		//s.setLastUpdated(lastUpdated); //Se debe hacer para el caso que la entidad haya sido modifcada por otro usuario
		
		em.persist(s);
	}
	
	/* elimino un Sector de la base de datos */

	public void deleteSector(String codigo) throws RollbackException{		
		Sector s = selectSector(codigo);
    	em.remove(s);
    }

	public void asociarSectorPuesto(Sector s, Puesto p) {
		s.getPuestos().add(p);
		p.getSectors().add(s);
		
		em.persist(s);
		em.persist(p);
	}	
	
	public void desasociarSectorPuesto(Sector s, Puesto p) {
		s.getPuestos().remove(p);
		p.getSectors().remove(s);
		
		em.persist(s);
		em.persist(p);
	}	
	
	/* Asigno un display a un sector */
	public void asignarDisplaySector(Display display, Sector sector) {
		display.addSector(sector);				
		em.persist(display);
		em.persist(sector);		
	}

	public void desasignarDisplaySector(Display display, Sector sector) {
		display.removeSector(sector);				
		em.persist(display);
		em.persist(sector);			
	}
	
}
