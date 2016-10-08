package com.sarp.test.adminService;


import org.junit.Test;

import com.sarp.classes.BusinessDisplay;
import com.sarp.classes.BusinessSector;
import com.sarp.classes.BusinessTramite;
import com.sarp.controllers.AdminActionsController;
import com.sarp.dao.controllers.DAODisplayController;
import com.sarp.dao.controllers.DAONumeroController;
import com.sarp.dao.controllers.DAOPuestoController;
import com.sarp.dao.controllers.DAOSectorController;
import com.sarp.dao.controllers.DAOTramiteController;
import com.sarp.factory.Factory;
import com.sarp.json.modeler.JSONDisplay;
import com.sarp.json.modeler.JSONPuesto;
import com.sarp.json.modeler.JSONSector;
import com.sarp.json.modeler.JSONTramite;
import com.sarp.json.modeler.JSONTramiteSector;

import org.junit.Ignore;
import static org.junit.Assert.*;
import java.util.List;


public class TramiteTestService {
	
	DAOTramiteController ctrlDaoTramite = new DAOTramiteController();
	   
	   @Test
	   public void asignarTramiteSector() throws Exception {	
		   
		   // creo un sector y un tramite para el test
		   DAOSectorController ctrlSector = new  DAOSectorController();
		   String codigoTest = "92949299789";
		   BusinessSector s1 = new BusinessSector();
		   s1.setSectorId(codigoTest);
		   s1.setRuta("/pcunix666/ban");
		   s1.setNombre("Jubilaciones");
		   ctrlSector.crearSector(s1);
		   
		   DAOTramiteController ctrlTramite = new DAOTramiteController();
		   BusinessTramite  bTram = new BusinessTramite();
		   bTram.setNombre("Consulta");
		   Integer codigoTramite = ctrlTramite.crearTramite(bTram);
		   // creados en base datos de prueba
		   Factory fac = Factory.getInstance();
		   AdminActionsController ctrl = fac.getAdminActionsController();
		   JSONTramiteSector tramSec = new JSONTramiteSector();
		   JSONSector sec = new JSONSector();
		   sec.setCodigo(s1.getSectorId());
		   JSONTramite tram = new JSONTramite();
		   tram.setCodigo(codigoTramite);
		   tramSec.setSector(sec);
		   tramSec.setTramite(tram);
		   ctrl.asignarTramiteSector(tramSec);
		   // luego de asignar busco en la base los tramites asociados al sector
		   List<BusinessTramite> tramitesBase = ctrlSector.obtenerTramitesSector(sec.getCodigo());
		   Boolean isOk = false;
		   for (BusinessTramite item:tramitesBase){
			   if (item.getCodigo().equals(codigoTramite)){
				   isOk = true;
			   }
		   }
		   assertEquals(isOk,true); 
		   
		   //borro de la base los datos de prueba
		   ctrlSector.eliminarSector(sec.getCodigo());
		   ctrlTramite.eliminarTramite(codigoTramite);
		   
	   }
	   

}
