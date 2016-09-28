package com.sarp.dao.test;


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
		Integer ci = 0;
		for(int i = 1; i < 10; i++){
			BusinessTramite t = new BusinessTramite();
			t.setNombre("nombre" + 1);
			ctrlTramite.crearTramite(t);
			
			BusinessSector s = new BusinessSector();
			String id = Integer.toString(i);
			s.setSectorId(id);
			s.setNombre("nombre" + id);
			s.setRuta("ruta" + id);			
			ctrlSector.crearSector(s);
			
			BusinessDisplay d = new BusinessDisplay();
			d.setRutaArchivo("ruta" + i);
			ctrlDisplay.crearDisplay(d);
			
			BusinessPuesto p = new BusinessPuesto();
			p.setNombreMaquina("maquina" + i);
			p.setEstado(EstadoPuesto.DIPONIBLE);
			p.setUsuarioId("usuario" + i);
			ctrlPuesto.crearPuesto(p);
			
			ci = ci + i;
			BusinessDatoComplementario dc = new BusinessDatoComplementario();
			dc.setDocIdentidad(ci);
			dc.setNombreCompleto("Pepito Perez");
			dc.setTipo_doc("cedula");
			BusinessNumero n = new BusinessNumero();
			n.setCodigoTramite(i);
			n.setEstado("nuevo");
			n.setExternalId("external");
			n.setPrioridad(5);
			ctrlNumero.crearNumero(n, dc);
		}
//		
		/* CREAR TRAMITE */
//		BusinessTramite t = new BusinessTramite();
//		int c = ctrlTramite.crearTramite(t);
		


		
		/*CREAR PUESTO */
////	BusinessPuesto p = new BusinessPuesto();
////	p.setNombreMaquina("maquina9");
////	ctrlPuesto.crearPuesto(p);
		
		/*CREAR NUMERO */
//		BusinessDatoComplementario dc = new BusinessDatoComplementario();
//		dc.setDocIdentidad(4543543);
//		dc.setNombreCompleto("Pepito Perez");
//		dc.setTipo_doc("cedula");
//		BusinessNumero n = new BusinessNumero();
//		n.setCodigoTramite(12);
//		n.setDatoComplementario(dc);
//		n.setEstado("nuevo");
//		n.setExternalId("external");
//		n.setPrioridad(5);
//		ctrlNumero.crearNumero(n);
		
		/*Obtener DATOSCOMPLEMENTARIOS de un NUMERO */
//		BusinessNumero n = ctrlNumero.obtenerNumero(5);
//		BusinessDatoComplementario dc = n.getDatoComplementario();
//		System.out.println(dc.getNombreCompleto() + " " + dc.getDocIdentidad());
		
		/*Obtener DISPLAY de un SECTOR */
//		BusinessDisplay d = ctrlSector.obtenerDisplaySector(1);	
//		System.out.println(d.getCodigo() + " " + d.getRutaArchivo());
		
		/*Listar TRAMITES de un SECTOR */
////	List<BusinessTramite> res =  ctrlSector.selectTramitesSector(2);
		
		/*Listar TRAMITES de un PUESTO */
///		List<BusinessTramite> a = ctrlPuesto.obtenerTramitesPuesto("maquina2");

		/*Asociar TRAMITE y SECTOR*/
////	ctrlTramite.asociarTramiteSector(c, 2);

		/*Asociar TRAMITE y PUESTO*/
////	ctrlTramite.asociarTramitePuesto(c, p.getNombreMaquina());
	
		/*Asociar DISPLAY a un SECTOR */
		//ctrlSector.asignarDisplay(1, 7);
		
		/*Asociar SECTOR y PUESTO */
		//ctrlSector.asociarSectorPuesto(4, "maquina3");
		
		//ctrlNumero.asociarNumeroPuesto(8, "maquina9");
//		List<BusinessPuesto> a = ctrlNumero.obtenerPuestosNumero(9);
//		List<BusinessNumero> b = ctrlPuesto.obtenerNumerosPuesto("maquina9");
//		List<BusinessNumero> a = ctrlNumero.listarNumerosDelDia();
		System.out.println("FIN");
	}
	
	
	
}
