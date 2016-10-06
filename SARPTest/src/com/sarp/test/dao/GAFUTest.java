package com.sarp.test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sarp.classes.BusinessNodeGAFU;
import com.sarp.classes.BusinessSector;
import com.sarp.dao.controllers.DAOSectorController;
import com.sarp.managers.GAFUManager;
import com.sarp.controllers.GAFUController;

public class GAFUTest {

	private static GAFUController ctrlGAFU;
	private static DAOSectorController ctrlSector;
	private static GAFUManager gafumgr;
   
	@BeforeClass
    public static void setUpClass(){   
		ctrlGAFU = new GAFUController();
		ctrlSector = new DAOSectorController();
		gafumgr = GAFUManager.getInstance();
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
   
   @Test
   public void testArbol() throws Exception{
	   //ELIMINO TODOS LOS SECTORES DE LA BD
	   List<BusinessSector> sectores = ctrlSector.listarSectores();
	   for (BusinessSector s : sectores) {
			ctrlSector.eliminarSector(s.getSectorId());
	   }
	   int cantidad = ctrlSector.listarSectores().size();
	   assertEquals(0,cantidad);
	   
	   //ACTUALIZO LOS SECTORES DE LA BD CON GAFU
	   ctrlGAFU.actualizarArbolGAFU();
	   sectores = ctrlSector.listarSectores();
	   assertEquals(sectores, gafumgr.arbolToList(ctrlGAFU.BusquedaNodo("BPS")));
	   
	   //AGREGO SECTORES EN LA BD, ACTUALIZO Y VEO QUE SE HAYAN BORRADO
	   BusinessSector sector1 = new BusinessSector("ID1","NOMBREtest","/test");
	   ctrlSector.crearSector(sector1);
	   BusinessSector sectorBD = ctrlSector.obtenerSector("ID1");
	   BusinessNodeGAFU sectorArbol = ctrlGAFU.BusquedaNodo("ID1");
	   assertEquals(null, sectorArbol);
	   assertEquals(sector1, sectorBD);
	   ctrlSector.crearSector(new BusinessSector("ID2","NOMBREtest","/test"));
	   ctrlSector.crearSector(new BusinessSector("ID3","NOMBREtest","/test"));
	   ctrlGAFU.actualizarArbolGAFU();
	   assertTrue(sectores.containsAll(ctrlSector.listarSectores()) && ctrlSector.listarSectores().containsAll(sectores));
	   
	   //MODIFICO UN SECTOR EN LA BD Y VEO QUE AL REGENERAR EL ÁRBOL SE MODIFICA
	   sectorBD = ctrlSector.obtenerSector("BPS");
	   sectorBD.setNombre("PRUEBA");
	   sectorBD.setRuta("/PRUEBA");
	   ctrlSector.modificarSector(sectorBD);
	   ctrlGAFU.actualizarArbolGAFU();
	   sectorBD = ctrlSector.obtenerSector("BPS");
	   sectorArbol = ctrlGAFU.BusquedaNodo("BPS");
	   assertEquals(sectorBD.getSectorId(), sectorArbol.getCodigo());
	   assertEquals(sectorBD.getNombre(), sectorArbol.getNombre());
	   assertEquals(sectorBD.getRuta(), sectorArbol.obtenerCamino());
   }
}