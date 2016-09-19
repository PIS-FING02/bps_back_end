package com.sarp.dao.controllers;

import java.util.LinkedList;
import java.util.List;

import com.sarp.classes.BusinessSector;
import com.sarp.classes.BusinessTramite;
import com.sarp.dao.factory.DAOFactory;
import com.sarp.dao.model.Sector;
import com.sarp.dao.model.Tramite;
import com.sarp.dao.repository.DAOSector;
import com.sarp.dao.repository.DAOTramite;

//import org.dao.repository.NumeroRepository;

public class DAOTramiteController {
	
	public void crearTramite(int codigoSector, String nom) throws Exception{
		System.out.println("hola desde crearTramite");
		DAOFactory factory = DAOFactory.getInstance();
		DAOTramite tramiteRepository = factory.getTramiteRepository();
		DAOSector sectorRepository = factory.getSectorRepository();
		System.out.println("hola desde crearTramite1");
		Sector s = sectorRepository.obtenerSector(codigoSector);
		System.out.println("hola desde crearTramite2" + s.getNombre());
		tramiteRepository.crearTramite(s, nom);
	}
	public List<BusinessTramite> listarTramites() {
		DAOFactory factory = DAOFactory.getInstance();
		DAOTramite tramiteRepository = factory.getTramiteRepository();
		List<Tramite> list = tramiteRepository.listarTramites();
		List<BusinessTramite> ret = new LinkedList<BusinessTramite>();
		for (Tramite t : list){
			BusinessTramite dt = new BusinessTramite(t.getCodigo(), t.getNombre());
			ret.add(dt);
		}
		
		return ret;
	}

}
