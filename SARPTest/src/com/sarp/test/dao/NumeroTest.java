package com.sarp.test.dao;

import org.junit.Test;

import com.sarp.classes.BusinessDatoComplementario;
import com.sarp.classes.BusinessNumero;
import com.sarp.classes.BusinessPuesto;
import com.sarp.classes.BusinessSector;
import com.sarp.classes.BusinessTramite;
import com.sarp.dao.controllers.DAONumeroController;
import com.sarp.dao.controllers.DAOPuestoController;
import com.sarp.dao.factory.DAOServiceFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import javax.persistence.OptimisticLockException;
import javax.persistence.RollbackException;

public class NumeroTest {
	private static DAONumeroController ctrlNumero;
	private static DAOPuestoController ctrlPuesto;
	private static List<Integer> id;	
	
	@BeforeClass
    public static void setUpClassNumeroTest(){  
		ctrlNumero = DAOServiceFactory.getInstance().getDAONumeroController();
		ctrlPuesto = DAOServiceFactory.getInstance().getDAOPuestoController();
		id = new ArrayList<Integer>();
		for(int i = 1; i < 7; i++){
			BusinessNumero n = new BusinessNumero();
			n.setExternalId("external" + i);
			Integer idS = ctrlNumero.crearNumero(n, i, Integer.toString(i-1), null);
	        id.add(idS);
		}
    }
   
   @AfterClass
   public static void tearDownClassNumeroTest() throws Exception {
	   for(int i = 0; i < id.size(); i++){
		   Integer ids = id.get(i);
		   ctrlNumero.eliminarNumero(ids);
	   }
   }
   
   @Test
   public void testCrearNumero1() throws Exception {
	   BusinessNumero n = new BusinessNumero();
	   Integer id = ctrlNumero.crearNumero(n, 8, "7", null);
	   BusinessNumero n2 = ctrlNumero.obtenerNumero(id);
	   assertEquals(n2.getInternalId(), id);
	   assertEquals(n2.getCodSector(), "7");
	   assertEquals(n2.getCodTramite() == 8, true);
	   assertEquals(n2.getEstado(), null);
	   assertEquals(n2.getHora(), null);
	   assertEquals(n2.getExternalId(), null);
	   assertEquals(n2.getPrioridad(), null);	   
	   ctrlNumero.eliminarNumero(id);
   }
   
   @Test
   public void testCrearNumero2() throws Exception {
	   BusinessNumero n = new BusinessNumero();
	   n.setEstado("estado2");
	   GregorianCalendar hora = new GregorianCalendar();
	   n.setHora(hora);
	   n.setPrioridad(9);
	   n.setExternalId("external2");
	   Integer id = ctrlNumero.crearNumero(n, 8, "7", null);
	   BusinessNumero n2 = ctrlNumero.obtenerNumero(id);
	   assertEquals(n2.getInternalId(), id);
	   assertEquals(n2.getCodSector(), "7");
	   assertEquals(n2.getCodTramite() == 8, true);
	   assertEquals(n2.getEstado(), "estado2");
	   assertEquals(n2.getHora().getTimeInMillis(), hora.getTimeInMillis());
	   assertEquals(n2.getExternalId(), "external2");
	   assertEquals(n2.getPrioridad() == 9, true);	   
	   ctrlNumero.eliminarNumero(id);
   }
   
   @Test
   public void testCrearNumeroConDatos() throws Exception {
	   BusinessNumero n = new BusinessNumero();
	   BusinessDatoComplementario dc = new BusinessDatoComplementario();
	   dc.setDocIdentidad("1234567");
	   dc.setNombreCompleto("Marcelo Rydel");
	   Integer id = ctrlNumero.crearNumero(n, 8, "7", dc);
	   
	   BusinessDatoComplementario d2 = ctrlNumero.obtenerDatosNumero(id);
	   assertEquals(d2.getNombreCompleto(), "Marcelo Rydel");
	   ctrlNumero.eliminarNumero(id);
   }
   
   @Test
   public void testCrearNumeroConDatos2() throws Exception {
	   BusinessNumero n = new BusinessNumero();
	   BusinessDatoComplementario dc = new BusinessDatoComplementario();
	   Integer id = ctrlNumero.crearNumero(n, 8, "7", dc);
	   
	   BusinessDatoComplementario d2 = ctrlNumero.obtenerDatosNumero(id);
	   assertEquals(d2.getNombreCompleto(), null);
	   assertEquals(d2.getDocIdentidad(), null);
	   assertEquals(d2.getTipoDoc(), null);
	   ctrlNumero.eliminarNumero(id);
   }
   
   @Test(expected=RollbackException.class)
   public void testCrearNumeroInvalido() throws Exception {
	   BusinessNumero n = new BusinessNumero();
	   ctrlNumero.crearNumero(n, 798798, "sector", null); //tramite invalido
   }
   
   @Test(expected=RollbackException.class)
   public void testCrearNumeroInvalido1() throws Exception {
	   BusinessNumero n = new BusinessNumero();
	   ctrlNumero.crearNumero(n, 2, "sectorquenoexiste", null); //sector invalido
   }
   
   @Test(expected=RollbackException.class)
   public void testCrearNumeroInvalido3() throws Exception {
	   BusinessNumero n = new BusinessNumero();
	   ctrlNumero.crearNumero(n, 2, "5", null); //sector y tramite no relacionados
   }
   
   @Test
   public void testListarNumeros(){
	   System.out.println("\nNumeros:");
	   List<BusinessNumero> lista = ctrlNumero.listarNumeros();
	   for(BusinessNumero n : lista){
		   System.out.println("Numero: " + n.getInternalId() + "-" + n.getExternalId()+ "-" + n.getCodTramite()+ "-" + n.getCodSector());
	   }
   }
   
   @Test
   public void testListarNumerosDelDia(){
	   System.out.println("\nNumerosDelDia:");
	   List<BusinessNumero> lista = ctrlNumero.listarNumerosDelDia();
	   for(BusinessNumero n : lista){
		   System.out.println("Numero: " + n.getInternalId() + "-" + n.getExternalId()+ "-" + n.getCodTramite()+ "-" + n.getCodSector());
	   }
   }
   
   @Test
   public void testModificarNumeroValido() throws Exception {
	   Integer i = id.get(0);
	   BusinessNumero n = ctrlNumero.obtenerNumero(i);
	   assertEquals(n.getExternalId(), "external1");
	   assertEquals(n.getInternalId(), i);
	   n.setPrioridad(7);
	   n.setEstado("estadotest");
	   n.setExternalId("externaltest");
	   GregorianCalendar c = new GregorianCalendar();
	   n.setHora(c);
	   ctrlNumero.modificarNumero(n);
	   BusinessNumero n2 = ctrlNumero.obtenerNumero(i);
	   assertEquals(n2.getInternalId(), i);
	   assertEquals(n2.getPrioridad() == 7, true);
	   assertEquals(n2.getEstado(), "estadotest");
	   assertEquals(n2.getExternalId(), "externaltest");
	   assertEquals(n2.getHora().getTimeInMillis(), c.getTimeInMillis());
   }
   
   @Test(expected=RollbackException.class)
   public void testModificarNumeroInvalido() throws Exception {
	  BusinessNumero n = new BusinessNumero();
	  n.setInternalId(78569); //id que no existe
	  n.setExternalId("external");
	  ctrlNumero.modificarNumero(n);
   }
   
   @Test(expected=RollbackException.class)
   public void testModificarNumeroInvalido2() throws Exception {
	  BusinessNumero n = ctrlNumero.obtenerNumero(id.get(1)); //id valido
	  n.setInternalId(798789);
	  ctrlNumero.modificarNumero(n);
   }
   
   @Test(expected=RollbackException.class)
   public void testEliminarNumeroInvalido() throws Exception{
	   ctrlNumero.eliminarNumero(98789);
   }
   
   @Test()
   public void testObtenerTramiteNumero() throws Exception{	 
	   BusinessTramite t = ctrlNumero.obtenerTramiteNumero(id.get(2));
	   assertEquals(t.getCodigo() == 3, true);
   }
   
   @Test()
   public void testObtenerSectorumero() throws Exception{
	   BusinessSector s = ctrlNumero.obtenerSectorNumero(id.get(3));
	   assertEquals(s.getSectorId(), "3");
   }
   
   @Test
   public void testObtenerPuestosNumero() throws Exception{
	   ctrlPuesto.asociarNumeroPuesto("NombreMaquina5", id.get(3));
	   ctrlPuesto.asociarNumeroPuesto("NombreMaquina6", id.get(3));
	   ArrayList<BusinessPuesto> p = ctrlNumero.obtenerPuestosNumero(id.get(3));
	   assertEquals(p.size() == 2, true);
	   ctrlPuesto.desasociarNumeroPuesto("NombreMaquina5", id.get(3));
	   p = ctrlNumero.obtenerPuestosNumero(id.get(3));
	   assertEquals(p.size() == 1, true);
	   ctrlPuesto.desasociarNumeroPuesto("NombreMaquina6", id.get(3));
	   p = ctrlNumero.obtenerPuestosNumero(id.get(3));
	   assertEquals(p.size() == 0, true);
   }
   
   @Test
   public void testObtenerPuestoActualNumero() throws Exception{
	   ctrlPuesto.asociarNumeroPuestoActual("NombreMaquina4", id.get(4));
	   BusinessPuesto p = ctrlNumero.obtenerPuestoActualNumero(id.get(4));
	   assertEquals(p.getNombreMaquina(), "NombreMaquina4");
	   ctrlPuesto.desasociarNumeroPuestoActual("NombreMaquina4");
	   p = ctrlNumero.obtenerPuestoActualNumero(id.get(4));
	   assertEquals(p, null);
   }
   
   @Test
   public void testOptimisticLockNumero(){
	   try{
		   System.out.println("\nOptimisticLockNumero");	
		   BusinessNumero n1 = ctrlNumero.obtenerNumero(id.get(5));
		   BusinessNumero n2 = ctrlNumero.obtenerNumero(id.get(5));
		   n1.setPrioridad(10);
		   n2.setPrioridad(10);
		   ctrlNumero.modificarNumero(n1);
		   ctrlNumero.modificarNumero(n2);
		   assertEquals(true, false);
	   }
	   catch(RollbackException e){
		   assertEquals(e.getCause() instanceof OptimisticLockException, true);
	   }
   }
  
}