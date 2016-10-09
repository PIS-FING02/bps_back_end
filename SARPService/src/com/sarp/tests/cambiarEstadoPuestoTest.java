package com.sarp.tests;

import org.junit.Test;

import com.sarp.classes.BusinessDisplay;
import com.sarp.classes.BusinessPuesto;
import com.sarp.classes.BusinessSector;
import com.sarp.controllers.AdminActionsController;
import com.sarp.controllers.AttentionsController;
import com.sarp.dao.controllers.DAODisplayController;
import com.sarp.dao.controllers.DAONumeroController;
import com.sarp.dao.controllers.DAOPuestoController;
import com.sarp.dao.controllers.DAOSectorController;
import com.sarp.dao.controllers.DAOTramiteController;
import com.sarp.enumerados.EstadoPuesto;
import com.sarp.factory.Factory;
import com.sarp.json.modeler.JSONPuesto;

import org.junit.Ignore;
import static org.junit.Assert.*;
import java.util.List;

public class cambiarEstadoPuestoTest {
	
	DAOPuestoController ctrlDaoPuesto = new DAOPuestoController();
	AdminActionsController adminController = AdminActionsController.getInstance();
	Factory fac = Factory.getInstance();
	
	@Test
	public void cambiarEstadosPuestos(){
		
		try{
			//Modificar Puesto
			AttentionsController ctrl = fac.getAttentionsController();		
			boolean result = false;
			//BusinessPuesto puestoModificar = new BusinessPuesto("PuestoCambioEstado3", "","CERRADO", 32);
			//ctrlDaoPuesto.crearPuesto(puestoModificar);
			
			JSONPuesto puesto = new JSONPuesto();	
			puesto.setNombreMaquina("PuestoCambioEstado3");
			puesto.setNumeroPuesto(null);
			puesto.setUsuarioId("Federico");
			puesto.setEstado(null);
			
			ctrl.abrirPuesto(puesto);
			BusinessPuesto puestoModificado = ctrlDaoPuesto.obtenerPuesto("PuestoCambioEstado3");
			
			ctrl.cerrarPuesto(puesto);
			 puestoModificado = ctrlDaoPuesto.obtenerPuesto("PuestoCambioEstado3");
			
			String s=  puestoModificado.getNombreMaquina();

		}catch(Exception e){
			System.out.println(e.getMessage());
		}
				
		
		
	}
}
