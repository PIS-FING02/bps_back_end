package com.sarp.test.dao;

import static org.junit.Assert.assertEquals;

import java.util.GregorianCalendar;

import org.junit.Test;

import com.sarp.classes.BusinessDatoComplementario;
import com.sarp.classes.BusinessDisplay;
import com.sarp.classes.BusinessNumero;
import com.sarp.classes.BusinessPuesto;
import com.sarp.classes.BusinessTramite;
import com.sarp.dao.controllers.DAONumeroController;
import com.sarp.dao.controllers.DAOPuestoController;
import com.sarp.dao.controllers.DAOTramiteController;


public class NumeroTest {

	DAONumeroController ctrlNumero = new DAONumeroController();
	DAOTramiteController ctrlTramite = new DAOTramiteController();
   
	@Test
	public void testNumero() throws Exception {	
	    /* Primero creo una nueva entidad y la persisto */
		BusinessTramite t = new BusinessTramite();
		t.setNombre("tramitedeprueba");
		Integer idTramite = ctrlTramite.crearTramite(t);
		BusinessNumero n = new BusinessNumero();
		n.setHora(new GregorianCalendar());
		Integer id = ctrlNumero.crearNumero(n, idTramite, null);
	    BusinessDisplay d1 = new BusinessDisplay();
	    d1.setRutaArchivo("ruta1");
//	      int c = ctrlDisplay.crearDisplay(d1);     
//	      
//	      /* La obtengo de la base de datos */
//	      BusinessDisplay d2 = ctrlDisplay.obtenerDisplay(c);
//	      assertEquals(d1.getRutaArchivo(), d2.getRutaArchivo());  
//	      
//	      /* La modifico y la obtengo nuevamente */
//	      d2.setRutaArchivo("ruta2");
//	      ctrlDisplay.modificarDisplay(d2);
//	      BusinessDisplay d3 = ctrlDisplay.obtenerDisplay(d2.getCodigo());
//	      assertEquals(d3.getRutaArchivo(), "ruta2");
//	      
//	      /* Test para el metodo listarDisplays */
//	      int cantidad2 = ctrlDisplay.listarDisplays().size();
//	      assertEquals(cantidad1 + 1, cantidad2);      
//	      ctrlDisplay.eliminarDisplay(c);
//	      int cantidad3 = ctrlDisplay.listarDisplays().size();
//	      assertEquals(cantidad1, cantidad3);
//	      
//	      /* Eliminar el mismo display 2 veces da exception */
//	      ctrlDisplay.eliminarDisplay(c);   
		
	}
	

   
  
}