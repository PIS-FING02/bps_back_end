package com.sarp.test.dao;

import org.junit.Test;

import com.sarp.classes.BusinessDisplay;
import com.sarp.classes.BusinessNumero;
import com.sarp.classes.BusinessPuesto;
import com.sarp.classes.BusinessSector;
import com.sarp.classes.BusinessTramite;
import com.sarp.dao.controllers.DAONumeroController;
import com.sarp.dao.controllers.DAOPuestoController;
import com.sarp.dao.controllers.DAOSectorController;
import com.sarp.enumerados.EstadoPuesto;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.RollbackException;

public class SectorTest {
	private static DAOSectorController ctrlSector;
	private static List<String> id;	
	
	@BeforeClass
    public static void setUpClassSectorTest(){  
		ctrlSector = new DAOSectorController();	
		id = new ArrayList<String>();
		for(int i = 0; i < 7; i++){
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
	   ctrlSector.crearSector(s);
	   BusinessSector s2 = ctrlSector.obtenerSector("testcrearpuesto2");
	   assertEquals(s2.getNombre(), s.getNombre());
	   ctrlSector.eliminarSector("testcrearpuesto2");
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
	   ctrlSector.modificarSector(s);
	   BusinessSector s2 = ctrlSector.obtenerSector("idsectortest1");
	   assertEquals(s2.getRuta(), "rutaejemplo"); 
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
   
   @Test()
   public void testAsociarDisplaySector() throws Exception{
	   String id = "idsectortest5";
	   ctrlSector.asignarDisplaySector(id, 1);
	   BusinessDisplay d = ctrlSector.obtenerDisplaySector(id);
	   assertEquals(d.getCodigo() == 1, true);  
   }
   
   @Test()
   public void testAsociarPuestoSector() throws Exception{
	   String id = "idsectortest6";
	   ctrlSector.asociarSectorPuesto(id, "maquina3");
	   List<BusinessPuesto> l = ctrlSector.obtenerPuestosSector(id);
	   assertEquals(l.get(0).getNombreMaquina(), "maquina3");
	   ctrlSector.desasociarSectorPuesto(id, "maquina3");
	   l = ctrlSector.obtenerPuestosSector(id);
	   assertEquals(l.size() == 0, true); 
   }
   
   @Test(expected=RollbackException.class)
   public void testAsociarPuestoSectorInvalido1() throws Exception{
	   ctrlSector.asociarSectorPuesto("idsectortest6", "maquinaquenoexiste"); //Puesto invalido
   }
   
   @Test(expected=RollbackException.class)
   public void testAsociarPuestoSectorInvalido2() throws Exception{
	   ctrlSector.asociarSectorPuesto("sectorquenoexiste", "maquina2"); //Sector invalido
   }
   
   @Test(expected=RollbackException.class)
   public void testAsociarPuestoSectorInvalido3() throws Exception{
	   ctrlSector.asociarSectorPuesto("idsectortest6", "maquina4");
	   ctrlSector.asociarSectorPuesto("idsectortest6", "maquina4"); //Los asocio 2 veces
   }
   
   @Test(expected=RollbackException.class)
   public void testDesasociarPuestoSectorInvalido1() throws Exception{
	   ctrlSector.desasociarSectorPuesto("idsectortest6", "maquina1"); //No asociados entre si
   }
   
   @Test(expected=RollbackException.class)
   public void testDesasociarPuestoSectorInvalido2() throws Exception{
	   ctrlSector.desasociarSectorPuesto("sectorquenoexiste", "maquina2"); //Sector invalido
   }    
  
}