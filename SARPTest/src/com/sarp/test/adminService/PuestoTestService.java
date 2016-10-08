package com.sarp.test.adminService;
import org.junit.Test;

import com.sarp.classes.BusinessDisplay;
import com.sarp.classes.BusinessPuesto;
import com.sarp.classes.BusinessSector;
import com.sarp.classes.BusinessTramite;
import com.sarp.controllers.AdminActionsController;
import com.sarp.dao.controllers.DAODisplayController;
import com.sarp.dao.controllers.DAONumeroController;
import com.sarp.dao.controllers.DAOPuestoController;
import com.sarp.dao.controllers.DAOSectorController;
import com.sarp.dao.controllers.DAOTramiteController;
import com.sarp.enumerados.EstadoPuesto;
import com.sarp.factory.Factory;
import com.sarp.json.modeler.JSONDisplay;
import com.sarp.json.modeler.JSONPuesto;
import com.sarp.json.modeler.JSONPuestoSector;
import com.sarp.json.modeler.JSONSector;
import com.sarp.service.response.maker.ResponseMaker;

import org.junit.Ignore;
import static org.junit.Assert.*;
import java.util.List;

import javax.persistence.RollbackException;

public class PuestoTestService {
	@Test(expected=Exception.class)
	public void asignarPuestoSectorError() throws Exception{
		// datos de prueba
		// creo un sector 
		   DAOSectorController ctrlSector = new  DAOSectorController();
		   String codigoTest = "1111156547893";
		   BusinessSector s1 = new BusinessSector();
		   s1.setSectorId(codigoTest);
		   s1.setRuta("/pcunix666/marioBaulBolso");
		   s1.setNombre("ClassicMario");
		   ctrlSector.crearSector(s1);
		//creo puesto
		    DAOPuestoController ctrlPuesto = new DAOPuestoController();
		    BusinessPuesto p1 = new BusinessPuesto();
			String id ="idepMaritoBaul9";
			p1.setNombreMaquina(id);
			p1.setEstado(EstadoPuesto.DIPONIBLE);
			ctrlPuesto.crearPuesto(p1);
		//-----
			Factory fac = Factory.getInstance();
			AdminActionsController ctrl = fac.getAdminActionsController();
			JSONPuestoSector puestoSec = new JSONPuestoSector();
			JSONPuesto jpuesto = new JSONPuesto();
			jpuesto.setNombreMaquina(id);
			JSONSector jsector = new JSONSector();
			jsector.setCodigo(s1.getSectorId());
			puestoSec.setPuesto(jpuesto);
			puestoSec.setSector(jsector);
			// asigno dos veces
			ctrl.asignarPuestoSector(puestoSec);
			ctrl.asignarPuestoSector(puestoSec);
			//borro datos de prueba
			   ctrlPuesto.eliminarPuesto(id);
			   ctrlSector.eliminarSector(s1.getSectorId());
	
	}
	
	
	@Test
	public void asignarPuestoSector() throws Exception{
		// datos de prueba
		// creo un sector 
		   DAOSectorController ctrlSector = new  DAOSectorController();
		   String codigoTest = "1111156547892";
		   BusinessSector s1 = new BusinessSector();
		   s1.setSectorId(codigoTest);
		   s1.setRuta("/pcunix666/marioBaulBolso");
		   s1.setNombre("ClassicMario");
		   ctrlSector.crearSector(s1);
		//creo puesto
		    DAOPuestoController ctrlPuesto = new DAOPuestoController();
		    BusinessPuesto p1 = new BusinessPuesto();
			String id ="idepMaritoBaul8";
			p1.setNombreMaquina(id);
			p1.setEstado(EstadoPuesto.DIPONIBLE);
			ctrlPuesto.crearPuesto(p1);
		//-----
			Factory fac = Factory.getInstance();
			AdminActionsController ctrl = fac.getAdminActionsController();
			JSONPuestoSector puestoSec = new JSONPuestoSector();
			JSONPuesto jpuesto = new JSONPuesto();
			jpuesto.setNombreMaquina(id);
			JSONSector jsector = new JSONSector();
			jsector.setCodigo(s1.getSectorId());
			puestoSec.setPuesto(jpuesto);
			puestoSec.setSector(jsector);
			// asigno dos veces
			ctrl.asignarPuestoSector(puestoSec);
			// luego de asignar busco en la base los puestos asociados al sector
			List<BusinessPuesto> puestos = ctrlSector.obtenerPuestosSector(s1.getSectorId());
			 
		   Boolean isOk = false;
		   for (BusinessPuesto item:puestos){
			   if (item.getNombreMaquina().equals(p1.getNombreMaquina())){
				   isOk = true;
			   }
		   }
		   assertEquals(isOk,true); 
		   
		   //borro datos de prueba
		   ctrlPuesto.eliminarPuesto(id);
		   ctrlSector.eliminarSector(s1.getSectorId());
	
	
	}
}
