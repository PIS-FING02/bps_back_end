package com.sarp.test.dao;

import org.junit.Test;

import com.sarp.classes.BusinessPuesto;
import com.sarp.classes.BusinessSector;
import com.sarp.classes.BusinessTramite;
import com.sarp.dao.controllers.DAOTramiteController;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.OptimisticLockException;
import javax.persistence.RollbackException;

public class TramiteTest {
	private static DAOTramiteController ctrlTramite;
	private static List<Integer> id;	
	
	@BeforeClass
    public static void setUpClassTramiteTest(){  
		ctrlTramite = new DAOTramiteController();	
		id = new ArrayList<Integer>();
		for(int i = 0; i < 7; i++){
			BusinessTramite t = new BusinessTramite();	
			t.setNombre("nombretramitetest" + i);
			Integer idS = ctrlTramite.crearTramite(t);
	        id.add(idS);
		}
    }
   
   @AfterClass
   public static void tearDownClassTramiteTest() throws Exception {
	   for(int i = 0; i < id.size(); i++){
		   Integer ids = id.get(i);
		   ctrlTramite.eliminarTramite(ids);
	   }
   }
   
   @Test
   public void testCrearTramite() throws Exception {
	   BusinessTramite t = new BusinessTramite();
	   t.setNombre("tramitedeprueba");
	   Integer id = ctrlTramite.crearTramite(t);
	   BusinessTramite t2 = ctrlTramite.obtenerTramite(id);
	   assertEquals(t.getNombre(), t2.getNombre());
	   ctrlTramite.eliminarTramite(id);
   }
   
   @Test
   public void testListarTramites(){
	   System.out.println("\nTramites:");
	   List<BusinessTramite> lista = ctrlTramite.listarTramites();
	   for(BusinessTramite t : lista){
		   System.out.println("Tramite: " + t.getCodigo() + "-" + t.getNombre());
	   }
   }
   

   @Test
   public void testModificarTramiteValido() throws Exception {
	   BusinessTramite t = ctrlTramite.obtenerTramite(id.get(0));
	   assertEquals(t.getNombre(), "nombretramitetest0");
	   t.setNombre("otronombre");
	   ctrlTramite.modificarTramite(t);
	   BusinessTramite t2 = ctrlTramite.obtenerTramite(id.get(0));
	   assertEquals(t2.getNombre(), "otronombre");
   }
   
   @Test(expected=RollbackException.class)
   public void testModificarTramiteInvalido() throws Exception {
	  BusinessTramite t = new BusinessTramite();
	  t.setCodigo(998798); //id que no existe
	  t.setNombre("nombre");
	  ctrlTramite.modificarTramite(t);
   }
   
   @Test(expected=RollbackException.class)
   public void testModificarTramiteInvalido2() throws Exception {
	  BusinessTramite t = ctrlTramite.obtenerTramite(id.get(1)); //id valido
	  t.setCodigo(979879);
	  ctrlTramite.modificarTramite(t);
   }
   
   @Test(expected=RollbackException.class)
   public void testEliminarTramiteInvalido() throws Exception{
	   ctrlTramite.eliminarTramite(98789);
   }
   
   @Test()
   public void testAsociarTramitePuesto() throws Exception{
	   ctrlTramite.asociarTramitePuesto(id.get(2), "NombreMaquina8");
	   List<BusinessPuesto> a = ctrlTramite.obtenerPuestosTramite(id.get(2));
	   assertEquals(a.get(0).getNombreMaquina(), "NombreMaquina8");
	   ctrlTramite.desasociarTramitePuesto(id.get(2), "NombreMaquina8");
	   a = ctrlTramite.obtenerPuestosTramite(id.get(2));
	   assertEquals(a.size() == 0, true);
   }
   
   @Test(expected=RollbackException.class)
   public void testAsociarTramitePuestoInvalido() throws Exception{
	   ctrlTramite.asociarTramitePuesto(8768768, "NombreMaquina7"); //tramite invalido
   }
   
   @Test(expected=RollbackException.class)
   public void testAsociarTramitePuestoInvalido2() throws Exception{
	   ctrlTramite.asociarTramitePuesto(id.get(3), "puestoquenoexiste"); //puesto invalido
   }
   
   @Test(expected=RollbackException.class)
   public void testAsociarTramitePuestoInvalido3() throws Exception{
	   ctrlTramite.asociarTramitePuesto(id.get(4), "NombreMaquina4");
	   ctrlTramite.asociarTramitePuesto(id.get(4), "NombreMaquina4");
   }
   
   @Test(expected=RollbackException.class)
   public void desasociarTramiteTramiteInvalido() throws Exception{
	   ctrlTramite.desasociarTramitePuesto(id.get(5), "NombreMaquina7"); //No asociados entre si
   }
   
   @Test
   public void testObtenerSectoresTramite(){
	   List<BusinessSector> l = ctrlTramite.obtenerSectoresTramite(1); // tramite con sectores
	   assertEquals(l.size() > 0, true);
	   
	   List<BusinessSector> l2 = ctrlTramite.obtenerSectoresTramite(id.get(6)); // tramite sin sectores
	   assertEquals(l2.size() == 0, true);
   }
   
   @Test
   public void testExisteTramiteSector(){
	   boolean b1 = ctrlTramite.existeTramiteSector("0", 1);
	   assertEquals(b1, true);
	   boolean b2 = ctrlTramite.existeTramiteSector("3", 8);
	   assertEquals(b2, false);
   }
   
   @Test
   public void testOptimisticLockTramite(){
	   try{
		   System.out.println("\nOptimisticLockTramite");	
		   BusinessTramite t1 = ctrlTramite.obtenerTramite(id.get(5));
		   BusinessTramite t2 = ctrlTramite.obtenerTramite(id.get(5));
		   t1.setNombre("otronombre");
		   t2.setNombre("otromas");
		   ctrlTramite.modificarTramite(t1);
		   ctrlTramite.modificarTramite(t2);
		   assertEquals(true, false);
	   }
	   catch(RollbackException e){
		   assertEquals(e.getCause() instanceof OptimisticLockException, true);
	   }
   }
  
}