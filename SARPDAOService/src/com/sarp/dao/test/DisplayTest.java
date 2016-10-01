package com.sarp.dao.test;

import org.junit.Test;

import com.sarp.classes.BusinessDisplay;
import com.sarp.classes.BusinessSector;
import com.sarp.dao.controllers.DAODisplayController;
import com.sarp.dao.controllers.DAONumeroController;
import com.sarp.dao.controllers.DAOPuestoController;
import com.sarp.dao.controllers.DAOSectorController;
import com.sarp.dao.controllers.DAOTramiteController;

import org.junit.Ignore;
import static org.junit.Assert.*;
import java.util.List;

public class DisplayTest {

	DAODisplayController ctrlDisplay = new DAODisplayController();
   
   @Test(expected=Exception.class)
   public void testDisplay() throws Exception {	
      int cantidad1 = ctrlDisplay.listarDisplays().size();
      /* Primero creo una nueva entidad y la persisto */
      BusinessDisplay d1 = new BusinessDisplay();
      d1.setRutaArchivo("ruta1");
      int c = ctrlDisplay.crearDisplay(d1);     
      
      /* La obtengo de la base de datos */
      BusinessDisplay d2 = ctrlDisplay.obtenerDisplay(c);
      assertEquals(d1.getRutaArchivo(), d2.getRutaArchivo());  
      
      /* La modifico y la obtengo nuevamente */
      d2.setRutaArchivo("ruta2");
      ctrlDisplay.modificarDisplay(d2);
      BusinessDisplay d3 = ctrlDisplay.obtenerDisplay(d2.getCodigo());
      assertEquals(d3.getRutaArchivo(), "ruta2");
      
      /* Test para el metodo listarDisplays */
      int cantidad2 = ctrlDisplay.listarDisplays().size();
      assertEquals(cantidad1 + 1, cantidad2);      
      ctrlDisplay.eliminarDisplay(c);
      int cantidad3 = ctrlDisplay.listarDisplays().size();
      assertEquals(cantidad1, cantidad3);
      
      /* Eliminar el mismo display 2 veces da exception */
      ctrlDisplay.eliminarDisplay(c);        
   }
   
  
}