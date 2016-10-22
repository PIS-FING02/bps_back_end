package com.sarp.dao.repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import com.sarp.dao.model.DatosComplementario;
import com.sarp.dao.model.MetricasEstadoNumero;
import com.sarp.dao.model.MetricasNumero;
import com.sarp.dao.model.MetricasPuesto;
import com.sarp.dao.model.Numero;
import com.sarp.dao.model.Puesto;
import com.sarp.dao.model.Sector;
import com.sarp.dao.model.Tramite;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DAONumero {
	
	/*El EntityManager se setea desde el DAOService, para manejar cada transaccion
	 * con un unico manager
	 */
	private EntityManager em;
	public void setEM(EntityManager em) {
		this.em = em;
	}
	
	public Numero insertNumero(Tramite tramite, Sector sector, String externalId, GregorianCalendar hora, Integer prioridad){
		Numero n = new Numero();
		n.setExternalId(externalId);
		n.setHora(hora);
		n.setPrioridad(prioridad);
		n.setEstado("DISPONIBLE");
		tramite.addNumero(n);
		sector.addNumero(n);

		em.persist(n);
		return n;
	}
	
	public void insertDatoComplementario(Numero numero, String docIdentidad, String nombreCompleto, String tipoDoc){
		DatosComplementario dc = new DatosComplementario();
		dc.setDocIdentidad(docIdentidad);
		dc.setNombreCompleto(nombreCompleto);
		dc.setTipoDoc(tipoDoc);
		dc.setNumero(numero);
		
		em.persist(dc);
	}
	
	public Numero selectNumero(int internalId) throws RollbackException{
		Numero n = em.find(Numero.class, internalId);
		if (n != null){
			return n;
		}
		else{
			throw new RollbackException("No existe el Numero con codigo " + internalId);
		}
    }
	
	@SuppressWarnings("unchecked")
	public ArrayList<Numero> selectNumeros(){
		ArrayList<Numero> ret = new ArrayList<Numero>(em.createQuery("select n from Numero n").getResultList());
		return ret;
	}
			
	@SuppressWarnings({"unchecked"})
	public ArrayList<Numero> selectNumerosDelDia(){
		GregorianCalendar hoy = new GregorianCalendar();
		ArrayList<Numero> list = new ArrayList<Numero>(em.createQuery("select n from Numero n").getResultList());
		ArrayList<Numero> res = new ArrayList<Numero>();
		for(Numero n : list){
			GregorianCalendar hora = n.getHora();
			if((hora != null) && (hora.get(Calendar.YEAR) == hoy.get(Calendar.YEAR) && hora.get(Calendar.MONTH) == hoy.get(Calendar.MONTH) && hora.get(Calendar.DAY_OF_MONTH) == hoy.get(Calendar.DAY_OF_MONTH))){
				res.add(n);
			}
		}
		return res;
	}		
	
	public void deleteNumero(int id) throws RollbackException {		
		Numero n = selectNumero(id);
		DatosComplementario d = n.getDatosComplementario();
		if(d != null){
			em.remove(d);
		}
		if(n.getPuesto() != null){
			n.getPuesto().setNumero_puesto(null);
		}
    	em.remove(n);
	}

	public void updateNumero(Integer internalId, String estado, String externalId, GregorianCalendar hora, Integer prioridad, Timestamp lastUpdated, String resultadoFinal, boolean fueAtrasado) throws RollbackException {
		Numero n = selectNumero(internalId);
		n.setEstado(estado);
		n.setExternalId(externalId);
		n.setHora(hora);
		n.setPrioridad(prioridad);
		n.setFueAtrasado(fueAtrasado);
		n.setResultadoFinal(resultadoFinal);
		n.setLastUpdated(lastUpdated); //Se debe hacer para el caso que la entidad haya sido modifcada por otro usuario

		em.persist(n);	
	}
	
	public boolean existsNumero(int internalId){
		Numero n = em.find(Numero.class, internalId);
		return n != null;
	}

	public void asociarNumeroPuestoActual(Numero numero, Puesto puesto) {
		numero.setPuesto(puesto);
		puesto.setNumero_puesto(numero);		
		
		em.persist(puesto);	
		em.persist(numero);		
	}

	public void asociarNumeroPuesto(Numero numero, Puesto puesto) {
		numero.getPuestos().add(puesto);
		puesto.getNumeros().add(numero);
		
		em.persist(puesto);	
		em.persist(numero);		
	}

	public void desasociarNumeroPuesto(Numero numero, Puesto puesto) {
		numero.getPuestos().remove(puesto);
		puesto.getNumeros().remove(numero);
		
		em.persist(puesto);	
		em.persist(numero);			
	}

	public void desasociarNumeroPuestoActual(Numero numero, Puesto puesto) {
		numero.setPuesto(null);
		puesto.setNumero_puesto(null);		
		
		em.persist(puesto);	
		em.persist(numero);		
	}
	
	public ArrayList<MetricasEstadoNumero> selectMetricasEstadoNumero() {
		ArrayList<MetricasEstadoNumero> res = new ArrayList<MetricasEstadoNumero>(em.createQuery("SELECT men FROM MetricasEstadoNumero men").getResultList());
		return res;
	}

	public ArrayList<MetricasEstadoNumero> selectMetricasEstadoDeNumero(Integer internalId) {		
		Query q = em.createQuery("SELECT men FROM MetricasEstadoNumero men WHERE men.id.internalId=:arg1");
		q.setParameter("arg1", internalId);
		ArrayList<MetricasEstadoNumero> res = new ArrayList<MetricasEstadoNumero>(q.getResultList());
		return res;
	}
	
	public ArrayList<MetricasNumero> selectMetricasNumero() {
		ArrayList<MetricasNumero> res = new ArrayList<MetricasNumero>(em.createQuery("SELECT mn FROM MetricasNumero mn").getResultList());
		return res;
	}
	
	public MetricasNumero selectMetricasDeNumero(int internalId) throws RollbackException{
		MetricasNumero mn = em.find(MetricasNumero.class, internalId);
		if (mn != null){
			return mn;
		}
		else{
			throw new RollbackException("No hay metricas para el Numero con codigo " + internalId);
		}
    }
	
}
