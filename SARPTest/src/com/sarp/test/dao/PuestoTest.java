package com.sarp.test.dao;

import org.junit.Test;

import com.sarp.classes.BusinessMetricasPuesto;
import com.sarp.classes.BusinessNumero;
import com.sarp.classes.BusinessPuesto;
import com.sarp.classes.BusinessSector;
import com.sarp.classes.BusinessTramite;
import com.sarp.dao.controllers.DAOPuestoController;
import com.sarp.dao.controllers.DAOSectorController;
import com.sarp.dao.controllers.DAOTramiteController;
import com.sarp.dao.factory.DAOServiceFactory;
import com.sarp.enumerados.EstadoPuesto;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.OptimisticLockException;
import javax.persistence.RollbackException;

public class PuestoTest {
	private static DAOPuestoController ctrlPuesto;
	private static DAOSectorController ctrlSector;
	private static DAOTramiteController ctrlTramite;
	private static List<String> id;	
	
	@BeforeClass
    public static void setUpClassPuestoTest(){  
		ctrlPuesto = DAOServiceFactory.getInstance().getDAOPuestoController();
		ctrlSector = DAOServiceFactory.getInstance().getDAOSectorController();
		ctrlTramite = DAOServiceFactory.getInstance().getDAOTramiteController();
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
   public void testCrearPuesto() throws Exception {
	   BusinessPuesto p = new BusinessPuesto();
	   p.setNombreMaquina("nombretest2");
	   p.setNumeroPuesto(6);
	   p.setUsuarioId("usuarioidtest");
	   p.setEstado(EstadoPuesto.DISPONIBLE);
	   ctrlPuesto.crearPuesto(p);
	   BusinessPuesto p2 = ctrlPuesto.obtenerPuesto("nombretest2");
	   assertEquals(p2.getNombreMaquina(), p.getNombreMaquina());
	   assertEquals(p2.getNumeroPuesto(), p.getNumeroPuesto());
	   assertEquals(p2.getUsuarioId(), "-");
	   assertEquals(p2.getEstado(),EstadoPuesto.CERRADO);
	   ctrlPuesto.eliminarPuesto("nombretest2");
   }
   
   @Test
   public void testCrearPuesto2() throws Exception {
	   BusinessPuesto p = new BusinessPuesto();
	   p.setNombreMaquina("nombretest3");
	   ctrlPuesto.crearPuesto(p);
	   BusinessPuesto p2 = ctrlPuesto.obtenerPuesto("nombretest3");
	   assertEquals(p2.getNombreMaquina(), "nombretest3");
	   assertEquals(p2.getNumeroPuesto(), null);
	   assertEquals(p2.getUsuarioId(), "-");
	   assertEquals(p2.getEstado(),EstadoPuesto.CERRADO);
	   ctrlPuesto.eliminarPuesto("nombretest3");
   }
   
   @SuppressWarnings("unused")
   @Test
   public void testListarPuestos(){
	   List<BusinessPuesto> lista = ctrlPuesto.listarPuestos();
   }
   
   @Test
   public void testObtenerPuestoValido() throws Exception {
	   BusinessPuesto p = ctrlPuesto.obtenerPuesto(id.get(0));
	   assertEquals(p.getNombreMaquina(), "nombremaquinatest0");
   }
   
   @SuppressWarnings("unused")
   @Test(expected=RollbackException.class)
   public void testObtenerPuestoInvalido() throws Exception {
	   BusinessPuesto p = ctrlPuesto.obtenerPuesto("idquenoexiste");
   }   
   
   @Test
   public void testModificarPuestoValido() throws Exception {
      BusinessPuesto p = ctrlPuesto.obtenerPuesto(id.get(1));
      assertEquals(p.getNombreMaquina(), "nombremaquinatest1");  
      p.setEstado(EstadoPuesto.LLAMANDO);
      p.setNumeroPuesto(8);
      p.setUsuarioId("usuarioidtest");    
      ctrlPuesto.modificarPuesto(p);
      BusinessPuesto p2 = ctrlPuesto.obtenerPuesto("nombremaquinatest1");
      assertEquals(p2.getNombreMaquina(), id.get(1));
      assertEquals(p2.getUsuarioId(), "usuarioidtest");
      assertEquals(p2.getNumeroPuesto() == 8, true);
      assertEquals(p2.getEstado(), EstadoPuesto.LLAMANDO);
      p2.setUsuarioId(null);
      ctrlPuesto.modificarPuesto(p2);
      BusinessPuesto p3 = ctrlPuesto.obtenerPuesto("nombremaquinatest1");
      assertEquals(p3.getUsuarioId(), "-");
      
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
	   ctrlPuesto.asociarNumeroPuestoActual("nombremaquinatest4", 3);
	   BusinessNumero n = ctrlPuesto.obtenerNumeroActualPuesto("nombremaquinatest4");
	   assertEquals(n.getInternalId() == 3, true);
	   ctrlPuesto.asociarNumeroPuestoActual("nombremaquinatest4", 4);
	   n = ctrlPuesto.obtenerNumeroActualPuesto("nombremaquinatest4");
	   assertEquals(n.getInternalId() == 4, true);
	   ctrlPuesto.desasociarNumeroPuestoActual("nombremaquinatest4");
	   n = ctrlPuesto.obtenerNumeroActualPuesto("nombremaquinatest4");
	   assertEquals(n, null);   
   }
   
   @Test()
   public void testAsociarNumeroActualPuesto2() throws Exception{
	   ctrlPuesto.asociarNumeroPuestoActual("nombremaquinatest4", 3);
	   ctrlPuesto.asociarNumeroPuestoActual("nombremaquinatest3", 3);
	   BusinessNumero n = ctrlPuesto.obtenerNumeroActualPuesto("nombremaquinatest4");
	   assertEquals(n, null);
	   n = ctrlPuesto.obtenerNumeroActualPuesto("nombremaquinatest3");
	   assertEquals(n.getInternalId() == 3, true);   
   }
   
   @Test(expected=RollbackException.class)
   public void testAsociarNumeroActualPuestoInvalido() throws Exception{
	   ctrlPuesto.asociarNumeroPuestoActual("nombremaquinatest3", 981);
   }
   
   @Test(expected=RollbackException.class)
   public void testAsociarNumeroActualPuestoInvalido2() throws Exception{
	   ctrlPuesto.asociarNumeroPuestoActual("nombremaquinatest2", 3);
	   ctrlPuesto.asociarNumeroPuestoActual("nombremaquinatest2", 3);
   }
     
   @Test
   public void testOptimisticLockPuesto() throws Exception{
	   try{
		   System.out.println("\nOptimisticLockPuesto");
		   BusinessPuesto p1 = ctrlPuesto.obtenerPuesto("nombremaquinatest5");
		   BusinessPuesto p2 = ctrlPuesto.obtenerPuesto("nombremaquinatest5");
		   p1.setEstado(EstadoPuesto.ATENDIENDO);
		   p2.setEstado(EstadoPuesto.LLAMANDO);	
		   ctrlPuesto.modificarPuesto(p1);
		   ctrlPuesto.modificarPuesto(p2);
		   assertEquals(true, false);
	   }
	   catch(RollbackException e){
		   assertEquals(e.getCause() instanceof OptimisticLockException, true);
	   }
   }
   
   @Test
   public void testObtenerSectoresYTramitesPuesto() throws Exception{
	   String t1 = null;
	   String t2 = null;
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
		   t1 = "testObtener1";
		   t.setCodigo(t1);
		   ctrlTramite.crearTramite(t);
		   t2 = "testObtener2";
		   t.setCodigo(t2);
		   ctrlTramite.crearTramite(t);
		   
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
   
   @Test
   public void testListarMetricasPuestos(){
	   System.out.print("\ntestListarMetricasPuestos");
	   ArrayList<BusinessMetricasPuesto> lista = ctrlPuesto.listarMetricasPuestos();
	   for(BusinessMetricasPuesto bpm : lista){
		   System.out.println(bpm.getNombreMaquina() + "-" + bpm.getUsuarioAtencion() + "-" + bpm.getEstado() + "-" + bpm.getTimeSpent());
	   }
   }
   
   @Test
   public void testListarMetricasDePuestos(){
	   System.out.print("\ntestListarMetricasDePuestos");
	   BusinessPuesto p = new BusinessPuesto();
	   p.setNombreMaquina("puestoobtenermetricas");
	   ctrlPuesto.crearPuesto(p);
	   p = ctrlPuesto.obtenerPuesto("puestoobtenermetricas");
	   p.setEstado(EstadoPuesto.DISPONIBLE);
	   ctrlPuesto.modificarPuesto(p);
	   p = ctrlPuesto.obtenerPuesto("puestoobtenermetricas");
	   p.setEstado(EstadoPuesto.LLAMANDO);
	   ctrlPuesto.modificarPuesto(p);
	   ctrlPuesto.eliminarPuesto("puestoobtenermetricas");
	   
	   ArrayList<BusinessMetricasPuesto> lista = ctrlPuesto.listarMetricasDePuestos("puestoobtenermetricas");
	   for(BusinessMetricasPuesto bpm : lista){
		   System.out.println(bpm.getNombreMaquina() + "-" + bpm.getUsuarioAtencion() + "-" + bpm.getEstado() + "-" + bpm.getTimeSpent());
	   }
   }
   
  
}