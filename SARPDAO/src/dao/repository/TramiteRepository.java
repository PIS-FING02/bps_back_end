package dao.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import java.util.LinkedList;
import java.util.List;

import model.DatosComplementario;
import model.Numero;
import model.Sector;
import model.Tramite;

public class TramiteRepository {
	
	private static EntityManager em;
	
	private EntityManager getEntityManagerInstance(){
		if(em == null){
			EntityManagerFactory factory = Persistence.createEntityManagerFactory("postgresUnit");
			em = factory.createEntityManager();
		}
		return em;
	}
	
	
	public void crearTramite(Sector s,String nombre){
		EntityManager em = getEntityManagerInstance();
		System.out.println("hola desde crearTramite10");
		Tramite t = new Tramite();
		System.out.println("hola desde crearTramite101");
		System.out.println("hola desde crearTramite102" + s.getNombre());
		System.out.println("hola desde crearTramite103");
		System.out.println("hola desde crearTramite11");
		s.getTramites().add(t);
		System.out.println("hola desde crearTramite12");
		t.setNombre(nombre);
		t.getSectors().add(s);
		System.out.println("hola desde crearTramite13");
		em.getTransaction().begin();
		System.out.println("hola desde crearTramite14");
		em.persist(s);
		em.persist(t);
		em.getTransaction().commit();
	}
	
	public Tramite obtenerTramite(int codigo){
		EntityManager em = getEntityManagerInstance();
		return em.find(Tramite.class, codigo);
	}
	
	public List<Tramite> listarTramites() {
		EntityManager em = getEntityManagerInstance();
		return (List<Tramite>) em.createQuery("select t from Tramite t").getResultList();
	}
	
	
	
}
