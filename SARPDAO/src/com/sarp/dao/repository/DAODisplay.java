package com.sarp.dao.repository;

import javax.persistence.EntityManager;
import com.sarp.dao.model.Display;
import com.sarp.dao.model.Sector;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class DAODisplay {
	
	/*El EntityManager se setea desde el DAOService, para manejar cada transaccion
	 * con un unico manager
	 */
	private EntityManager em;
	public void setEM(EntityManager em) {
		this.em = em;
	}
	
	public Display insertDisplay(String rutaArchivo){		
		Display d = new Display();
		d.setRutaArchivo(rutaArchivo);
		d.setDateCreated(new Date());
		d.setLastUpdated(new Timestamp(System.currentTimeMillis()));	
		em.persist(d);
		return d;
	}
	
	/* Obtengo la entidad de Display en la bd con su codigo */
	public Display selectDisplay(int codigo) throws Exception{		
		Display d = em.find(Display.class, codigo);
		if (d != null){
			return d;
		}
		else{
			throw new Exception("No existe el Display con código " + codigo);
		}
    }
	
	/* Obtengo todos los displays en la base de datos */
	@SuppressWarnings("unchecked")
	public List<Display> selectDisplays(){	
		List<Display> res = em.createQuery("select d from Display d").getResultList();
		return res;
	}
	
	/* Modifico la ruta de un display dado por su codigo */
	public void updateDisplay(int codigo, String rutaArchivo, Timestamp t) throws Exception{		
		Display d = selectDisplay(codigo);
		d.setRutaArchivo(rutaArchivo);
		d.setLastUpdated(t);
		//d.setLastUpdated(new Timestamp(System.currentTimeMillis()));	
		//em.lock(d, LockModeType.OPTIMISTIC);
		em.persist(d);

	}
	
	/* elimino un display de la base de datos */
	public void deleteDisplay(int codigo) throws Exception{		
		Display d = selectDisplay(codigo);		
    	em.remove(d);
    }

	public void asociarDisplaySector(Display displa, Sector secto) {
		Sector sector = em.find(Sector.class, secto.getCodigo());
		Display display = em.find(Display.class, displa.getCodigo());
		sector.setDisplay(display);
		display.addSector(sector);		
		
		//display.setLastUpdated(new Timestamp(System.currentTimeMillis()));		
		//sector.setLastUpdated(new Date());
		
		em.persist(display);
		em.persist(sector);
		
	}
	
}
