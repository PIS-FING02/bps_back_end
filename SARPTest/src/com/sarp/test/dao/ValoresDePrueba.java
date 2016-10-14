package com.sarp.test.dao;


import java.util.GregorianCalendar;
import java.util.List;

import org.eclipse.persistence.exceptions.OptimisticLockException;

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
				BusinessTramite t = new BusinessTramite();
				t.setNombre("Tramite " + i);
				ctrlTramite.crearTramite(t);
				
				BusinessSector s = new BusinessSector();
				String id = Integer.toString(i);
				s.setSectorId(id);
				s.setNombre("Sector " + id);
				s.setRuta("ruta de ejemplo " + id);			
				ctrlSector.crearSector(s);
				
				
				/*BusinessDisplay d = new BusinessDisplay();
				d.setIdDisplay("C:/dir/dis" + id + ".txt");
				ctrlDisplay.crearDisplay(d);*/
				
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
				n.setEstado("nuevo");
				n.setExternalId("external");
				n.setPrioridad(5);
				n.setHora(new GregorianCalendar());
				
				ctrlSector.asociarTramiteSector(i+1, Integer.toString(i));
				
				ctrlNumero.crearNumero(n, i+1, Integer.toString(i), dc);
			}
		}
		
		System.out.println("FIN cargar=" + cargar);
	}
	
	
	
}
