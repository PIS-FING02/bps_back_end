package com.sarp.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.sarp.classes.BusinessPuesto;
import com.sarp.controllers.AdminActionsController;
import com.sarp.dao.controllers.DAOTramiteController;
import com.sarp.enumerados.EstadoPuesto;
import com.sarp.factory.Factory;
import com.sarp.json.modeler.JSONPuesto;
import com.sarp.json.modeler.JSONTramite;

public class TramiteTest {
	
	DAOTramiteController ctrlDaoPuesto = new DAOTramiteController();
	AdminActionsController adminController = AdminActionsController.getInstance();
	Factory fac = Factory.getInstance();
	
	@Test
	public void altaTramite(){
		//Alta Tramite
				try{
					//Funcionalidades del puesto basicas
					AdminActionsController ctrl = fac.getAdminActionsController();
					JSONTramite tramite = new JSONTramite();
					ctrl.altaTramite(tramite);
					
					
					boolean result = false;
					//BusinessPuesto puestoBase = ctrlDaoPuesto.obtenerPuesto("Nombre de maquina 1");
					
					//if(puestoBase.getNombreMaquina().equals("Nombre de maquina 1") && puestoBase.getNumeroPuesto().equals(1) && puestoBase.getEstado() == EstadoPuesto.CERRADO && puestoBase.getUsuarioId() == ""){
					//	result = true;
					//}
					
					assertEquals(result,true); 
							
								
				}catch(Exception e){
					
					System.out.println(e.getMessage());
						
				}
		
		
	}
	
	@Test
	public void bajaTramite(){
		

				
		
	}
	
	@Test
	public void modificarTramite(){
		

				
		
	}
}
