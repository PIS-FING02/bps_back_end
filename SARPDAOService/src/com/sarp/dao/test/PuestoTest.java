package com.sarp.dao.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.sarp.classes.BusinessDisplay;
import com.sarp.classes.BusinessNumero;
import com.sarp.classes.BusinessPuesto;
import com.sarp.dao.controllers.DAONumeroController;
import com.sarp.dao.controllers.DAOPuestoController;import com.sarp.enumerados.EstadoPuesto;

public class PuestoTest {

	DAOPuestoController ctrlPuesto = new DAOPuestoController();
	DAONumeroController ctrlNumero = new DAONumeroController();
   
	@Test
	public void testPuesto() throws Exception {	
		//int cantidad1 = ctrlPuesto.listarPuestos().size();
		BusinessPuesto p1 = new BusinessPuesto();
		String id = "zzzzzzzzzaaaaa";
		p1.setNombreMaquina(id);
		p1.setUsuarioId("usuario");
		p1.setEstado(EstadoPuesto.DIPONIBLE);
		ctrlPuesto.crearPuesto(p1);
	
		BusinessPuesto p2 = ctrlPuesto.obtenerPuesto(id);
		assertEquals(p1.getUsuarioId(), p2.getUsuarioId());
		
	    p2.setUsuarioId("usuario2");
	    ctrlPuesto.modificarPuesto(p2);
	    BusinessPuesto p3 = ctrlPuesto.obtenerPuesto(id);
	    assertEquals(p3.getUsuarioId(), p2.getUsuarioId());
	    ctrlPuesto.eliminarPuesto(id);
	    System.out.println("OK");
	}
	
	@Test
	public void testPuestoNumero() throws Exception {	
		//int cantidad1 = ctrlPuesto.listarPuestos().size();
		BusinessPuesto p1 = new BusinessPuesto();
		String id ="iddeprueba";
		p1.setNombreMaquina(id);
		p1.setUsuarioId("usuario");
		p1.setEstado(EstadoPuesto.DIPONIBLE);
		ctrlPuesto.crearPuesto(p1);
			
		ctrlPuesto.asociarNumeroPuesto(id, 9);		
		List<BusinessNumero> a = ctrlPuesto.obtenerNumerosPuesto(id);		
		ctrlPuesto.eliminarPuesto(id);
	}
	
	@Test
	public void testPuestoNumeroActual() throws Exception {	
		//int cantidad1 = ctrlPuesto.listarPuestos().size();
		BusinessPuesto p1 = new BusinessPuesto();
		String id ="iddeprueba4";
		p1.setNombreMaquina(id);
		p1.setUsuarioId("usuario");
		p1.setEstado(EstadoPuesto.DIPONIBLE);
		ctrlPuesto.crearPuesto(p1);
			
		ctrlPuesto.asociarNumeroPuestoActual(id, 9);
		BusinessNumero a = ctrlPuesto.obtenerNumeroActualPuesto(id);
		ctrlPuesto.desasociarNumeroPuestoActual(id);

		ctrlPuesto.eliminarPuesto(id);
		assertEquals(a.getInternalId().toString(), "9");
		
	}
	
	
	
	
   
  
}