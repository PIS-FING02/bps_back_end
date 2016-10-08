package com.sarp.test.dao;

import org.junit.Test;

import com.sarp.classes.BusinessNumero;
import com.sarp.classes.BusinessPuesto;
import com.sarp.classes.BusinessSector;
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
		for(int i = 0; i < 6; i++){
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
	   BusinessSector s = new BusinessSector();
	   ctrlSector.crearSector(s);
   }
   
   @Test(expected=RollbackException.class)
   public void testCrearSectorInvalido2() throws RollbackException {
	   BusinessSector s = new BusinessSector();
	   s.setSectorId("idsectortest0");
	   ctrlSector.crearSector(s);
   }
    
   @Test
   public void testCrearSector2() throws Exception {
	   BusinessSector s = new BusinessSector();
	   s.setNombre("nombretest");
	   s.setSectorId("testcrearpuesto2");
	   ctrlSector.crearSector(s);
	   BusinessSector s2 = ctrlSector.obtenerSector("testcrearpuesto2");
	   assertEquals(s2.getNombre(), s.getNombre());
	   ctrlSector.eliminarSector("testcrearpuesto2");
   }
   
   @Test
   public void testListarPuestos(){
	   List<BusinessSector> lista = ctrlSector.listarSectores();
   }
   
   /* TODO
   @Test
   public void testObtenerPuestoValido() throws Exception {
	   BusinessPuesto p = ctrlPuesto.obtenerPuesto(id.get(0));
	   assertEquals(p.getNombreMaquina(), "nombremaquinatest0");
   }
   
   @Test(expected=RollbackException.class)
   public void testObtenerPuestoInvalido() throws Exception {
	   BusinessPuesto p = ctrlPuesto.obtenerPuesto("idquenoexiste");
   }   
   
   @Test
   public void testModificarPuestoValido() throws Exception {
      BusinessPuesto p = ctrlPuesto.obtenerPuesto(id.get(1));
      assertEquals(p.getNombreMaquina(), "nombremaquinatest1");  
      p.setUsuarioId("usuarioid");
  
      ctrlPuesto.modificarPuesto(p);
      BusinessPuesto p2 = ctrlPuesto.obtenerPuesto("nombremaquinatest1");
      assertEquals(p2.getUsuarioId(), "usuarioid");     
   }
   
   @Test(expected=RollbackException.class)
   public void testModificarPuestoInvalido() throws Exception {
      BusinessPuesto p = new BusinessPuesto();
      p.setNombreMaquina("nombrequenoexiste");
      ctrlPuesto.modificarPuesto(p);
   }
   
   @Test(expected=RollbackException.class)
   public void testEliminarPuestoInvalido() throws Exception{
	   ctrlPuesto.eliminarPuesto("nombrequenoexiste");
   }
   
   @Test()
   public void testAsociarNumeroPuesto() throws Exception{
	   ctrlPuesto.asociarNumeroPuesto("nombremaquinatest0", 1);
	   List<BusinessNumero> a = ctrlPuesto.obtenerNumerosPuesto("nombremaquinatest0");
	   assertEquals(a.size() > 0, true);
	   ctrlPuesto.desasociarNumeroPuesto("nombremaquinatest0", 1);
	   a = ctrlPuesto.obtenerNumerosPuesto("nombremaquinatest0");
	   assertEquals(a.size() == 0, true);   
   }
   
   @Test(expected=RollbackException.class)
   public void testAsociarNumeroPuestoInvalido() throws Exception{
	   ctrlPuesto.asociarNumeroPuesto("nombremaquinatest0", 981);
   }
   
   @Test(expected=RollbackException.class)
   public void testAsociarNumeroPuestoInvalido2() throws Exception{
	   ctrlPuesto.asociarNumeroPuesto("nombremaquinatest1", 2);
	   ctrlPuesto.asociarNumeroPuesto("nombremaquinatest1", 2);
   }
   
   @Test()
   public void testAsociarNumeroActualPuesto() throws Exception{
	   ctrlPuesto.asociarNumeroPuestoActual("nombremaquinatest2", 3);
	   BusinessNumero n = ctrlPuesto.obtenerNumeroActualPuesto("nombremaquinatest2");
	   assertEquals(n.getInternalId() == 3, true);
	   ctrlPuesto.desasociarNumeroPuestoActual("nombremaquinatest2");
	   n = ctrlPuesto.obtenerNumeroActualPuesto("nombremaquinatest2");
	   assertEquals(n, null);   
   }
   
   @Test(expected=RollbackException.class)
   public void testAsociarNumeroActualPuestoInvalido() throws Exception{
	   ctrlPuesto.asociarNumeroPuestoActual("nombremaquinatest3", 981);
   }
   
   @Test(expected=RollbackException.class)
   public void testAsociarNumeroActualPuestoInvalido2() throws Exception{
	   ctrlPuesto.asociarNumeroPuesto("nombremaquinatest4", 4);
	   ctrlPuesto.asociarNumeroPuesto("nombremaquinatest4", 4);
   }
   */
   

   
  
}