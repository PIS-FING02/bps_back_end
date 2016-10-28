package com.sarp.test.dao;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import com.sarp.classes.BusinessDatoComplementario;
import com.sarp.classes.BusinessDisplay;
import com.sarp.classes.BusinessMetricasPuesto;
import com.sarp.classes.BusinessNumero;
import com.sarp.classes.BusinessNumeroTramite;
import com.sarp.classes.BusinessPuesto;
import com.sarp.classes.BusinessSector;
import com.sarp.classes.BusinessTramite;
import com.sarp.dao.controllers.DAODisplayController;
import com.sarp.dao.controllers.DAONumeroController;
import com.sarp.dao.controllers.DAOPuestoController;
import com.sarp.dao.controllers.DAOSectorController;
import com.sarp.dao.controllers.DAOTramiteController;
import com.sarp.enumerados.EstadoNumero;
import com.sarp.enumerados.EstadoPuesto;

public class ValoresDePrueba {

	public static void main(String[] args) throws Exception{
		DAOTramiteController ctrlTramite = new DAOTramiteController();
		DAODisplayController ctrlDisplay = new DAODisplayController();
		DAOSectorController ctrlSector = new DAOSectorController();
		DAONumeroController ctrlNumero = new DAONumeroController();
		DAOPuestoController ctrlPuesto = new DAOPuestoController();
		
		boolean cargar = false;
		boolean cargar2 = true;
		
		/*VALORES DE PRUEBA*/
		if(cargar){
			String ci = "0";
			for(int i = 0; i < 10; i++){
				System.out.println(Integer.toString(i));
				BusinessTramite t = new BusinessTramite();
				t.setNombre("Tramite " + i);
				t.setCodigo("CodigoTramite"+i);
				ctrlTramite.crearTramite(t);
				
				BusinessSector s = new BusinessSector();
				String id = Integer.toString(i);
				s.setSectorId(id);
				s.setNombre("Sector " + id);
				s.setRuta("ruta de ejemplo " + id);			
				ctrlSector.crearSector(s);
				
				
				BusinessDisplay d = new BusinessDisplay();
				d.setIdDisplay("iddisplay" + id);
				ctrlDisplay.crearDisplay(d);
				
				BusinessPuesto p = new BusinessPuesto();
				p.setNombreMaquina("NombreMaquina" + i);
				p.setUsuarioId("usuario" + i);
				p.setNumeroPuesto(i);
				ctrlPuesto.crearPuesto(p);
				
				ci = ci + i;
				BusinessDatoComplementario dc = new BusinessDatoComplementario();
				dc.setDocIdentidad(ci);
				dc.setNombreCompleto("Pepito Perez");
				dc.setTipo_doc("cedula");
				
				BusinessNumero n = new BusinessNumero();
				//n.setEstado();
				n.setExternalId("external");
				n.setPrioridad(5);
				n.setHora(new GregorianCalendar());
				
				ctrlSector.asociarTramiteSector("CodigoTramite"+i, Integer.toString(i));	
				n.setCodTramite("CodigoTramite"+i);
				n.setCodSector(Integer.toString(i));
				int num = ctrlNumero.crearNumero(n, dc);
				
				ctrlSector.asociarDisplaySector(id, "iddisplay"+id);
				ctrlSector.asociarSectorPuesto(id, "NombreMaquina" + i);
				ctrlTramite.asociarTramitePuesto("CodigoTramite"+i, "NombreMaquina" + i);
				ctrlPuesto.asociarNumeroPuesto("NombreMaquina" + i, num);
				ctrlNumero.asociarNumeroTramite("CodigoTramite"+i, i+1, "resultado"+i);
			}
		}
		
		if(cargar2){
			//Creo Displays
			System.out.println("CREO DISPLAYS");
			for(int i = 0; i < 8; i++){
				BusinessDisplay d = new BusinessDisplay();
				d.setIdDisplay("idDisplay0" + i);
				ctrlDisplay.crearDisplay(d);
				System.out.println("d: " + Integer.toString(i));
			}
			//Asocio Displays a Sectores
			System.out.println("ASOCIO DISPLAYS");
			ctrlSector.asociarDisplaySector("MVD_CER", "idDisplay01");
			ctrlSector.asociarDisplaySector("MVD_CRRN", "idDisplay01");
			ctrlSector.asociarDisplaySector("MVD_FIS", "idDisplay01");
			
			ctrlSector.asociarDisplaySector("MVD_PR_PS", "idDisplay02");
			ctrlSector.asociarDisplaySector("MVD_PR_PE", "idDisplay02");
			
			ctrlSector.asociarDisplaySector("INT_MAL_ATYR_CE", "idDisplay03");
			ctrlSector.asociarDisplaySector("INT_MAL_ATYR_FS", "idDisplay03");
			ctrlSector.asociarDisplaySector("INT_MAL_PR_PS", "idDisplay03");
			
			ctrlSector.asociarDisplaySector("INT_SAL_ATYR_CE", "idDisplay04");
			ctrlSector.asociarDisplaySector("INT_SAL_ATYR_CR", "idDisplay04");
			ctrlSector.asociarDisplaySector("INT_SAL_PR_PS", "idDisplay04");
			ctrlSector.asociarDisplaySector("INT_SAL_PR_PE", "idDisplay04");
			
			ctrlSector.asociarDisplaySector("MVD_CER", "idDisplay06");
			ctrlSector.asociarDisplaySector("MVD_CRRN", "idDisplay06");
			ctrlSector.asociarDisplaySector("MVD_FIS", "idDisplay06");
			ctrlSector.asociarDisplaySector("MVD_PR_PS", "idDisplay06");
			ctrlSector.asociarDisplaySector("MVD_PR_PE", "idDisplay06");
			
			ctrlSector.asociarDisplaySector("MVD_CER", "idDisplay07");
			ctrlSector.asociarDisplaySector("MVD_CRRN", "idDisplay07");
			ctrlSector.asociarDisplaySector("MVD_FIS", "idDisplay07");
			ctrlSector.asociarDisplaySector("MVD_PR_PS", "idDisplay07");
			ctrlSector.asociarDisplaySector("MVD_PR_PE", "idDisplay07");
			
			//Creo Tramites
			System.out.println("CREO TRAMITES");
			BusinessTramite t = new BusinessTramite();
			String tid = "NoTiene";
			t.setCodigo(tid);
			t.setNombre(tid + "_nombre");
			ctrlTramite.crearTramite(t);
			tid = "ConsultaA";
			t.setCodigo(tid);
			t.setNombre(tid + "_nombre");
			ctrlTramite.crearTramite(t);
			tid = "Certificados";
			t.setCodigo(tid);
			t.setNombre(tid + "_nombre");
			ctrlTramite.crearTramite(t);
			tid = "Fiscalizaciones";
			t.setCodigo(tid);
			t.setNombre(tid + "_nombre");
			ctrlTramite.crearTramite(t);
			tid = "ConexionesRemotas";
			t.setCodigo(tid);
			t.setNombre(tid + "_nombre");
			ctrlTramite.crearTramite(t);
			tid = "Prestaciones";
			t.setCodigo(tid);
			t.setNombre(tid + "_nombre");
			ctrlTramite.crearTramite(t);
			tid = "Sociales";
			t.setCodigo(tid);
			t.setNombre(tid + "_nombre");
			ctrlTramite.crearTramite(t);
			tid = "Economicas";
			t.setCodigo(tid);
			t.setNombre(tid + "_nombre");
			ctrlTramite.crearTramite(t);
			tid = "General";
			t.setCodigo(tid);
			t.setNombre(tid + "_nombre");
			ctrlTramite.crearTramite(t);
			
			//Asocio Tramites a Sectores
			System.out.println("ASOCIO SECTORES");
			ctrlSector.asociarTramiteSector("ConsultaA", "MVD_CER");
			ctrlSector.asociarTramiteSector("ConsultaA", "MVD_CRRN");
			ctrlSector.asociarTramiteSector("ConsultaA", "MVD_FIS");
			ctrlSector.asociarTramiteSector("ConsultaA", "INT_MAL_ATYR_CE");
			ctrlSector.asociarTramiteSector("ConsultaA", "INT_MAL_ATYR_FS");
			
			ctrlSector.asociarTramiteSector("Certificados", "MVD_CER");
			ctrlSector.asociarTramiteSector("Certificados", "INT_MAL_ATYR_CE");
			ctrlSector.asociarTramiteSector("Certificados", "INT_SAL_ATYR_CE");
			
			ctrlSector.asociarTramiteSector("Fiscalizaciones", "MVD_FIS");
			ctrlSector.asociarTramiteSector("Fiscalizaciones", "INT_MAL_ATYR_FS");
			
			ctrlSector.asociarTramiteSector("ConexionesRemotas", "MVD_CRRN");
			ctrlSector.asociarTramiteSector("ConexionesRemotas", "INT_SAL_ATYR_CR");
			
			ctrlSector.asociarTramiteSector("Prestaciones", "MVD_PR_PS");
			ctrlSector.asociarTramiteSector("Prestaciones", "MVD_PR_PE");
			ctrlSector.asociarTramiteSector("Prestaciones", "INT_MAL_PR_PS");
			ctrlSector.asociarTramiteSector("Prestaciones", "INT_SAL_PR_PS");
			ctrlSector.asociarTramiteSector("Prestaciones", "INT_SAL_PR_PE");
			
			ctrlSector.asociarTramiteSector("Sociales", "MVD_PR_PS");
			ctrlSector.asociarTramiteSector("Sociales", "INT_SAL_PR_PS");
			
			ctrlSector.asociarTramiteSector("Economicas", "MVD_PR_PE");
			ctrlSector.asociarTramiteSector("Economicas", "INT_SAL_PR_PE");
								
			ctrlSector.asociarTramiteSector("General", "MVD_CER");
			ctrlSector.asociarTramiteSector("General", "MVD_CRRN");
			ctrlSector.asociarTramiteSector("General", "MVD_FIS");
			ctrlSector.asociarTramiteSector("General", "MVD_PR_PS");
			ctrlSector.asociarTramiteSector("General", "MVD_PR_PE");
			ctrlSector.asociarTramiteSector("General", "INT_MAL_ATYR_CE");
			ctrlSector.asociarTramiteSector("General", "INT_MAL_ATYR_FS");
			ctrlSector.asociarTramiteSector("General", "INT_MAL_PR_PS");
			ctrlSector.asociarTramiteSector("General", "INT_SAL_ATYR_CE");
			ctrlSector.asociarTramiteSector("General", "INT_SAL_ATYR_CR");
			ctrlSector.asociarTramiteSector("General", "INT_SAL_PR_PS");
			ctrlSector.asociarTramiteSector("General", "INT_SAL_PR_PE");
			
			//Creo Puestos
			System.out.println("CREO PUESTOS");
			for(int i = 0; i < 11; i++){
				BusinessPuesto p = new BusinessPuesto();
				p.setNombreMaquina("maq" + i);
				ctrlPuesto.crearPuesto(p);
			}
			//Asocio Puestos a Sectores
			System.out.println("ASOCIO PUESTOS");			
			ctrlSector.asociarSectorPuesto("MVD_CER", "maq1");
			ctrlSector.asociarSectorPuesto("MVD_CRRN", "maq1");
			ctrlSector.asociarSectorPuesto("MVD_FIS", "maq1");
			
			ctrlSector.asociarSectorPuesto("MVD_PR_PS", "maq2");
			ctrlSector.asociarSectorPuesto("MVD_PR_PE", "maq2");
			
			ctrlSector.asociarSectorPuesto("INT_MAL_ATYR_CE", "maq3");
			ctrlSector.asociarSectorPuesto("INT_MAL_ATYR_FS", "maq3");
			ctrlSector.asociarSectorPuesto("INT_MAL_PR_PS", "maq3");
			
			ctrlSector.asociarSectorPuesto("INT_SAL_ATYR_CE", "maq4");
			ctrlSector.asociarSectorPuesto("INT_SAL_ATYR_CR", "maq4");
			ctrlSector.asociarSectorPuesto("INT_SAL_PR_PS", "maq4");
			ctrlSector.asociarSectorPuesto("INT_SAL_PR_PE", "maq4");						
			
			ctrlSector.asociarSectorPuesto("MVD_PR_PS", "maq6");
			ctrlSector.asociarSectorPuesto("MVD_PR_PE", "maq6");
			ctrlSector.asociarSectorPuesto("INT_MAL_PR_PS", "maq6");
			ctrlSector.asociarSectorPuesto("INT_SAL_PR_PS", "maq6");
			ctrlSector.asociarSectorPuesto("INT_SAL_PR_PE", "maq6");
			
			ctrlSector.asociarSectorPuesto("MVD_CER", "maq7");
			ctrlSector.asociarSectorPuesto("MVD_CRRN", "maq7");
			ctrlSector.asociarSectorPuesto("MVD_FIS", "maq7");
			ctrlSector.asociarSectorPuesto("INT_MAL_ATYR_CE", "maq7");
			ctrlSector.asociarSectorPuesto("INT_MAL_ATYR_FS", "maq7");
			ctrlSector.asociarSectorPuesto("INT_SAL_ATYR_CE", "maq7");
			ctrlSector.asociarSectorPuesto("INT_SAL_ATYR_CR", "maq7");
			
			ctrlSector.asociarSectorPuesto("MVD_CER", "maq8");
			ctrlSector.asociarSectorPuesto("INT_MAL_ATYR_CE", "maq8");
			ctrlSector.asociarSectorPuesto("INT_SAL_ATYR_CE", "maq8");
			
			ctrlSector.asociarSectorPuesto("MVD_PR_PS", "maq9");
			ctrlSector.asociarSectorPuesto("INT_SAL_PR_PS", "maq9");
			
			ctrlSector.asociarSectorPuesto("MVD_CER", "maq10");
			ctrlSector.asociarSectorPuesto("MVD_CRRN", "maq10");
			ctrlSector.asociarSectorPuesto("MVD_FIS", "maq10");
			ctrlSector.asociarSectorPuesto("MVD_PR_PS", "maq10");
			ctrlSector.asociarSectorPuesto("MVD_PR_PE", "maq10");
			ctrlSector.asociarSectorPuesto("INT_MAL_ATYR_CE", "maq10");
			ctrlSector.asociarSectorPuesto("INT_MAL_ATYR_FS", "maq10");
			ctrlSector.asociarSectorPuesto("INT_MAL_PR_PS", "maq10");
			ctrlSector.asociarSectorPuesto("INT_SAL_ATYR_CE", "maq10");
			ctrlSector.asociarSectorPuesto("INT_SAL_ATYR_CR", "maq10");
			ctrlSector.asociarSectorPuesto("INT_SAL_PR_PS", "maq10");
			ctrlSector.asociarSectorPuesto("INT_SAL_PR_PE", "maq10");						
		}
		
		
		System.out.println("\nFIN");
		System.out.println("\ncargar=" + cargar);
		System.out.println("\ncargar2=" + cargar2);
	}
	
	
	
}
