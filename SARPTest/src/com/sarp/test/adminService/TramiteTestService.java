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
import com.sarp.json.modeler.JSONPuestoTramite;
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
		   String codigoTramite = "CodigoConsulta";
		   bTram.setCodigo(codigoTramite);
		   ctrlTramite.crearTramite(bTram);
		   // creados en base datos de prueba
		   Factory fac = Factory.getInstance();
		   AdminActionsController ctrl = fac.getAdminActionsController();
		   JSONTramiteSector tramSec = new JSONTramiteSector();
		   JSONSector sec = new JSONSector();
		   sec.setCodigo(s1.getSectorId());
		   JSONTramite tram = new JSONTramite();
		   tram.setCodigo(codigoTramite);
		   tramSec.setSectorId(sec.getCodigo());
		   tramSec.setTramiteId(tram.getCodigo());
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
	   
	   
	   @Test
	   public void asignarTramitepuesto() throws Exception {	
				   
			// creo un sector 
				DAOSectorController ctrlSector = new  DAOSectorController();
				String codigoTest = "92949299789";
				BusinessSector s1 = new BusinessSector();
				s1.setSectorId(codigoTest);
				s1.setRuta("/pcunix666/ban");
				s1.setNombre("Peperoni");
				ctrlSector.crearSector(s1);
			//creo sector2
				String codigoTest2 = "99999999";
				BusinessSector s2 = new BusinessSector();
				s2.setSectorId(codigoTest2);
				s2.setRuta("/pcunix666/ban/2");
				s2.setNombre("Jubilaciones2");
				ctrlSector.crearSector(s2);
								
			//creo tramite
				DAOTramiteController ctrlTramite = new DAOTramiteController();
				BusinessTramite t1 = new BusinessTramite();
				t1.setNombre("Consulta");
				String codigoTramite = "Consulta2";
				t1.setCodigo(codigoTramite);
				ctrlTramite.crearTramite(t1);
			//creo puesto 
				DAOPuestoController ctrlPuesto = new DAOPuestoController();
				BusinessPuesto p1 = new BusinessPuesto();
				String id ="idPuestoJuancho";
				p1.setNombreMaquina(id);
				p1.setEstado(EstadoPuesto.DISPONIBLE);
				ctrlPuesto.crearPuesto(p1);
			   
			//le asingo al tramite al sector
				Factory fac = Factory.getInstance();
				AdminActionsController ctrl = fac.getAdminActionsController();
				JSONTramiteSector tramSec = new JSONTramiteSector();
				 
				JSONSector js1 = new JSONSector();
				js1.setCodigo(s1.getSectorId());
				   
				JSONTramite jt1 = new JSONTramite();
				jt1.setCodigo(codigoTramite);
				   
				tramSec.setSectorId(js1.getCodigo());
				tramSec.setTramiteId(jt1.getCodigo());
				   
				ctrl.asignarTramiteSector(tramSec);
				
			//le asingo puesto sector 
				JSONPuestoSector puestoSec = new JSONPuestoSector();
					
				JSONPuesto jp1 = new JSONPuesto();
				jp1.setNombreMaquina(p1.getNombreMaquina());
						
				puestoSec.setNombreMaquina(jp1.getNombreMaquina());
				puestoSec.setSectorId(js1.getCodigo());
						
				ctrl.asignarPuestoSector(puestoSec);
			   
			/**** Prueba de asignar tramite puesto  ****/
			
			//uso tramite y sector de arriba   
				JSONPuestoTramite puestoTramite = new JSONPuestoTramite();
				
				puestoTramite.setTramiteId(jt1.getCodigo());
				puestoTramite.setNombreMaquina(jp1.getNombreMaquina());
				
			// se asigna tramite a un puesto que tenga un sectro q a su vez tenga el tramite
				ctrl.asignarTramitePuesto(puestoTramite);
				
			// luego de asignar busco en la base los tramites asociados al puesto
			
			//tambien testeo el listar 
				List<JSONTramite> tramitesdepueto = ctrl.listarTramitesPuesto( p1.getNombreMaquina() );
				Boolean isOk = false;
				for (JSONTramite item:tramitesdepueto){
					if (item.getCodigo().equals(codigoTramite)){
						isOk = true;
					}
				}
				assertEquals(isOk,true); 
			// asociar ya asociados
				
				isOk=false;
				try{ 
					ctrl.asignarTramitePuesto(puestoTramite);
				}catch (Exception e){
					String exp = "El puesto de " + puestoTramite.getNombreMaquina() + " y el tramite " + puestoTramite.getTramiteId().toString() + " ya estan asociados";
					isOk = exp.equals(e.getMessage());
					System.out.println(e.getMessage());
				}
				assertEquals(isOk,true); 
				
			// se asigna tramite a un puesto que NO tenga un sectro q a su vez tenga el tramite 
			//creo tramite2
				
				BusinessTramite  t2 = new BusinessTramite();
				t2.setNombre("t2");
				String codigoTramite2 = "CodigoTramiteTest2";
				t2.setCodigo(codigoTramite2);
				ctrlTramite.crearTramite(t2);
			//creo puesto2 
				
				BusinessPuesto p2 = new BusinessPuesto();
				String id2 ="idPuestoJuancho2";
				p2.setNombreMaquina(id2);
				p2.setEstado(EstadoPuesto.DISPONIBLE);
				ctrlPuesto.crearPuesto(p2);
				
			//no se asignan 
				
				JSONPuestoTramite puestoTramite2 = new JSONPuestoTramite();
				puestoTramite2.setNombreMaquina(p2.getNombreMaquina());
			
				puestoTramite2.setTramiteId(codigoTramite2);
				
				JSONPuestoSector puestoSec2= new JSONPuestoSector();
				
				puestoSec2.setSectorId(s2.getSectorId());
				puestoSec2.setNombreMaquina(p2.getNombreMaquina());
				   
				ctrl.asignarPuestoSector(puestoSec2);
				
			// se asigna tramite a un puesto que tenga un sectro q a su vez tenga el tramite
				isOk=false;
				try{ 
					ctrl.asignarTramitePuesto(puestoTramite2);
				}catch (Exception e){
					isOk = "El puesto no tiene un sector que atienda ese tramite".equals(e.getMessage());
					System.out.println(e.getMessage());
				}
				assertEquals(isOk,true); 
				
			
			//se asigna tramite  nulo a un puesto 	
			
				
				JSONPuestoTramite puestoTramite3 = new JSONPuestoTramite();

				puestoTramite3.setNombreMaquina(p2.getNombreMaquina());
	
				isOk=false;
				try{ 
					ctrl.asignarTramitePuesto(puestoTramite3);
				}catch (Exception e){
					isOk = "Debe seleccionar un tramite".equals(e.getMessage());
					System.out.println(e.getMessage());
				}
				assertEquals(isOk,true); 
				
			
			//se asigna tramite a un puesto nulo
				
				JSONPuestoTramite puestoTramite4 = new JSONPuestoTramite();

				puestoTramite4.setTramiteId(codigoTramite2);
	
				isOk=false;
				try{ 
					ctrl.asignarTramitePuesto(puestoTramite4);
				}catch (Exception e){
					isOk = "Debe seleccionar un puesto".equals(e.getMessage());
					System.out.println(e.getMessage());
					
				}
				assertEquals(isOk,true); 
			//se asigna tramite nulo a puesto nulo
				
				JSONPuestoTramite puestoTramite5 = new JSONPuestoTramite();
				
				isOk=false;
				try{ 
					ctrl.asignarTramitePuesto(puestoTramite5);
				}catch (Exception e){
					isOk = "Debe seleccionar un puesto".equals(e.getMessage());
					System.out.println(e.getMessage());
				}
				assertEquals(isOk,true); 
			//se asigna tramite inexitete a puesto
				JSONPuestoTramite puestoTramite6 = new JSONPuestoTramite();
			//se asigna  puesto a sector1
				
				
				ctrlTramite.eliminarTramite(codigoTramite2);
				puestoTramite6.setTramiteId(codigoTramite2);/*no existe porque lo acabo de borrar*/
				puestoTramite6.setNombreMaquina(p2.getNombreMaquina());
				
				
				
				isOk=false;
				try{ 
					ctrl.asignarTramitePuesto(puestoTramite6);
				}catch (Exception e){
					isOk = e.getMessage().indexOf("No existe el Tramite") > -1;	
					System.out.println(e.getMessage());
				}
				assertEquals(isOk,true); 
			//vuelvo a crear tramite 2
				
				t2 = new BusinessTramite();
				t2.setNombre("t2");
				codigoTramite2 = "CodigoTramiteTest3";
				t2.setCodigo(codigoTramite2);
				ctrlTramite.crearTramite(t2);
				
			//se asigna tramite a puesto inexitete
				
				
				JSONPuestoTramite puestoTramite7 = new JSONPuestoTramite();
				ctrlPuesto.eliminarPuesto(p2.getNombreMaquina()); 
				puestoTramite7.setNombreMaquina(p2.getNombreMaquina());/*no existe porque lo acabo de borrar*/
				isOk=false;
				try{ 
					ctrl.asignarTramitePuesto(puestoTramite7);
				puestoTramite7.setTramiteId(codigoTramite2);
				}catch (Exception e){
					isOk = e.getMessage().indexOf("No existe el Puesto") > -1;
					System.out.println(e.getMessage());
				}
				assertEquals(isOk,true); 
				
				
				
				ctrlTramite.eliminarTramite(codigoTramite2);
				ctrlSector.eliminarSector(s2.getSectorId());
				ctrlSector.eliminarSector(s1.getSectorId());
				ctrlTramite.eliminarTramite(codigoTramite);
				ctrlPuesto.eliminarPuesto(p1.getNombreMaquina());   
		}
}
