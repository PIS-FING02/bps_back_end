package com.sarp.tests;

import org.junit.Test;

import com.sarp.classes.BusinessDatoComplementario;
import com.sarp.classes.BusinessDisplay;
import com.sarp.classes.BusinessNumero;
import com.sarp.classes.BusinessPuesto;
import com.sarp.classes.BusinessSector;
import com.sarp.controllers.AdminActionsController;
import com.sarp.controllers.AttentionsController;
import com.sarp.controllers.NumberController;
import com.sarp.dao.controllers.DAODisplayController;
import com.sarp.dao.controllers.DAONumeroController;
import com.sarp.dao.controllers.DAOPuestoController;
import com.sarp.dao.controllers.DAOSectorController;
import com.sarp.dao.controllers.DAOTramiteController;
import com.sarp.dao.factory.DAOServiceFactory;
import com.sarp.enumerados.EstadoPuesto;
import com.sarp.factory.Factory;
import com.sarp.json.modeler.JSONNumero;
import com.sarp.json.modeler.JSONPuesto;
import com.sarp.json.modeler.JSONSector;

import org.junit.Ignore;
import static org.junit.Assert.*;

import java.util.GregorianCalendar;
import java.util.List;

public class desviarNumeroTest {
	
	DAOPuestoController ctrlDaoPuesto = new DAOPuestoController();
	AttentionsController adminController = AttentionsController.getInstance();
	Factory fac = Factory.getInstance();
	
	//@Test
		public void listarSectoresDesvio(){
			
			//Eliminar Puesto
			try{
				AttentionsController ctrl = fac.getAttentionsController();
				
				String sectorConfigurado = "BPS";//sector configurado
				List<JSONSector> sectoresDesvio = ctrl.obtenerSectoresDesvio(sectorConfigurado);
				
				String sectorNoConfigurado = "MVD";
				List<JSONSector> sectoresDesvioNull = ctrl.obtenerSectoresDesvio(sectorNoConfigurado);
				
				Integer a = 3;
			
			}catch(Exception e){
				System.out.println(e.getMessage());
			}

			
		}
	
	@Test
	public void desviarNumero(){
		
		//Eliminar Puesto
		try{
			
			AttentionsController ctrl = fac.getAttentionsController();
			NumberController ctrlNum = fac.getNumberController();
			DAOServiceFactory daoFac = DAOServiceFactory.getInstance();
			DAONumeroController controladorNumero = daoFac.getDAONumeroController();
			
			//Sector y nombre maquina
			String sectorConfigurado = "BPS";
			String nombreMaquinaPuesto ="maq1";
			
			//Configuracion de numero nuevo
			BusinessNumero numeroADesviar = new BusinessNumero();
			numeroADesviar.setExternalId("EL EXTERNAL ID");
			numeroADesviar.setHora(new GregorianCalendar());
			//numeroADesviar.setCodTramite(1);
			numeroADesviar.setCodSector(sectorConfigurado);
			numeroADesviar.setPrioridad(1);
			BusinessDatoComplementario bDatosComplementario = null;
			
			//Integer idNumero = controladorNumero.crearNumero(numeroADesviar, numeroADesviar.getCodTramite(), numeroADesviar.getCodSector(),bDatosComplementario );
	
			//asociacion a puesto
			BusinessPuesto puestoSend = ctrlDaoPuesto.obtenerPuesto(nombreMaquinaPuesto);
			puestoSend.setEstado(EstadoPuesto.LLAMANDO);
			ctrlDaoPuesto.modificarPuesto(puestoSend);
			//ctrlDaoPuesto.asociarNumeroPuestoActual(puestoSend.getNombreMaquina(), idNumero);
			
			
			//luego de solicitado lo desvio
			
			String sectorADesviarOK = "MVD_CER";
			String sectorADesviarNOT = "MVD_CER";
			
			//ctrl.desviarNumero(nombreMaquinaPuesto, sectorADesviarOK);
			
			Integer a =3;
		
		}catch(Exception e){
			System.out.println(e.getMessage());
		}

		
	}
}
