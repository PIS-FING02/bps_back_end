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
	
	//Test ABM Puesto -
	
	DAOPuestoController ctrlDaoPuesto = new DAOPuestoController();
	AdminActionsController adminController = AdminActionsController.getInstance();
	Factory fac = Factory.getInstance();
	
	
/*
	public void puestoCrear(){
		
		//Crear Puesto
		try{
			AdminActionsController ctrl = fac.getAdminActionsController();
			JSONPuesto puesto = new JSONPuesto();
			
			//Funcionalidades del puesto basicas
			
			puesto.setNombreMaquina("NombMaq44");
			puesto.setNumeroPuesto(4);
			puesto.setUsuarioId("test");
			puesto.setEstado("CERRADO");
			
			
			ctrl.altaPuesto(puesto);
			
			boolean result = false;
			BusinessPuesto puestoBase = ctrlDaoPuesto.obtenerPuesto("NombMaq44");
			
			if(puestoBase.getNombreMaquina().equals("NombMaq44") && puestoBase.getNumeroPuesto().equals(2) && puestoBase.getEstado() == EstadoPuesto.CERRADO && puestoBase.getUsuarioId() == "test"){
				result = true;
			}
			
			assertEquals(result,true); 
			System.out.println("crear");
						
		}catch(Exception e){
			
			System.out.println(e.getMessage());
				
		}
	
		
		
		
	}
	*/
	//--------------------------------------------------------------------------------
	
	//@Test
	/*public void eliminarPuesto(){
		
		//Eliminar Puesto
		try{
			AdminActionsController ctrl = fac.getAdminActionsController();
			BusinessPuesto puestoEliminar2 = new BusinessPuesto("PuestoEliminar12345", "","CERRADO", 34343);
			
			JSONPuesto puesto = new JSONPuesto();
			
			puesto.setNombreMaquina("PuestoEliminar12345");
			puesto.setNumeroPuesto(0);
			puesto.setUsuarioId("");
			puesto.setEstado("ATENDIENDO"); 
			ctrlDaoPuesto.crearPuesto(puestoEliminar2);
			
			
			ctrl.bajaPuesto(puesto);
			boolean result = true;
			
			List<BusinessPuesto> puestos = ctrlDaoPuesto.listarPuestos();
			
			for( BusinessPuesto thisPuesto : puestos){
				if(thisPuesto.getNombreMaquina().equals("PuestoEliminar1234")){
					result = false;
					break;
				}
			}
			
			System.out.println("termino el eliminar puesto");
			assertEquals(result,true); 
		
		}catch(Exception e){
			System.out.println(e.getMessage());
		}

		
	}
*/
	@Test
	public void modificarPuesto(){
		
		try{

			//-------------------------------------------------------------
			//Modificar Puesto
			AdminActionsController ctrl = fac.getAdminActionsController();		
			boolean result = false;
			BusinessPuesto puestoModificar = new BusinessPuesto("PuestoModificar121212123", "","CERRADO", 32);
			ctrlDaoPuesto.crearPuesto(puestoModificar);
			
			JSONPuesto puesto3 = new JSONPuesto();	
			puesto3.setNombreMaquina("PuestoModificar121212123");
			puesto3.setNumeroPuesto(6);
			puesto3.setUsuarioId("Guzaman");
			puesto3.setEstado("DISPONIBLE");
			
			ctrl.modificarPuesto(puesto3);
			
			BusinessPuesto puestoModificado = ctrlDaoPuesto.obtenerPuesto("PuestoModificar121212123");
			if(puestoModificado.getUsuarioId().equals("Guzaman")&& puestoModificado.getEstado() == EstadoPuesto.ATENDIENDO){
				result = true;
			}
			
			assertEquals(result,true); 
			System.out.println("termino el modificar puesto");

		}catch(Exception e){
			System.out.println(e.getMessage());
		}		

	}
/*
	@Test
	public void listarPuestos(){
		
		try{

			//-------------------------------------------------------------
			//Modificar Puesto
			AdminActionsController ctrl = fac.getAdminActionsController();		
			boolean result = false;
			
			List<JSONPuesto> puestosmios = ctrl.listarPuestos(null);

			List<BusinessPuesto> puestos = ctrlDaoPuesto.listarPuestos();
			
			for( BusinessPuesto thisPuesto : puestos){
				if(thisPuesto.getNombreMaquina().equals("PuestoEliminar1234")){
					result = false;
					break;
				}
			}
			
			assertEquals(result,true); 
			System.out.println("termino el modificar puesto");

		}catch(Exception e){
			System.out.println(e.getMessage());
		}		

	}
	//--------------------------------------------------------------------------------
	*/
	
	
}
