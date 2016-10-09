package com.sarp.test.dao;

import org.junit.Test;

import com.sarp.classes.BusinessNumero;
import com.sarp.classes.BusinessPuesto;
import com.sarp.classes.BusinessSector;
import com.sarp.classes.BusinessTramite;
import com.sarp.dao.controllers.DAOPuestoController;
import com.sarp.dao.controllers.DAOSectorController;
import com.sarp.dao.controllers.DAOTramiteController;
import com.sarp.enumerados.EstadoPuesto;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.RollbackException;

public class PuestoTest {
	private static DAOPuestoController ctrlPuesto;
	private static DAOSectorController ctrlSector;
	private static DAOTramiteController ctrlTramite;
	private static List<String> id;	
	
	@BeforeClass
    public static void setUpClassPuestoTest(){  
		ctrlPuesto = new DAOPuestoController();	
		ctrlSector = new DAOSectorController();
		ctrlTramite = new DAOTramiteController();
		id = new ArrayList<String>();
		for(int i = 0; i < 7; i++){
			BusinessPuesto p = new BusinessPuesto();
			String idP = "nombremaquinatest" + i;
			p.setNombreMaquina(idP);
	        ctrlPuesto.crearPuesto(p);
	        id.add(idP);
		}
    }
   
   @AfterClass
   public static void tearDownClassPuestoTest() throws Exception {
	   for(int i = 0; i < id.size(); i++){
		   String idp = id.get(i);
		   ctrlPuesto.eliminarPuesto(idp);
	   }
   }
   
   @Test(expected=RollbackException.class)
   public void testCrearPuestoInvalido() throws RollbackException {
	   BusinessPuesto p = new BusinessPuesto();
	   ctrlPuesto.crearPuesto(p);
   }
   
   @Test(expected=RollbackException.class)
   public void testCrearPuestoInvalido2() throws RollbackException {
	   BusinessPuesto p = new BusinessPuesto();
	   p.setNombreMaquina("");
	   ctrlPuesto.crearPuesto(p);
   }
   
   @Test(expected=RollbackException.class)
   public void testCrearPuestoInvalido3() throws RollbackException {
	   BusinessPuesto p = new BusinessPuesto();
	   p.setNombreMaquina("nombremaquinatest0");
	   ctrlPuesto.crearPuesto(p);
   }
    
   @Test
   public void testCrearPuesto2() throws Exception {
	   BusinessPuesto p = new BusinessPuesto();
	   p.setNombreMaquina("nombretest2");
	   ctrlPuesto.crearPuesto(p);
	   BusinessPuesto p2 = ctrlPuesto.obtenerPuesto("nombretest2");
	   assertEquals(p2.getNombreMaquina(), p.getNombreMaquina());
	   ctrlPuesto.eliminarPuesto("nombretest2");
   }
   
   @Test
   public void testListarPuestos(){
	   List<BusinessPuesto> lista = ctrlPuesto.listarPuestos();
   }
   
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
	   ArrayList<BusinessNumero> a = ctrlPuesto.obtenerNumerosPuesto("nombremaquinatest0");
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
   
   @Test(expected=RollbackException.class)
   public void testDesasociarNumeroPuestoInvalido() throws Exception{
	   ctrlPuesto.desasociarNumeroPuesto("nombremaquinatest3", 3);
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
   
   @Test(expected=RollbackException.class)
   public void testOptimisticLockPuesto() throws Exception{
	   System.out.println("\nOptimisticLockPuesto");
	   BusinessPuesto p1 = ctrlPuesto.obtenerPuesto("nombremaquinatest5");
	   BusinessPuesto p2 = ctrlPuesto.obtenerPuesto("nombremaquinatest5");
	   p1.setEstado(EstadoPuesto.ATENDIENDO);
	   p2.setEstado(EstadoPuesto.LLAMANDO);	
	   ctrlPuesto.modificarPuesto(p1);
	   ctrlPuesto.modificarPuesto(p2);
   }
   
   @Test
   public void testObtenerSectoresYTramitesPuesto() throws Exception{
	   int t1 = 0;
	   int t2 = 0;
	   try{
		   BusinessPuesto p = new BusinessPuesto();	   
		   p.setNombreMaquina("puestotest");
		   ctrlPuesto.crearPuesto(p);	   
		   BusinessSector s = new BusinessSector();
		   s.setSectorId("sectortest10");
		   ctrlSector.crearSector(s);
		   s.setSectorId("sectortest11");
		   ctrlSector.crearSector(s);
		   BusinessTramite t = new BusinessTramite();
		   t1 = ctrlTramite.crearTramite(t);
		   t2 = ctrlTramite.crearTramite(t);
		   
		   ctrlSector.asociarSectorPuesto("sectortest10", "puestotest");
		   ctrlSector.asociarSectorPuesto("sectortest11", "puestotest");
		   ArrayList<BusinessSector> l = ctrlPuesto.obtenerSectoresPuesto("puestotest");
		   System.out.println("Sectores del puesto puestotest:");
		   for(BusinessSector se : l){
			   System.out.println("Sector: " + se.getSectorId());
		   }
		   
		   ctrlTramite.asociarTramitePuesto(t1, "puestotest");
		   ctrlTramite.asociarTramitePuesto(t2, "puestotest");
		   List<BusinessTramite> lt = ctrlPuesto.obtenerTramitesPuesto("puestotest");
		   System.out.println("Tramites del puesto puestotest:");
		   for(BusinessTramite tb : lt){
			   System.out.println("Tramite: " + tb.getCodigo());
		   }
		   
		   ctrlSector.asociarTramiteSector(t1, "sectortest10");
		   ArrayList<BusinessTramite> a = ctrlPuesto.obtenerTramitesDeSector("puestotest", "sectortest10");
		   assertEquals(a.size() == 1, true);
		   assertEquals(a.get(0).getCodigo() == t1, true);
	   }
	   finally{
		   ctrlPuesto.eliminarPuesto("puestotest");
		   ctrlSector.eliminarSector("sectortest10");
		   ctrlSector.eliminarSector("sectortest11");
		   ctrlTramite.eliminarTramite(t1);
		   ctrlTramite.eliminarTramite(t2);
	   }   
   }
   
   @Test(expected=RollbackException.class)
   public void testobtenerTramitesDeSectorInvalido() throws Exception{
	   ctrlPuesto.obtenerTramitesDeSector("nombremaquinatest6", "4");
   }
   

   
  
}