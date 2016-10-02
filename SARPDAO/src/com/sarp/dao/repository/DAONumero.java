package com.sarp.dao.repository;

import javax.persistence.EntityManager;

import com.sarp.dao.model.DatosComplementario;
import com.sarp.dao.model.Numero;
import com.sarp.dao.model.Puesto;
import com.sarp.dao.model.Tramite;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class DAONumero {
	
	/*El EntityManager se setea desde el DAOService, para manejar cada transaccion
	 * con un unico manager
	 */
	private EntityManager em;
	public void setEM(EntityManager em) {
		this.em = em;
	}
	
	public Numero insertNumero(Tramite tramite, String externalId, Date hora, Integer prioridad, String estado){
		//Creo la nueva entidad Numero y la asocio con el Tramite
		Numero n = new Numero();
		n.setExternalId(externalId);
		n.setHora(hora);
		n.setPrioridad(prioridad);
		n.setEstado(estado);
		n.setDateCreated(new Date());
		n.setLastUpdated(new Date());
		tramite.addNumero(n);
		//Creo una nueva entidad de DatoComplementario y las asocio
		//TODO hacer en otro metodo	
		em.persist(n);
		return n;
	}
	
	public void insertDatoComplementario(Numero numero, String docIdentidad, String nombreCompleto, String tipoDoc){
		DatosComplementario dc = new DatosComplementario();
		dc.setDocIdentidad(docIdentidad);
		dc.setNombreCompleto(nombreCompleto);
		dc.setTipoDoc(tipoDoc);
		dc.setNumero(numero);
		numero.setDatosComplementario(dc);
		dc.setDateCreated(new Date());
		dc.setLastUpdated(new Date());
		
		em.persist(dc);
	}
	
	public Numero selectNumero(int internalId) throws Exception{
		Numero n = em.find(Numero.class, internalId);
		if (n != null){
			return n;
		}
		else{
			throw new Exception("No existe el Numero con código " + internalId);
		}
    }
	
	@SuppressWarnings("unchecked")
	public List<Numero> selectNumeros(){
		List<Numero> ret = (List<Numero>) em.createQuery("select n from Numero n").getResultList();
		return ret;
	}
			
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Numero> selectNumerosDelDia(){
		Date hoy = new Date();
		List<Numero> list = (List<Numero>) em.createQuery("select n from Numero n").getResultList();
		List<Numero> res = new LinkedList<Numero>();
		for(Numero n : list){
			Date hora = n.getHora();
			if(hora.getYear() == hoy.getYear() && hora.getMonth() == hoy.getMonth() && hora.getDate() == hoy.getDate()){
				res.add(n);
			}
		}
		return res;
	}		
	
	public void deleteNumero(int id) throws Exception {		
		Numero n = selectNumero(id);
    	em.remove(n);
	}

	public void updateNumero(Integer internalId, String estado, String externalId, Date hora, Integer prioridad) throws Exception {
		Numero n = selectNumero(internalId);
		n.setEstado(estado);
		n.setExternalId(externalId);
		n.setHora(hora);
		n.setPrioridad(prioridad);
		n.setLastUpdated(new Date());
		
		em.persist(n);	
	}
	
	public boolean existsNumero(int internalId){
		Numero n = em.find(Numero.class, internalId);
		return n != null;
	}

	public void asociarNumeroPuestoActual(Numero numero, Puesto puesto) {
		numero.setPuesto(puesto);
		puesto.setNumero_puesto(numero);		
		numero.setLastUpdated(new Date());	
		puesto.setLastUpdated(new Date());
		
		em.persist(puesto);	
		em.persist(numero);		
	}

	public void asociarNumeroPuesto(Numero numero, Puesto puesto) {
		numero.getPuestos().add(puesto);
		puesto.getNumeros().add(numero);
		numero.setLastUpdated(new Date());	
		puesto.setLastUpdated(new Date());
		
		em.persist(puesto);	
		em.persist(numero);
		
	}

	public void desasociarNumeroPuesto(Numero numero, Puesto puesto) {
		numero.getPuestos().remove(puesto);
		puesto.getNumeros().remove(numero);
		numero.setLastUpdated(new Date());	
		puesto.setLastUpdated(new Date());
		
		em.persist(puesto);	
		em.persist(numero);
			
	}

	public void desasociarNumeroPuestoActual(Numero numero, Puesto puesto) {
		numero.setPuesto(null);
		puesto.setNumero_puesto(null);		
		numero.setLastUpdated(new Date());	
		puesto.setLastUpdated(new Date());
		
		em.persist(puesto);	
		em.persist(numero);		
	}
	
}
