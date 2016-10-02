package com.sarp.test.dao;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sarp.classes.BusinessSector;
import com.sarp.dao.controllers.DAOSectorController;


public class SectorTest {

	private static DAOSectorController ctrlSector;
   
	@BeforeClass
    public static void setUpClass(){   
		ctrlSector = new DAOSectorController(); 
        System.out.print("antes");
    }
    
    @AfterClass
    public static void tearDownClass() {
    	System.out.print("despues");
    }
   
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