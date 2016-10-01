package com.sarp.test;

import org.junit.Test;

import com.sarp.classes.BusinessDisplay;
import com.sarp.classes.BusinessSector;
import com.sarp.controllers.AdminActionsController;
import com.sarp.dao.controllers.DAODisplayController;
import com.sarp.dao.controllers.DAONumeroController;
import com.sarp.dao.controllers.DAOPuestoController;
import com.sarp.dao.controllers.DAOSectorController;
import com.sarp.dao.controllers.DAOTramiteController;
import com.sarp.factory.Factory;

import org.junit.Ignore;
import static org.junit.Assert.*;
import java.util.List;

public class DisplayTest {

	DAODisplayController ctrlDaoDisplay = new DAODisplayController();
   
   @Test
   public void testABMDisplay() throws Exception {	
	   // prueba abm display
	   Factory fac = Factory.getInstance();
	   AdminActionsController ctrl = fac.getAdminActionsController();
	   String rutPrueba = "ruta";
	   // se da de alta el display
	   ctrl.altaDisplay(rutPrueba);
	   // La obtengo los displays de la base y busco el creado
	   List<BusinessDisplay> lista = ctrlDaoDisplay.listarDisplays();
	   boolean existe = false;
	   for (BusinessDisplay item:lista){
		   if (item.getRutaArchivo() == rutPrueba){
			   existe = true;
		   }
	   }
	   assertEquals(existe,true);  
	   
	   //prueba modificar display
	   int id = 1;
	   String rutPrueba2 = "ruta2";
	   ctrl.modificarRutaDisplay(id,rutPrueba2);
	   
	   // obtengo de la base ese display y veo si es correcta la ruta
	   BusinessDisplay d2 = ctrlDaoDisplay.obtenerDisplay(id);
	   assertEquals(rutPrueba2,d2.getRutaArchivo());  
	   
	   
	   //doy de baja el display
	   ctrl.bajaDisplay(id);
	  
   }
   
   @Test
   public void testAsignarDisplaySector() throws Exception {	
	   // prueba asignar display sector
	   Factory fac = Factory.getInstance();
	   AdminActionsController ctrl = fac.getAdminActionsController();
	   // falta implementar por fede
   }
   
   
  
}