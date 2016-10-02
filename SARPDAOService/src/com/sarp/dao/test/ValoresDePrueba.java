package com.sarp.dao.test;


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
		
		/*VALORES DE PRUEBA*/
//		String ci = "0";
//		for(int i = 1; i < 10; i++){
//			BusinessTramite t = new BusinessTramite();
//			t.setNombre("nombre" + 1);
//			ctrlTramite.crearTramite(t);
//			
//			BusinessSector s = new BusinessSector();
//			String id = Integer.toString(i);
//			s.setSectorId(id);
//			s.setNombre("nombre" + id);
//			s.setRuta("ruta" + id);			
//			ctrlSector.crearSector(s);
//			
//			BusinessDisplay d = new BusinessDisplay();
//			d.setRutaArchivo("ruta" + i);
//			ctrlDisplay.crearDisplay(d);
//			
//			BusinessPuesto p = new BusinessPuesto();
//			p.setNombreMaquina("maquina" + i);
//			p.setEstado(EstadoPuesto.DIPONIBLE);
//			p.setUsuarioId("usuario" + i);
//			p.setNumeroPuesto(i);
//			ctrlPuesto.crearPuesto(p);
//			
//			ci = ci + i;
//			BusinessDatoComplementario dc = new BusinessDatoComplementario();
//			dc.setDocIdentidad(ci);
//			dc.setNombreCompleto("Pepito Perez");
//			dc.setTipo_doc("cedula");
//			BusinessNumero n = new BusinessNumero();
//			n.setEstado("nuevo");
//			n.setExternalId("external");
//			n.setPrioridad(5);
//			n.setHora(new GregorianCalendar());
//			ctrlNumero.crearNumero(n, i, dc);
//		}

		//ctrlTramite.desasociarTramiteSector(1, "1");
		
		try{
			BusinessDisplay d = ctrlDisplay.obtenerDisplay(13);		
			ctrlSector.asignarDisplaySector("1", 13);
		}
		catch(Exception ex){
			Throwable t = ex.getCause();
			if(t instanceof org.eclipse.persistence.exceptions.OptimisticLockException)
				System.out.println("Optimistic locking, bitch!");
		}
		
		

		System.out.println("FIN");
	}
	
	
	
}
