package com.sarp.test.dao;


import java.util.GregorianCalendar;
import com.sarp.classes.BusinessDatoComplementario;
import com.sarp.classes.BusinessDisplay;
import com.sarp.classes.BusinessNumero;
import com.sarp.classes.BusinessPuesto;
import com.sarp.classes.BusinessSector;
import com.sarp.classes.BusinessTramite;
import com.sarp.dao.controllers.DAODisplayController;
import com.sarp.dao.controllers.DAONumeroController;
import com.sarp.dao.controllers.DAOPuestoController;
import com.sarp.dao.controllers.DAOSectorController;
import com.sarp.dao.controllers.DAOTramiteController;

public class ValoresDePrueba {

	public static void main(String[] args) throws Exception{
		DAOTramiteController ctrlTramite = new DAOTramiteController();
		DAODisplayController ctrlDisplay = new DAODisplayController();
		DAOSectorController ctrlSector = new DAOSectorController();
		DAONumeroController ctrlNumero = new DAONumeroController();
		DAOPuestoController ctrlPuesto = new DAOPuestoController();
		
		boolean cargar = false;
		
		/*VALORES DE PRUEBA*/
		if(cargar){
			String ci = "0";
			for(int i = 0; i < 10; i++){
				System.out.println(Integer.toString(i));
				BusinessTramite t = new BusinessTramite();
				t.setNombre("Tramite " + i);
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
				
				ctrlSector.asociarTramiteSector(i+1, Integer.toString(i));			
				int num = ctrlNumero.crearNumero(n, i+1, Integer.toString(i), dc);
				
				ctrlSector.asociarDisplaySector(id, "iddisplay"+id);
				ctrlSector.asociarSectorPuesto(id, "NombreMaquina" + i);
				ctrlTramite.asociarTramitePuesto(i+1, "NombreMaquina" + i);
				ctrlPuesto.asociarNumeroPuesto("NombreMaquina" + i, num);
			}
		}
		
		
		BusinessNumero n = ctrlPuesto.obtenerNumeroActualPuesto("NombreMaquina0");
		n.setExternalId("cambia");
		ctrlNumero.modificarNumero(n);
		System.out.println("FIN cargar=" + cargar);
	}
	
	
	
}
