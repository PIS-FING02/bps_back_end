package com.sarp.tests;

import org.junit.Test;

import com.sarp.classes.BusinessDisplay;
import com.sarp.classes.BusinessPuesto;
import com.sarp.classes.BusinessSector;
import com.sarp.controllers.AdminActionsController;
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


public class PuestoTest {
	
	//Test ABM Puesto - ABM Tramite - llamar numero - atrasar numero - tramite
	
	DAOPuestoController ctrlDaoPuesto = new DAOPuestoController();
	AdminActionsController adminController = AdminActionsController.getInstance();
	Factory fac = Factory.getInstance();
	
	
	@Test
	public void puestoTesteo(){
		
		//Crear Puesto
		
		AdminActionsController ctrl = fac.getAdminActionsController();
		JSONPuesto puesto = new JSONPuesto();
		
		//Funcionalidades del puesto basicas
		
		puesto.setNombreMaquina("Nombre de maquina 1");
		puesto.setNumeroPuesto(1);
		puesto.setUsuarioId("");
		puesto.setEstado("CERRADO");
		
		try{
			ctrl.altaPuesto(puesto);
			
			boolean result = false;
			BusinessPuesto puestoBase = ctrlDaoPuesto.obtenerPuesto("Nombre de maquina 1");
			
			if(puestoBase.getNombreMaquina().equals("Nombre de maquina 1") && puestoBase.getNumeroPuesto().equals(1) && puestoBase.getEstado() == EstadoPuesto.CERRADO && puestoBase.getUsuarioId() == ""){
				result = true;
			}
			
			assertEquals(result,true); 
					
						
		}catch(Exception e){
			
			System.out.println(e.getMessage());
				
		}
	
		
		
		
	}
	
	//--------------------------------------------------------------------------------
	
	@Test
	public void eliminarPuesto(){
		
		//Eliminar Puesto
		
		AdminActionsController ctrl = fac.getAdminActionsController();
		BusinessPuesto puestoEliminar = new BusinessPuesto("PuestoEliminar", "","CERRADO", 999999);
		
		JSONPuesto puesto = new JSONPuesto();	
		puesto.setNombreMaquina("PuestoEliminar");
		puesto.setNumeroPuesto(0);
		puesto.setUsuarioId("");
		puesto.setEstado("");
		ctrlDaoPuesto.crearPuesto(puestoEliminar);
		
		try{
			ctrl.bajaPuesto(puesto);
			boolean result = true;
			
			List<BusinessPuesto> puestos = ctrlDaoPuesto.listarPuestos();
			
			for( BusinessPuesto thisPuesto : puestos){
				if(thisPuesto.getNombreMaquina().equals("PuestoEliminar")){
					result = false;
					break;
				}
			}
			
			assertEquals(result,true); 
		
		}catch(Exception e){
			System.out.println(e.getMessage());
		}

		
	}
	@Test
	public void modificarPuesto(){
		
		try{

			//-------------------------------------------------------------
			//Modificar Puesto
			AdminActionsController ctrl = fac.getAdminActionsController();		
			boolean result = false;
			BusinessPuesto puestoModificar = new BusinessPuesto("PuestoModificar", "","CERRADO", 999999);
			ctrlDaoPuesto.crearPuesto(puestoModificar);
			
			JSONPuesto puesto3 = new JSONPuesto();	
			puesto3.setNombreMaquina("PuestoModificar");
			puesto3.setNumeroPuesto(null);
			puesto3.setUsuarioId("Guzaman");
			puesto3.setEstado("ATENDIENDO");
			
			ctrl.modificarPuesto(puesto3);
			
			BusinessPuesto puestoModificado = ctrlDaoPuesto.obtenerPuesto("PuestoModificar");
			if(puestoModificado.getUsuarioId().equals("Guzaman")&& puestoModificado.getEstado() == EstadoPuesto.ATENDIENDO){
				result = true;
			}
			
			assertEquals(result,true); 
			

		}catch(Exception e){
			System.out.println(e.getMessage());
		}		

	}

	
	//--------------------------------------------------------------------------------
	
	@Test
	public void llamarNumeroTesteo(){
		

	}
	
	@Test
	public void atrasarNumeroTesteo(){
		

				
		
	}
	@Test
	public void pausarNumeroTesteo(){
		
		
	
		
	}
	
}
