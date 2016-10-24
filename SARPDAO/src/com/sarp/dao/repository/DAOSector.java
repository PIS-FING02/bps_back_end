package com.sarp.dao.repository;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import com.sarp.dao.model.Display;
import com.sarp.dao.model.Numero;
import com.sarp.dao.model.Puesto;
import com.sarp.dao.model.Sector;
import java.sql.Timestamp;
import java.util.ArrayList;

@SuppressWarnings("unchecked")
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
		s.setHabilitado(true);
		
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
	public ArrayList<Sector> selectSectores(){		
		ArrayList<Sector> res = new ArrayList<Sector>(em.createQuery("select s from Sector s where s.habilitado = true").getResultList());
		return res;
	}
	
	/* Modifico la ruta de un Sector dado por su codigo */
	public void updateSector(String codigo, String nombre, String rutaSector,Timestamp lastUpdated) throws RollbackException{		
		Sector s = selectSector(codigo);
		s.setNombre(nombre);
		s.setRutaSector(rutaSector);
		s.setLastUpdated(lastUpdated); //Se debe hacer para el caso que la entidad haya sido modifcada por otro usuario
		s.setHabilitado(true);
		em.persist(s);
	}
	
	/* Dada de baja lógica de un Sector dado por su codigo */
	public void bajaLogicaSector(String codigo) throws RollbackException{		
		Sector s = selectSector(codigo);
		s.setHabilitado(false);
		
		em.persist(s);
	}
	
	/* elimino un Sector de la base de datos */
	public void deleteSector(String codigo) throws RollbackException{		
		Sector s = selectSector(codigo);
		for (Numero n: s.getNumeros()){
            n.setSector(null);
        }
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
	
	public void asignarDisplaySector(Display display, Sector sector) {
		display.getSectors().add(sector);
		sector.getDisplays().add(display);
		em.persist(display);
		em.persist(sector);		
	}

	public void desasignarDisplaySector(Display display, Sector sector) {
		display.getSectors().remove(sector);
		sector.getDisplays().remove(display);
		em.persist(display);
		em.persist(sector);			
	}
	
}
