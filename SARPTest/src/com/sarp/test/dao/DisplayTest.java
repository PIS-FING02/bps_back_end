package com.sarp.test.dao;

import org.junit.Test;
import com.sarp.classes.BusinessDisplay;
import com.sarp.dao.controllers.DAODisplayController;
import com.sarp.dao.factory.DAOServiceFactory;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.OptimisticLockException;
import javax.persistence.RollbackException;

public class DisplayTest {
	private static DAODisplayController ctrlDisplay;
	private static List<String> id;	
	
	@BeforeClass
    public static void setUpClassDisplayTest(){   
		ctrlDisplay = DAOServiceFactory.getInstance().getDAODisplayController();
		id = new ArrayList<String>();
		for(int i = 0; i < 4; i++){
	        BusinessDisplay d = new BusinessDisplay();
	        d.setIdDisplay("iddisplaytest" + i);
	        id.add(ctrlDisplay.crearDisplay(d));
		}
    }
    
   @AfterClass
   public static void tearDownClassDisplayTest() throws Exception {
	   for(int i = 0; i < id.size(); i++){
		   String idd = id.get(i);
		   ctrlDisplay.eliminarDisplay(idd);
	   }
   }
   
   @Test(expected=RollbackException.class)
   public void testCrearDisplayInvalido() throws Exception {
	   BusinessDisplay d = new BusinessDisplay();
	   ctrlDisplay.crearDisplay(d);
   }
   
   @Test
   public void testCrearDisplay1() throws Exception {
	   BusinessDisplay d = new BusinessDisplay();
	   d.setIdDisplay("998789789");
	   String id = ctrlDisplay.crearDisplay(d);
	   BusinessDisplay d2 = ctrlDisplay.obtenerDisplay(id);
	   assertEquals(d2.getIdDisplay(), "998789789");
	   ctrlDisplay.eliminarDisplay(id);
   }
   
   @Test
   public void testObtenerDisplayValido() throws Exception {
	   BusinessDisplay d = ctrlDisplay.obtenerDisplay(id.get(0));
	   assertEquals(d.getIdDisplay(), id.get(0));
   }
   
   @SuppressWarnings("unused")
   @Test(expected=RollbackException.class)
   public void testObtenerDisplayInvalido() throws Exception {
	   BusinessDisplay d = ctrlDisplay.obtenerDisplay("999999");
   }   
   
//   @Test
//   public void testModificarDisplayValido() throws Exception {
//      BusinessDisplay d = ctrlDisplay.obtenerDisplay(id.get(2));
//      assertEquals(d.getRutaArchivo(), "RUTAEJEMPLO2");  
//      
//      d.setRutaArchivo("cambio");
//      ctrlDisplay.modificarDisplay(d);
//      BusinessDisplay d3 = ctrlDisplay.obtenerDisplay(id.get(2));
//      assertEquals(d3.getRutaArchivo(), "cambio");     
//   }
//   
//   @Test(expected=RollbackException.class)
//   public void testModificarDisplayInvalido() throws Exception {
//	  System.out.println("\nModificarDisplay2");
//      BusinessDisplay d = new BusinessDisplay();
//      d.setCodigo(98789789);   
//      d.setRutaArchivo("cambio");
//      ctrlDisplay.modificarDisplay(d);
//   }
    
   @Test
   public void testListarDisplays() throws Exception{
	   System.out.println("\nDisplays:");
	   List<BusinessDisplay> lista = ctrlDisplay.listarDisplays();
	   for(BusinessDisplay d : lista){
		   System.out.println("Display: " + d.getIdDisplay());
	   }
   }
   
   @Test(expected=RollbackException.class)
   public void testEliminarDisplayInvalido(){
	   ctrlDisplay.eliminarDisplay("99789789");
   }
   
//   @Test
//   public void testOptimisticLockDisplay(){
//	   try{
//		   System.out.println("\nOptimisticLockDisplay");	  
//		   BusinessDisplay d1 = ctrlDisplay.obtenerDisplay(id.get(2));	
//		   BusinessDisplay d2 = ctrlDisplay.obtenerDisplay(id.get(2));
//		   ctrlDisplay.modificarDisplay(d1);					
//		   ctrlDisplay.modificarDisplay(d2);
//		   assertEquals(true, false);
//	   }
//	   catch(RollbackException e){
//		   assertEquals(e.getCause() instanceof OptimisticLockException, true);
//	   }
//   }
   
  
}