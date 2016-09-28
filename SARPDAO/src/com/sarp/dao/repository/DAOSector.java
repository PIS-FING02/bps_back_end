package com.sarp.dao.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.sarp.dao.factory.EMFactory;
import com.sarp.dao.model.DatosComplementario;
import com.sarp.dao.model.Display;
import com.sarp.dao.model.Numero;
import com.sarp.dao.model.Puesto;
import com.sarp.dao.model.Sector;
import com.sarp.dao.model.Tramite;

import java.util.Date;
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
		s.setDateCreated(new Date());
		s.setLastUpdated(new Date());
		
		em.persist(s);
	}
	
	/* Obtengo la entidad de Sector en la bd con su codigo */

	public Sector selectSector(String codigo) throws Exception{		
		Sector s = em.find(Sector.class, codigo);
		if (s != null){
			return s;
		}
		else{
			throw new Exception("No existe el Sector con código " + codigo);
		}
    }
	
	/* Obtengo todos los Sectores en la base de datos */
	public List<Sector> selectSectores(){		
		List<Sector> res = em.createQuery("select s from Sector s").getResultList();
		return res;
	}
	
	/* Modifico la ruta de un Sector dado por su codigo */

	public void updateSector(String codigo, String nombre, String rutaSector) throws Exception{		
		Sector s = selectSector(codigo);
		s.setNombre(nombre);
		s.setRutaSector(rutaSector);
		s.setLastUpdated(new Date());
		
		em.persist(s);
	}
	
	/* elimino un Sector de la base de datos */

	public void deleteSector(String codigo) throws Exception{		
		Sector s = selectSector(codigo);
    	em.remove(s);
    }

	public void asociarSectorPuesto(Sector s, Puesto p) {
		s.getPuestos().add(p);
		p.getSectors().add(s);
		s.setLastUpdated(new Date());
		p.setLastUpdated(new Date());
		
		em.persist(s);
		em.persist(p);
	}	
	
	public void desasociarSectorPuesto(Sector s, Puesto p) {
		s.getPuestos().remove(p);
		p.getSectors().remove(s);
		s.setLastUpdated(new Date());
		p.setLastUpdated(new Date());
		
		em.persist(s);
		em.persist(p);
	}	
	
}
