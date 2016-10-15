package com.sarp.test.adminService;
import org.junit.Test;

import com.sarp.classes.BusinessDisplay;
import com.sarp.classes.BusinessNodeGAFU;
import com.sarp.classes.BusinessPuesto;
import com.sarp.classes.BusinessSector;
import com.sarp.classes.BusinessTramite;
import com.sarp.controllers.AdminActionsController;
import com.sarp.controllers.GAFUController;
import com.sarp.dao.controllers.DAODisplayController;
import com.sarp.dao.controllers.DAONumeroController;
import com.sarp.dao.controllers.DAOPuestoController;
import com.sarp.dao.controllers.DAOSectorController;
import com.sarp.dao.controllers.DAOTramiteController;
import com.sarp.dao.factory.DAOServiceFactory;
import com.sarp.enumerados.EstadoPuesto;
import com.sarp.factory.Factory;
import com.sarp.json.modeler.JSONDisplay;
import com.sarp.json.modeler.JSONPuesto;
import com.sarp.json.modeler.JSONPuestoSector;
import com.sarp.json.modeler.JSONSector;
import com.sarp.managers.GAFUManager;
import com.sarp.service.response.maker.ResponseMaker;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import static org.junit.Assert.*;
import java.util.List;

public class SectorTestService {
	
	@Test
	public void listarSectores() throws Exception{
		Factory fac = Factory.getInstance();
		AdminActionsController ctrl = fac.getAdminActionsController();
		List<JSONSector> listaSectores = ctrl.listarSectores();
		
		DAOServiceFactory factory = DAOServiceFactory.getInstance();
		DAOSectorController ctrlDAO = factory.getDAOSectorController();
		List<BusinessSector> listaSectoresDB = ctrlDAO.listarSectores();
		
		assertEquals(listaSectores.size(), listaSectoresDB.size());
		for (JSONSector s : listaSectores) {
			BusinessSector sec = ctrlDAO.obtenerSector(s.getCodigo());
			assertEquals(sec.getNombre(), s.getNombre());
			assertEquals(sec.getRuta(), s.getRutaSector());
	   }
	}
	
	@Test
	   public void actualizarGAFU() throws Exception{
		   //ELIMINO TODOS LOS SECTORES DE LA BD
			DAOSectorController ctrlSector = new DAOSectorController();
		   List<BusinessSector> sectores = ctrlSector.listarSectores();
		   for (BusinessSector s : sectores) {
				ctrlSector.eliminarSector(s.getSectorId());
		   }
		   int cantidad = ctrlSector.listarSectores().size();
		   assertEquals(0,cantidad);
		   
		   //ACTUALIZO LOS SECTORES DE LA BD CON GAFU
		   GAFUManager gafumgr = GAFUManager.getInstance();
		   GAFUController ctrlGAFU = new GAFUController();
		   Factory fac = Factory.getInstance();
		   AdminActionsController ctrl = fac.getAdminActionsController();
		   ctrl.actualizarGAFU();
		   sectores = ctrlSector.listarSectores();
		   assertEquals(sectores, gafumgr.arbolToList(ctrlGAFU.BusquedaNodo("BPS")));
		   
		   //AGREGO SECTORES EN LA BD, ACTUALIZO Y VEO QUE SE HAYAN BORRADO
		   BusinessSector sector1 = new BusinessSector("ID1","NOMBREtest","/test");
		   ctrlSector.crearSector(sector1);
		   BusinessSector sectorBD = ctrlSector.obtenerSector("ID1");
		   BusinessNodeGAFU sectorArbol = ctrlGAFU.BusquedaNodo("ID1");
		   assertEquals(null, sectorArbol);
		   assertEquals(sector1, sectorBD);
		   ctrl.actualizarGAFU();
		   assertTrue(sectores.containsAll(ctrlSector.listarSectores()) && ctrlSector.listarSectores().containsAll(sectores));
		   
		   //MODIFICO UN SECTOR EN LA BD Y VEO QUE AL REGENERAR EL ÁRBOL SE MODIFICA
		   sectorBD = ctrlSector.obtenerSector("BPS");
		   sectorBD.setNombre("PRUEBA");
		   sectorBD.setRuta("/PRUEBA");
		   ctrlSector.modificarSector(sectorBD);
		   ctrl.actualizarGAFU();
		   sectorBD = ctrlSector.obtenerSector("BPS");
		   sectorArbol = ctrlGAFU.BusquedaNodo("BPS");
		   assertEquals(sectorBD.getSectorId(), sectorArbol.getCodigo());
		   assertEquals(sectorBD.getNombre(), sectorArbol.getNombre());
		   assertEquals(sectorBD.getRuta(), sectorArbol.obtenerCamino());
	   }
}
