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
		
		boolean cargar = true;
		
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
		
		System.out.println("FIN cargar=" + cargar);
	}
	
	
	
}
