package com.sarp.test.dao;

import org.junit.Test;
import com.sarp.classes.BusinessDisplay;
import com.sarp.classes.BusinessPuesto;
import com.sarp.classes.BusinessSector;
import com.sarp.classes.BusinessTramite;
import com.sarp.dao.controllers.DAOSectorController;
import com.sarp.dao.factory.DAOServiceFactory;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.OptimisticLockException;
import javax.persistence.RollbackException;

public class SectorTest {
	private static DAOSectorController ctrlSector;
	private static List<String> id;	
	
	@BeforeClass
    public static void setUpClassSectorTest(){  
		ctrlSector = DAOServiceFactory.getInstance().getDAOSectorController();
		id = new ArrayList<String>();
		for(int i = 0; i < 9; i++){
			BusinessSector s = new BusinessSector();		
			String idS = "idsectortest" + i;
			s.setSectorId(idS);
	        ctrlSector.crearSector(s);
	        id.add(idS);
		}
    }
   
   @AfterClass
   public static void tearDownClassSectorTest() throws Exception {
	   for(int i = 0; i < id.size(); i++){
		   String ids = id.get(i);
		   ctrlSector.eliminarSector(ids);
	   }
   }
   
   @Test(expected=RollbackException.class)
   public void testCrearSectorInvalido() throws RollbackException {
	   BusinessSector s = new BusinessSector(); //sin id
	   ctrlSector.crearSector(s);
   }
   
   @Test(expected=RollbackException.class)
   public void testCrearSectorInvalido2() throws RollbackException {
	   BusinessSector s = new BusinessSector(); //sin id
	   s.setSectorId("");
	   ctrlSector.crearSector(s);
   }
   
   @Test(expected=RollbackException.class)
   public void testCrearSectorInvalido3() throws RollbackException {
	   BusinessSector s = new BusinessSector();
	   s.setSectorId("idsectortest0"); //id repetido
	   ctrlSector.crearSector(s);

   }
    
   @Test
   public void testCrearSector() throws Exception {
	   BusinessSector s = new BusinessSector();
	   s.setNombre("nombretest");
	   s.setSectorId("testcrearpuesto2");
	   s.setRuta("rutaej");
	   ctrlSector.crearSector(s);
	   BusinessSector s2 = ctrlSector.obtenerSector("testcrearpuesto2");
	   assertEquals(s2.getNombre(), s.getNombre());
	   assertEquals(s2.getRuta(), "rutaej");
	   assertEquals(s.getSectorId(), "testcrearpuesto2");
	   ctrlSector.eliminarSector("testcrearpuesto2");
   }
   
   @Test
   public void testCrearSector2() throws Exception {
	   BusinessSector s = new BusinessSector();
	   s.setSectorId("testcrearpuesto3");
	   ctrlSector.crearSector(s);
	   BusinessSector s2 = ctrlSector.obtenerSector("testcrearpuesto3");
	   assertEquals(s2.getNombre(), null);
	   assertEquals(s2.getRuta(), null);
	   assertEquals(s.getSectorId(), "testcrearpuesto3");
	   ctrlSector.eliminarSector("testcrearpuesto3");
   }
   
   @Test
   public void testListarSectores(){
	   System.out.println("\nSectores:");
	   List<BusinessSector> lista = ctrlSector.listarSectores();
	   for(BusinessSector s : lista){
		   System.out.println("Sector: " + s.getSectorId() + "-" + s.getNombre() + "-" + s.getRuta());
	   }
   }
   

   @Test
   public void testModificarSectorValido() throws Exception {
	   BusinessSector s = ctrlSector.obtenerSector("idsectortest1"); 
	   assertEquals(s.getSectorId(), "idsectortest1");
	   s.setRuta("rutaejemplo");
	   s.setNombre("nombretest");
	   ctrlSector.modificarSector(s);
	   BusinessSector s2 = ctrlSector.obtenerSector("idsectortest1");
	   assertEquals(s2.getSectorId(), "idsectortest1"); 
	   assertEquals(s2.getRuta(), "rutaejemplo"); 
	   assertEquals(s2.getNombre(), "nombretest"); 
   }
   
   @Test(expected=RollbackException.class)
   public void testModificarSectorInvalido() throws Exception {
	  BusinessSector s = new BusinessSector();
	  s.setSectorId("idquenoexiste"); //id que no existe
	  s.setNombre("nombre");
	  ctrlSector.modificarSector(s);
   }
   
   @Test(expected=RollbackException.class)
   public void testModificarSectorInvalido2() throws Exception {
	  BusinessSector s = ctrlSector.obtenerSector("idsectortest2"); //id valido
	  s.setSectorId("otroidquenoexiste");
	  ctrlSector.modificarSector(s);
   }
   
   @Test(expected=RollbackException.class)
   public void testEliminarSectorInvalido() throws Exception{
	   ctrlSector.eliminarSector("idquenoexiste");
   }
   
   @Test()
   public void testAsociarTramiteSector() throws Exception{
	   String id = "idsectortest3";
	   ctrlSector.asociarTramiteSector(1, id);
	   List<BusinessTramite> l = ctrlSector.obtenerTramitesSector(id);
	   assertEquals(l.get(0).getCodigo() == 1, true);
	   ctrlSector.desasociarTramiteSector(1, id);
	   l = ctrlSector.obtenerTramitesSector(id);
	   assertEquals(l.size() == 0, true);  
   }
   
   @Test(expected=RollbackException.class)
   public void testAsociarTramiteSectorInvalido() throws Exception{
	   ctrlSector.asociarTramiteSector(456546, "idsectortest4");
   }
   
   @Test(expected=RollbackException.class)
   public void testAsociarTramiteSectorInvalido2() throws Exception{
	   ctrlSector.asociarTramiteSector(2, "sectorquenoexiste");
   }
   
   @Test(expected=RollbackException.class)
   public void testAsociarTramiteSectorInvalido3() throws Exception{
	   ctrlSector.asociarTramiteSector(2, "idsectortest4");
	   ctrlSector.asociarTramiteSector(2, "idsectortest4");
   }
   
   @Test(expected=RollbackException.class)
   public void desasociarTramiteSectorInvalido() throws Exception{
	   ctrlSector.desasociarTramiteSector(3, "4"); //No asociados entre si
   }
   
   @Test(expected=RollbackException.class)
   public void testAsociarDisplaySectorInvalido() throws Exception{
	   ctrlSector.asociarDisplaySector("idsectortest3", "iddisplay7");
	   ctrlSector.asociarDisplaySector("idsectortest3", "iddisplay7");	  
   }
   
   @Test()
   public void testAsociarDisplaySector() throws Exception{
	   String id = "idsectortest5";
	   ctrlSector.asociarDisplaySector(id, "iddisplay1");
	   List<BusinessDisplay> l = ctrlSector.obtenerDisplaysSector(id);
	   assertEquals(l.get(0).getIdDisplay(), "iddisplay1");
	   ctrlSector.desasociarDisplaySector(id, "iddisplay1");
	   l = ctrlSector.obtenerDisplaysSector(id);
	   assertEquals(l.size() == 0, true); 
   }
   
   @Test(expected=RollbackException.class)
   public void testDesociarDisplaySectorInvalido() throws Exception{
	   ctrlSector.desasociarDisplaySector("idsectortest6", "iddisplay4");
   }
   
   @Test()
   public void testAsociarPuestoSector() throws Exception{
	   String id = "idsectortest6";
	   ctrlSector.asociarSectorPuesto(id, "NombreMaquina3");
	   List<BusinessPuesto> l = ctrlSector.obtenerPuestosSector(id);
	   assertEquals(l.get(0).getNombreMaquina(), "NombreMaquina3");
	   ctrlSector.desasociarSectorPuesto(id, "NombreMaquina3");
	   l = ctrlSector.obtenerPuestosSector(id);
	   assertEquals(l.size() == 0, true); 
   }
   
   @Test(expected=RollbackException.class)
   public void testAsociarPuestoSectorInvalido1() throws Exception{
	   ctrlSector.asociarSectorPuesto("idsectortest6", "maquinaquenoexiste"); //Puesto invalido
   }
   
   @Test(expected=RollbackException.class)
   public void testAsociarPuestoSectorInvalido2() throws Exception{
	   ctrlSector.asociarSectorPuesto("sectorquenoexiste", "NombreMaquina2"); //Sector invalido
   }
   
   @Test(expected=RollbackException.class)
   public void testAsociarPuestoSectorInvalido3() throws Exception{
		   ctrlSector.asociarSectorPuesto("idsectortest6", "NombreMaquina4");   
		   ctrlSector.asociarSectorPuesto("idsectortest6", "NombreMaquina4"); //Los asocio 2 veces
   }
   
   
   @Test(expected=RollbackException.class)
   public void testDesasociarPuestoSectorInvalido1() throws Exception{
	   ctrlSector.desasociarSectorPuesto("idsectortest6", "NombreMaquina2"); //No asociados entre si
   }
  
   @Test(expected=RollbackException.class)
   public void testDesasociarPuestoSectorInvalido2() throws Exception{
	   ctrlSector.desasociarSectorPuesto("sectorquenoexiste", "NombreMaquina2"); //Sector invalido
   }    
   
   @Test
   public void testOptimisticLockSector(){
	   try{
		   System.out.println("\testOptimisticLockSector");	 
		   BusinessSector s1 = ctrlSector.obtenerSector(id.get(6));
		   BusinessSector s2 = ctrlSector.obtenerSector(id.get(6));
		   s1.setNombre("cambioelnombre");
		   s2.setNombre("cambiodevuelta");
		   ctrlSector.modificarSector(s1);
		   ctrlSector.modificarSector(s2);
		   assertEquals(true, false);
	   }
	   catch(RollbackException e){
		   assertEquals(e.getCause() instanceof OptimisticLockException, true);
	   }
   }
  
}