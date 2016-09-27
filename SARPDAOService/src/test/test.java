package test;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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
import com.sarp.dao.factory.DAOServiceFactory;




public class test {

	public static void main(String[] args) throws Exception{
		DAOTramiteController ctrlTramite = new DAOTramiteController();
		DAODisplayController ctrlDisplay = new DAODisplayController();
		DAOSectorController ctrlSector = new DAOSectorController();
		DAONumeroController ctrlNumero = new DAONumeroController();
		DAOPuestoController ctrlPuesto = new DAOPuestoController();
		
//		BusinessTramite t = new BusinessTramite();
//		int c = ctrlTramite.crearTramite(t);
////		
//		BusinessSector s = new BusinessSector();
//		int id = 2;
//		s.setSectorId(id);
//		s.setNombre("nombre" + id);
//		s.setRuta("ruta" + id);
//		ctrlSector.crearSector(s);
////		
//		BusinessTramite t = new BusinessTramite();
//		int c = ctrlTramite.crearTramite(t);
//		
//		ctrlTramite.asociarTramiteSector(c, 2);
//		BusinessPuesto p = new BusinessPuesto();
//		p.setNombreMaquina("maquina9");
//		ctrlPuesto.crearPuesto(p);
//		ctrlTramite.asociarTramitePuesto(c, p.getNombreMaquina());
//		
//		List<BusinessTramite> res =  ctrlSector.selectTramitesSector(2);

		BusinessDisplay d = new BusinessDisplay();
		d.setRutaArchivo("ruta1");
		int c = ctrlDisplay.crearDisplay(d);
		
		System.out.println("FIN");
	}
	
	
	
}
