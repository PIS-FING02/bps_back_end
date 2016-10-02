package com.sarp.test.dao;

import org.junit.Test;

import com.sarp.classes.BusinessDisplay;
import com.sarp.classes.BusinessSector;
import com.sarp.dao.controllers.DAODisplayController;
import com.sarp.dao.controllers.DAONumeroController;
import com.sarp.dao.controllers.DAOPuestoController;
import com.sarp.dao.controllers.DAOSectorController;
import com.sarp.dao.controllers.DAOTramiteController;
import com.sun.org.apache.bcel.internal.generic.DADD;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.OptimisticLockException;
import javax.persistence.RollbackException;

public class DisplayTest {
	private static DAODisplayController ctrlDisplay;
	private static List<Integer> id;
	
	@BeforeClass
    public static void setUpClassDisplayTest(){   
		ctrlDisplay = new DAODisplayController();
		id = new ArrayList<Integer>();
		for(int i = 0; i < 3; i++){
	        BusinessDisplay d = new BusinessDisplay();
	        d.setRutaArchivo("RUTAEJEMPLO" + i);
	        id.add(ctrlDisplay.crearDisplay(d));
		}
    }
    
   @AfterClass
   public static void tearDownClassDisplayTest() throws Exception {
	   for(int i = 0; i < id.size(); i++){
		   Integer idd = id.get(i);
		   ctrlDisplay.eliminarDisplay(idd);
	   }
   }
   
   @Test
   public void testModificarDisplay() throws Exception {	      	   
      BusinessDisplay d = ctrlDisplay.obtenerDisplay(id.get(0));
      assertEquals(d.getRutaArchivo(), "RUTAEJEMPLO0");  
      
      /* La modifico y la obtengo nuevamente */
      d.setRutaArchivo("cambio");
      ctrlDisplay.modificarDisplay(d);
      BusinessDisplay d3 = ctrlDisplay.obtenerDisplay(id.get(0));
      assertEquals(d3.getRutaArchivo(), "cambio");     
   }
   
   @Test(expected=RollbackException.class)
   public void testOptimisticLockDisplay() throws Exception{
	   BusinessDisplay d1 = ctrlDisplay.obtenerDisplay(id.get(1));	
	   BusinessDisplay d2 = ctrlDisplay.obtenerDisplay(id.get(1));
	   d1.setRutaArchivo("otzroaassaq11");		
	   d2.setRutaArchivo("otro212");
	   ctrlDisplay.modificarDisplay(d1);					
	   ctrlDisplay.modificarDisplay(d2);
   }
   
   @Test
   public void testListarDisplays() throws Exception{
	   List<BusinessDisplay> lista = ctrlDisplay.listarDisplays();
	   BusinessDisplay d = ctrlDisplay.obtenerDisplay(id.get(2));
	   boolean esta = false;
	   for(BusinessDisplay it : lista){
		   if(it.getCodigo() == d.getCodigo()){
			   esta = true;
		   }
	   }
	   assertEquals(esta, true);
   }
   
  
}