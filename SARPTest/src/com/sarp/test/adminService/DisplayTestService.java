package com.sarp.test.adminService;

import org.junit.Test;

import com.sarp.classes.BusinessDisplay;
import com.sarp.classes.BusinessSector;
import com.sarp.controllers.AdminActionsController;
import com.sarp.dao.controllers.DAODisplayController;
import com.sarp.dao.controllers.DAONumeroController;
import com.sarp.dao.controllers.DAOPuestoController;
import com.sarp.dao.controllers.DAOSectorController;
import com.sarp.dao.controllers.DAOTramiteController;
import com.sarp.dao.factory.DAOServiceFactory;
import com.sarp.factory.Factory;
import com.sarp.json.modeler.JSONDisplay;
import com.sarp.json.modeler.JSONPuesto;
import com.sarp.json.modeler.JSONSectorDisplay;

import org.junit.Ignore;
import static org.junit.Assert.*;
import java.util.List;

public class DisplayTestService {

	DAODisplayController ctrlDaoDisplay = new DAODisplayController();
  /* 
   @Test
   public void testABMDisplay() throws Exception {	
	   // prueba abm display
	   Factory fac = Factory.getInstance();
	   AdminActionsController ctrl = fac.getAdminActionsController();
	   JSONDisplay jsonD = new JSONDisplay();
	   String rutaArchivo = "rutaNueva";
	   jsonD.setRutaArchivo(rutaArchivo);
	   // se da de alta el display
	   ctrl.altaDisplay(jsonD.getRutaArchivo());
	   // La obtengo los displays de la base y busco el creado
	   List<BusinessDisplay> lista = ctrlDaoDisplay.listarDisplays();
	   boolean existe = false;
	   for (BusinessDisplay item:lista){
		   if (item.getRutaArchivo().equals(jsonD.getRutaArchivo())){
			   
			   existe = true;
		   }
	   }
	   assertEquals(existe,true); 
	   
	   //prueba modificar display
	   
	   Integer id = 0;
	   for (BusinessDisplay item:lista){
		  id = item.getCodigo(); 
	   }
	   String rutPrueba2 = "rutaNueva";
	   ctrl.modificarRutaDisplay(id,rutPrueba2);
	   
	   // obtengo de la base ese display y veo si es correcta la ruta
	   BusinessDisplay d2 = ctrlDaoDisplay.obtenerDisplay(id);
	   assertEquals(rutPrueba2,d2.getRutaArchivo());  
	   
	   
	   //doy de baja el display
	   ctrl.bajaDisplay(id);
	   boolean existe2 = false;
	   
	   for (BusinessDisplay item:lista){
		   if (item.getCodigo().equals(id))
			   existe2 = true;
		   }
	   
	   }
   	
    */
	
	@Test
	public void asignarDisplaySector() throws Exception{
		Factory fac = Factory.getInstance();
		   AdminActionsController ctrl = fac.getAdminActionsController();
		   JSONSectorDisplay secDisplay = new JSONSectorDisplay();
		   BusinessDisplay d = new BusinessDisplay();
			DAODisplayController ctrlDisplay = DAOServiceFactory.getInstance().getDAODisplayController();
			DAOSectorController ctrlSector = DAOServiceFactory.getInstance().getDAOSectorController();
		   d.setIdDisplay("9987897899908");
		   String id = ctrlDisplay.crearDisplay(d);
		   secDisplay.setDisplayId("998789789");
		   secDisplay.setSectorId("MVD_CER");
		   ctrl.asignarSectorDisplay(secDisplay);
		   List<BusinessDisplay> lista = ctrlSector.obtenerDisplaysSector("MVD_CER");
		   boolean existe = false;
		   for (BusinessDisplay item:lista){
			   if (item.getIdDisplay().equals(secDisplay.getDisplayId())){
				   existe = true;
			   }
		   }
		   assertEquals(existe,true); 
		   // La obtengo los displays de la base y busco el creado

	}
     
}