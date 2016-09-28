package com.sarp.dao.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.sarp.classes.BusinessDisplay;
import com.sarp.classes.BusinessSector;
import com.sarp.dao.controllers.DAODisplayController;
import com.sarp.dao.controllers.DAONumeroController;
import com.sarp.dao.controllers.DAOPuestoController;
import com.sarp.dao.controllers.DAOSectorController;
import com.sarp.dao.controllers.DAOTramiteController;


public class SectorTest {

	DAOSectorController ctrlSector = new DAOSectorController();
   
//   @Test(expected=javax.persistence.RollbackException.class)
//   public void testCrearInvalido() throws Exception  {	
//	   BusinessSector s = new BusinessSector();
//	   ctrlSector.crearSector(s);
//   }
   
   @Test
   public void testSector() throws Exception{
	   int cantidad1 = ctrlSector.listarSectores().size();
	   /* Creo una nueva entidad, la persisto, la modifico y la elimino */
	   try{
		   String codigoTest = "123456789";
		   BusinessSector s1 = new BusinessSector();
		   s1.setSectorId(codigoTest);
		   s1.setRuta("ruta");
		   s1.setNombre("nombre");
		   ctrlSector.crearSector(s1);
		   BusinessSector s2 = ctrlSector.obtenerSector(codigoTest);
		   assertEquals(s1.getNombre() + s1.getRuta(), s2.getNombre() + s2.getRuta());
		   ctrlSector.eliminarSector(codigoTest);
	   }
	   catch(Exception ex){
		   System.out.println("Ya existe");
	   }
	   
	   	  
   }
   
  
}